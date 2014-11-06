package cn.zmdx.locker.dao.interfaces;

import java.util.Map;

import cn.zmdx.locker.entity.Data_img_table;
import cn.zmdx.locker.entity.PageResult;

public interface LockerDAO extends ParentDAO {

	/**
	 * 查询非图片数据数据
	 * 
	 */
	public PageResult queryDataTable(Map<String, String> filterMap);

	/**
	 * 查询图片数据数据
	 * 
	 */
	public PageResult queryDataImgTable(Map<String, String> filterMap);

	/**
	 * 删除图片数据
	 * 
	 */
	public void deleteDataImgById(String ids);

	/**
	 * 增加数据
	 */
	public String saveDataImg(Data_img_table dit);

	/**
	 * 修改数据
	 */
	public void updateDataImg(Data_img_table dit);

	/**
	 * 根据id查询对象
	 * 
	 * @param id
	 * @return
	 */
	public Data_img_table getDataImgById(String id);

	/**
	 * 插入数据到云数据库
	 * @return 
	 * 
	 */
	public int insertDataImg(String ids);
	/**
	 * 插入数据到云数据库
	 * @return 
	 * 
	 */
	public int insertDataImg(Data_img_table dit);
	
	/**
	 * 批量保存参数
	 * @return 
	 * 
	 */
	public int saveParams(Map<String, String> filterMap);
}
