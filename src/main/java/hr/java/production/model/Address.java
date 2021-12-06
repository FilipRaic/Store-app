package hr.java.production.model;

import hr.java.production.enums.Cities;

import java.util.Objects;

/**
 * Class used for creating an object that stores information of addresses for factories and stores
 */
public class Address {
    private String street;
    private String houseNumber;
    private Cities city;

    /**
     * Builder pattern for address objects
     */
    public static class Builder {
        private final String street;
        private String houseNumber;
        private Cities city;

        /**
         * Builder constructor that only takes String street
         *
         * @param street Used to store street name
         */
        public Builder(String street) {
            this.street = street;
        }

        /**
         * Builder constructor that only takes String houseNumber
         *
         * @param houseNumber Used to store house number
         * @return Returns a reference on houseNumber for the current object
         */
        public Builder withHouseNumber(String houseNumber) {
            this.houseNumber = houseNumber;
            return this;
        }

        /**
         * Builder constructor that only takes String city
         *
         * @param city Used to store city name
         * @return Returns a reference on city for the current object
         */
        public Builder withCity(Cities city) {
            this.city = city;
            return this;
        }

        /**
         * Builds the object
         *
         * @return Returns an object of type address
         */
        public Address build() {
            Address address = new Address();
            address.street = this.street;
            address.houseNumber = this.houseNumber;
            address.city = this.city;

            return address;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Builder builder = (Builder) o;
            return Objects.equals(street, builder.street) && Objects.equals(houseNumber, builder.houseNumber) && Objects.equals(city, builder.city);
        }

        @Override
        public int hashCode() {
            return Objects.hash(street, houseNumber, city);
        }
    }
    private Address() {}

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public Cities getCity() {
        return city;
    }

    public void setCity(Cities city) {
        this.city = city;
    }
}