package husacct.analyse.domain.layering;


import java.util.*;
import java.util.stream.Collectors;

public interface SoftwareUnit {
    List<Dependency> getDependencies();

    void cutDependency(SoftwareUnit toRemove);

    default boolean hasNoDependenciesIn(Collection<SoftwareUnit> units) {
        List<SoftwareUnit> flattened = units.stream().flatMap(u -> u.flatten().stream()).collect(Collectors.toList());

        return this.getDependencies().stream().noneMatch(d -> flattened.contains(d.getTo()));
    }

    Optional<Dependency> findDependency(SoftwareUnit on);

    List<SoftwareUnit> flatten();

    SoftwareUnit deepClone(Map<SoftwareUnit, SoftwareUnit> replacements);

    static Collection<SoftwareUnit> clone(Collection<SoftwareUnit> units) {
        HashMap<SoftwareUnit, SoftwareUnit> replacements = new HashMap<>();

        return units.stream().map(u -> u.deepClone(replacements)).collect(Collectors.toList());
    }
}
