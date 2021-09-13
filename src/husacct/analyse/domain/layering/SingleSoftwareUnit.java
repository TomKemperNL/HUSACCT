package husacct.analyse.domain.layering;

import java.util.*;
import java.util.stream.Collectors;

public class SingleSoftwareUnit implements SoftwareUnit {

    public enum SoftwareUnitType {Class, Package, Interface}

    private String name;
    private SoftwareUnitType type;
    private Collection<Dependency> dependencies;

    public SingleSoftwareUnit(String name, SoftwareUnitType type) {
        if (name == null) {
            throw new NullPointerException("name");
        }
        if (type == null) {
            throw new NullPointerException("type");
        }
        this.name = name;
        this.type = type;
        this.dependencies = new HashSet<>();
    }

    public SoftwareUnitType getType() {
        return type;
    }

    public void addDependency(SingleSoftwareUnit unit, int nrOfTimes) {
        if (nrOfTimes <= 0) {
            throw new IllegalArgumentException("A dependency depends on something at least once");
        }
        this.dependencies.add(new Dependency(this, unit, nrOfTimes));
    }

    @Override
    public Optional<Dependency> findDependency(SoftwareUnit unit) {
        return this.dependencies.stream().filter(d -> d.getTo().equals(unit)).findAny();
    }

    @Override
    public List<SoftwareUnit> flatten() {
        return Arrays.asList(this);
    }

    @Override
    public void cutDependency(SoftwareUnit unit) {
        List<Dependency> matchingDeps = this.dependencies.stream().filter(d -> d.getTo().equals(unit)).collect(Collectors.toList());
        for (Dependency match : matchingDeps) {
            this.dependencies.remove(match);
        }
    }

    @Override
    public List<Dependency> getDependencies(){
        return new ArrayList<>(this.dependencies);
    }

    @Override
    public boolean hasNoDependencies() {
        return this.dependencies.size() == 0;
    }

    public String getName() {
        return name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SingleSoftwareUnit that = (SingleSoftwareUnit) o;
        return name.equals(that.name) &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }
}
