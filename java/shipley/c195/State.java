package shipley.c195;


/**
 * Creates the State Class and methods getting and setting state data
 */
public class State {
    private int state;
    private String stateName;
    private int countryID;

    public State(int state, String stateName, int countryID) {
        this.state = state;
        this.stateName = stateName;
        this.countryID = countryID;
    }

    public int getState() {
        return state;
    }

    public String getStateName() {
        return stateName;
    }

    public int getCountryID() {
        return countryID;
    }
}
