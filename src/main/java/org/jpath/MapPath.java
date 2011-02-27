package org.jpath;
/**
 * 
 */


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;

public class MapPath implements ObjectPath {

	private List<Map<String, String>> map;

	public MapPath(List<Map<String, String>> map) {
		this.map = map;
	}

	public MapPath Shuffle() {
		Collections.shuffle(map);
		
		return this;
	}

	public boolean any(Predicate el) {
		return CollectionUtils.exists(map,el);
	}
	
	public boolean all(Predicate el){
		return count(el) == map.size();
	}
	
	public int count(Predicate predicate) {
		return CollectionUtils.countMatches(map,predicate);
	}

	public Map<String, String> find(Predicate predicate) {
		return findAll(predicate).first();
	}

	public Map<String,String> first() {
		return map.get(0);
	}

	public ListPath collect(String field) {
		List<String> list = new ArrayList<String>();

		for (Map<String,String> entry:map) {
			if (entry.containsKey(field)) {
				list.add(entry.get(field));
			}
		}

		return new ListPath(list);
	}

	public Map<String,List<Map<String,String>>> groupBy(String field) {
		Map<String,List<Map<String,String>>> grouped = new HashMap<String,List<Map<String,String>>>();

		for (Map<String, String> entry:map) {
			if (!grouped.containsKey(entry.get(field))) {
				grouped.put(entry.get(field),new ArrayList<Map<String,String>>());
			}

			grouped.get(entry.get(field)).add(entry);
		}

		return grouped;
	}

	public MapPath sort(final String field) {
		return sort(field,1);
	}
	
	public MapPath Sort(Comparator<Map<String,String>> comparator) {
		Collections.sort(map,comparator);
		
		return this;
	}

	public MapPath sort(final String field,final int direcction) {
		Collections.sort(map,new Comparator<Map<String, String>>() {
			public int compare(Map<String, String> o1,Map<String, String> o2) {
				if (direcction<0) {
					Map<String, String>  tmp  = o1;
					o1 = o2;
					o2 = tmp;
				}

				if (o1.containsKey(field) && o2.containsKey(field)) {
					return o1.get(field).compareTo(o2.get(field));
				} else if (!o1.containsKey(field) && !o2.containsKey(field)) { 
					return 0;
				} else {
					if (!o1.containsKey(field)) {
						return 1;
					} else {
						return -1;
					}
				}
			}
		});

		return this;

	}

	public MapPath findAll(Predicate predicate) {
		List<Map<String, String>> list = newList();

		for (Map<String,String> entry:map) {
			if (predicate.evaluate(entry)) {
				list.add(entry);	
			}
		}

		return new MapPath(list);
	}

	public MapPath uniques() {
		HashSet<Map<String, String>> set = new HashSet<Map<String, String>>();

		set.addAll(map);

		List<Map<String, String>> list = newList();

		list.addAll(set);

		return new MapPath(list);
	}

	private List<Map<String, String>> newList() {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		return list;
	}

	public MapPath uniques(String field) {
		Map<String,Map<String, String>> list = new HashMap<String,Map<String,String>>();

		for (Map<String,String> entry:map) {
			list.put(entry.get(field),entry);
		}

		return new MapPath(new ArrayList<Map<String,String>>(list.values()));
	}

	public List<Map<String,String>> value() {
		return map;
	}

	public MapPath collect(Transformer elTran) {
		return new MapPath(new ArrayList(CollectionUtils.collect(map,elTran)));
	}
}