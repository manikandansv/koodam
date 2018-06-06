package in.koodam.mongodb.service.api;

public interface KoodamMongoPersistenceManager<T extends Object> {
	
	T persist(T entity);
	
	T update(T entity);
	
	T get(Class<T> clazz, final T id);
	
	long count(Class<T> clazz);

}
