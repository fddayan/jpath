package org.jpath;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;
import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.math.NumberUtils;

public class JPath {

	public static Entry<String,String> e(String key, String value) {
		return new EntryImpl(key,value);
	}

	public static Predicate p(String jexlExp) {
		final Expression e = createExpression(jexlExp);
	
		return new Predicate() {
			public boolean evaluate(Object object) {
				JexlContext jc = new MapContext();
				jc.set("it",object);
	
				Object o = e.evaluate(jc);
	
				return Boolean.parseBoolean(o.toString());
			}
		};
	}
	
	public static Transformer t(String jexlExp) {
		final Expression e = createExpression(jexlExp);
		
		return new Transformer() {
			public Object transform(Object object) {
				JexlContext jc = new MapContext();
				jc.set("it",object);
				
				Object o = e.evaluate(jc);
				
				return ObjectUtils.toString(o,"");
			}
		};
	}

	private static Expression createExpression(String jexlExp) {
		final JexlEngine jexl = getJexlEngine();
		final Expression e = jexl.createExpression(jexlExp);
		return e;
	}
	
	private static synchronized JexlEngine getJexlEngine() {
		jexl.setSilent(true);
		return jexl;
	}
	
	public static Comparator c(String jexlExp) {
		final Expression e = createExpression(jexlExp);
		
		return new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				JexlContext jc = new MapContext();
				jc.set("it",o1);
				jc.set("other",o2);
				
				Object o = e.evaluate(jc);
				
				if (o==null){
					return -1;
				} else {
					return NumberUtils.toInt(o.toString(),0);
				}
			}
		};
	}

	static JexlEngine jexl =  new JexlEngine();
	
	public static Object fildValue(String field, Object o) throws NoSuchFieldException, IllegalAccessException {
		boolean accessChanged = false;
		Field fieldInstance = o.getClass().getDeclaredField(field);
		
		if (!fieldInstance.isAccessible()) {
			fieldInstance.setAccessible(true);
			accessChanged = true;
		}
		
		Object val = fieldInstance.get(o);
		
		if (accessChanged)
			fieldInstance.setAccessible(false);
		return val;
	}

	public static <T> List<T> List(T...objects) {
		List<T> list = new ArrayList<T>();
	
		for (T t:objects) {
			list.add(t);
		}
	
		return list;
	}

	public static Map<String,String> Map(Entry<String,String>...entries) {
		Map<String,String> map = new HashMap<String,String>();
	
		for (Entry<String,String> e:entries) {
			map.put(e.getKey(),e.getValue());
		}
	
		return map;
	}

	public static MapPath on(List<? extends Object> list, String...fields) {
		List<Map<String,String>> objects = new ArrayList<Map<String,String>>();
		
		try {
			for (Object o : list) {
				Map<String,String> entryMap = new HashMap<String,String>();
				
				for (String field:fields){
					Object val = fildValue(field, o);
				
					entryMap.put(field,val.toString());
				}
				
				objects.add(entryMap);
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
	
		return new MapPath(objects);
	}

	public static MapPath on(List<Map<String, String>> map) {
		return new MapPath(map);
	}

	public static Find where(String field) {
		return new Find(field);
	}

}
