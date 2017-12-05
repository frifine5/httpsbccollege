package com.json.jackson.t;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

public class JacksonTest {

	@Test
	public void test() {
		JacksonTest.readJson2List();
		JacksonTest.readJson2Array();
	}
	
	@Test
	public void test2(){
		
		Student student = new Student();  
        student.setIsstudent(true);  
        student.setUid(1000);  
        student.setUname("xiao liao");  
        student.setUpwd("123");  
        student.setNumber(12);  
          
        Map<String, Student> stuMap = new HashMap<String, Student>();  
        stuMap.put("1", student);  
        stuMap.put("2", student);  
          
        List<Object> stuList = new ArrayList<Object>();  
        List<Student> stuList1 = new ArrayList<Student>();  
        stuList1.add(student);  
        student=  new  Student();  
        student.setIsstudent(false);  
        student.setUid(200);  
        student.setUname("xiao mi");  
        stuList1.add(student);  
          
        stuList.add(student);  
        stuList.add("xiao xin");  
        stuList.add("xiao er");  
        stuList.add(stuMap);  
          
//        readJson2List();  
        try {  
//            readJson2Array();  
            writeArray2Json(stuList1);  
//            writeJson2List();  
//            writeEntity2Json(student);  
//            writeJson2Entity();  
//            writeMap2Json(stuMap);  
//            writeList2Json(stuList1);  
              
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
		
	}
	

	private static ObjectMapper mapper = new ObjectMapper();

	/**
	 * 
	 * <code>writeEntity2Json</code>
	 * 
	 * @param object
	 * @throws IOException
	 */
	public static void writeEntity2Json(Object object) throws IOException {
		mapper.writeValue(new File("aa.txt"), object);
		mapper.writeValue(System.out, object);

	}

	/**
	 * 
	 * <code>writeArray2Json</code>
	 * 
	 * @param object
	 * @throws IOException
	 */
	public static void writeArray2Json(Object object) throws IOException {

		// writeValue具有和writeObject相同的功能
		mapper.writeValue(new File("aa.txt"), object);
		mapper.writeValue(System.out, object);

	}

	/**
	 * 
	 * <code>writeMap2Json</code>
	 * 
	 * @param object
	 * @throws IOException
	 */
	public static void writeMap2Json(Object object) throws IOException {

		System.out.println("使用ObjectMapper-----------");
		// writeValue具有和writeObject相同的功能
		System.out.println("==>" + mapper.writeValueAsString(object));
		mapper.writeValue(new File("aamap.txt"), object);
		mapper.writeValue(System.out, object);
	}

	/**
	 * 
	 * <code>writeList2Json</code>
	 * 
	 * @param object
	 * @throws IOException
	 */
	public static void writeList2Json(Object object) throws IOException {
		System.out.println("==>" + mapper.writeValueAsString(object));
		mapper.writeValue(new File("aamap.txt"), object);
		mapper.writeValue(System.out, object);
	}

	/**
	 * 
	 * <code>writeJson2Entity</code>
	 * 
	 * @throws IOException
	 * @since 2011-11-8 廖益平
	 */
	public static void writeJson2Entity() throws IOException {
		System.out.println("json串转换成entity-------------");
		 File file = new File("aa.txt");
		 FileInputStream inputStream = new FileInputStream(file);
		 Student student = mapper.readValue(inputStream,Student.class);
		 System.out.println(student.toString());
		 
//		 mapper.defaultPrettyPrintingWriter().writeValueAsString(value);

		String json = "{\"uid\":1000,\"uname\":\"xiao liao\",\"upwd\":\"123\",\"number\":12.0,\"isstudent\":true}";
		Student student1 = mapper.readValue(json, Student.class);
		System.out.println("json2:" + student1.toString());
	}

	/**
	 * 
	 * <code>writeJson2List</code>
	 * 
	 * @throws IOException
	 * @since 2011-11-8 廖益平
	 */
	public static void writeJson2List() throws IOException {
		System.out.println("json串转换成entity-------------");
		File file = new File("aa.txt");
		FileInputStream inputStream = new FileInputStream(file);
		Student student = mapper.readValue(inputStream, Student.class);
		System.out.println(student.toString());

		String json = "[{\"uid\":1000,\"uname\":\"xiao liao\",\"upwd\":\"123\",\"number\":12.0,\"isstudent\":true},{\"uid\":200,\"uname\":\"xiao mi\",\"upwd\":null,\"number\":0.0,\"isstudent\":false}]";
		List<LinkedHashMap<String, Object>> s = mapper.readValue(json, List.class);
		for (int i = 0; i < s.size(); i++) {
			LinkedHashMap<String, Object> link = s.get(i);
			Set<String> key = link.keySet();
			for (Iterator iterator = key.iterator(); iterator.hasNext();) {
				String string = (String) iterator.next();
				System.out.println(string + "==>" + link.get(string));

			}
			System.out.println("json:" + i + "" + s.get(i).toString());

		}
	}

	/**
	 * JSON转换为List对象
	 */
	public static void readJson2List() {
		String json = "[{\"uid\":1,\"uname\":\"www\",\"number\":234,\"upwd\":\"456\"},"
				+ "{\"uid\":5,\"uname\":\"tom\",\"number\":3.44,\"upwd\":\"123\"}]";
		try {
			List<LinkedHashMap<String, Object>> list = mapper.readValue(json, List.class);
			System.out.println(list.size());
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				Set<String> set = map.keySet();
				for (Iterator<String> it = set.iterator(); it.hasNext();) {
					String key = it.next();
					System.out.println(key + ":" + map.get(key));
				}
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * JSON转换为List对象
	 */
	public static void readJson2Array() {
		String json = "[{\"uid\":1,\"uname\":\"www\",\"number\":234,\"upwd\":\"456\"},"
				+ "{\"uid\":5,\"uname\":\"tom\",\"number\":3.44,\"upwd\":\"123\"}]";
		try {
			Student[] students = mapper.readValue(json, Student[].class);
			for (Student student : students) {
				System.out.println(">" + student.toString());
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

class Student {
	/*
	 * 
	 * Class Descripton goes here.
	 * 
	 * @class Student
	 * 
	 * @version 1.0
	 * 
	 * @author 廖益平
	 * 
	 * @time 2011-11-8 上午03:01:08
	 */
	private int uid;
	private String uname;
	private String upwd;
	private double number;
	private boolean isstudent;

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getUpwd() {
		return upwd;
	}

	public void setUpwd(String upwd) {
		this.upwd = upwd;
	}

	public double getNumber() {
		return number;
	}

	public void setNumber(double number) {
		this.number = number;
	}

	public boolean isIsstudent() {
		return isstudent;
	}

	public void setIsstudent(boolean isstudent) {
		this.isstudent = isstudent;
	}

	@Override
	public String toString() {

		return "uid=" + uid + ",name=" + uname + ",upwd=" + upwd + ",number=" + number + ",isStudent=" + isstudent;
	}

}
