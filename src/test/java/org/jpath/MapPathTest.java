package org.jpath;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import static org.jpath.JPath.*;


public class MapPathTest {

	@Test
	public void test_map_path() {
		List<Map<String,String>> map = List(Map(e("a","2")),Map(e("a","1")),Map(e("b","2")),Map(e("b","3")),Map(e("a","1")));

		List<Map<String, String>> ret = null;

		ret = on(map).uniques().value();

		assertTrue(ret.size()==4);


		ret = on(map).uniques("a").value();

		assertEquals(ret.size(),3);


		ret = on(map).findAll(where("b").is("2")).value();

		assertEquals(ret.size(),1);


		ret = on(map).findAll(p("it.b==2")).value();

		assertEquals(ret.size(),1);


		Map<String,String> val = on(map).find(p("it.b==2"));

		assertEquals(val.size(),1);


		ret = on(map).sort("a",1).value();

		assertEquals(ret.get(0).get("a"),"1");
		assertEquals(ret.get(2).get("a"),"2");
		assertEquals(ret.get(4).get("b"),"3");

		Map<String, List<Map<String, String>>> grouped = on(map).groupBy("a");

		assertEquals(grouped.get("1").get(0).get("a"),"1");
		assertEquals(grouped.get("2").get(0).get("a"),"2");

		boolean bret = on(map).any(p("it.a==1"));

		assertTrue(bret);

		List<String> values = on(map).uniques().collect("a").value();

		System.out.println(values);

		List<Integer> intVals = on(map).uniques().collect("a").toIntegers();

		System.out.println(intVals);

		intVals = on(map).uniques().collect("a").as(Integer.class);

		System.out.println(intVals);

		class MyObject { 
			String name;
			
			String lastName;

			public MyObject(String name,String lastName) {
				this.name = name;
				this.lastName = lastName;
			}
		} 

		List<MyObject> list = List(new MyObject("federico","dayan"),new MyObject("federico","dayan"),new MyObject("mariano","dayan"));

		System.out.println(on(list,"name","lastName").uniques().value());
		
		on(map).Shuffle();
		
		System.out.println(on(map).collect(t("it.a")).uniques().value());
		
		ret = on(map).Sort(c("it.a.compareTo(other.a)")).value();
		
		System.out.println("=========");
		System.out.println(ret);
	}
}
