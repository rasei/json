/*
 * Copyright 2015 Ralf Seidengarn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package net.seidengarn.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * Test which contains the sample from the usage within the readme
 * 
 * @author Ralf Seidengarn
 */
public class UsageSampleTest {

   @Test
   @SuppressWarnings("unchecked")
   public void testUsageSampleParsing() {
      Object result = JSONParser
               .parse("{\"value1\":\"4711\",\"datamap\":{\"filter\":\"true\",\"internal\":true},\"datalist\":[\"49\",\"8\"]}}");
      Map<String, Object> map = (Map<String, Object>) result;
      String st = (String) map.get("value1");
      System.out.println("value1 is " + st);
      assertEquals("4711", st);

      Object o = map.get("datamap");
      Map<String, Object> map2 = (Map<String, Object>) o;
      st = (String) map2.get("filter");
      System.out.println("datamap.filter is " + st);
      assertEquals("true", st);

      o = map.get("datalist");
      List<Object> list = (List<Object>) o;
      st = (String) list.get(0);
      System.out.println("datalist[0] is " + st);
      assertEquals("49", st);
   }

   @Test
   public void testUsageSampleComposing() {
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

      assertNotNull(result);
      assertTrue(result.contains("\"value1\":\"4711\""));
      assertTrue(result.contains("\"datamap\":{"));
      assertTrue(result.contains("\"filter\":\"true\""));
      assertTrue(result.contains("\"datalist\":["));
   }

}
