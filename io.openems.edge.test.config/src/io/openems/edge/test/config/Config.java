package io.openems.edge.test.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition( //
		name = "Config Component", //
		description = "This component holds some configurations")
@interface Config {

	@AttributeDefinition(name = "Component-ID", description = "Unique ID of this Component")
	String id() default "cmpConfig";

	@AttributeDefinition(name = "Language", description = "Language of System")
	Language language() default Language.GERMAN;
	
	String webconsole_configurationFactory_nameHint() default "Config Comp [{id}]";
}