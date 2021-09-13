package husacct.analyse.domain.layering;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface SoftwareUnit {
    List<Dependency> getDependencies();

    void cutDependency(SoftwareUnit toRemove);

    default boolean hasNoDependenciesIn(Collection<SoftwareUnit> units) {
        List<SoftwareUnit> flattened = units.stream().flatMap(u -> u.flatten().stream()).collect(Collectors.toList());

        return !this.getDependencies().stream().anyMatch(d -> flattened.contains(d.getTo()));
    }

    Optional<Dependency> findDependency(SoftwareUnit on);

    List<SoftwareUnit> flatten();
}
