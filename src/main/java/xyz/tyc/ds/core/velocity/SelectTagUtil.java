package xyz.tyc.ds.core.velocity;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

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
 * select 标签
 * @author taoyuchu 2014-3-18
 */
public class SelectTagUtil extends Directive {

	@Override
	public String getName() {
		return "select";
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
			
			String name = map.get("name") == null? "" : map.get("name"); //标签的属性名字
			String path = map.get("path") == null? "" : map.get("path"); //对应字典表
			String property = map.get("property") == null? "" : String.valueOf(map.get("property"));//回显需要的值
			String event = map.get("event") == null? "" : map.get("event");//添加事件
			String style = map.get("style") == null? "" : map.get("style"); //普通样式
			String class_ = map.get("class") == null? "" : map.get("class"); //类样式 
			
			if (StringUtils.isEmpty(path)) {
				return false;
			}

			String paths[] = path.split("\\.");

			html = handler(paths,name,property,event,style,class_);

			writer.write(html);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	
	/**
	 * select标签的处理逻辑
	 * 
	 * @param value
	 */
	public static String handler(String[] paths,String name,String property,String event,String style,String class_) throws Exception {

		StringBuffer sb = new StringBuffer();

		try {
			
			List<DataDict> dicts = LocalFactory.getDefaultQHelper().queryMulti(DataDict.class, CheckboxTagUtil.getSQL(paths) + "order by ordinal asc");
			if (dicts != null && dicts.size() > 0) {
				sb.append("<select name='").append(name).append("'");
				
				//添加事件
				if(event != null && !"".equals(event)){
					sb.append(" onchange=\"").append(event).append(";\"");
				}
				
				//添加样式
				if(style != null && !"".equals(style)){
					sb.append(" style=\"").append(style).append("\"");
				}
				
				//添加类样式
				if(class_ != null && !"".equals(class_)){
					sb.append(" class=\"").append(class_).append("\"");
				}
				
				sb.append(">");
				sb.append("<option value=\'\' >-请选择-</option>");
				
				for (DataDict dataDict : dicts) {
					sb.append("<option value='").append(dataDict.getItemvalue()).append("'");
					if(property != null && !"".equals(property) && dataDict.getItemvalue().equals(property)){
						sb.append(" selected");	
					}
					sb.append(">").append(dataDict.getName()).append("</option>");
				}
				
				sb.append("<select/>");
			}
			
		} catch (Exception e) {
			throw e;
		}
		return sb.toString();
	}
}
