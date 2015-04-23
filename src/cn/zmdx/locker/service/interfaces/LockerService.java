package cn.zmdx.locker.service.interfaces;

import java.util.List;
import java.util.Map;

import cn.zmdx.locker.entity.Data_img_table;
import cn.zmdx.locker.entity.Notification;
import cn.zmdx.locker.entity.PageResult;
import cn.zmdx.locker.entity.Tag;
import cn.zmdx.locker.entity.WallPaper;

public interface LockerService {

	/**
	 * 查询非图片数据
	 * 
	 * @author 张加宁
	 */
	public PageResult queryDataTable(Map<String, String> filterMap);

	/**
	 * 查询图片数据
	 * 
	 * @author 张加宁
	 */
	public PageResult queryDataImgTable(Map<String, String> filterMap);

	/**
	 * 删除图片数据
	 * 
	 * @author 张加宁
	 */
	public void deleteDataImgById(String ids);

	/**
	 * 保存数据信息
	 * 
	 * @author 张加宁
	 */
	public String saveDataImg(Data_img_table dit);

	/**
	 * 修改数据
	 * 
	 * @author 张加宁
	 */
	public void updateDataImg(Data_img_table dit);

	/**
	 * 根据id查询对象
	 * 
	 * @param id
	 * @return
	 * @author 张加宁
	 */
	public Data_img_table getDataImgById(String id);

	/**
	 * 插入数据到云数据库
	 * 
	 * @return
	 * @author 张加宁
	 * 
	 */
	public int insertDataImg(String ids);

	/**
	 * 插入数据到云数据库
	 * 
	 * @return
	 * @author 张加宁
	 */
	public int insertDataImg(Data_img_table dit);

	/**
	 * 批量保存参数
	 * 
	 * @return
	 * @author 张加宁
	 */
	public int saveParams(Map<String, String> filterMap);

	/**
	 * 查询标签数据
	 * 
	 * @author 张加宁
	 */
	public PageResult queryTagTable(Map<String, String> filterMap);

	/**
	 * 查询通知数据
	 * 
	 * @author 张加宁
	 */
	public PageResult queryNotifyTable(Map<String, String> filterMap);

	/**
	 * 保存标签信息
	 * 
	 * @author 张加宁
	 */
	public String saveTag(Tag tag);

	/**
	 * 修改标签信息
	 * 
	 * @author 张加宁
	 */
	public void updateTag(Tag tag);

	/**
	 * 根据id查询对象
	 * 
	 * @author 张加宁
	 */
	public Notification getNotifyById(String id);

	/**
	 * 根据id查询对象
	 * 
	 * @param id
	 * @return
	 * @author 张加宁
	 */
	public Tag getTagById(String id);

	/**
	 * 删除标签
	 * 
	 * @author 张加宁
	 */
	public void deleteTagById(String ids);

	/**
	 * 删除通知
	 * 
	 * @author 张加宁
	 */
	public void deleteNotifyById(String ids);

	/**
	 * 根据SQL查询所有数据
	 * 
	 * @author 张加宁
	 */
	List<?> queryAllBySql(String sql);

	/**
	 * 执行sql语句 增删改
	 * 
	 * @param entity
	 */
	public int executeBySQL(String sql);

	public void saveOrUpdate(Object entity);

	public String save(Object entity);

	/**
	 * 根据数据ID删除中间表中对应标签
	 */
	void deleteTagByDataId(int id);

	/**
	 * 获取壁纸数据
	 * 
	 * @param filterMap
	 * @return
	 */
	PageResult queryWallPaper(Map<String, String> filterMap);

	/**
	 * 根据id获取Object对象
	 * 
	 * @param clazz
	 * @param id
	 * @return
	 */
	public Object getObjectById(Class clazz, int id);

	/**
	 * 保存壁纸到云数据库
	 * 
	 * @param wallPaper
	 * @return
	 */
	public int insertWallPaper(WallPaper wallPaper);

	/**
	 * 保存通知到云数据库
	 * 
	 * @param wallPaper
	 * @return
	 */
	public int insertNotify(Notification notify);

	/**
	 * 根据id删除壁纸
	 * 
	 * @param ids
	 */
	public void deleteWallPaperById(String ids);

	/**
	 * 将指定id的壁纸保存至云服务器
	 * 
	 * @param ids
	 * @return
	 */
	public int insertWallPaper(String ids);

	/**
	 * 根据ID将通知插入云数据库中
	 * 
	 * @param ids
	 * @return
	 */
	public int insertNotify(String ids);

	/**
	 * 删除图片过高的新闻数据
	 * @author louxiaojian
	 * @date： 日期：2015-3-26 时间：上午10:55:50
	 * @param ids
	 */
	public int delDataImgTableByTooHeight(String ids);
	
	/**
	 * 根据表名获取审核中的所有数据id
	 * @author louxiaojian
	 * @date： 日期：2015-3-30 时间：下午12:13:09
	 * @param tableName
	 * @return
	 */
	public String getIdsByEntity(String tableName);

	/**
	 * 新闻置顶
	 * @author louxiaojian
	 * @date： 日期：2015-4-20 时间：下午4:22:05
	 * @param ids
	 * @return
	 */
	public int stickByIds(String ids);

	/**
	 * 取消新闻置顶
	 * @author louxiaojian
	 * @date： 日期：2015-4-20 时间：下午4:22:21
	 * @param ids
	 * @return
	 */
	public int cancelStickByIds(String ids);
}
