package com.bsolz.locationquery.service;

import com.bsolz.locationquery.exception.InputValidationException;
import com.bsolz.locationquery.model.Location;
import com.bsolz.locationquery.repository.LocationRepository;
import com.bsolz.locationquery.util.EntityDtoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    public Flux<Location> findAllLocation() {
        return locationRepository.findAll().map(EntityDtoUtil::toDto);
    }

    public Flux<Location> findByGeoType(String geoType) {
        return locationRepository.findByGeoType(geoType).map(EntityDtoUtil::toDto);
    }

    public Flux<Location> findByNameAndGeoType(String name, String geoType) {
        return locationRepository.findByNameAndGeoType(name, geoType).map(EntityDtoUtil::toDto);
    }

    public Flux<Location> findByName(String name) {
        return locationRepository.findByName(name).map(EntityDtoUtil::toDto);
    }
}
