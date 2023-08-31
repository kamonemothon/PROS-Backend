package com.nemo.neplan.controller;

import com.nemo.neplan.model.Plan;
import com.nemo.neplan.model.PlanPlace;
import com.nemo.neplan.repository.PlanPlaceRepository;
import com.nemo.neplan.service.PlanPlaceService;
import com.nemo.neplan.service.PlanService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.nemo.neplan.model.KakaoApiResponse;

import java.util.List;

@RestController
@RequestMapping("/planplaces")
@Api(tags = "플랜 속 경유지 관련 API", description = "경유지 관리 API")
public class PlanPlaceController {

    @Autowired
    private PlanPlaceService planPlaceService;

    @GetMapping("/getAll")
    public ResponseEntity<List<PlanPlace>> getAllPlanPlaces() {
        List<PlanPlace> planPlaces = planPlaceService.getAllPlanPlaces();
        return ResponseEntity.ok(planPlaces);
    }

    @GetMapping("/search/by-plan/{planId}")
    public ResponseEntity<List<PlanPlace>> getPlanPlacesByPlanId(@PathVariable Long planId) {
        List<PlanPlace> planPlaces = planPlaceService.getPlanPlacesByPlanId(planId);
        return ResponseEntity.ok(planPlaces);
    }

    @GetMapping("/search/by-place/{placeId}")
    public ResponseEntity<List<PlanPlace>> getPlanPlacesByPlaceId(@PathVariable Long placeId) {
        List<PlanPlace> planPlaces = planPlaceService.getPlanPlacesByPlaceId(placeId);
        return ResponseEntity.ok(planPlaces);
    }

    @PostMapping("/add")
    public ResponseEntity<PlanPlace> createPlanPlace(@RequestBody PlanPlace planPlace) {
        PlanPlace createdPlanPlace = planPlaceService.createPlanPlace(planPlace);
        return ResponseEntity.ok(createdPlanPlace);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePlanPlace(@PathVariable Long id) {
        planPlaceService.deletePlanPlace(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/changeOrder/{planId}")
    public ResponseEntity<List<PlanPlace>> changeOrder(
            @PathVariable Long planId,
            @RequestParam int who,
            @RequestParam int where) {

        List<PlanPlace> planPlaces = planPlaceService.getPlanPlacesByPlanId(planId);

        if (who >= 0 && who < planPlaces.size() && where >= 0 && where < planPlaces.size()) {
            PlanPlace planPlaceToMove = planPlaces.get(who);
            planPlaces.remove(who); // Remove from original position
            planPlaces.add(where, planPlaceToMove); // Add to new position

            // Update placeOrder values
            for (int i = 0; i < planPlaces.size(); i++) {
                planPlaces.get(i).setPlaceOrder(i);
                planPlaceService.createPlanPlace(planPlaces.get(i));
            }

            return ResponseEntity.ok(planPlaces);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }




    class TimeDuration{
        int distance; //미터
        int duration; //초

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }
    }

    @Value("${kakao.api.key}")
    private String apiKey;

    @Autowired
    private PlanService planService;



    @GetMapping("/calculateTime/{id}")
    public ResponseEntity<TimeDuration> calculateTime(@PathVariable Long id) {
        Plan plan = planService.getPlanById(id);
        String departureTime = plan.getDepartureDatetime();

        List<PlanPlace> planPlaces = planPlaceService.getPlanPlacesByPlanId(id);
        System.out.println("찾은 장소의 개수 : "+planPlaces.size());
        String waypointsString = buildWaypointsString(planPlaces);
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK 45212e7b98de5fc6be36009fadc07ab5");
//        headers.set("Authorization", "KakaoAK " + apikey);
        headers.set("Content-Type","application/json");
        System.out.println(waypointsString);


        String apiUrl = "https://apis-navi.kakaomobility.com/v1/future/directions";


        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("origin", planPlaces.get(0).getPlace().getX() + "," +
                        planPlaces.get(0).getPlace().getY())
                .queryParam("destination", planPlaces.get(planPlaces.size() - 1).getPlace().getX() + "," +
                        planPlaces.get(planPlaces.size() - 1).getPlace().getY())
                .queryParam("departure_time", departureTime)
                .queryParam("waypoints", waypointsString);

        System.out.println(builder.toUriString()+"\n\n\n\n");
        KakaoApiResponse response = restTemplate.getForObject(builder.toUriString(), KakaoApiResponse.class);

        TimeDuration timeDuration = new TimeDuration();
        timeDuration.setDistance(response.getDistance());
        timeDuration.setDuration(response.getDuration());

        return ResponseEntity.ok(timeDuration);
    }

    private String buildWaypointsString(List<PlanPlace> planPlaces) {
        StringBuilder waypointsBuilder = new StringBuilder();
        for (int i = 1; i < planPlaces.size() - 1; i++) {
            PlanPlace place = planPlaces.get(i);
            String waypoint = place.getPlace().getX() + "," + place.getPlace().getY();
            waypointsBuilder.append(waypoint);
            if (i < planPlaces.size() - 2) {
                waypointsBuilder.append(" | ");
            }
        }
        return waypointsBuilder.toString();
    }
}
