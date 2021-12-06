package hr.java.production.enums;

/**
 * Enumeration used to create cities
 */
public enum Cities {

    GRAD_ZAGREB("10000", "Grad Zagreb"),
    SAMOBOR("10430", "Samobor"),
    SVETA_NEDELJA("10431", "Sveta Nedelja"),
    KARLOVAC("47000", "Karlovac");

    private final String postalCode;
    private final String cityName;

    /**
     * Consturctor for Cities
     *
     * @param postalCode String used to represent a cities postal code
     * @param cityName String used to represent a cities name
     */
    Cities(String postalCode, String cityName) {
        this.postalCode = postalCode;
        this.cityName = cityName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCityName() {
        return cityName;
    }

}
