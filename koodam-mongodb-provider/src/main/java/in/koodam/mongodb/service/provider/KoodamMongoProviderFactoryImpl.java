package in.koodam.mongodb.service.provider;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

import in.koodam.mongodb.service.api.KoodamMongoProvider;
import in.koodam.mongodb.service.api.KoodamMongoProviderFactory;

@Component(enabled=true,immediate=true,name="KoodamMongoProviderFactory",service=KoodamMongoProviderFactory.class)
public class KoodamMongoProviderFactoryImpl implements KoodamMongoProviderFactory {

	private Map<String, KoodamMongoProvider> map;

	@Override
	public KoodamMongoProvider getKoodamMongoProvider(String clientId) {
		
		if (map != null && map.containsKey(clientId)) {

			return map.get(clientId);

		}

		return null;
	}

	@Reference(name = "KoodamMongoProvider", cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	protected void bindKoodamMongoProvider(KoodamMongoProvider koodamMongoProvider) {

		if (map == null) {

			map = new HashMap<String, KoodamMongoProvider>();

		}

		map.put(koodamMongoProvider.getMongoClientConfiguration().clientId(), koodamMongoProvider);

	}

	@Reference(name = "KoodamMongoProvider", cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	protected void unbindKoodamMongoProvider(KoodamMongoProvider koodamMongoProvider) {

		if (map != null) {

			map.remove(koodamMongoProvider.getMongoClientConfiguration().clientId());

		}

	}

}
