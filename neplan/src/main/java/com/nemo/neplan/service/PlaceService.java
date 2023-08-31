package com.nemo.neplan.service;

import com.nemo.neplan.model.Place;
import com.nemo.neplan.model.User;

import java.util.List;

public interface PlaceService {

    public Place savePlace(Place place);
    public List<Place> getAllPlace();

    public Place getPlace(long id);
    public Place modifyPlace(Place place);
    public int deletePlace(long id);

    List<Place> searchByCity(String city);
    List<Place> searchByCityAndDistrict(String city, String district);
    List<Place> searchByCityAndDistrictAndNeighborhood(String city, String district, String neighborhood);
    List<Place> searchByCityAndDistrictAndNeighborhoodAndStreetAndBuildingNumber(
            String city, String district, String neighborhood, String street, String buildingNumber
    );
    List<Place> searchByCityAndDistrictAndNeighborhoodAndStreet(
            String city, String district, String neighborhood, String street);



}
