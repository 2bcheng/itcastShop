package cn.itcast.shop.utils;

import java.util.UUID;

/**
 * @author liuhaicheng 工具类,提供了一些通用方法
 */
public class CommonUtils {

	// 得到36位uuid,把所有"-"转换成"" 放回32位uuid
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
