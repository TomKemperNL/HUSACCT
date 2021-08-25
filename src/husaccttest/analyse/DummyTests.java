package husaccttest.analyse;

import husacct.ServiceProvider;
import husacct.analyse.domain.famix.FamixCreationServiceImpl;
import husacct.analyse.domain.famix.FamixPersistencyServiceImpl;
import husacct.analyse.domain.famix.FamixQueryServiceImpl;
import husacct.analyse.task.AnalyseTaskControl;
import husacct.common.dto.SoftwareUnitDTO;
import husacct.common.enums.States;
import org.apache.log4j.BasicConfigurator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class DummyTests {
    @BeforeClass
    public static void configureLog4J(){
        BasicConfigurator.configure();
    }

    @Test
    public void kanEenAnalyseDoen() {
        //It's rrrrreally weird that this is necessary, and possible.
        ServiceProvider.getInstance().getControlService().getStates().add(States.ANALYSING);

        FamixQueryServiceImpl queryService = new FamixQueryServiceImpl();
        FamixCreationServiceImpl creationService = new FamixCreationServiceImpl();
        FamixPersistencyServiceImpl persistencyService = new FamixPersistencyServiceImpl(queryService);
        AnalyseTaskControl atc = new AnalyseTaskControl(persistencyService, queryService);

        //This .-path probably only works from the IDE, but that's fine -for now-
        atc.analyseApplication(new String[]{"./src/husacct"}, "Java");

        List<SoftwareUnitDTO> classes = queryService.getAllClasses();
        Assert.assertNotEquals(0, classes.size());

    }
}
