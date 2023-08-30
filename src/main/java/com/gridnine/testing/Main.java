package com.gridnine.testing;

import java.util.List;

import static com.gridnine.testing.FlightFilter.filterAndPrint;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();

        System.out.println("\nFlights with departure before the current time:");
        filterAndPrint(flights, new DepartureBeforeCurrentTimeFilter());

        System.out.println("\nFlights with segments where arrival is before departure:");
        filterAndPrint(flights, new ArrivalBeforeDepartureFilter());

        System.out.println("\nFlights with total ground time exceeding two hours:");
        filterAndPrint(flights, new ExceedsTwoHoursGroundTimeFilter());
    }
}
