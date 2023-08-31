package com.nemo.neplan.model;

public enum PlaceType {
    CAFE("Cafe"),
    RESTAURANT("Restaurant"),
    PARK("Park"),
    HOSTEL("Hostel"),
    CULTURAL_FACILITY("CulturalFacility"),
    ETC("Etc");

    private final String value;

    PlaceType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
