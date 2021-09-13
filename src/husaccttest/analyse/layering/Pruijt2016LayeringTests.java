package husaccttest.analyse.layering;

import husacct.analyse.domain.layering.Layer;
import husacct.analyse.domain.layering.Pruijt2016Layering;
import husacct.analyse.domain.layering.SoftwareUnit;
import husacct.analyse.domain.layering.SoftwareUnit.SoftwareUnitType;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class Pruijt2016LayeringTests {

    private SoftwareUnit presentation;
    private SoftwareUnit logic;
    private SoftwareUnit domain;

    private Pruijt2016Layering layeringAlgorithm;

    @Before
    public void setup() {
        presentation = new SoftwareUnit("Presentation", SoftwareUnitType.Package);
        logic = new SoftwareUnit("Logic", SoftwareUnitType.Package);
        domain = new SoftwareUnit("Domain", SoftwareUnitType.Package);
        layeringAlgorithm = new Pruijt2016Layering();
    }


    @Test
    public void canCreateSimpleLayeredModel() {
        presentation.dependsOn(logic, 1);
        logic.dependsOn(domain, 1);

        List<Layer> layers = layeringAlgorithm.reconstructArchitecture(10, Arrays.asList(presentation, logic, domain));

        assertEquals(presentation, layers.get(2).getContents().get(0));
        assertEquals(logic, layers.get(1).getContents().get(0));
        assertEquals(domain, layers.get(0).getContents().get(0));
    }

    @Test
    public void canCreateSingleLayerFromTooManyBackCalls() {
        logic.dependsOn(domain, 10);
        domain.dependsOn(logic, 8);

        Pruijt2016Layering layeringAlgorithm = new Pruijt2016Layering();

        List<Layer> layers = layeringAlgorithm.reconstructArchitecture(10, Arrays.asList(logic, domain));

        assertEquals(1, layers.size());
        assertEquals(2, layers.get(0).getContents().size());
    }
}
