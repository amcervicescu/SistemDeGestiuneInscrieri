package guests;

public class Guest {
    private String lastName = "-";
    private String firstName = "-";
    private String email = "-";
    private String phoneNumber = "-";

    public Guest(String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public Guest(String emailOrPhoneNumber) {
        if (Character.isDigit(emailOrPhoneNumber.charAt(emailOrPhoneNumber.length() - 1))) {
            this.phoneNumber = emailOrPhoneNumber;
        } else {
            this.email = emailOrPhoneNumber;
        }
    }

    public Guest(String lastName, String firstName, String email, String phoneNumber) {
        this(lastName, firstName);
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guest guest = (Guest) o;

        return guest.lastName.equals(this.lastName) && guest.firstName.equals(this.firstName) &&
                guest.email.equals(this.email) &&
                guest.phoneNumber.equals(this.phoneNumber);
    }

    public void printGuest() {
        System.out.println("\tNume: " + this.lastName);
        System.out.println("\tPrenume: " + this.firstName);
        System.out.println("\tEmail: " + this.email);
        System.out.println("\tNumar de telefon: " + this.phoneNumber);
    }
}
