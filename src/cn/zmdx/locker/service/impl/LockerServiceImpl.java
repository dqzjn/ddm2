package cn.zmdx.locker.service.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import cn.zmdx.locker.dao.interfaces.LockerDAO;
import cn.zmdx.locker.entity.Data_img_table;
import cn.zmdx.locker.entity.Data_table;
import cn.zmdx.locker.entity.PageResult;
import cn.zmdx.locker.entity.Tag;
import cn.zmdx.locker.entity.WallPaper;
import cn.zmdx.locker.service.interfaces.LockerService;

public class LockerServiceImpl implements LockerService {
	private LockerDAO lockerDAO;

	public LockerServiceImpl(LockerDAO lockerDAO) {
		this.lockerDAO = lockerDAO;
	}

	@Override
	public PageResult queryDataTable(Map<String, String> filterMap) {
		
		return lockerDAO.queryDataTable(filterMap);
	}

	@Override
	public PageResult queryDataImgTable(Map<String, String> filterMap) {
		
		return lockerDAO.queryDataImgTable(filterMap);

	}

	public void deleteDataImgById(String ids){
		lockerDAO.deleteDataImgById(ids);
	}

	@Override
	public String saveDataImg(Data_img_table dit){
		return lockerDAO.saveDataImg(dit);
	}

	public Data_img_table getDataImgById(String id){
		return lockerDAO.getDataImgById(id);
	}

	@Override
	public void updateDataImg(Data_img_table dit) {
		 lockerDAO.updateDataImg(dit);
	}

	@Override
	public int insertDataImg(String ids) {
		return lockerDAO.insertDataImg(ids);
		
	}
	@Override
	public int insertDataImg(Data_img_table dit) {
		return lockerDAO.insertDataImg(dit);
		
	}

	@Override
	public int saveParams(Map<String, String> filterMap) {
		return lockerDAO.saveParams(filterMap);
	}

	@Override
	public PageResult queryTagTable(Map<String, String> filterMap) {
		return lockerDAO.queryTagTable(filterMap);
	}
	public List<?> queryAllBySql(String sql) {
		return lockerDAO.queryAllBySql(sql);
	}
	public int executeBySQL(String sql) {
		return lockerDAO.executeBySQL(sql);
	}

	@Override
	public String saveTag(Tag tag) {
		return lockerDAO.saveTag(tag);
	}

	@Override
	public void updateTag(Tag tag) {
		lockerDAO.updateTag(tag);
		
	}

	@Override
	public Tag getTagById(String id) {
		return lockerDAO.getTagById(id);
	}

	@Override
	public void deleteTagById(String ids) {
		lockerDAO.deleteTagById(ids);
	}
	
	@Override
	public void saveOrUpdate(Object entity){
		lockerDAO.saveOrUpdate(entity);
	}
	
	@Override
	public String save(Object entity){
		return lockerDAO.save(entity);
	}

	@Override
	public void deleteTagByDataId(int id) {
		lockerDAO.deleteTagByDataId(id);
	}
	
	@Override
	public PageResult queryWallPaper(Map<String, String> filterMap) {
		return lockerDAO.queryWallPaper(filterMap);
	}

	@Override
	public Object getObjectById(Class clazz, int id) {
		return lockerDAO.getObjectById(clazz, id);
	}

	@Override
	public int insertWallPaper(WallPaper wallPaper) {
		return lockerDAO.insertWallPaper(wallPaper);
	}

	@Override
	public void deleteWallPaperById(String ids) {
		String idss[] = ids.split(",");
		for (String id : idss) {
			WallPaper wallPaper=(WallPaper)lockerDAO.getObjectById(WallPaper.class, Integer.parseInt(id));
			lockerDAO.delete(wallPaper);
		}
	}
	
	@Override
	public int insertWallPaper(String ids) {
		return lockerDAO.insertWallPaper(ids);
	}
}
