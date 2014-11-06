package cn.zmdx.locker.service.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import cn.zmdx.locker.dao.interfaces.LockerDAO;
import cn.zmdx.locker.entity.Data_img_table;
import cn.zmdx.locker.entity.Data_table;
import cn.zmdx.locker.entity.PageResult;
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
}
