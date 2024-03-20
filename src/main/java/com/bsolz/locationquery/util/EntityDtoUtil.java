package com.bsolz.locationquery.util;

import com.bsolz.locationquery.entities.LocationDetails;
import com.bsolz.locationquery.model.Location;

public class EntityDtoUtil {

    private EntityDtoUtil() {}

    public static Location toDto(LocationDetails locationDetails) {
        return new Location(locationDetails.getId(), locationDetails.getGeoId(), locationDetails.getGeoType(), locationDetails.getName(), locationDetails.getStatus());
    }
}
