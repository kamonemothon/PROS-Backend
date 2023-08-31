package com.nemo.neplan.repository;


import com.nemo.neplan.model.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long> {

    List<Place> findByCity(String city);

    List<Place> findByCityAndDistrict(String city, String district);

    List<Place> findByCityAndDistrictAndNeighborhood(String city, String district, String neighborhood);

    List<Place> findByCityAndDistrictAndNeighborhoodAndStreetAndBuildingNumber(
            String city, String district, String neighborhood, String street, String buildingNumber
    );

    List<Place> findByCityAndDistrictAndNeighborhoodAndStreet(String city, String district, String neighborhood, String street);
}
