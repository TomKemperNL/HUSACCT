package husaccttest.analyse.layering;

import husacct.analyse.domain.layering.MergedSoftwareUnit;
import husacct.analyse.domain.layering.SingleSoftwareUnit;
import husacct.analyse.domain.layering.SingleSoftwareUnit.SoftwareUnitType;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Arrays;

public class MergedSoftwareUnitTests {

    @Test
    public void getDependenciesOnlyReturnsOutsideDependencies() {
        SingleSoftwareUnit someUnit = new SingleSoftwareUnit("SomeUnit", SoftwareUnitType.Package);
        SingleSoftwareUnit someOtherUnit = new SingleSoftwareUnit("SomeOtherUnit", SoftwareUnitType.Package);
        SingleSoftwareUnit someUnRelatedUnit = new SingleSoftwareUnit("SomeOutsideUnit", SoftwareUnitType.Package);

        someUnit.addDependency(someOtherUnit, 10);

        someUnit.addDependency(someUnRelatedUnit, 3);
        someOtherUnit.addDependency(someUnRelatedUnit, 4);

        MergedSoftwareUnit merged = new MergedSoftwareUnit(Arrays.asList(someUnit, someOtherUnit));

        assertEquals(1, merged.getDependencies().size());
        assertEquals(7, merged.getDependencies().get(0).getCount());

    }

    @Test
    public void somethingWithInnerDeps() {
        SingleSoftwareUnit someUnit = new SingleSoftwareUnit("SomeUnit", SoftwareUnitType.Package);
        SingleSoftwareUnit someOtherUnit = new SingleSoftwareUnit("SomeOtherUnit", SoftwareUnitType.Package);
        SingleSoftwareUnit someOuterUnit = new SingleSoftwareUnit("SomeOuterUnit", SoftwareUnitType.Package);

        someUnit.addDependency(someOtherUnit, 10);

        someOuterUnit.addDependency(someOtherUnit, 5);

        MergedSoftwareUnit merged = new MergedSoftwareUnit(Arrays.asList(someUnit, someOtherUnit));

        assertEquals(false, someOuterUnit.hasNoDependenciesIn(Arrays.asList(merged, someOuterUnit)));

    }
}
