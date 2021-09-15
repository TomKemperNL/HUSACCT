package husacct.analyse.domain.layering;

import java.util.*;
import java.util.stream.Collectors;

public class Pruijt2016Layering {

    private void mergeCycles(Collection<SoftwareUnit> units) {

        Set<Set<Dependency>> cycles = new HashSet<>();

        for (SoftwareUnit unit : units) {
            for (Dependency dep : unit.getDependencies()) {
                Optional<Set<Dependency>> maybeCycle = dep.findCycle();
                maybeCycle.ifPresent(cycles::add);
            }
        }

        for (Set<Dependency> cycle : cycles) {
            Set<SoftwareUnit> toBeMerged = new HashSet<>();
            for (Dependency part : cycle) {
                units.remove(part.getTo());
                units.remove(part.getFrom());
                toBeMerged.add(part.getTo());
                toBeMerged.add(part.getFrom());
            }

            units.add(new MergedSoftwareUnit(new ArrayList<>(toBeMerged)));
        }
    }

    private void applyBackcallTreshhold(int backcallTreshhold, Collection<SoftwareUnit> units) {
        for (SoftwareUnit unit : units) {
            for (Dependency dep : unit.getDependencies()) {
                if (dep.isWithinBackCallTreshhold(backcallTreshhold)) {
                    dep.cutDependency();
                }
            }
        }
    }

    private Layer reconstructLayer(Collection<SoftwareUnit> units) {
        List<SoftwareUnit> unitsWithNoDependencies =
                units.stream()
                        .filter(u -> u.hasNoDependenciesIn(units))
                        .collect(Collectors.toList());

        if (unitsWithNoDependencies.size() == 0) {
            throw new RuntimeException("Cannot resolve layer when no units without dependencies exist");
        }

        units.removeAll(unitsWithNoDependencies);

        return new Layer("SomeLayer", unitsWithNoDependencies.stream().flatMap(u -> u.flatten().stream()).distinct().collect(Collectors.toList()));
    }

    public List<Layer> reconstructArchitecture(int backcallTreshhold, Collection<SoftwareUnit> units) {
        units = SoftwareUnit.clone(units);

        applyBackcallTreshhold(backcallTreshhold, units);
        mergeCycles(units);

        List<Layer> layers = new ArrayList<>();

        while (units.size() > 0) {
            Layer newLayer = reconstructLayer(units);
            layers.add(newLayer);
        }

        return layers;
    }
}
