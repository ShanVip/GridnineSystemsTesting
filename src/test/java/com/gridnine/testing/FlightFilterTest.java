package com.gridnine.testing;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlightFilterTest {

    @Test
    public void testDepartureBeforeCurrentTimeFilter() {
        List<Flight> flights = FlightBuilder.createFlights();
        FlightFilterRule filterRule = new DepartureBeforeCurrentTimeFilter();
        List<Flight> filteredFlights = flights.stream()
                .filter(filterRule::filter)
                .collect(Collectors.toList());

        assertEquals(1, filteredFlights.size());
    }

    @Test
    public void testArrivalBeforeDepartureFilter() {
        List<Flight> flights = FlightBuilder.createFlights();
        FlightFilterRule filterRule = new ArrivalBeforeDepartureFilter();
        List<Flight> filteredFlights = flights.stream()
                .filter(filterRule::filter)
                .collect(Collectors.toList());

        assertEquals(1, filteredFlights.size());
    }

    @Test
    public void testExceedsTwoHoursGroundTimeFilter() {
        List<Flight> flights = FlightBuilder.createFlights();
        FlightFilterRule filterRule = new ExceedsTwoHoursGroundTimeFilter();
        List<Flight> filteredFlights = flights.stream()
                .filter(filterRule::filter)
                .collect(Collectors.toList());

        assertEquals(2, filteredFlights.size());
    }
}
