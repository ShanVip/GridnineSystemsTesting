package com.gridnine.testing;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class FlightFilter {
    public static void filterAndPrint(List<Flight> flights, FlightFilterRule rule) {
        List<Flight> filteredFlights = new ArrayList<>();
        for (Flight flight : flights) {
            if (rule.filter(flight)) {
                filteredFlights.add(flight);
            }
        }

        for (Flight filteredFlight : filteredFlights) {
            System.out.println(filteredFlight);
        }
    }
}

interface FlightFilterRule {
    boolean filter(Flight flight);
}

class DepartureBeforeCurrentTimeFilter implements FlightFilterRule {
    @Override
    public boolean filter(Flight flight) {
        LocalDateTime currentTime = LocalDateTime.now();
        for (Segment segment : flight.getSegments()) {
            if (segment.getDepartureDate().isAfter(currentTime)) {
                return false;
            }
        }
        return true;
    }
}

class ArrivalBeforeDepartureFilter implements FlightFilterRule {
    @Override
    public boolean filter(Flight flight) {
        for (Segment segment : flight.getSegments()) {
            if (segment.getArrivalDate().isAfter(segment.getDepartureDate())) {
                return false;
            }
        }
        return true;
    }
}

class ExceedsTwoHoursGroundTimeFilter implements FlightFilterRule {
    @Override
    public boolean filter(Flight flight) {
        List<Segment> segments = flight.getSegments();
        long totalMillis = 0;

        for (int i = 1; i < segments.size(); i++) {
            Segment previousSegment = segments.get(i - 1);
            Segment currentSegment = segments.get(i);

            LocalDateTime previousArrival = previousSegment.getArrivalDate();
            LocalDateTime currentDeparture = currentSegment.getDepartureDate();

            long millisArrival = previousArrival.atZone(ZoneId.of("America/Los_Angeles")).toInstant().toEpochMilli();
            long millisDeparture = currentDeparture.atZone(ZoneId.of("America/Los_Angeles")).toInstant().toEpochMilli();

            totalMillis += (millisDeparture - millisArrival);
        }

        double hoursDifference = totalMillis / (1000.0 * 3600.0);
        return !(hoursDifference < 2);
    }
}
