package husaccttest.analyse;

import husacct.analyse.domain.famix.FamixCreationServiceImpl;
import husacct.analyse.domain.famix.FamixPersistencyServiceImpl;
import husacct.analyse.domain.famix.FamixQueryServiceImpl;
import husacct.analyse.task.AnalyseTaskControl;
import org.junit.Assert;
import org.junit.Test;

public class DummyTests {
    @Test
    public void kanEenAnalyseDoen() {
        FamixQueryServiceImpl queryService = new FamixQueryServiceImpl();
        FamixCreationServiceImpl creationService = new FamixCreationServiceImpl();
        FamixPersistencyServiceImpl persistencyService = new FamixPersistencyServiceImpl(queryService);
        AnalyseTaskControl atc = new AnalyseTaskControl(persistencyService, queryService);

        atc.analyseApplication(new String[]{"."}, "Java");
    }
}
