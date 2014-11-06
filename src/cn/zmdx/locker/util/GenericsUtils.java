package cn.zmdx.locker.util;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

/**
 * 通过反射获得Class声明的范型Class.
 *
 * @author 张加宁
 */
public class GenericsUtils {
	/**
	 * Locates the first generic declaration on a class.
	 *
	 * @param clazz The class to introspect
	 * @return the first generic declaration, or <code>Object.class</code> if cannot be determined
	 */
	public static Class getGenericClass(Class clazz) {
		return getGenericClass(clazz, 0);
	}

	/**
	 * Locates  generic declaration by index on a class.
	 *
	 * @param clazz clazz The class to introspect
	 * @param index the Index of the generic ddeclaration,start from 0.
	 */
	public static Class getGenericClass(Class clazz, int index) throws IndexOutOfBoundsException {
		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size of Parameterized Type: " + params.length);
		}
		return (Class) params[index];

	}
	//根据key读取value
		 public  static String readValue(String filePath,String key) {
		  Properties props = new Properties();
		        try {
		         InputStream in = new BufferedInputStream (new FileInputStream(filePath));
		         props.load(in);
		         String value = props.getProperty (key);
//		            System.out.println(key+value);
		            return value;
		        } catch (Exception e) {
		         e.printStackTrace();
		         return null;
		        }
		 }
	
	public static Properties loadProperty(String propertyName) {
		Properties properties = new Properties();
		if(StringUtils.isEmpty(propertyName)){
			return null;
		}
		try {
			properties.load(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(propertyName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}
}
