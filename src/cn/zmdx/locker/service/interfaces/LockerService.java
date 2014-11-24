package cn.zmdx.locker.service.interfaces;

import java.util.List;
import java.util.Map;

import cn.zmdx.locker.entity.Data_img_table;
import cn.zmdx.locker.entity.PageResult;
import cn.zmdx.locker.entity.Tag;

public interface LockerService {

	/**
	 * 查询非图片数据
	 * @author 张加宁
	 */
	public PageResult queryDataTable(Map<String, String> filterMap);

	/**
	 * 查询图片数据
	 * @author 张加宁
	 */
	public PageResult queryDataImgTable(Map<String, String> filterMap);

	/**
	 * 删除图片数据
	 * @author 张加宁
	 */
	public void deleteDataImgById(String ids);

	/**
	 * 保存数据信息
	 * @author 张加宁
	 */
	public String saveDataImg(Data_img_table dit);

	/**
	 * 修改数据
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
	 * @return 
	 * @author 张加宁
	 * 
	 */
	public int insertDataImg(String ids);
	/**
	 * 插入数据到云数据库
	 * @return 
	 * @author 张加宁
	 */
	public int insertDataImg(Data_img_table dit);

	/**
	 * 批量保存参数
	 * @return 
	 * @author 张加宁
	 */
	public int saveParams(Map<String, String> filterMap);
	
	/**
	 * 查询标签数据
	 * @author 张加宁
	 */
	public PageResult queryTagTable(Map<String, String> filterMap);
	
	/**
	 * 保存标签信息
	 * @author 张加宁
	 */
	public String saveTag(Tag tag);
	
	/**
	 * 修改标签信息
	 * @author 张加宁
	 */
	public void updateTag(Tag tag);
	
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
	 * @author 张加宁
	 */
	public void deleteTagById(String ids);
	
	/**
	 * 根据SQL查询所有数据
	 * @author 张加宁
	 */
	List<?> queryAllBySql(String sql);
	
	public void saveOrUpdate(Object entity);
	
	public String save(Object entity);
	
	/**
	 * 根据数据ID删除中间表中对应标签
	 */
	void deleteTagByDataId(int id);
}
