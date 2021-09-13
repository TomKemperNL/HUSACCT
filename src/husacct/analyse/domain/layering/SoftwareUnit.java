package husacct.analyse.domain.layering;

import java.util.List;
import java.util.Optional;

public interface SoftwareUnit {
    List<Dependency> getDependencies();

    void cutDependency(SoftwareUnit toRemove);

    default boolean hasNoDependencies() {
        return this.getDependencies().size() == 0;
    }

    Optional<Dependency> findDependency(SoftwareUnit on);

    List<SoftwareUnit> flatten();
}
