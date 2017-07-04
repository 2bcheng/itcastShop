package cn.itcast.shop.test;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;

import cn.itcast.shop.utils.CommonUtils;
import cn.itcast.shop.utils.DataSourceUtils;
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
		MailUtils.sendMail("884024720@qq.com", "bbb","71a3a933353347a4bcacff699e6baa9c950a02f6b84e4f6fb8404ca06febfd6f");
	}
}
