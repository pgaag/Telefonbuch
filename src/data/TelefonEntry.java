package data;

import java.util.Objects;

public class TelefonEntry {
    private String firstName;
    private String lastName;

    // number is a String because User might save the number with a plus for country codes (+49) or with a slash 0165/xxxxxx
    private String number;

    public TelefonEntry(String firstName, String lastName, String number) {
        setFirstName(firstName);
        setLastName(lastName);
        setNumber(number);
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TelefonEntry that = (TelefonEntry) o;
        return Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, number);
    }
}
