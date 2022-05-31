package guests;

import java.util.ArrayList;

public class GuestsList {
    private final int numberOfAvailableSeats;
    private final ArrayList<Guest> guestList = new ArrayList<>();
    private final ArrayList<Guest> waitingList = new ArrayList<>();

    public GuestsList(int numberOfAvailableSeats) {
        this.numberOfAvailableSeats = numberOfAvailableSeats;
    }

    public ArrayList<Guest> getGuestList() {
        return guestList;
    }

    public ArrayList<Guest> getWaitingList() {
        return waitingList;
    }

    public int addPersonToGuestList(Guest person) {
        if (isAllDataOnAnyList(person)) {
            System.out.println("Aceasta persoana a fost deja inregistrata.");
            return -1;
        }
        if (guestList.size() < numberOfAvailableSeats) {
            guestList.add(person);
            System.out.println("[" + person.getLastName() + " " + person.getFirstName() +
                    "] Felicitari! Locul tau la eveniment este confirmat. Te asteptam!");
            return 0;
        } else {
            waitingList.add(person);
            System.out.println("[" + person.getLastName() + " " + person.getFirstName() +
                    "] Te-ai inscris cu succes in lista de asteptare si ai primit numarul de ordine " +
                    (waitingList.indexOf(person) + 1) + ". Te vom notifica daca un loc devine disponibil.");
            return waitingList.indexOf(person);
        }
    }

    public boolean isAllDataOnAnyList(Guest person) {
        if (isAllDataOnGuestList(person) || isAllDataOnWaitingList(person)) {
            return true;
        }
        return false;
    }

    public boolean isAllDataOnGuestList(Guest person) {
        if (guestList.size() == 0) {
            return false;
        }
        for (Guest guest : guestList) {
            if (person.equals(guest)) {
                return true;
            }
        }
        return false;
    }

    public boolean isAllDataOnWaitingList(Guest person) {
        if (waitingList.size() == 0) {
            return false;
        }
        for (Guest guest : waitingList) {
            if (person.equals(guest)) {
                return true;
            }
        }
        return false;
    }

    public boolean isSomeDataOnGuestList(Guest person) {
        for (Guest guest : guestList) {
            if (person.getLastName().equals(guest.getLastName()) && person.getFirstName().equals(guest.getLastName()) ||
                    person.getEmail().equals(guest.getEmail()) ||
                    person.getPhoneNumber().equals(guest.getPhoneNumber())) {
                return true;
            }
        }
        return false;
    }

    public boolean isSomeDataOnWaitingList(Guest person) {
        for (Guest guest : waitingList) {
            if ((person.getLastName().equals(guest.getLastName()) && person.getFirstName().equals(guest.getLastName())) ||
                    person.getEmail().equals(guest.getEmail()) ||
                    person.getPhoneNumber().equals(guest.getPhoneNumber())) {
                return true;
            }
        }
        return false;
    }


    public boolean removeGuest(Guest person) {
        if (isAllDataOnWaitingList(person)) {
            waitingList.remove(person);
            return true;
        } else if (isAllDataOnGuestList(person)) {
            guestList.remove(person);
            if (!waitingList.isEmpty()) {
                guestList.add(waitingList.get(0));

                System.out.println("[" + waitingList.get(0).getLastName() + " " + waitingList.get(0).getFirstName() +
                        "] Felicitari! Locul tau la eveniment este confirmat. Te asteptam!");

                waitingList.remove(0);
            }
            return true;
        }
        return false;
    }

    public int updateGuestData(Guest person, String updatedInfo, int option) {
        if (isSomeDataOnGuestList(person) || isSomeDataOnWaitingList(person)) {
            switch (option) {
                case 1:
                    person.setLastName(updatedInfo);
                    System.out.println("Nume actualizat!");
                    return 1;
                case 2:
                    person.setFirstName(updatedInfo);
                    System.out.println("Prenume actualizat!");
                    return 1;
                case 3:
                    person.setEmail(updatedInfo);
                    System.out.println("Email actualizat!");
                    return 1;
                case 4:
                    person.setPhoneNumber(updatedInfo);
                    System.out.println("Numar de telefon actualizat!");
                    return 1;
                default:
                    System.out.println("Optiune invalida.");
                    return -1;
            }
        } else {
            System.out.println("Persoana nu este inregistrata (nume, prenume).");
            return -1;
        }
    }

