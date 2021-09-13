package husacct.analyse.domain.layering;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Pruijt2016Layering {

    private Collection<SoftwareUnit> applyBackcallTreshhold(int backcallTreshhold, Collection<SoftwareUnit> units) {
        return units;
    }

    private Layer reconstructLayer(Collection<SoftwareUnit> units) {
        List<SoftwareUnit> unitsWithNoDependencies =
                units.stream()
                        .filter(SoftwareUnit::hasNoDependencies)
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

        return new Layer("SomeLayer", unitsWithNoDependencies);
    }

    public List<Layer> reconstructArchitecture(int backcallTreshhold, Collection<SoftwareUnit> units) {
        units = new ArrayList<>(units);

        units = applyBackcallTreshhold(backcallTreshhold, units);

        List<Layer> layers = new ArrayList<>();

        while (units.size() > 0) {
            Layer newLayer = reconstructLayer(units);
            layers.add(newLayer);
        }

        return layers;
    }
}
