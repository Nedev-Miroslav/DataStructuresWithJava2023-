package barbershopjava;

import java.util.*;
import java.util.stream.Collectors;

public class BarberShopImpl implements BarberShop {

    private Map<String, Barber> barberByName;
    private Map<String, List<Client>> barberWithClients;
    //   private List<Barber> barbers;
    private Map<String, Client> clientByName;
    //   private List<Client> clients;

    public BarberShopImpl() {
        this.barberByName = new LinkedHashMap<>();
        this.clientByName = new LinkedHashMap<>();
        this.barberWithClients = new LinkedHashMap<>();
        //       this.barbers = new ArrayList<>();
        //       this.clients = new ArrayList<>();
    }

    @Override
    public void addBarber(Barber b) {
        if (barberByName.containsKey(b.name)) {
            throw new IllegalArgumentException();
        }
        barberByName.put(b.name, b);
        barberWithClients.put(b.name, new ArrayList<>());
        //       barbers.add(b);


    }

    @Override
    public void addClient(Client c) {
        if (clientByName.containsKey(c.name)) {
            throw new IllegalArgumentException();
        }

        clientByName.put(c.name, c);
//        clients.add(c);

    }

    @Override
    public boolean exist(Barber b) {
        return barberByName.containsKey(b.name);
    }

    @Override
    public boolean exist(Client c) {
        return clientByName.containsKey(c.name);
    }

    @Override
    public Collection<Barber> getBarbers() {
        return barberByName.values();
    }

    @Override
    public Collection<Client> getClients() {
        return clientByName.values();
    }

    @Override
    public void assignClient(Barber b, Client c) {
        if (!exist(b) || !exist(c)) {
            throw new IllegalArgumentException();
        }

        c.barber = b;
        barberWithClients.get(b.name).add(c);

    }

    @Override
    public void deleteAllClientsFrom(Barber b) {
        if (!exist(b)) {
            throw new IllegalArgumentException();
        }

        List<Client> clients = barberWithClients.get(b.name);
        clients.clear();
    }

    @Override
    public Collection<Client> getClientsWithNoBarber() {
        return getClients().stream()
                .filter(c -> c.barber == null)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Barber> getAllBarbersSortedWithClientsCountDesc() {
//        Друг начин за сортировка
//        return getBarbers().stream()
//                .sorted((b1, b2) -> {
//
//                    int firstClient = barberWithClients.get(b1.name).size();
//                    int secondClient = barberWithClients.get(b2.name).size();
//
//                    return Integer.compare(secondClient, firstClient);
//                }).collect(Collectors.toList());

        return barberByName.values().stream()
                .sorted(Comparator.comparing((Barber c)-> barberWithClients.get(c.name).size()).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Barber> getAllBarbersSortedWithStarsDescendingAndHaircutPriceAsc() {
//        Друг начин за сортировка
//        return getBarbers().stream()
//                .sorted((b1, b2) -> {
//                    int result = Integer.compare(b2.stars, b1.stars);
//
//                    if (result == 0) {
//                        result = Integer.compare(b1.haircutPrice, b2.haircutPrice);
//                    }
//
//                    return result;
//
//                })
//                .collect(Collectors.toList());

        return barberByName.values().stream()
                .sorted(Comparator.comparing((Barber b) -> b.stars).reversed()
                        .thenComparing(b -> b.haircutPrice))
                .collect(Collectors.toList());

    }

    @Override
    public Collection<Client> getClientsSortedByAgeDescAndBarbersStarsDesc() {
        return barberWithClients.values()
                .stream()
                .flatMap(List::stream)
                .sorted((c1, c2) -> {
                    int result = Integer.compare(c2.age, c1.age);

                    if (result == 0) {
                        result = Integer.compare(c2.barber.stars, c1.barber.stars);

                    }
                    return result;

                })
                .collect(Collectors.toList());

    }
}
