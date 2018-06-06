package in.koodam.communication.component;

import org.apache.commons.lang3.StringUtils;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "Koodam MessageType Configuration", description = "The configuration for a messagtype")
public @interface KoodamMessageTypeConfiguration {

	@AttributeDefinition(name="Message Code", type = AttributeType.STRING)
	String messageCode() default StringUtils.EMPTY;

	@AttributeDefinition(name="Message Id", type = AttributeType.INTEGER)
	int messageId() default 0;

	@AttributeDefinition(name="Message Type", type = AttributeType.STRING)
	String messageType() default StringUtils.EMPTY;

}
