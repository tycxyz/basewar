package xyz.tyc.ds.core.velocity;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;
import org.apache.velocity.runtime.parser.node.SimpleNode;

import xyz.tyc.ds.core.data.LocalFactory;


/**
 * 一维数据（下拉框）标签化，统一模型
 * @author taoyuchu 2014-3-18
 * checkbox 标签
 */
public class CheckboxTagUtil extends Directive {

	@Override
	public String getName() {
		return "checkbox";
	}

	@Override
	public int getType() {
		return LINE;// line行指令，不要end结束符，block块指令，需要end结束符
	}

	@Override
	public boolean render(InternalContextAdapter context, Writer writer,
			Node node) throws IOException, ResourceNotFoundException,
			ParseErrorException, MethodInvocationException {

		// String body_tpl = body.literal();检查内容是否有变化
		String html = "";

		try {
			// 获得缓存信息
			Map <String,String> map = (Map<String,String>) ((SimpleNode) node.jjtGetChild(0)).value(context);
			
			String name = map.get("name") == null? "" : map.get("name");
			String path = map.get("path") == null? "" : map.get("path"); 
			String property = map.get("property") == null? "" : map.get("property");
			String event = map.get("event") == null? "" : map.get("event");
			String style = map.get("style") == null? "" : map.get("style");
			String exclude = map.get("exclude");
			String class_ = map.get("class") == null? "" : map.get("class");
			
			if (StringUtils.isEmpty(path)) {
				return false;
			}

			String paths[] = path.split("\\.");
			String propertys[] = property.split(",");

			html = handler(paths, name,propertys,event,style,exclude, class_);

			writer.write(html);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 拼接SQL语句字符串
	 * 
	 * @param value
	 */
	public static String getSQL(String[] paths) {

		StringBuffer sb = new StringBuffer();
		String temp = "son";
		sb.append("select * from data_dict " + temp + " where exists (");


		for (int i = paths.length - 1; i >= 0; i--) {
			String str = paths[i];
			if (i == (paths.length - 1)) {
				sb.append("select 1 from data_dict " + str + " where ddid = " + temp + ".pddid and itemkey = '" + str + "'");
				temp = str;
				continue;
			}
			sb.append(" and  exists (select 1 from data_dict " + str + " where ddid  = " + temp + ".pddid and itemkey = '" + str + "'");
			temp = str;
		}

		for (int i = 0; i < paths.length; i++)
			sb.append(")");

		return sb.toString();
	}

	/**
	 * checkBox标签的处理逻辑
	 * 
	 * @param values
	 * @param showName
	 * @return
	 * @throws Exception
	 */
	public static String handler(String[] paths, String name,String [] propertys,String event,String style, String exclude, String class_) throws Exception {

		StringBuffer sb = new StringBuffer();

		try {
			List<DataDict> dicts = LocalFactory.getDefaultQHelper().queryMulti(DataDict.class, CheckboxTagUtil.getSQL(paths) + "order by ordinal asc");
			if (dicts != null && dicts.size() > 0) {
				String[] excludes = null;

				if (!StringUtils.isEmpty(exclude)) {
					excludes = exclude.split("\\,");
				}

				for (DataDict dataDict : dicts) {
					String itemValue = dataDict.getItemvalue();

					if (!ArrayUtils.contains(excludes, itemValue)) {

						sb.append("<input type='checkbox' name='").append(name)
								.append("' value='");
						sb.append(itemValue).append("'");
						for (int i = 0; i < propertys.length; i++) {
							if (itemValue.equals(propertys[i])) {
								sb.append(" checked");
								break;
							}
						}
						// 添加样式
						if (style != null && !"".equals(style)) {
							sb.append(" style=\"").append(style).append("\"");
						}

						// 添加类样式
						if (class_ != null && !"".equals(class_)) {
							sb.append(" class=\"").append(class_).append("\"");
						}

						sb.append(">");
						sb.append(dataDict.getName()).append("<br>");
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return sb.toString();
	}

}
