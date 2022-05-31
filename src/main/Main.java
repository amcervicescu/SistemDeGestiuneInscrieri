package main;

import extraExceptions.Invalid123ChoiceException;
import extraExceptions.InvalidPhoneNumberException;
import guests.Guest;
import guests.GuestsList;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final Scanner inputScanner = new Scanner(System.in);

    // method to search for a person through the input of only a part of their personal data
    public static ArrayList<Guest> searchPersonByData(GuestsList guestList, byte choice) {
        String lastName, firstName, email, phoneNumber;
        ArrayList<Guest> search = new ArrayList<>();
        switch (choice) {
            case 1:
                System.out.println("Nume:");
                lastName = inputScanner.next();
                System.out.println("Prenume:");
                firstName = inputScanner.next();
                search = guestList.searchPerson(lastName, firstName);
                break;
            case 2:
                System.out.println("Email:");
                email = inputScanner.next();
                search = guestList.searchPerson(email, choice);
                break;
            case 3:
                System.out.println("Numar de telefon:");
                phoneNumber = inputScanner.next();
                search = guestList.searchPerson(phoneNumber, choice);
                break;
            default:
                System.out.println("Alegere invalida");
                break;
        }
        return search;
    }


    // Exception
    // -> main - number of seats
    private static int getIntegerOperand() {
        while (true) {
            try {
                System.out.println("Introduceti o valoare intreaga:");
                return inputScanner.nextInt();
            } catch (InputMismatchException e) {
                inputScanner.nextLine();
                System.out.println("EXCEPTIE: Valoarea introdusa nu este intreaga.\nVa rugam sa incercati din nou.");
            }
        }
    }

    // Exception
    // -> main - switch - case "add"
    public static void isPhoneNumberValid(String phoneNumber) throws InvalidPhoneNumberException {
        if (!phoneNumber.matches("[0-9]+")) {
            throw new InvalidPhoneNumberException("Numar de telefon invalid.\nVa rugam sa incercati din nou.");
        }
    }

    // Exception
    // -> main - switch - ...
    private static byte getByteOperand() {
        while (true) {
            try {
                // System.out.println("Introduceti o valoare tip byte:");
                return inputScanner.nextByte();
            } catch (InputMismatchException e) {
                inputScanner.nextLine();
                System.out.println("EXCEPTIE: Valoarea introdusa nu este byte.\nVa rugam sa incercati din nou.");
            }
        }
    }

    public static void is123ChoiceValid(byte b) throws Invalid123ChoiceException {
        if (b < 1 || b > 3) {
            throw new Invalid123ChoiceException("Alegere invalida. Va rugam sa incercati din nou.");
        }
    }

    private static byte get123Choice() {
        byte b = getByteOperand();
        while (true) {
            try {
                is123ChoiceValid(b);
                return b;
            } catch (Invalid123ChoiceException e) {
                System.out.println("EXCEPTIE: " + e);
                b = getByteOperand();
            }
        }
    }


    public static void main(String[] args) {

        System.out.println("\n\t=== Sistem de gestiune inscrieri ===\n");

        System.out.println("Bun venit! Introduceti numarul de locuri disponibile.");

        int numberOfSeats = getIntegerOperand();


        GuestsList guestList = new GuestsList(numberOfSeats);
        System.out.println("Momentan sunt " + numberOfSeats + " locuri disponibile.\n");

        System.out.println("In asteptarea unei comenzi... (Sugestie: help)");
        String command = inputScanner.next();


        // variables needed for later use
        String lastName, firstName, email, phoneNumber;
        Guest guest;
        ArrayList<Guest> search;
        int index = 0;
        byte choice;

        while (!command.equals("quit")) {
            switch (command) {
                case "help":
                    System.out.println("help         -> Afiseaza aceasta lista de comenzi");
                    System.out.println("add          -> Adauga o noua persoana (inscriere)");
                    System.out.println("check        -> Verifica daca o persoana este inscrisa la eveniment");
                    System.out.println("remove       -> Sterge o persoana existenta din lista");
                    System.out.println("update       -> Actualizeaza detaliile unei persoane");
                    System.out.println("guests       -> Lista de persoane care participa la eveniment");
                    System.out.println("waitlist     -> Persoanele din lista de asteptare");
                    System.out.println("available    -> Numarul de locuri libere");
                    System.out.println("guests_no    -> Numarul de persoane care participa la eveniment");
                    System.out.println("waitlist_no  -> Numarul de persoane din lista de asteptare");
                    System.out.println("subscribe_no -> Numarul total de persoane inscrise");
                    System.out.println("search       -> Cauta toti invitatii conform sirului de caractere introdus");
                    System.out.println("quit         -> Inchide aplicatia");

                    System.out.println("\nIntroduceti o comanda:");
                    command = inputScanner.next();
                    break;

                case "add":
                    System.out.println("Se adauga o noua persoana...");
                    guest = new Guest("", "", "", "");
                    System.out.println("Nume:");
                    guest.setLastName(inputScanner.next());
                    System.out.println("Prenume:");
                    guest.setFirstName(inputScanner.next());
                    System.out.println("Email:");
                    guest.setEmail(inputScanner.next());
                    System.out.println("Numar de telefon:");
                    String pn;
                    while (true) {
                        pn = inputScanner.next();
                        try {
                            isPhoneNumberValid(pn);
                        } catch (InvalidPhoneNumberException e) {
                            System.out.println("Exceptie prinsa: " + e);
                            continue;
                        }
                        break;
                    }
                    guest.setPhoneNumber(pn);

                    guestList.addPersonToGuestList(guest);

                    System.out.println("\nIntroduceti o comanda:");
                    command = inputScanner.next();
                    break;

                case "check":
                    System.out.println("Va rugam introduceti datele persoanei pe care doriti sa o verificati.");
                    System.out.println("1. nume si prenume");
                    System.out.println("2. email");
                    System.out.println("3. numar de telefon");

                    choice = get123Choice();
                    search = searchPersonByData(guestList, choice);

                    boolean isRegistered = false;
                    if (!search.isEmpty()) {
                        for (Guest currentGuest : search) {
                            if (guestList.isAllDataOnAnyList(currentGuest)) {
                                System.out.println("Persoana este inregistrata.");
                                isRegistered = true;
                                break;
                            }
                        }

                    }
                    if (!isRegistered) {
                        System.out.println("Persoana nu este inregistrata.");
                    }

                    System.out.println("\nIntroduceti o comanda:");
                    command = inputScanner.next();
                    break;

                case "remove":
                    System.out.println("Va rugam sa alegeti metoda preferata de cautare a persoanei pe care " +
                            "doriti sa o stergeti:");
                    System.out.println("1. nume si prenume");
                    System.out.println("2. email");
                    System.out.println("3. numar de telefon");

                    choice = get123Choice();
                    search = searchPersonByData(guestList, choice);

                    if (search.isEmpty()) {
                        System.out.println("Persoana nu a fost gasita.");
                    } else {
                        if (search.size() > 1) {
                            System.out.println("Au fost gasite mai multe rezultate.");
                            System.out.println("A cata persoana gasita doriti sa o stergeti? (ex: 1, 2, 3):");
                            index = inputScanner.nextInt() - 1;
                        }

                        if (index >= 0 && index < search.size()) {
                            guest = search.get(index);
                            boolean removed = guestList.removeGuest(guest);
                            if (removed) {
                                System.out.println("Persoana a fost eliminata.");
                            } else {
                                System.out.println("Persoana nu era inregistrata.");
                            }
                        } else {
                            System.out.println("Eroare: Numarul introdus este in afara optiunilor; stergerea nu " +
                                    "a putut fi efectuata.");
                        }

                        search.clear();
                        index = 0;
                    }

                    System.out.println("\nIntroduceti o comanda:");
                    command = inputScanner.next();
                    break;

                case "update":
                    System.out.println("Va rugam sa alegeti metoda preferata de cautare a persoanei ale carei date " +
                            "doriti sa le actualizati:");
                    System.out.println("1. nume si prenume");
                    System.out.println("2. email");
                    System.out.println("3. numar de telefon");

                    choice = get123Choice();
                    search = searchPersonByData(guestList, choice);

                    if (search.isEmpty()) {
                        System.out.println("Persoana nu a fost gasita.");
                    } else {
                        if (search.size() > 1) {
                            System.out.println("Au fost gasite mai multe rezultate.");
                            System.out.println("Al catelea corespunde persoanei ale care date doriti sa le actualizati?");
                            index = inputScanner.nextInt() - 1;
                        }
                        if (index >= 0 && index < search.size()) {
                            System.out.println("Va rugam introduceti datele pe care doriti sa le actualizati:");
                            System.out.println("1. nume");
                            System.out.println("2. prenume");
                            System.out.println("3. email");
                            System.out.println("4. numar de telefon");
                            choice = inputScanner.nextByte();
                            switch (choice) {
                                case 1:
                                    System.out.println("Nume nou:");
                                    lastName = inputScanner.next();
                                    guestList.updateGuestData(search.get(index), lastName, choice);
                                    break;
                                case 2:
                                    System.out.println("Prenume nou:");
                                    firstName = inputScanner.next();
                                    guestList.updateGuestData(search.get(index), firstName, choice);
                                    break;
                                case 3:
                                    System.out.println("Email nou:");
                                    email = inputScanner.next();
                                    guestList.updateGuestData(search.get(index), email, choice);
                                    break;
                                case 4:
                                    System.out.println("Numar de telefon nou:");
                                    phoneNumber = inputScanner.next();
                                    guestList.updateGuestData(search.get(index), phoneNumber, choice);
                                    break;
                                default:
                                    System.out.println("Alegere invalida");
                                    break;
                            }
                        } else {
                            System.out.println("Eroare: Numarul introdus este in afara optiunilor; stergerea nu " +
                                    "a putut fi efectuata.");
                        }
                        search.clear();
                        index = 0;
                    }

                    System.out.println("\nIntroduceti o comanda:");
                    command = inputScanner.next();
                    break;

                case "guests":
                    guestList.printGuestList();
                    System.out.println("\nIntroduceti o comanda:");
                    command = inputScanner.next();
                    break;

                case "waitlist":
                    guestList.printWaitingList();
                    System.out.println("\nIntroduceti o comanda:");
                    command = inputScanner.next();
                    break;

                case "available":
                    System.out.println("Mai sunt " + guestList.getNumberOfAvailableSeats() + " locuri disponibile.");
                    System.out.println("\nIntroduceti o comanda:");
                    command = inputScanner.next();
                    break;

                case "guests_no":
                    System.out.println("Numar de participanti: " + guestList.getGuestListSize());
                    System.out.println("\nIntroduceti o comanda:");
                    command = inputScanner.next();
                    break;

                case "waitlist_no":
                    System.out.println("Dimensiunea listei de asteptare: " + guestList.getWaitingListSize());
                    System.out.println("\nIntroduceti o comanda:");
                    command = inputScanner.next();
                    break;

                case "subscribe_no":
                    System.out.println("Numar total de persoane: " + guestList.getTotalNumberOfPeopleOnLists());
                    System.out.println("\nIntroduceti o comanda:");
                    command = inputScanner.next();
                    break;

                case "search":
                    System.out.println("Introduceti un sir de caractere:");
                    String s = inputScanner.next();
                    for (Guest person : guestList.searchBySubstring(s)) {
                        person.printGuest();
                        System.out.println();
                    }
                    System.out.println("\nIntroduceti o comanda:");
                    command = inputScanner.next();
                    break;

                // case "quit" not reached

                default:
                    System.out.println("Comanda invalida. Introduceti o noua comanda (sugestii: help, quit):");
                    command = inputScanner.next();
            }
        }
        System.out.println("Aplicatia se inchide...");
    }
}

