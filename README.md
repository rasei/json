# json
Java library for json-string processing

# Usage
## Parsing a json-String

	Object result = JSONParser.parse("{\"value1\":\"4711\",\"datamap\":{\"filter\":\"true\",\"internal\":true},\"datalist\":[\"49\",\"8\"]}}");
	Map<String, Object> map = (Map<String, Object>) result;
	String st = (String) map.get("value1");
	System.out.println("value1 is " + st);
	
	Object o = map.get("datamap");
	Map<String, Object> map2 = (Map<String, Object>) o;
	st = (String) map2.get("filter");
	System.out.println("datamap.filter is " + st);
	
	o = map.get("datalist");
	List<Object> list = (List<Object>) o;
	st = (String) list.get(0);
	System.out.println("datalist[0] is " + st);

## Composing a json-String

	Map<String, Object> map = new HashMap<String, Object>();
	map.put("value1", "4711");
	Map<String, Object> map2 = new HashMap<String, Object>();
	map2.put("filter", "true");
	map2.put("internal", "true");
	map.put("datamap", map2);
	List<Object> list = new ArrayList<Object>();
	list.add("49");
	list.add("8");
	map.put("datalist", list);
	
	String result = JSONParser.compose(map);
	System.out.println(result);

The output should be similar to 

	{"value1":"4711","datalist":["49","8"],"datamap":{"filter":"true","internal":"true"}}
 