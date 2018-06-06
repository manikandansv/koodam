package in.koodam.mongodb.service.provider;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.metatype.annotations.Designate;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.Tag;
import com.mongodb.TagSet;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoDatabase;

import in.koodam.mongodb.component.config.MongoClientConfiguration;
import in.koodam.mongodb.service.api.KoodamMongoProvider;

@Component(name = "KoodamMongoProvider", immediate = true, enabled = true, factory = KoodamMongoProvider.PID, service = KoodamMongoProvider.class)
@Designate(ocd = MongoClientConfiguration.class, factory = true)
public class KoodamMongoProviderImpl implements KoodamMongoProvider {

	private MongoClient mongoClient;
	
	private String dbName;
	
	private MongoClientConfiguration mongoClientConfiguration;

	@Activate
	protected void activate(MongoClientConfiguration mongoClientConfiguration) {
		
		this.mongoClientConfiguration = mongoClientConfiguration;

		List<String> uris = new ArrayList<String>();
		
		this.dbName = mongoClientConfiguration.dbName();
		
		System.out.println(this.dbName);

		boolean isValid = validateURI(mongoClientConfiguration.clientURI(), uris);
		
		System.out.println(isValid);

		if (isValid) {

			MongoClientOptions mongoClientOptions = createMongoClientOptions(mongoClientConfiguration);

			this.mongoClient = createMongoClient(uris, mongoClientOptions);
		}
		
		System.out.println(this.mongoClient);
		
	}
	
	@Deactivate
	protected void deactivate(){
		
		if (mongoClient != null){
			
			mongoClient.close();
			
			mongoClient = null;
			
		}
		
	}

	@Override
	public MongoClient getMongoClient() {
		
		return mongoClient;
		
	}
	
	@Override
	public MongoClientConfiguration getMongoClientConfiguration(){
		
		return mongoClientConfiguration;
		
	}

	@Override
	public MongoDatabase getMongoDatabase() {

		if (mongoClient != null && StringUtils.isNotEmpty(dbName)) {

			return mongoClient.getDatabase(dbName);

		}
		
		return null;
		
	}

	private ServerAddress createServerAddress(String uriProperty) throws URISyntaxException, UnknownHostException {
		URI uri = new URI(uriProperty);
		int port = uri.getPort();
		ServerAddress serverAddress = port == -1 ? new ServerAddress(uri.getHost())
				: new ServerAddress(uri.getHost(), uri.getPort());
		return serverAddress;
	}

	private boolean validateURI(String value, Collection<String> uris) {

		if (StringUtils.isNotEmpty(value)) {
			// The regex \s matches whitepsace. The extra \ is needed because of
			// how
			// it's treated in java
			// strings. The split is done on any number of whitespace chars
			// followed by
			// a comma followed by
			// any number of whitespace chars. What is left is the URI(s).

			for (String targetURI : value.split("\\s*,\\s*")) {
				String uri = targetURI.trim();
				String[] segments = uri.split("/");

				if (!uri.startsWith("mongodb://") || uri.endsWith("/") || segments.length < 3 || segments.length > 5)
					return false;

				if (uris != null)
					uris.add(uri);
			}
		} else {
			return false;
		}

		return true;

	}

	private MongoClientOptions createMongoClientOptions(MongoClientConfiguration mongoClientConfiguration) {

		MongoClientOptions.Builder optionsBuilder = new MongoClientOptions.Builder();
		optionsBuilder.alwaysUseMBeans(mongoClientConfiguration.alwaysUseMBeans());
		optionsBuilder.connectionsPerHost(mongoClientConfiguration.connectionsPerHost());
		optionsBuilder.connectTimeout(mongoClientConfiguration.connectionTimeout());
		optionsBuilder.cursorFinalizerEnabled(mongoClientConfiguration.cursorFinalizerEnabled());
		optionsBuilder.description(mongoClientConfiguration.description());
		optionsBuilder.heartbeatConnectTimeout(mongoClientConfiguration.heartbeatConnectTimeout());
		optionsBuilder.heartbeatFrequency(mongoClientConfiguration.heartbeatFrequency());
		optionsBuilder.heartbeatSocketTimeout(mongoClientConfiguration.heartbeatSocketTimeout());
		optionsBuilder.localThreshold(mongoClientConfiguration.localThreshold());
		optionsBuilder.maxConnectionIdleTime(mongoClientConfiguration.maxConnectionIdleTime());
		optionsBuilder.maxConnectionLifeTime(mongoClientConfiguration.maxConnectionLifeTime());
		optionsBuilder.maxWaitTime(mongoClientConfiguration.maxWaitTime());
		optionsBuilder.minConnectionsPerHost(mongoClientConfiguration.minConnectionsPerHost());
		optionsBuilder.minHeartbeatFrequency(mongoClientConfiguration.minHeartbeatFrequency());
		optionsBuilder.serverSelectionTimeout(mongoClientConfiguration.serverSelectionTimeout());
		optionsBuilder.socketKeepAlive(mongoClientConfiguration.socketKeepAlive());
		optionsBuilder.socketTimeout(mongoClientConfiguration.socketTimeout());
		optionsBuilder.sslEnabled(mongoClientConfiguration.sslEnabled());
		optionsBuilder.sslInvalidHostNameAllowed(mongoClientConfiguration.sslInvalidHostNameAllowed());
		optionsBuilder.threadsAllowedToBlockForConnectionMultiplier(
				mongoClientConfiguration.threadsAllowedToBlockForConnectionMultiplier());

		WriteConcern writeConcern = new WriteConcern(mongoClientConfiguration.writeConcernW(),
				mongoClientConfiguration.writeConcernWtimeout());

		writeConcern.withJournal(mongoClientConfiguration.writeConcernJ());

		if (mongoClientConfiguration.requiredReplicaSetName() != null
				&& !mongoClientConfiguration.requiredReplicaSetName().isEmpty())
			optionsBuilder.requiredReplicaSetName(mongoClientConfiguration.requiredReplicaSetName());

		List<Tag> tags = new ArrayList<>();

		if (mongoClientConfiguration.readPreferenceTags() != null) {
			for (String tag : mongoClientConfiguration.readPreferenceTags()) {
				String[] elements = tag.split("=");
				tags.add(new Tag(elements[0], elements[1]));
			}
		}

		TagSet tagSet = new TagSet(tags);

		switch (mongoClientConfiguration.readPreferenceType()) {
		case 1:
			optionsBuilder.readPreference(ReadPreference.nearest(tagSet));
			break;
		case 2:
			optionsBuilder.readPreference(ReadPreference.primary());
			break;
		case 3:
			optionsBuilder.readPreference(ReadPreference.primaryPreferred(tagSet));
			break;
		case 4:
			optionsBuilder.readPreference(ReadPreference.secondary(tagSet));
			break;
		case 5:
			optionsBuilder.readPreference(ReadPreference.secondaryPreferred(tagSet));
			break;
		}

		return optionsBuilder.build();

	}

	private MongoClient createMongoClient(List<String> uriList, MongoClientOptions options) {

		String currentURI = null;

		List<ServerAddress> serverAddresses = new ArrayList<>(uriList.size());
		try {

			for (String uri : uriList) {

				currentURI = uri;

				serverAddresses.add(createServerAddress(currentURI));

			}

			if (serverAddresses.size() == 1)
				return new MongoClient(serverAddresses.get(0), options);
			else
				return new MongoClient(serverAddresses, options);
		} catch (UnknownHostException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
