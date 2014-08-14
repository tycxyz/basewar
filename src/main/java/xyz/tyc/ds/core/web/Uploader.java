package xyz.tyc.ds.core.web;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xyz.tyc.baseapi.comm.DateUtil;
import xyz.tyc.baseapi.comm.StringUtil;
import xyz.tyc.ds.comm.CommConst;
import xyz.tyc.ds.comm.RsUtil;
import xyz.tyc.ds.core.data.LocalFactory;


/**
 * 由于servlet类的成员属性是多请求线程共享，所以需要一个封装
 * @author taoyuchu Jul 25, 2014
 */
public class Uploader {
	private ServletFileUpload  _upload;
	private static Logger log = LoggerFactory.getLogger(Uploader.class);
	
	private EnumStoredType _st;
	private String _id = null;
	private String _fileName = null;
	private String _fileExt = null;
	private StringBuilder _sb = new StringBuilder();
	private boolean _ignoreUpdateDB = false; //默认最后需要更新数据库
	private boolean _isStoreInOss = false; //是否存在云文件服务器上
	private String _rootDir = null;
	private HttpServletRequest req;
	private boolean _forceNewFile = false;
	/**
	 * @param req 
	 * @param rootDir 
	 * @param _upload
	 */
	public Uploader(HttpServletRequest req, ServletFileUpload upload, String rootDir) {
		this.req = req;
		this._upload = upload;
		this._rootDir = rootDir;
	}
	
	/**
	 * @throws IOException 
	 * 
	 */
	public String[] execute() throws IOException {

		// Parse the request
		@SuppressWarnings("unchecked")
		List<FileItem> items = null;
		try {
			items = _upload.parseRequest(req);
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		FileItem fi = null;
		
		for (FileItem item : items) {
			if (item.isFormField()) {
				String name = item.getFieldName();
				String value = item.getString();
				
				if ("tar".equalsIgnoreCase(name)) {//标明业务
					_st = EnumStoredType.getStoredType(value);
				} else if ("id".equalsIgnoreCase(name)) {//找数据用
					_id = value;
				} else if ("filename".equalsIgnoreCase(name)) {//为了获取扩展名
					_fileExt = value.substring(value.lastIndexOf(".")).toLowerCase();
				} else if ("storeinoss".equalsIgnoreCase(name)) {//将保存到云服务器
					_isStoreInOss = Boolean.parseBoolean(value);
				} else if ("forcenewfile".equalsIgnoreCase(name)) {//强行创建新文件名，复制数据行时防数据防止mp3/zip文件名不能窜用
					_forceNewFile = Boolean.parseBoolean(value);
				}
			} else { //上传文件
				fi = item;
			}
		}
		//上传文件
		boolean bUploadF  = false;
		_initFileName();
		String flagupload2oss = LocalFactory.getPropHelper().get("upload2oss");
		
		if (_isStoreInOss && "true".equals(flagupload2oss)) //存储到oss，测试版的flag为False
			bUploadF = _upload2Server(fi); 
		else  //存储在本地
			bUploadF = _uploadFile(fi);
		
		//cu数据库
		if (bUploadF) {
			_sb.append("上传文件成功——").append(_fileName).append("\n");
			
			if (_ignoreUpdateDB) {
				_sb.append("不需要更新数据库！");
			} else {
				String result = _updateDB();//更新或插入记录
				
				if (CommConst.NOT_HANDLE.equals(result))
					_sb.append("数据库更新未处理！");
				else if (CommConst.SUCCESS.equals(result))
					_sb.append("数据库更新成功！");
			}
		} else {
			_sb.append("文件保存失败，放弃操作，可能原因：没有这个业务！ ");
		}
		return new String[]{_sb.toString(), _fileName};
	}
	
	
	/**
	 * 上传云存储服务器
	 * @param fi
	 * @return
	 * @throws IOException 
	 */
	private boolean _upload2Server(FileItem fi) throws IOException   {
		//先备份到服务器，再上传到云存储服务器
		if (StringUtil.isEmpty(_fileName)) {
			_sb.append("文件名未初始化，上传失败！\n");
			return false;
		} else {
			if (_uploadFile(fi)) {
				//InputStream is = new FileInputStream(_rootDir + _st.getPath() + _fileName);
				//TODO 其它处理，比如上传到云存储服务器
				return true;
			} else {
				return false;
			}
		}
	}
	
	/**
	 * 这个接口有两个功能：（1）查原文件名（2）更新记录
	 * @return
	 */
	private String _updateDB(){
		if (_id == null) {//没有新增，不处理
			return CommConst.NOT_HANDLE; 
		} else {
			if (_fileName == null) { //获取原文件名，用于替换文件
				return LocalFactory.getDefaultQHelper().queryFirst(String.class, _st.getQuerySql(), new Object[]{_id});
			} else { //更新文件名字段以及最后日期
				LocalFactory.getDefaultQHelper().cud(_st.getUpdateSql(), new Object[]{_fileName, DateUtil.getGMTDateString(), _id});
				return CommConst.SUCCESS;
			}
		}
	}

	/**
	 * 文件上传
	 * @param fi
	 */
	private boolean _uploadFile(FileItem fi) {
		if (_st == null) {
			return false;
		}
		String fileDir = _rootDir + _st.getPath();
		File fDir = new File(fileDir);
		
		if (!fDir.exists())
			fDir.mkdirs();
		
		try {
			//执行文件上传
			fi.write(new File(fileDir + _fileName));
			
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private void _initFileName() {
		
		if (!StringUtil.isEmpty(_fileName))
			return;

		// 替换，删除原文件
		if (".png".equalsIgnoreCase(_fileExt) || ".jpg".equalsIgnoreCase(_fileExt)) {
			// File normalFile = new File(fileDir + _fileName);
			//
			// if (normalFile.exists()) //删除原文件
			// normalFile.delete();
			// _ignore = true; //由于需要更新lasttime，这里不能忽略

			_fileName = _genFileName() + _fileExt; // 换名字，由于情景需要，如不换名字，客户端不会到服务器请求新的图片，而会用缓存图片
			_ignoreUpdateDB = false;	//需要更新数据库（最后更新日期，文件名）
		} else {
			if (!StringUtil.isEmpty(_id) && (!_forceNewFile )) // 由id去数据库找fileName，看数据库中是否有这条记录
				_fileName = _updateDB();
			
			if (StringUtil.isEmpty(_fileName)) {
				_fileName = _genFileName() + _fileExt;
				_ignoreUpdateDB = false; 	//记录更新了，需要更新文件名及时间戳
			}
		}
	}
	
	
	private String _genFileName(){
		String name = RsUtil.generateRandomString();
		File f = new File(_rootDir + _st.getPath() + name + _fileExt);
		
		//需要满足生成的名字，当前文件不存在，检查这个是为了避免在数据表字段重名，是一个保障，虽然理应没有什么可能性
		while(f.exists()) {
			_genFileName();
		}
		return name;
	}

}
