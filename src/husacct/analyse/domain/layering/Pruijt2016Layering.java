package husacct.analyse.domain.layering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Pruijt2016Layering {

    private Collection<SoftwareUnit> applyBackcallTreshhold(int backcallTreshhold, Collection<SoftwareUnit> units) {
        for (SoftwareUnit unit : units) {
            for (Dependency dep : unit.getDependencies()) {
                if (dep.isWithinBackCallTreshhold(backcallTreshhold)) {
                    dep.cutDependency();
                }
            }
        }

        return units;
    }

    private Collection<SoftwareUnit> mergeCycles(Collection<SoftwareUnit> units) {
        for (SoftwareUnit unit : units) {
            for (Dependency dep : unit.getDependencies()) {
                if (dep.isCyclic()) {
                    units.remove(dep.getTo());
                    units.remove(dep.getFrom());
                    units.add(new MergedSoftwareUnit(Arrays.asList(dep.getTo(), dep.getFrom())));
                }
            }
        }

        return units;
    }

    private Layer reconstructLayer(Collection<SoftwareUnit> units) {
        List<SoftwareUnit> unitsWithNoDependencies =
                units.stream()
                        .filter(u -> u.hasNoDependencies())
                        .collect(Collectors.toList());

        if (unitsWithNoDependencies.size() == 0) {
            throw new RuntimeException("Cannot resolve layer when no units without dependencies exist");
        }

        units.removeAll(unitsWithNoDependencies);
        for (SoftwareUnit toRemove : unitsWithNoDependencies) {
            for (SoftwareUnit u : units) {
                u.cutDependency(toRemove);
            }
        }

        return new Layer("SomeLayer", unitsWithNoDependencies.stream().flatMap(u -> u.flatten().stream()).collect(Collectors.toList()));
    }

    public List<Layer> reconstructArchitecture(int backcallTreshhold, Collection<SoftwareUnit> units) {
        units = new ArrayList<>(units);

        units = applyBackcallTreshhold(backcallTreshhold, units);
        units = mergeCycles(units);

        List<Layer> layers = new ArrayList<>();

        while (units.size() > 0) {
            Layer newLayer = reconstructLayer(units);
            layers.add(newLayer);
        }

        return layers;
    }
}
