package org.softuni.exam.structures;

import org.softuni.exam.entities.Deliverer;
import org.softuni.exam.entities.Package;

import java.util.*;
import java.util.stream.Collectors;

public class DeliveriesManagerImpl implements DeliveriesManager {

    private Map<String, Deliverer> delivererMap;
    private Map<String, Package> packageMap;
    private Map<String, Package> unassignedPackages;
    private Map<String, Integer> packageCountByDeliverer;


    public DeliveriesManagerImpl() {
        this.delivererMap = new LinkedHashMap<>();
        this.packageMap = new LinkedHashMap<>();
        this.unassignedPackages = new LinkedHashMap<>();
        this.packageCountByDeliverer = new LinkedHashMap<>();

    }

    @Override
    public void addDeliverer(Deliverer deliverer) {
        delivererMap.put(deliverer.getId(), deliverer);
        packageCountByDeliverer.put(deliverer.getId(), 0);

    }

    @Override
    public void addPackage(Package _package) {
        packageMap.put(_package.getId(), _package);
        unassignedPackages.put(_package.getId(), _package);


    }

    @Override
    public boolean contains(Deliverer deliverer) {
        return delivererMap.containsKey(deliverer.getId());
    }

    @Override
    public boolean contains(Package _package) {
        return packageMap.containsKey(_package.getId());
    }

    @Override
    public Iterable<Deliverer> getDeliverers() {
        return delivererMap.values();
    }

    @Override
    public Iterable<Package> getPackages() {
        return packageMap.values();
    }

    @Override
    public void assignPackage(Deliverer deliverer, Package _package) throws IllegalArgumentException {
        if (!contains(deliverer) || !contains(_package)) {
            throw new IllegalArgumentException();
        }

        int currentCount = packageCountByDeliverer.get(deliverer.getId());
        packageCountByDeliverer.put(deliverer.getId(), currentCount + 1);
        unassignedPackages.remove(_package.getId());


    }

    @Override
    public Iterable<Package> getUnassignedPackages() {

        return unassignedPackages.values();
    }

    @Override
    public Iterable<Package> getPackagesOrderedByWeightThenByReceiver() {
        return packageMap.values()
                .stream()
                .sorted(Comparator.comparing(Package::getWeight).reversed()
                        .thenComparing(Package::getReceiver))
                .collect(Collectors.toList());


    }

    @Override
    public Iterable<Deliverer> getDeliverersOrderedByCountOfPackagesThenByName() {
        return delivererMap.values()
                .stream()
                .sorted(Comparator.comparing((Deliverer d) -> packageCountByDeliverer.get(d.getId())).reversed()
                        .thenComparing(Deliverer::getName))
                .collect(Collectors.toList());
    }
}
