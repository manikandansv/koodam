package in.koodam.mongodb.service.api;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import in.koodam.mongodb.component.config.MongoClientConfiguration;

public interface KoodamMongoProvider {
	
	String PID = "in.koodam.mongodb.service.api.KoodamMongoProvider";

	MongoClient getMongoClient();
	
	MongoDatabase getMongoDatabase();

	MongoClientConfiguration getMongoClientConfiguration();
	
}
