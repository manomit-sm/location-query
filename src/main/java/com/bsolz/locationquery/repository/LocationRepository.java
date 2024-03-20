package com.bsolz.locationquery.repository;

import com.bsolz.locationquery.entities.LocationDetails;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LocationRepository extends ReactiveCrudRepository<LocationDetails, Integer> {
    @Query("SELECT * FROM locations WHERE geo_id = :geoId")
    Mono<LocationDetails> findByGeoId(String geoId);

    Flux<LocationDetails> findByGeoType(String geoType);

    @Query("SELECT * FROM locations WHERE UPPER(name) LIKE CONCAT('%',UPPER(:name),'%') AND geo_type = :geoType")
    Flux<LocationDetails> findByNameAndGeoType(String name, String geoType);

    @Query("SELECT * FROM locations WHERE UPPER(name) LIKE CONCAT('%',UPPER(:name),'%')")
    Flux<LocationDetails> findByName(String name);
}