    public int getNumberOfAvailableSeats() {
        return numberOfAvailableSeats - guestList.size();
    }

    public int getGuestListSize() {
        return guestList.size();
    }

    public int getWaitingListSize() {
        return waitingList.size();
    }

    public int getTotalNumberOfPeopleOnLists() {
        return guestList.size() + waitingList.size();
    }

    public ArrayList<Guest> searchBySubstring(String s) {
        s = s.toLowerCase();
        ArrayList<Guest> searchResult = new ArrayList<>();

        for (Guest person : guestList) {
            if (person.getLastName().toLowerCase().contains(s) || person.getFirstName().toLowerCase().contains(s) ||
                    person.getEmail().toLowerCase().contains(s) || person.getPhoneNumber().toLowerCase().contains(s)) {
                searchResult.add(person);
            }
        }

        for (Guest person : waitingList) {
            if (person.getLastName().toLowerCase().contains(s) || person.getFirstName().toLowerCase().contains(s) ||
                    person.getEmail().toLowerCase().contains(s) || person.getPhoneNumber().toLowerCase().contains(s)) {
                searchResult.add(person);
            }
        }

        return searchResult;
    }

    public ArrayList<Guest> searchPerson(String lastName, String firstName) {
        ArrayList<Guest> searchList = new ArrayList<>();
        for (Guest guest : guestList) {
            if (guest.getLastName().equalsIgnoreCase(lastName) &&
                    guest.getFirstName().equalsIgnoreCase(firstName)) {
                searchList.add(guest);
            }
        }
        for (Guest guest : waitingList) {
            if (guest.getLastName().equalsIgnoreCase(lastName) &&
                    guest.getFirstName().equalsIgnoreCase(firstName)) {
                searchList.add(guest);
            }
        }
        return searchList;
    }

    public ArrayList<Guest> searchPerson(String emailOrPhoneNumber, byte choice) {
        ArrayList<Guest> searchList = new ArrayList<>();
        if (choice == 2) {
            for (Guest guest : guestList) {
                if (guest.getEmail().equalsIgnoreCase(emailOrPhoneNumber)) {
                    searchList.add(guest);
                }
            }
            for (Guest guest : waitingList) {
                if (guest.getEmail().equalsIgnoreCase(emailOrPhoneNumber)) {
                    searchList.add(guest);
                }
            }
        } else if (choice == 3) {
            for (Guest guest : guestList) {
                if (guest.getPhoneNumber().equals(emailOrPhoneNumber)) {
                    searchList.add(guest);
                }
            }
            for (Guest guest : waitingList) {
                if (guest.getPhoneNumber().equals(emailOrPhoneNumber)) {
                    searchList.add(guest);
                }
            }
        }

        return searchList;
    }

    public void printGuestList() {
        if (guestList.size() == 0) {
            System.out.println("Lista invitatilor este goala...");
        } else {
            System.out.println("Lista invitatilor: ");
            for (Guest guest : guestList) {
                System.out.println("Numar de ordine: " + (guestList.indexOf(guest) + 1));
                guest.printGuest();
            }
        }
        System.out.println();
    }

    public void printWaitingList() {
        if (waitingList.size() == 0) {
            System.out.println("Lista de asteptare este goala...");
        } else {
            System.out.println("Lista de asteptare: ");
            for (Guest guest : waitingList) {
                System.out.println("Numar de ordine: " + (waitingList.indexOf(guest) + 1));
                guest.printGuest();
            }
        }
        System.out.println();
    }
}
