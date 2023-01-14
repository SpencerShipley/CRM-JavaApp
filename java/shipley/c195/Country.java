package shipley.c195;

/**
 * Creates the country Class and methods getting and setting country data
 */
public class Country {
    private int countryID;
    private String country;

    public Country(int countryID, String country) {
        this.countryID = countryID;
        this.country = country;
    }

    public int getCountryID() {
        return countryID;
    }

    public String getCountry() {
        return country;
    }
}
