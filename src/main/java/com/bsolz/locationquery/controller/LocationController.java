package com.bsolz.locationquery.controller;

import com.bsolz.locationquery.model.Location;
import com.bsolz.locationquery.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping("test")
    public Mono<Map<String, Object>> claims(@AuthenticationPrincipal JwtAuthenticationToken auth) {
        return Mono.just(auth.getTokenAttributes());
    }

    @GetMapping("all")
    public Flux<Location> allLocations() {
        return locationService.findAllLocation();
    }
    @GetMapping
    public Flux<Location> findByGeoType(@RequestParam String geoType) {
        return locationService.findByGeoType(geoType);
    }

    /*@GetMapping("search")
    public Flux<LocationDetails> findByNameAndGeoType(@RequestParam String name, @RequestParam String geoType) {
        return locationRepository.findByNameAndGeoType(name, geoType);
    }

    @GetMapping("search-by-name")
    public Flux<LocationDetails> findByName(@RequestParam String name) {
        return locationRepository.findByName(name);
    } */
}
