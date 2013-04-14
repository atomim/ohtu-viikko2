package olutopas;

import com.avaje.ebean.EbeanServer;
import java.util.List;
import java.util.Scanner;
import javax.persistence.OptimisticLockException;
import olutopas.model.Beer;
import olutopas.model.Brewery;
import olutopas.model.Pub;
import olutopas.model.Rating;
import olutopas.model.User;

public class Application {

    private EbeanServer server;
    private Scanner scanner = new Scanner(System.in);

    public Application(EbeanServer server) {
        this.server = server;
    }

    public void run(boolean newDatabase) {
        if (newDatabase) {
            seedDatabase();
        }

        User currentUser;

        while (true) {
            currentUser = login();
            if (currentUser != null) {
                break;
            }
        }

        System.out.println("Welcome! " + currentUser);


        while (true) {
            menu();
            System.out.print("> ");
            String command = scanner.nextLine();

            if (command.equals("0")) {
                break;
            } else if (command.equals("1")) {
                findBrewery();
            } else if (command.equals("2")) {
                findBeer(currentUser);
            } else if (command.equals("3")) {
                addBeer();
            } else if (command.equals("4")) {
                listBreweries();
            } else if (command.equals("5")) {
                deleteBeer();
            } else if (command.equals("6")) {
                listBeers();
            } else if (command.equals("7")) {
                addBrewery();
            } else if (command.equals("8")) {
                deleteBrewery();
            } else if (command.equals("9")) {
                showBeersInPub();
            } else if (command.equals("10")) {
                listPubs();
            } else if (command.equals("11")) {
                addBeerToPub();
            } else if (command.equals("12")) {
                addPub();
            } else if (command.equals("t")) {
                showRatings(currentUser);
            } else if (command.equals("y")) {
                listUsers();
            } else {
                System.out.println("unknown command");
            }

            System.out.print("\npress enter to continue");
            scanner.nextLine();
        }

        System.out.println("bye");
    }

    private void menu() {
        System.out.println("");
        System.out.println("1   find brewery");
        System.out.println("2   find beer");
        System.out.println("3   add beer");
        System.out.println("4   list breweries");
        System.out.println("5   delete beer");
        System.out.println("6   list beers");
        System.out.println("7   add brewery");
        System.out.println("8   delete brewery");
        System.out.println("9   show beers in pub");
        System.out.println("10  list pubs");
        System.out.println("11  add beer to pub");
        System.out.println("12  add pub");
        System.out.println("t   show my ratings");
        System.out.println("y   list users");
        System.out.println("0   quit");
        System.out.println("");
    }

    // jos kanta on luotu uudelleen, suoritetaan tämä ja laitetaan kantaan hiukan dataa
    private void seedDatabase() throws OptimisticLockException {
        Brewery brewery = new Brewery("Schlenkerla");
        brewery.addBeer(new Beer("Urbock"));
        brewery.addBeer(new Beer("Lager"));
        // tallettaa myös luodut oluet, sillä Brewery:n OneToMany-mappingiin on määritelty
        // CascadeType.all
        server.save(brewery);

        // luodaan olut ilman panimon asettamista
        Beer b = new Beer("Märzen");
        server.save(b);

        // jotta saamme panimon asetettua, tulee olot lukea uudelleen kannasta
        b = server.find(Beer.class, b.getId());
        brewery = server.find(Brewery.class, brewery.getId());
        brewery.addBeer(b);
        server.save(brewery);

        server.save(new Brewery("Paulaner"));
    }

    private void findBeer(User currentUser) {
        System.out.print("beer to find: ");
        String n = scanner.nextLine();
        Beer foundBeer = server.find(Beer.class).where().like("name", n).findUnique();

        if (foundBeer == null) {
            System.out.println(n + " not found");
            return;
        }

        System.out.println(foundBeer.toString());
        while (true) {
            System.out.println("give rating (leave emtpy if not): ");
            String rating = scanner.nextLine();
            if (rating.equals("")) {
                return;
            }
            try {
                int points = Integer.parseInt(rating);
                Rating r = new Rating(foundBeer, currentUser, points);
                server.save(r);
                return;
            } catch (Exception e) {
            }
        }
    }

    private void findBrewery() {
        System.out.print("brewery to find: ");
        String n = scanner.nextLine();
        Brewery foundBrewery = server.find(Brewery.class).where().like("name", n).findUnique();

        if (foundBrewery == null) {
            System.out.println(n + " not found");
            return;
        }

        System.out.println(foundBrewery);
        for (Beer bier : foundBrewery.getBeers()) {
            System.out.println("   " + bier.getName());
        }
    }

