package dns.client;

/**
 * Created by mrchebik on 08.01.17.
 */
public enum Flag {
    TC(false);

    private boolean set;

    Flag(boolean initialSet) {
        this.set = initialSet;
    }

    public boolean isSet() {
        return set;
    }

    public void set(boolean set) {
        this.set = set;
    }
}
