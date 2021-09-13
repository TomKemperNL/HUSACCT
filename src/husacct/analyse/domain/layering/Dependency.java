package husacct.analyse.domain.layering;

import javax.swing.text.html.Option;
import java.util.Optional;

public class Dependency {
    private SoftwareUnit to;
    private SoftwareUnit from;
    private int count;

    public Dependency(SoftwareUnit from, SoftwareUnit to, int count) {
        this.to = to;
        this.from = from;
        this.count = count;
    }

    public SoftwareUnit getTo() {
        return to;
    }

    public int getCount() {
        return count;
    }

    public boolean isBackCall() {
        Optional<Dependency> possiblyOther = to.findDependency(from);
        return possiblyOther.isPresent();
    }

    public boolean isWithinBackCallTreshhold(int treshhold) {
        Optional<Dependency> possiblyOther = to.findDependency(from);

        if (possiblyOther.isPresent()) {
            Dependency other = possiblyOther.get();

            double value = ((double) this.count / (double) other.count) * 100;
            return value <= treshhold;
        }

        return false;
    }

    public void cutDependency() {
        this.from.cutDependency(this.to);
    }
}