    private void listBreweries() {
        List<Brewery> breweries = server.find(Brewery.class).findList();
        for (Brewery brewery : breweries) {
            System.out.println(brewery);
        }
    }

    private void addBeer() {
        System.out.print("to which brewery: ");
        String name = scanner.nextLine();
        Brewery brewery = server.find(Brewery.class).where().like("name", name).findUnique();

        if (brewery == null) {
            System.out.println(name + " does not exist");
            return;
        }

        System.out.print("beer to add: ");

        name = scanner.nextLine();

        Beer exists = server.find(Beer.class).where().like("name", name).findUnique();
        if (exists != null) {
            System.out.println(name + " exists already");
            return;
        }

        brewery.addBeer(new Beer(name));
        server.save(brewery);
        System.out.println(name + " added to " + brewery.getName());
    }

    private void deleteBeer() {
        System.out.print("beer to delete: ");
        String n = scanner.nextLine();
        Beer beerToDelete = server.find(Beer.class).where().like("name", n).findUnique();

        if (beerToDelete == null) {
            System.out.println(n + " not found");
            return;
        }

        server.delete(beerToDelete);
        System.out.println("deleted: " + beerToDelete);

    }

    private void listBeers() {
        List<Beer> beers = server.find(Beer.class).findList();
        for (Beer beer : beers) {
            System.out.println(beer);
        }
    }

    private void addBrewery() {
        System.out.print("brewery to add: ");

        String name = scanner.nextLine();

        Brewery exists = server.find(Brewery.class).where().like("name", name).findUnique();
        if (exists != null) {
            System.out.println(name + " exists already");
            return;
        }

        Brewery brewery = new Brewery(name);
        server.save(brewery);
        System.out.println(name + " added");
    }

    private void deleteBrewery() {
        System.out.print("brewery to delete: ");

        String name = scanner.nextLine();

        Brewery breweryToDelete = server.find(Brewery.class).where().like("name", name).findUnique();
        if (breweryToDelete == null) {
            System.out.println(name + " doesnt exist");
            return;
        }

        server.delete(breweryToDelete);
        System.out.println(name + " deleted");
    }

    private void register() {
        System.out.println("Register a new user");
        System.out.println("give username: ");
        String username = scanner.nextLine();

        User exists = server.find(User.class).where().like("username", username).findUnique();
        if (exists != null) {
            System.out.println(username + " exists already");
            return;
        }
        User newUser = new User();
        newUser.setUsername(username);
        server.save(newUser);
    }

    private User login() {
        System.out.println("Login (give ? to register a new user)");
        System.out.println("username: ");
        String username = scanner.nextLine();

        if (username.equals("?")) {
            register();
            return null;
        }
        User userToLogIn = server.find(User.class).where().like("username", username).findUnique();
        if (userToLogIn == null) {
            System.out.println(username + " doesn't exist");
            return null;
        }


        return userToLogIn;
    }

    private void showRatings(User user) {
        System.out.println("Ratings by " + user.getUsername());
        List<Rating> ratings = user.getRatings();
        for (Rating rating : ratings) {
            System.out.println(rating);
        }
    }

    private void listUsers() {
        List<User> users = server.find(User.class).findList();
        for (User user : users) {
            System.out.println(user);
        }
    }

    private void showBeersInPub() {
        System.out.println("Which pub?");
        String pubName = scanner.nextLine();
        Pub pub = server.find(Pub.class).where().like("name", pubName).findUnique();
        List<Beer> beers = pub.getBeers();
        for (Beer beer : beers) {
            System.out.println(beer);
        }
    }

    private void listPubs() {
        List<Pub> pubs = server.find(Pub.class).findList();
        for (Pub pub : pubs) {
            System.out.println(pub);
        }
    }

    private void addBeerToPub() {
        System.out.print("beer: ");
        String name = scanner.nextLine();
        Beer beer = server.find(Beer.class).where().like("name", name).findUnique();

        System.out.print("pub: ");
        name = scanner.nextLine();
        Pub pub = server.find(Pub.class).where().like("name", name).findUnique();

        pub.getBeers().add(beer);
        server.save(pub);
    }

    private void addPub() {
        System.out.print("name: ");
        String name = scanner.nextLine();
        server.save(new Pub(name));
    }
}
