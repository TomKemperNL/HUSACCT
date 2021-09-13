package husaccttest.analyse.layering;

import husacct.analyse.domain.layering.Layer;
import husacct.analyse.domain.layering.Pruijt2016Layering;
import husacct.analyse.domain.layering.SingleSoftwareUnit;
import husacct.analyse.domain.layering.SingleSoftwareUnit.SoftwareUnitType;

import husacct.analyse.domain.layering.SoftwareUnit;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class Pruijt2016LayeringTests {

    private SingleSoftwareUnit presentation;
    private SingleSoftwareUnit logic;
    private SingleSoftwareUnit domain;

    private Pruijt2016Layering layeringAlgorithm;

    @Before
    public void setup() {
        presentation = new SingleSoftwareUnit("Presentation", SoftwareUnitType.Package);
        logic = new SingleSoftwareUnit("Logic", SoftwareUnitType.Package);
        domain = new SingleSoftwareUnit("Domain", SoftwareUnitType.Package);
        layeringAlgorithm = new Pruijt2016Layering();
    }


    @Test
    public void canCreateSimpleLayeredModel() {
        presentation.addDependency(logic, 1);
        logic.addDependency(domain, 1);

        List<Layer> layers = layeringAlgorithm.reconstructArchitecture(10, Arrays.asList(presentation, logic, domain));

        assertEquals(presentation, layers.get(2).getContents().get(0));
        assertEquals(logic, layers.get(1).getContents().get(0));
        assertEquals(domain, layers.get(0).getContents().get(0));
    }

    @Test
    public void canCreateSingleLayerFromTooManyBackCalls() {
        logic.addDependency(domain, 10);
        domain.addDependency(logic, 8);

        Pruijt2016Layering layeringAlgorithm = new Pruijt2016Layering();

        List<Layer> layers = layeringAlgorithm.reconstructArchitecture(10, Arrays.asList(logic, domain));

        assertEquals(1, layers.size());
        assertEquals(2, layers.get(0).getContents().size());
    }

    @Test
    public void canCreateSeperateLayersFromAFewBackCalls() {
        logic.addDependency(domain, 100);
        domain.addDependency(logic, 1);

        Pruijt2016Layering layeringAlgorithm = new Pruijt2016Layering();

        List<Layer> layers = layeringAlgorithm.reconstructArchitecture(10, Arrays.asList(logic, domain));

        assertEquals(2, layers.size());
        assertEquals(domain, layers.get(0).getContents().get(0));
        assertEquals(logic, layers.get(1).getContents().get(0));
    }
}
