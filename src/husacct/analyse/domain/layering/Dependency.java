package husacct.analyse.domain.layering;

public class Dependency {
    private SoftwareUnit to;
    private int count;

    public Dependency(SoftwareUnit to, int count) {
        this.to = to;
        this.count = count;
    }

    public SoftwareUnit getTo() {
        return to;
    }

    public int getCount() {
        return count;
    }
}
