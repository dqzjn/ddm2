package cn.zmdx.locker.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;


import com.tencent.cos.Cos;
import com.tencent.cos.CosImpl;
import com.tencent.cos.bean.Message;
import com.tencent.cos.constant.Common;

import cn.zmdx.locker.dao.interfaces.LockerDAO;
import cn.zmdx.locker.entity.Data_img_table;
import cn.zmdx.locker.entity.Notification;
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

	public void deleteDataImgById(String ids) {
		lockerDAO.deleteDataImgById(ids);
	}

	@Override
	public String saveDataImg(Data_img_table dit) {
		return lockerDAO.saveDataImg(dit);
	}

	public Data_img_table getDataImgById(String id) {
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
	public Notification getNotifyById(String id) {
		return lockerDAO.getNotifyById(id);
	}

	@Override
	public void deleteTagById(String ids) {
		lockerDAO.deleteTagById(ids);
	}

	@Override
	public void deleteNotifyById(String ids) {
		lockerDAO.deleteNotifyById(ids);
	}

	@Override
	public void saveOrUpdate(Object entity) {
		lockerDAO.saveOrUpdate(entity);
	}

	@Override
	public String save(Object entity) {
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
	public int insertNotify(Notification notify) {
		return lockerDAO.insertNotify(notify);
	}

	@Override
	public void deleteWallPaperById(String ids) {
		String idss[] = ids.split(",");
		int accessId = 11000436; // accessId
		String accessKey = "7OgnLklEIptHNwZCS0RDNk1rUXrxXJfP"; // accessKey
		String bucketId = "wallpaper"; // bucket id
		String secretId = "AKIDBvY9dcNUS2LeFTxI2ThzgrKxuWuNROIr";
		Cos cos = null;
		try {
			cos = new CosImpl(accessId, accessKey, Common.COS_HOST,
					Common.DOWNLOAD_HOST, secretId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (String id : idss) {
			WallPaper wallPaper = (WallPaper) lockerDAO.getObjectById(
					WallPaper.class, Integer.parseInt(id));
			String imgName=wallPaper.getThumbURL();
			imgName=imgName.substring(imgName.indexOf("thumb/")+6);
			// 返回消息体, 包含错误码和错误消息
			Message msg = new Message();
			Message thumbMsg = new Message();
			List<String> fileList = new Vector<String>();
			List<String> thumbFileList = new Vector<String>();
			Map<String, Object> deParams = new HashMap<String, Object>();
			Map<String, Object> thumbDeParams = new HashMap<String, Object>();
    		deParams.put("bucketId", bucketId);
    		deParams.put("path", "/image");
    		fileList.add(imgName);
    		thumbDeParams.put("bucketId", bucketId);
    		thumbDeParams.put("path", "/thumb");
    		thumbFileList.add(imgName);
    		//删除原有文件
    		cos.deleteFile(deParams, fileList, msg);
    		cos.deleteFile(thumbDeParams, thumbFileList, thumbMsg);
    		System.out.println(msg);
    		System.out.println(thumbMsg);
			lockerDAO.delete(wallPaper);
		}
	}

	@Override
	public int insertWallPaper(String ids) {
		return lockerDAO.insertWallPaper(ids);
	}

	@Override
	public int insertNotify(String ids) {
		return lockerDAO.insertNotify(ids);
	}

	@Override
	public PageResult queryNotifyTable(Map<String, String> filterMap) {
		return lockerDAO.queryNotifyTable(filterMap);
	}
	
	@Override
	public int delDataImgTableByTooHeight(String ids) {
		return lockerDAO.delDataImgTableByTooHeight(ids);
	}

	@Override
	public String getIdsByEntity(String tableName) {
		return lockerDAO.getIdsByEntity(tableName);
	}

	@Override
	public int stickByIds(String ids) {
		return lockerDAO.stickByIds(ids);
	}

	@Override
	public int cancelStickByIds(String ids) {
		return lockerDAO.cancelStickByIds(ids);
	}
}
