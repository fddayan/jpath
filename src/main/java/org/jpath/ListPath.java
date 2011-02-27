package org.jpath;
/**
 * 
 */


import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;

public class ListPath {

	List<String> list;

	public ListPath(List<String> list) {
		super();
		this.list = list;
	}

	/**
	 * Converts the objects in context to specified type. 
	 * Class type must have a constructor receives one string as an arguments
	 * 
	 * @param <T> 
	 * @param class1 This class must have a constructor receives a string.
	 * 
	 * @return A converted list type.
	 */
	public <T> List<T> as(Class<T> class1) {
		try {
			Constructor<T> constructor = class1.getConstructor(String.class);


			List<T> intList = new ArrayList<T>();

			for (String val:list){
				intList.add(constructor.newInstance(val));
			}

			return intList;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
	}
	
	public String max() {
		return Collections.max(list);
	}
	
	public String min() {
		return Collections.min(list);
	}
	
	public int sum() {
		List<Integer> ints = toIntegers();
		
		int sum = 0;
		
		for (Integer i: ints) {
			sum+=i;
		}
		
		return sum;
	}

	public List<Integer> toIntegers() {
		List<Integer> intList = new ArrayList<Integer>();

		for (String val:list){
			intList.add(Integer.valueOf(val));
		}

		return intList;
	}

	public List<String> value() {
		return list;
	}

	public ListPath transform(Transformer transformer) {
		List<String> list = new ArrayList<String>();

		list.addAll(CollectionUtils.transformedCollection(list,transformer));

		return new ListPath(list);
	}

	public ListPath unique() {
		Set<String> set = new HashSet<String>();

		List<String> list = new ArrayList<String>();

		return new ListPath(list);
	}

	public ListPath findAll(Predicate predicate) {
		Collection<String> selected = CollectionUtils.select(list,predicate);

		return new ListPath(new ArrayList<String>(selected));
	}

	public String find(Predicate predicate) {
		return findAll(predicate).first();
	}

	public String first() {
		return list.get(0);
	}

	public ListPath Sort() {
		Collections.sort(list);

		return this;
	}

	public String join(String delimiter) {
		return StringUtils.join(list,delimiter);
	}

}