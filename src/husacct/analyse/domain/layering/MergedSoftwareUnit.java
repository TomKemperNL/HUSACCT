package husacct.analyse.domain.layering;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class MergedSoftwareUnit implements SoftwareUnit {

    private List<SoftwareUnit> units = new ArrayList<>();

    public MergedSoftwareUnit(List<SoftwareUnit> units) {
        this.units = units;
    }

    @Override
    public List<Dependency> getDependencies() {
        Map<SoftwareUnit, List<Dependency>> grouped = units.stream()
                .flatMap(u -> u.getDependencies().stream())
                .filter(d -> !this.units.contains(d.getTo()))
                .collect(Collectors.groupingBy(Dependency::getTo));

        List<Dependency> result = new ArrayList<>();

        for(SoftwareUnit u: grouped.keySet()){
            int totalDeps = grouped.get(u).stream().mapToInt(Dependency::getCount).sum();
            result.add(new Dependency(this, u, totalDeps));
        }
        return result;
    }

    @Override
    public void cutDependency(SoftwareUnit toRemove) {
        for (SoftwareUnit u : this.units) {
            u.cutDependency(toRemove);
        }
    }

    @Override
    public Optional<Dependency> findDependency(SoftwareUnit on) {
        return this.getDependencies().stream().filter(d -> d.getTo().equals(on)).findAny();
    }

    @Override
    public List<SoftwareUnit> flatten() {
        return units.stream().flatMap(u -> u.flatten().stream()).distinct().collect(Collectors.toList());
    }
}
