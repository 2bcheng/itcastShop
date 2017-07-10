package cn.itcast.shop.utils;

import java.io.File;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class BeanFactory {
	public static Object getBean(String id) {

		try {
			SAXReader reader = new SAXReader();

			Document doc = reader.read(new File(BeanFactory.class
					.getClassLoader().getResource("bean.xml").getPath()));

			List<Element> elements = doc.getRootElement().elements();
			String className = "";
			for (Element e : elements) {
				if (id.equals(e.attributeValue("id"))) {
					className = e.attributeValue("class");
				}
			}
			// 使用反射创建对象
			Class clazz = Class.forName(className);
			Object object = clazz.newInstance();

			return object;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}
}
