package org.softuni.exam.structures;

import org.softuni.exam.entities.Airline;
import org.softuni.exam.entities.Flight;

import java.util.*;
import java.util.stream.Collectors;

public class AirlinesManagerImpl implements AirlinesManager {

    private Map<String, Airline> airlineMap;
    private Map<String, Flight> flightMap;
    private Map<String, Flight> completedFlights;
    private Map<String, List<Flight>> flightsByAirLine;

    public AirlinesManagerImpl() {
        this.airlineMap = new LinkedHashMap<>();
        this.flightMap = new LinkedHashMap<>();
        this.completedFlights = new LinkedHashMap<>();
        this.flightsByAirLine = new LinkedHashMap<>();
    }

    @Override
    public void addAirline(Airline airline) {
            this.airlineMap.put(airline.getId(), airline);
            flightsByAirLine.put(airline.getId(), new ArrayList<>());


    }

    @Override
    public void addFlight(Airline airline, Flight flight) {
        if (!contains(airline)) {
            throw new IllegalArgumentException();
        }

        this.flightMap.put(flight.getId(), flight);
        flightsByAirLine.get(airline.getId()).add(flight);
        if (flight.isCompleted()){
            completedFlights.put(flight.getId(), flight);
        }


    }

    @Override
    public boolean contains(Airline airline) {
        return airlineMap.containsKey(airline.getId());
    }

    @Override
    public boolean contains(Flight flight) {
        return flightMap.containsKey(flight.getId());
    }

    @Override
    public void deleteAirline(Airline airline) throws IllegalArgumentException {
            if(!contains(airline)){
                throw new IllegalArgumentException();
            }

            airlineMap.remove(airline.getId());
            List<Flight> removedAirlineFlights = flightsByAirLine.get(airline.getId());

        for (Flight flight : removedAirlineFlights) {
            flightMap.remove(flight.getId());
            completedFlights.remove(flight.getId());
        }


    }

    @Override
    public Iterable<Flight> getAllFlights() {
        return flightMap.values();
    }

    @Override
    public Flight performFlight(Airline airline, Flight flight) throws IllegalArgumentException {
        if(!contains(airline) || !contains(flight)){
            throw new IllegalArgumentException();
        }

        Flight completeFlight = flightMap.get(flight.getId());
        completeFlight.setCompleted(true);
        completedFlights.put(completeFlight.getId(), completeFlight);

        return completeFlight;
    }

    @Override
    public Iterable<Flight> getCompletedFlights() {

        return completedFlights.values();


//        return flightMap.values().stream()
//                .filter(Flight::isCompleted)
//                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Flight> getFlightsOrderedByNumberThenByCompletion() {
        return flightMap.values().stream()
                .sorted(Comparator.comparing(Flight::isCompleted)
                        .thenComparing(Flight::getNumber))
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Airline> getAirlinesOrderedByRatingThenByCountOfFlightsThenByName() {
        return airlineMap.values().stream()
                .sorted(Comparator.comparing(Airline::getRating)
                        .thenComparing(a -> flightsByAirLine.get(a.getId()).size()).reversed()
                        .thenComparing(Airline::getName))
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Airline> getAirlinesWithFlightsFromOriginToDestination(String origin, String destination) {
        return airlineMap.values()
                .stream()
                .filter(a -> flightsByAirLine.get(a.getId()).stream().anyMatch(
                        f -> !f.isCompleted() && f.getOrigin().equals(origin) && f.getDestination().equals(destination)))
                .collect(Collectors.toList());
    }
}
