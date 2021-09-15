package husacct.analyse.domain.layering;

import java.util.*;

public class Dependency {
    private final SoftwareUnit to;
    private final SoftwareUnit from;
    private final int count;

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

    public Optional<Set<Dependency>> findCycle() {
        Optional<Dependency> possiblyOther = this.to.findDependency(this.from);

        if (possiblyOther.isPresent()) {
            Set<Dependency> result = new HashSet<>();
            result.add(this);
            result.add(possiblyOther.get());

            return Optional.of(result);
        }
        //TODO: detect longer cycles

        return Optional.empty();
    }

    public SoftwareUnit getFrom() {
        return this.from;
    }

    Dependency deepClone(Map<SoftwareUnit, SoftwareUnit> replacements) {
        if (replacements.get(this.from) == null) {
            replacements.put(this.from, this.from.deepClone(replacements));
        }

        if (replacements.get(this.to) == null) {
            replacements.put(this.to, this.to.deepClone(replacements));
        }

        return new Dependency(replacements.get(this.from), replacements.get(this.to), this.count);
    }
}
