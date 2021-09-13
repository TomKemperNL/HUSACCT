package husaccttest.analyse.layering;

import husacct.analyse.domain.layering.Layer;
import husacct.analyse.domain.layering.Pruijt2016Layering;
import husacct.analyse.domain.layering.SoftwareUnit;
import husacct.analyse.domain.layering.SoftwareUnit.SoftwareUnitType;

import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;

public class Pruijt2016LayeringTests {
    @Test
    public void canCreateSimpleLayeredModel(){
        SoftwareUnit presentation = new SoftwareUnit("Presentation", SoftwareUnitType.Package);
        SoftwareUnit logic = new SoftwareUnit("Logic", SoftwareUnitType.Package);
        SoftwareUnit domain = new SoftwareUnit("Domain", SoftwareUnitType.Package);

        presentation.dependsOn(logic);
        logic.dependsOn(domain);

        Pruijt2016Layering layeringAlgorithm = new Pruijt2016Layering();

        List<Layer> layers = layeringAlgorithm.reconstructArchitecture(10, Arrays.asList(presentation, logic, domain));

        assertEquals(presentation, layers.get(2));
        assertEquals(logic, layers.get(1));
        assertEquals(domain, layers.get(0));
    }
}
