package xyz.tyc.ds.client;

import java.util.List;
import java.util.Map;

import xyz.tyc.baseapi.comm.JsonUtil;
import xyz.tyc.baseapi.comm.StringUtil;
import xyz.tyc.ds.core.data.LocalFactory;
import xyz.tyc.ds.core.data.ResourceReader;
import xyz.tyc.ds.po.Music;
import xyz.tyc.ds.po.MusicType;


/**
 * 业务处理方法
 * @author taoyuchu 2014-7-19
 */
public class ClientBo {

	/**
	 * 获取音乐列表
	 * @param params 过滤条件
	 */
	public static Object[][] listMusic(Map<String, String> params) {
		String lang = params.get("lang");
		String key = params.get("key");
		String mtypes = params.get("mtypes");//可选参数，类别串
		String lid = ResourceReader.getValue(lang, lang);
		StringBuilder sb = new StringBuilder("select m.mid, mname, mfname, author, performer, favourites, coverpic, lyric, recsupport from ds_music m where m.deleted = 0");
		
		if (!StringUtil.isEmpty(lid)) {
			sb.append(" and exists (select 1 from ds_music_language where mid = m.mid and deleted = 0 and lid = ").append(lid).append(" limit 1)");
		}
		if (!StringUtil.isEmpty(key)) {
			sb.append(" and (mname like '%").append(key).append("%')");
		}
		
		if (!StringUtil.isEmpty(mtypes)) {
			sb.append(" and exists (select 1 from ds_music_type where mid = m.mid and typeid in (").append(mtypes).append(") limit 1)");
		}
		List<Music> musics = LocalFactory.getDefaultQHelper().queryMulti(Music.class, sb.toString());
		
		if (musics != null) {
			return new Object[][]{{"result", musics.size()}, {"msg", ResourceReader.getValue(lang, "msg.general.success")}, {"music", JsonUtil.beans2JsonArr(musics)}};
		}
		return new Object[][]{{"result", 0}, {"msg", ResourceReader.getValue(lang, "msg.no_music")}};
	}
	
	/**
	 * 客户端获取音乐类型
	 * @param params
	 * @return
	 */
	public static Object[][] listMusicTypes(Map<String, String> params) {
		String lang = params.get("lang");
		String sql = "select a.itemvalue as typeval from data_dict a where exists (select 1 from data_dict where itemkey = 's1_music_type' and ddid =  a.pddid)";
		List<MusicType> types = LocalFactory.getDefaultQHelper().queryMulti(MusicType.class, sql);
		
		if (types != null) {
			for (MusicType t : types) {
				//根据lang从配置中读typename
				t.setTypename(ResourceReader.getValue(lang, "s1_music_type." + t.getTypeval()));
			}
			return new Object[][]{{"result", types.size()}, {"msg", ResourceReader.getValue(lang, "msg.general.success")}, {"mtypes", JsonUtil.beans2JsonArr(types)}};
		}
		return new Object[][]{{"result", 0}, {"msg", ResourceReader.getValue(lang, "msg.no_music_type")}};
	}
}
