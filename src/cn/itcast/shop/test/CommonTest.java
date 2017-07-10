package cn.itcast.shop.test;

import java.io.File;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.*;

import redis.clients.jedis.Jedis;
import cn.itcast.shop.utils.BeanFactory;
import cn.itcast.shop.utils.CommonUtils;
import cn.itcast.shop.utils.DataSourceUtils;
import cn.itcast.shop.utils.JedisUtils;
import cn.itcast.shop.utils.MailUtils;

/**
 * @author liuhaicheng 测试类,测试所有内容
 */
public class CommonTest {
	// 测试数据库连接
	@Test
	public void testDataSource() {
		DataSource dataSource = DataSourceUtils.getDataSource();

		if (dataSource != null) {
			System.out.println("连接成功");
			System.out.println(dataSource);
		}
	}

	// 测试32位uuid生成
	@Test
	public void testUUID() {
		System.out.println(CommonUtils.getUUID());
	}

	// 测试日志
	@Test
	public void testLog4j2() {

		Logger log = LogManager.getLogger(CommonTest.class);
		log.info("我是info信息");
	}

	// 任意测试
	@Test
	public void test() {
		System.out.println("".length());
	}

	@Test
	public void testMail() throws AddressException, MessagingException {
		String msg = "这是一封激活邮件，请<a href='user?method=激活'>点击</a>进行激活";
		MailUtils
				.sendMail("884024720@qq.com", "bbb",
						"71a3a933353347a4bcacff699e6baa9c950a02f6b84e4f6fb8404ca06febfd6f");
	}

	@Test
	public void testJedis() {
		Jedis jedis = JedisUtils.getJedis();
		System.out.println(jedis.get("xxx"));
	}

	@Test
	public void getBean() {

		try {
			// 1、创建解析器
			SAXReader reader = new SAXReader();
			Document doc = reader.read(new File(CommonTest.class
					.getClassLoader().getResource("bean.xml").getPath()));
			Element rootElement = doc.getRootElement();
			List<Element> elements = rootElement.elements();
			for (Element element : elements) {
				if(element.attributeValue("id").equals("userService")){
					
					System.out.println(element.attributeValue("class"));
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String args[]) {
		Logger Log = LogManager.getLogger(CommonTest.class.getName());
		Log.info("this is the info");
		Log.warn("this is the warn info");
		Log.error("this is the error info");
		Log.fatal("this is the fatal info");
		Log.trace("enter Main.test()");
		// new Main().test();
		Log.trace("exit Main.test()");
	}
}
