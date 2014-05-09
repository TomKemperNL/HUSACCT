package husacct.analyse;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JInternalFrame;

import husacct.common.dto.AnalysedModuleDTO;
import husacct.common.dto.ApplicationDTO;
import husacct.common.dto.DependencyDTO;
import husacct.common.dto.ExternalSystemDTO;
import husacct.common.dto.ProjectDTO;
import husacct.common.savechain.ISaveable;
import husacct.common.services.IObservableService;

public interface IAnalyseService extends IObservableService, ISaveable {

    public String[] getAvailableLanguages();

    public void analyseApplication(ProjectDTO project);

    public boolean isAnalysed();

    public JInternalFrame getJInternalFrame();

    //The following function has been inserted due to performance issues. The function enables
    //function-users to use cache-mechanisms and special search-algorithms
    public DependencyDTO[] getAllDependencies();
    
    // Returns unique names of all types (classes, interfaces, inner classes) of SoftwareUnit with uniqueName  
    public List<String> getAllPhysicalClassPathsOfSoftwareUnit(String uniqueName);
    
    public DependencyDTO[] getAllUnfilteredDependencies();

    public DependencyDTO[] getDependencies(String from, String to);

    public DependencyDTO[] getDependenciesFrom(String from);

	// Returns a list of dependencies between the fromClass and toClass.
    // Fast function, based on HashMap get-search. Both class paths should match exactly to a uniqueName of a type! 
    public DependencyDTO[] getDependenciesFromTo(String classPathFrom, String classPathTo);
	
    public DependencyDTO[] getDependenciesTo(String to);

    public DependencyDTO[] getDependencies(String from, String to, String[] dependencyFilter);

    public DependencyDTO[] getDependenciesFrom(String from, String[] dependencyFilter);

    public DependencyDTO[] getDependenciesTo(String to, String[] dependencyFilter);

    public AnalysedModuleDTO getModuleForUniqueName(String uniquename);

    public AnalysedModuleDTO[] getRootModules();
    
    public AnalysedModuleDTO[] getChildModulesInModule(String from);

    public AnalysedModuleDTO[] getChildModulesInModule(String from, int depth);

    public AnalysedModuleDTO getParentModuleForModule(String child);

    public void exportDependencies(String fullPath);
    
    public void logHistory(ApplicationDTO applicationDTO, String workspaceName);
    
    public int getAmountOfDependencies();

    public int getAmountOfInterfaces();
    
    public int getAmountOfPackages();
    
    public int getAmountOfClasses();
    
}
