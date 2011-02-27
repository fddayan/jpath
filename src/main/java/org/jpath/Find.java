package org.jpath;
/**
 * 
 */


import java.util.Map;

import org.apache.commons.collections.Predicate;

public class Find {

	final String field;

	public Find(String field) {
		super();
		this.field = field;
	}

	public Predicate is(final String value) {
		return new Predicate() {
			public boolean evaluate(Object args) {
				Map<String,String> mapEntry = (Map<String,String>) args;

				if (!mapEntry.containsKey(field)) 
					return false;
				if (mapEntry.get(field).equals(value)) {
					return true;
				} else {
					return false;
				}
			}
		};
	}

	public Predicate isG(final String value) {
		return new Predicate() {
			public boolean evaluate(Object args) {
				Map<String,String> mapEntry = (Map<String,String>) args;

				if (!mapEntry.containsKey(field)) 
					return false;
				if (mapEntry.get(field).compareTo(value)>0) {
					return true;
				} else {
					return false;
				}
			}
		};
	}

	public Predicate isL(final String value) {
		return new Predicate() {
			public boolean evaluate(Object args) {
				Map<String,String> mapEntry = (Map<String,String>) args;

				if (!mapEntry.containsKey(field)) 
					return false;
				if (mapEntry.get(field).compareTo(value)<0) {
					return true;
				} else {
					return false;
				}
			}
		};
	}

}