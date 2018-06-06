package in.koodam.communication.component;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.metatype.annotations.Designate;

@Component(name = "KoodamMessageType", immediate = true, enabled = true, factory=KoodamMessageType.FACTORY)
@Designate(ocd = KoodamMessageTypeConfiguration.class, factory = true)
public class KoodamMessageType {
	
	public static final String FACTORY = "KoodamMessageType";

	private KoodamMessageTypeConfiguration koodamMessageTypeConfig;

	@Activate
	protected void activate(KoodamMessageTypeConfiguration koodamMessageTypeConfig) {

		this.koodamMessageTypeConfig = koodamMessageTypeConfig;

	}

	@Deactivate
	protected void deactivate() {

		this.koodamMessageTypeConfig = null;

	}

	public KoodamMessageTypeConfiguration getKoodamMessageTypeConfig() {
		
		return koodamMessageTypeConfig;
		
	}

}
