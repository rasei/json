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
 * Test for JSONParser
 * 
 * @author Ralf Seidengarn
 */
public class JSONParserTest {

   @Test
   public void testComposeSimple() {
      Map<String, Object> map = new HashMap<String, Object>();
      map.put("error", "Invalid cookie.");
      List<Object> list = new ArrayList<Object>();
      map.put("error_params", list);
      map.put("category", "4");
      map.put("code", "SVL-0003");
      map.put("error_id", "566008504-17068007");

      String result = JSONParser.compose(map);
      assertNotNull(result);
      assertTrue(result.contains("\"category\":\"4\""));
      assertTrue(result.contains("\"error\":\"Invalid cookie.\""));
      assertTrue(result.contains("\"error_id\":\"566008504-17068007\""));
      assertTrue(result.contains("\"code\":\"SVL-0003\""));
   }

   @SuppressWarnings("unchecked")
   @Test
   public void testParseSimple() {
      Object result = JSONParser
               .parse("{\"category\":\"4\",\"error_params\":[],\"error\":\"Invalid cookie.\",\"error_id\":\"566008504-17068007\",\"code\":\"SVL-0003\"}");
      assertTrue(result instanceof Map);
      Map<String, Object> map = (Map<String, Object>) result;

      assertEquals(5, map.size());
      String st = (String) map.get("error");
      assertNotNull(st);
      assertEquals("Invalid cookie.", st);
      List<Object> list = (List<Object>) map.get("error_params");
      assertNotNull(list);
      assertEquals(0, list.size());
      st = (String) map.get("category");
      assertNotNull(st);
      assertEquals("4", st);
      st = (String) map.get("code");
      assertNotNull(st);
      assertEquals("SVL-0003", st);
      st = (String) map.get("error_id");
      assertNotNull(st);
      assertEquals("566008504-17068007", st);
   }

   @Test
   public void testParseWithObject() {
      Object result = JSONParser
               .parse("{\"session\":\"4711\",\"modules\":{\"filter\":true,\"data\":{\"module\":true,\"internal\":true}}}");
      assertTrue(result instanceof Map);
      Map<String, Object> map = (Map<String, Object>) result;
      assertEquals(2, map.size());
      String st = (String) map.get("session");
      assertNotNull(st);
      assertEquals("4711", st);
      Object o = map.get("modules");
      assertNotNull(o);
      assertTrue(o instanceof Map);
      Map<String, Object> map2 = (Map<String, Object>) o;
      assertEquals(2, map2.size());
      st = (String) map2.get("filter");
      assertNotNull(st);
      assertEquals("true", st);
      o = map2.get("data");
      assertNotNull(o);
      assertTrue(o instanceof Map);
      Map<String, Object> map3 = (Map<String, Object>) o;
      assertEquals(2, map3.size());
      st = (String) map3.get("internal");
      assertNotNull(st);
      assertEquals("true", st);
   }

   @Test
   public void testParseObjectWithArray() {
      Object result = JSONParser.parse("{\"data\":[[\"49\",8,8]]},\"data2\":1358098465804}");
      assertTrue(result instanceof Map);
      Map<String, Object> map = (Map<String, Object>) result;
      assertEquals(2, map.size());
      assertNotNull(map.get("data"));
      assertTrue(map.get("data") instanceof List);
      List<Object> list = (List<Object>) map.get("data");
      assertEquals(1, list.size());
      assertNotNull(list.get(0));
      assertTrue(list.get(0) instanceof List);
      List<Object> list2 = (List<Object>) list.get(0);
      assertEquals(3, list2.size());
      assertEquals("49", list2.get(0));
   }

   @Test
   public void testArray() {
      Object result = JSONParser.parse("[{\"data\":[[49,179,1358102220309,8,null]],\"timestamp\":1358098620309}]");
      assertTrue(result instanceof List);
      List<Object> list = (List<Object>) result;
      assertEquals(1, list.size());
      assertNotNull(list.get(0));
      assertTrue(list.get(0) instanceof Map);
      Map<String, Object> map = (Map<String, Object>) list.get(0);
      assertEquals(2, map.size());
   }

   @Test
   public void testQuoteInObject() {
      Object result = JSONParser.parse("{\"data\":\"Arno, Nym\"}");
      assertTrue(result instanceof Map);
      Map<String, Object> map = (Map<String, Object>) result;
      assertEquals(1, map.size());
      assertNotNull(map.get("data"));
      assertEquals("Arno, Nym", map.get("data"));
   }

   @Test
   public void testQuoteInArray() {
      Object result = JSONParser.parse("[\"data\",\"Arno, Nym\"]");
      assertTrue(result instanceof List);
      List<Object> list = (List<Object>) result;
      assertEquals(2, list.size());
      assertNotNull(list.get(1));
      assertEquals("Arno, Nym", list.get(1));
   }

   @Test
   public void testMapSimple1() {
      Object result = JSONParser.parse("{\"private\":4711,\"data\":{\"creator\":12}}");
      assertTrue(result instanceof Map);
      Map<String, Object> map = (Map<String, Object>) result;
      assertEquals(2, map.size());
      assertNotNull(map.get("data"));
      assertTrue(map.get("data") instanceof Map);
      assertEquals("12", ((Map<String, Object>) map.get("data")).get("creator"));
   }
}
