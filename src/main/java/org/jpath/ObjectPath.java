package org.jpath;
/**
 * 
 */


import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;

public interface ObjectPath {

	ObjectPath uniques();
	
	ObjectPath collect(Transformer closure);
	
//	ObjectPath sort(Comparator<String> comparator);
	
//	<T> T as(Class<T> clazz);
	
	ObjectPath findAll(Predicate predicate);
	
//	ObjectPath find(Predicate predicate);
	
	Object first();

//	List<Map<String,String>> asEntries();
}