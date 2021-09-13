package husacct.analyse.domain.layering;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

public class SoftwareUnit {


    public enum SoftwareUnitType { Class, Package, Interface }

    private String name;
    private SoftwareUnitType type;
    private Collection<SoftwareUnit> dependencies;

    public SoftwareUnit(String name, SoftwareUnitType type){
        if(name == null){
            throw new NullPointerException("name");
        }
        if(type == null){
            throw new NullPointerException("type");
        }
        this.name = name;
        this.type = type;
        this.dependencies = new HashSet<>();
    }

    public SoftwareUnitType getType() {
        return type;
    }

    public void dependsOn(SoftwareUnit unit) {
        this.dependencies.add(unit);
    }

    public void cutDependency(SoftwareUnit unit){
        this.dependencies.remove(unit);
    }

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
        SoftwareUnit that = (SoftwareUnit) o;
        return name.equals(that.name) &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }
}
