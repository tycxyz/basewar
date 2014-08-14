package xyz.tyc.ds.core.data;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import xyz.tyc.baseapi.comm.StringUtil;


/**
 * @author taoyuchu Aug 2, 2014
 */
public class ResourceReader {

	private static Map<String, ResourceBundle> bundles = new HashMap<String, ResourceBundle>();
	
	static {
		bundles.put("default", ResourceBundle.getBundle("msg"));
	}	

	/**
	 * initialization
	 * @param lang 语言名称，如zh-CN/zh-TW/zh-TW-HK/zh-TW-TW/zh-TW-MAC，
	 * 后三者都是繁体，通用msg_zh_TW.properties配置文件
	 */
	public static void initConfig(String lang) {
		if (!bundles.containsKey(lang)) {
			try {
				Locale locale = null;
				String s[] = lang.split("-");
				
				if (s.length >= 2) {
					locale = new Locale(s[0], s[1]);
				} else {
					locale = new Locale(lang);
				}
				bundles.put(lang, ResourceBundle.getBundle("msg", locale));
			} catch (MissingResourceException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param language url中的语言标识，比如zh-CN/zh-TW-HK/fr，由此去找property文件。
	 * zh-TW-MAC/zh-TW-HK/zh-TW-TW会找到同一个文件msg_zh_TW.properties，而fr，是没有这个文件的，最终会从msg.properties里读取项目
	 * @param key
	 * @return
	 */
	public static String getValue(String language, String key) {
		try {
			if (StringUtil.isEmpty(language)) {
				language = "zh-CN";
			}
			
			if (!bundles.containsKey(language)) {
				initConfig(language);
			} 
			String value = bundles.get(language).getString(key);
			
			//如果当前语言没有这一项，从默认文件language.properties里取
			if (StringUtil.isEmpty(value)) {
				value = bundles.get("default").getString(key);
			}
			return value;
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String[] args) {

//			System.out.println(getValue("zh-TW", "message2"));
//			System.out.println(getValue("zh-TW", "message3"));
//			System.out.println(getValue("zh-CN", "message2"));
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("FR", "1");
		map.put("fr", "2");
		System.out.println(map.get("FR"));
		System.out.println(map.get("fr"));

	}
}
