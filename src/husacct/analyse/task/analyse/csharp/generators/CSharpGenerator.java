package husacct.analyse.task.analyse.csharp.generators;

import husacct.analyse.domain.IModelCreationService;
import husacct.analyse.domain.famix.FamixCreationServiceImpl;

abstract class CSharpGenerator {

    public CSharpGenerator(IModelCreationService modelCreationService){
        this.modelService = modelCreationService;
    }

    protected IModelCreationService modelService;
}
