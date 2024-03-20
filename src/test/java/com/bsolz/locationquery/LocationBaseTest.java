package com.bsolz.locationquery;

import com.bsolz.locationquery.model.Location;
import com.bsolz.locationquery.service.LocationService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockJwt;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.springSecurity;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class LocationBaseTest {
    @Autowired
    private ApplicationContext applicationContext;
    protected Flux<Location> locationList;

    protected Flux<Location> locationListByNameAndGeoType;

    protected WebTestClient client;

    @MockBean
    private ReactiveJwtDecoder decoder;

    @MockBean
    protected LocationService locationService;


    @BeforeAll
    public void setupContext() {
        locationList = Flux.just(
                new Location(1, "A", "AT", "Homer Simpson", "Active"),
                new Location(2, "B", "BT", "Merge Simpson", "Active")
        );

        locationListByNameAndGeoType = Flux.just(
                new Location(3, "10Z2W9ZTH2RUM", "Country", "India", "Active")
        );
        client = WebTestClient
                .bindToApplicationContext(applicationContext)
                .apply(springSecurity())
                .configureClient()
                .build()
                .mutateWith(mockJwt());
    }
}
