package com.bsolz.locationquery;

import com.bsolz.locationquery.entities.LocationDetails;
import com.bsolz.locationquery.exception.InputValidationException;
import com.bsolz.locationquery.model.Location;
import com.bsolz.locationquery.repository.LocationRepository;
import com.bsolz.locationquery.service.LocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Flux;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LocationServiceTest {

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private LocationService locationService;

    private Flux<LocationDetails> locationDetails;


    @BeforeEach
    public void setup() {
        var locationDetails_1 = new LocationDetails();
        var locationDetails_2 = new LocationDetails();
        locationDetails_1.setId(3);
        locationDetails_1.setName("India");
        locationDetails_1.setGeoId("10Z2W9ZTH2RUM");
        locationDetails_1.setStatus("Active");
        locationDetails_1.setGeoType("Country");

        locationDetails_2.setId(4);
        locationDetails_2.setName("Pune");
        locationDetails_2.setGeoId("10Z2W9ZTH2RUB");
        locationDetails_2.setStatus("Active");
        locationDetails_2.setGeoType("City");
        locationDetails = Flux.just(
                locationDetails_1,
                locationDetails_2
        );

        assertNotEquals(locationDetails_1, locationDetails_2);
        assertNotEquals(locationDetails_1.hashCode(), locationDetails_2.hashCode());
        assertNotNull(locationDetails_1);
        assertNotNull(locationDetails_2);

    }

    @Test
    public void givenNoInputWhenFindAllLocationThenReturnLocationList() {
        when(locationRepository.findAll()).thenReturn(locationDetails);

        var locations = locationService.findAllLocation();

        assertEquals(2, locations.count().block());
        verify(locationRepository, times(1)).findAll();
    }

    @Test
    public void givenGeoTypeWhenFindByGeoTypeThenReturnLocationByGeoType() {
        when(locationRepository.findByGeoType(anyString())).thenReturn(locationDetails);

        var locations = locationService.findByGeoType("City");

        assertEquals(2, locations.count().block());
        verify(locationRepository, times(1)).findByGeoType("City");
    }

    @Test
    public void givenGeoTypeAndNameWhenFindByNameAndGeoTypeThenReturnLocationByGeoTypeAndName() {
        when(locationRepository.findByNameAndGeoType(anyString(), anyString())).thenReturn(locationDetails);

        var locations = locationService.findByNameAndGeoType("V", "City");

        assertEquals(2, locations.count().block());
        verify(locationRepository, times(1)).findByNameAndGeoType("V", "City");
    }

    @Test
    public void givenNameWhenFindByNameThenReturnLocationName() {
        when(locationRepository.findByName(anyString())).thenReturn(locationDetails);

        var locations = locationService.findByName("I");

        assertEquals(2, locations.count().block());
        verify(locationRepository, times(1)).findByName("I");
    }

}
