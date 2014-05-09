package husacct.validate.domain.validation.ruletype.relationruletypes;

import husacct.common.dto.DependencyDTO;
import husacct.common.dto.RuleDTO;
import husacct.validate.domain.configuration.ConfigurationServiceImpl;
import husacct.validate.domain.validation.Severity;
import husacct.validate.domain.validation.Violation;
import husacct.validate.domain.validation.ViolationType;
import husacct.validate.domain.validation.internaltransferobjects.Mapping;
import husacct.validate.domain.validation.logicalmodule.LogicalModule;
import husacct.validate.domain.validation.logicalmodule.LogicalModules;
import husacct.validate.domain.validation.ruletype.RuleType;
import husacct.validate.domain.validation.ruletype.RuleTypes;

import java.util.EnumSet;
import java.util.List;

public class MustUseRule extends RuleType {
	private final static EnumSet<RuleTypes> exceptionrules = EnumSet.of(RuleTypes.IS_ALLOWED_TO_USE, RuleTypes.IS_NOT_ALLOWED_TO_USE);

	public MustUseRule(String key, String category, List<ViolationType> violationtypes, Severity severity) {
		super(key, category, violationtypes, exceptionrules, severity);
	}

	@Override
	public List<Violation> check(ConfigurationServiceImpl configuration, RuleDTO rootRule, RuleDTO currentRule) {
		violations.clear();
		fromMappings = getAllClasspathsOfModule(currentRule.moduleFrom, currentRule.violationTypeKeys);
		toMappings = getAllClasspathsOfModule(currentRule.moduleTo, currentRule.violationTypeKeys);

		boolean isUsingModule = false;
		for (Mapping classPathFrom : fromMappings) {
			for (Mapping classPathTo : toMappings) {
				if(isUsingModule == true){
					break;
				}
				DependencyDTO[] dependencies = analyseService.getDependenciesFromTo(classPathFrom.getPhysicalPath(), classPathTo.getPhysicalPath());
				if(dependencies != null && dependencies.length > 0){
					for(DependencyDTO dependency : dependencies){
						if(dependency != null){
							isUsingModule = true;
							break;
						}
					}
				}
			}
		}
		
		if (!isUsingModule) {
			LogicalModule logicalModuleFrom = new LogicalModule(currentRule.moduleFrom.logicalPath, currentRule.moduleTo.type);
			LogicalModule logicalModuleTo = new LogicalModule(currentRule.moduleTo.logicalPath, currentRule.moduleTo.type);
			LogicalModules logicalModules = new LogicalModules(logicalModuleFrom, logicalModuleTo);
			Violation violation = createViolation(rootRule, logicalModules, configuration);
			violations.add(violation);
		}		
		
		return violations;
	}
}