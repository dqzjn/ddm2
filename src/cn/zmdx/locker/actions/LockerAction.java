package cn.zmdx.locker.actions;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.zmdx.locker.entity.Data_img;
import cn.zmdx.locker.entity.Data_img_table;
import cn.zmdx.locker.entity.Data_table;
import cn.zmdx.locker.entity.Data_tag;
import cn.zmdx.locker.entity.Img;
import cn.zmdx.locker.entity.PageResult;
import cn.zmdx.locker.entity.Tag;
import cn.zmdx.locker.entity.User;
import cn.zmdx.locker.entity.WallPaper;
import cn.zmdx.locker.service.impl.LockerServiceImpl;
import cn.zmdx.locker.util.MD5;
import cn.zmdx.locker.util.StringUtil;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionSupport;
import com.tencent.cos.Cos;
import com.tencent.cos.CosImpl;
import com.tencent.cos.bean.CosFile;
import com.tencent.cos.bean.Message;
import com.tencent.cos.constant.Common;

public class LockerAction extends ActionSupport {
	Logger logger = Logger.getLogger(LockerAction.class);
	private Data_table dataTable;
	private LockerServiceImpl lockerService;
	private Data_img_table dataImgTable;
	private WallPaper wallPaper;
	private String dataImgId;
	private Data_img dataImg;
	private Img img;
	private Tag tag;
	private String result;
	private String page;
	private String rows;
	private String sidx;
	private String sord;
	private String check;
	private String imgNames;
	private String imgName;
	private String[] imgUrl;
	private File[] image;
	private String[] uImgId;
	private String[] oldFile;
	private List<File> images;
	private List<String> imagesContentType; // 文件的内容类型
	private List<String> imagesFileName; // 上传文件名

	public String getResult() {
		return result;
	}

	public void setLockerService(LockerServiceImpl lockerService) {
		this.lockerService = lockerService;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public Data_table getDataTable() {
		return dataTable;
	}

	public void setDataTable(Data_table dataTable) {
		this.dataTable = dataTable;
	}

	public Data_img_table getDataImgTable() {
		return dataImgTable;
	}

	public void setDataImgTable(Data_img_table dataImgTable) {
		this.dataImgTable = dataImgTable;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}

	public Data_img getDataImg() {
		return dataImg;
	}

	public void setDataImg(Data_img dataImg) {
		this.dataImg = dataImg;
	}

	public Img getImg() {
		return img;
	}

	public void setImg(Img img) {
		this.img = img;
	}

	public WallPaper getWallPaper() {
		return wallPaper;
	}

	public void setWallPaper(WallPaper wallPaper) {
		this.wallPaper = wallPaper;
	}

	public String getImgNames() {
		return imgNames;
	}

	public void setImgNames(String imgNames) {
		this.imgNames = imgNames;
	}

	public String[] getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String[] imgUrl) {
		this.imgUrl = imgUrl;
	}

	public File[] getImage() {
		return image;
	}

	public void setImage(File[] image) {
		this.image = image;
	}

	public String getDataImgId() {
		return dataImgId;
	}

	public void setDataImgId(String dataImgId) {
		this.dataImgId = dataImgId;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public String[] getuImgId() {
		return uImgId;
	}

	public void setuImgId(String[] uImgId) {
		this.uImgId = uImgId;
	}

	public String[] getOldFile() {
		return oldFile;
	}

	public void setOldFile(String[] oldFile) {
		this.oldFile = oldFile;
	}

	public List<File> getImages() {
		return images;
	}

	public void setImages(List<File> images) {
		this.images = images;
	}

	public List<String> getImagesContentType() {
		return imagesContentType;
	}

	public void setImagesContentType(List<String> imagesContentType) {
		this.imagesContentType = imagesContentType;
	}

	public List<String> getImagesFileName() {
		return imagesFileName;
	}

	public void setImagesFileName(List<String> imagesFileName) {
		this.imagesFileName = imagesFileName;
	}

	public String getColumnJson(PageResult result, String[] viewArray) {
		int rowsInt = Integer.parseInt(rows);
		int totalRows = result.getRowCount(); // 所有的行数
		int totalPages = (totalRows + rowsInt - 1) / rowsInt; // 总页数
		List rowAll = result.getData(); // 查询出的所有记录
		JSONObject obj = new JSONObject();
		obj.put("page", "" + page);
		obj.put("total", totalPages);
		obj.put("records", "" + totalRows);
		JSONArray lineitemArray = new JSONArray();
		Iterator it = rowAll.iterator();
		while (it.hasNext()) {
			Object[] objlist = (Object[]) it.next();
			lineitemArray.add(getColumnValue(objlist, viewArray));
		}
		obj.put("rows", lineitemArray);
		return obj.toString();
	}

	/*
	 * 取得页面传过来的列表参数
	 */
	public Map getPagerMap() {
		if (page == null)
			page = "1";
		if (sidx == null)
			sidx = "id";
		if (rows == null)
			rows = "10";
		if (sord == null)
			sord = "asc";
		Map filterMap = new HashMap();// 存储参数的map
		filterMap.put("page", Integer.parseInt(page));
		filterMap.put("rows", Integer.parseInt(rows));
		filterMap.put("sidx", sidx);
		filterMap.put("sord", sord);
		return filterMap;
	}

	/**
	 * 
	 * @param objlist
	 * @param viewArray
	 * @return
	 * @author 张加宁
	 */
	public JSONObject getColumnValue(Object[] objlist, String[] viewArray) {
		JSONObject objlineitem = new JSONObject();
		for (int i = 0; i < viewArray.length; i++) {
			if (!StringUtil.isEmpty(viewArray[i])) {
				if (objlist[i] != null) {
					if (viewArray[i].indexOf(":") > 0) {
						String realfieldName = viewArray[i].substring(0,
								viewArray[i].indexOf(":"));
						String jsonStr = viewArray[i].substring(viewArray[i]
								.indexOf(":") + 1);
						JSONArray json = JSONArray.fromObject(jsonStr);
						JSONObject job = json.getJSONObject(0);
						if (job.containsKey(objlist[i].toString())) {
							String realvalue = job.getString(objlist[i]
									.toString());
							if (realvalue != null) {
								objlineitem.put(realfieldName,
										realvalue.toString());
							}
						} else {
							objlineitem.put(realfieldName,
									objlist[i].toString());
						}
					} else {
						if (objlist[i] != null) {
							objlineitem
									.put(viewArray[i], objlist[i].toString());
						} else {
							objlineitem.put(viewArray[i], "");
						}
					}
				} else {
					objlineitem.put(viewArray[i], "");
				}
			}
		}
		return objlineitem;
	}

	/**
	 * 查询非图片数据数据
	 * 
	 * @throws IOException
	 * @author 张加宁
	 */
	public void queryDataTable() throws IOException {
		try {
			ServletActionContext.getResponse().setContentType(
					"text/html; charset=utf-8");
			String title = ServletActionContext.getRequest().getParameter(
					"data_title");
			String start_date = ServletActionContext.getRequest().getParameter(
					"start_date");
			String end_date = ServletActionContext.getRequest().getParameter(
					"end_date");
			String data_type = ServletActionContext.getRequest().getParameter(
					"data_type");
			PrintWriter out = ServletActionContext.getResponse().getWriter();
			Map<String, String> filterMap = getPagerMap();
			String[] viewArray = { "ID", "title", "url", "data_type" };
			if (title != null && !"".equals(title)) {
				filterMap.put("title", title);
			}
			if (start_date != null && !"".equals(start_date)) {
				filterMap.put("start_date", start_date);
			}
			if (end_date != null && !"".equals(end_date)) {
				filterMap.put("end_date", end_date);
			}
			if (data_type != null && !"".equals(data_type)) {
				filterMap.put("data_type", data_type);
			}
			PageResult result = (PageResult) lockerService
					.queryDataTable(filterMap);
			String returnStr = getColumnJson(result, viewArray);
			out.print(returnStr);
		} catch (IOException e) {
			logger.error(e);
			e.printStackTrace();
		}

	}

	/**
	 * 查询图片数据
	 * 
	 * @throws IOException
	 * @author 张加宁
	 */
	public void queryDataImgTable() throws IOException {
		try {
			ServletActionContext.getResponse().setContentType(
					"text/html; charset=utf-8");
			String title = StringUtil.encodingUrl(ServletActionContext
					.getRequest().getParameter("data_title"));
			String start_date = StringUtil.encodingUrl(ServletActionContext
					.getRequest().getParameter("start_date"));
			String end_date = StringUtil.encodingUrl(ServletActionContext
					.getRequest().getParameter("end_date"));
			String type = StringUtil.encodingUrl(ServletActionContext
					.getRequest().getParameter("type"));
			String edit_date = StringUtil.encodingUrl(ServletActionContext
					.getRequest().getParameter("edit_date"));
			String data_sub = StringUtil.encodingUrl(ServletActionContext
					.getRequest().getParameter("data_sub"));
			String collect_website = StringUtil
					.encodingUrl(ServletActionContext.getRequest()
							.getParameter("collect_website"));
			PrintWriter out = ServletActionContext.getResponse().getWriter();
			Map<String, String> filterMap = getPagerMap();
			String[] viewArray = {
					"ID",
					"title",
					"url",
					"imgUrl",
					"type:[{'joke':'搞笑','news':'新闻','film':'电影','game':'游戏','entertainment':'娱乐'}]",
					"collect_time", "collect_website",
					"data_sub:[{'0':'审核中','1':'审核通过','2':'审核未通过'}]" };
			if (title != null && !"".equals(title)) {
				filterMap.put("title", title);
			}
			if (start_date != null && !"".equals(start_date)) {
				filterMap.put("start_date", start_date);
			}
			if (end_date != null && !"".equals(end_date)) {
				filterMap.put("end_date", end_date);
			}
			if (type != null && !"".equals(type)) {
				filterMap.put("type", type);
			}
			if (edit_date != null && !"".equals(edit_date)) {
				filterMap.put("edit_date", edit_date);
			}
			if (data_sub != null && !"".equals(data_sub)) {
				filterMap.put("data_sub", data_sub);
			}
			if (collect_website != null && !"".equals(collect_website)) {
				filterMap.put("collect_website", collect_website);
			}
			HttpSession session = ServletActionContext.getRequest()
					.getSession();
			String userOrg = (String) session.getAttribute("USER_ORG");
			// String userRole = (String) session.getAttribute("USER_ROLE");
			if (!"".equals(userOrg) && null != userOrg) {
				filterMap.put("userOrg", userOrg);
			}
			// if (!"".equals(userRole) && null != userRole) {
			// filterMap.put("userRole", userRole);
			// }
			PageResult result = lockerService.queryDataImgTable(filterMap);
			String returnStr = getColumnJson(result, viewArray);
			out.print(returnStr);
		} catch (IOException e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	/**
	 * 根据ID删除图片数据
	 * 
	 * @throws IOException
	 * @author 张加宁
	 */
	public void deleteDataImgById() throws IOException {
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		try {
			String ids = ServletActionContext.getRequest().getParameter("ids");
			lockerService.deleteDataImgById(ids);
			out.print("{\"result\":\"success\"}");
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			out.print("{\"result\":\"error\"}");
		}
	}

	/**
	 * 添加数据
	 * 
	 * @throws IOException
	 * @author 张加宁
	 */
	public String addDataImg() throws IOException {
		ServletActionContext.getResponse().setContentType(
				"text/html; charset=utf-8");
		HttpSession session = ServletActionContext.getRequest().getSession();
		HttpServletRequest request = ServletActionContext.getRequest();
		String userOrg = (String) session.getAttribute("USER_ORG");
		Data_img_table dataImgTable = new Data_img_table();
		String sql = "select id,tag_name from tag ";
		List<Tag> tagList = (List<Tag>) lockerService.queryAllBySql(sql);
		if (tagList.size() > 0) {
			request.setAttribute("tagList", tagList);
		}
		if (!"0".equals(userOrg)) {
			dataImgTable.setCollect_website(userOrg);
			ServletActionContext.getRequest().setAttribute("dataImgTable",
					dataImgTable);
		} else {
			request.setAttribute("userOrg", userOrg);
		}
		session.removeAttribute("tagIdList");
		return "addDataImg";
	}

	/**
	 * 修改数据
	 * 
	 * @throws IOException
	 * @author 张加宁
	 */
	public String editDataImg() throws IOException {
		ServletActionContext.getResponse().setContentType(
				"text/html; charset=utf-8");
		String id = ServletActionContext.getRequest().getParameter("id");
		dataImgTable = lockerService.getDataImgById(id);
		HttpSession session = ServletActionContext.getRequest().getSession();
		HttpServletRequest request = ServletActionContext.getRequest();
		String sql = "select id,tag_name from tag ";
		List<Tag> tagList = (List<Tag>) lockerService.queryAllBySql(sql);
		if (tagList.size() > 0) {
			request.setAttribute("tagList", tagList);
		}
		String sqlId = "select tag_id from data_tag where data_id =" + id + "";
		List<?> tagIdList = lockerService.queryAllBySql(sqlId);
		if (tagIdList.size() > 0) {
			ServletActionContext.getRequest().setAttribute("tagIdList",
					tagIdList);
		}
		String sqlImgs = "select t.imageUrl,t.content,t.id from img t left join data_img di on di.img_id=t.id where di.data_id= "
				+ id;
		List<?> imgList = lockerService.queryAllBySql(sqlImgs);
		ServletActionContext.getRequest().setAttribute("imgList", imgList);
		String userOrg = (String) session.getAttribute("USER_ORG");
		request.setAttribute("userOrg", userOrg);
		return "editDataImg";
	}

	/**
	 * 保存数据
	 * 
	 * @throws IOException
	 * @author 张加宁
	 */
	public void saveDataImg() throws IOException {
		ServletActionContext.getResponse().setContentType(
				"text/html; charset=utf-8");
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		// List<String> imageName = new ArrayList<String>();
		String imageName = "";
		String checks[] = check.split(",");
		try {
			if (null == dataImgId || "".equals(dataImgId)
					|| "0".equals(dataImgId)) {
				dataImgTable.setData_sub(0);
				dataImgTable.setImgUrl(imgUrl[0]);
				// if (dataImgTable.getData_type() == null
				// || "".equals(dataImgTable.getData_type())) {
				// dataImgTable.setData_type("singleImg");
				// }
				String id = lockerService.save(dataImgTable);
				lockerService.save(dataImgTable);
				for (String ck : checks) {
					Data_tag dt = new Data_tag();
					dt.setTag_id(Integer.parseInt(ck.trim()));
					dt.setData_id(Integer.parseInt(id));
					lockerService.saveOrUpdate(dt);
				}
				if (image != null && image[0] != null) {
					String[] imgNamess = imgNames.substring(0,
							imgNames.length() - 1).split("#");
					if (image.length == 1 && image[0] != null) {
						imageName = uploadImg(imgNamess[0], image[0]);
						Data_img_table entity = lockerService
								.getDataImgById(id);
						entity.setUrl("http://cos.myqcloud.com/11000436/data/image/"
								+ imageName);
						entity.setImgUrl(imgUrl[0]);
						if (!"gif".equals(imageName.substring(
								imageName.indexOf(".") + 1).toLowerCase())
								&& !"html".equals(imageName.substring(
										imageName.indexOf(".") + 1)
										.toLowerCase())) {
							entity.setData_type("singleImg");
						}
						Img img = new Img();
						img.setContent(imgUrl[0]);
						img.setImageUrl("http://cos.myqcloud.com/11000436/data/image/"
								+ imageName);
						lockerService.save(img);
						Data_img dataImg = new Data_img();
						dataImg.setData_id(entity.getId());
						dataImg.setImg_id(img.getId());
						lockerService.save(dataImg);
						lockerService.save(entity);
					} else if (image.length > 1) {
						for (int i = 0; i < image.length; i++) {
							if (image[i] != null) {
								imageName = uploadImg(imgNamess[i], image[i]);
								Img img = new Img();
								Data_img dataImg = new Data_img();
								img.setImageUrl("http://cos.myqcloud.com/11000436/data/image/"
										+ imageName);
								img.setContent(imgUrl[i]);
								String img_id = lockerService.save(img);
								dataImg.setData_id(Integer.parseInt(id));
								dataImg.setImg_id(Integer.parseInt(img_id));
								lockerService.save(dataImg);
								if (i == 0) {
									Data_img_table entity = lockerService
											.getDataImgById(id);
									entity.setUrl("http://cos.myqcloud.com/11000436/data/image/"
											+ imageName);
									entity.setImgUrl("http://nb.hdlocker.com/pandora/locker!viewDataImg.action?id="
											+ Integer.parseInt(id) + "");
									entity.setData_type("multiImg");
									lockerService.save(entity);
								}
							}
						}
					}
				} else {
					Img img = new Img();
					img.setContent(imgUrl[0]);
					img.setImageUrl(dataImgTable.getUrl());
					lockerService.save(img);
					Data_img dataImg = new Data_img();
					dataImg.setData_id(dataImgTable.getId());
					dataImg.setImg_id(img.getId());
					lockerService.save(dataImg);
				}
			}
			out.print("{\"result\":\"success\"}");
		} catch (Exception e) {
			out.print("{\"result\":\"error\"}");
			logger.error(e);
			e.printStackTrace();
		}

	}

	/**
	 * 插入云数据库
	 * 
	 * @throws IOException
	 * @author 张加宁
	 */
	public void insertDataImg() throws IOException {
		HttpSession session = ServletActionContext.getRequest().getSession();
		String userOrg = (String) session.getAttribute("USER_ORG");
		if ("0".equals(userOrg)) {
			ServletActionContext.getResponse().setContentType(
					"text/html; charset=utf-8");
			PrintWriter out = ServletActionContext.getResponse().getWriter();
			try {
				String ids = ServletActionContext.getRequest().getParameter(
						"ids");
				int bl = lockerService.insertDataImg(ids);
				if (bl > 0)
					out.print("{\"result\":\"success\"}");
				else
					out.print("{\"result\":\"error\"}");
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
				out.print("{\"result\":\"error\"}");
			}
		}
	}

	/**
	 * 保存并插入云数据库
	 * 
	 * @throws IOException
	 * @author 张加宁
	 */
	public void saveInsertDataImg() throws IOException {
		HttpSession session = ServletActionContext.getRequest().getSession();
		String userOrg = (String) session.getAttribute("USER_ORG");
		if ("0".equals(userOrg)) {
			ServletActionContext.getResponse().setContentType(
					"text/html; charset=utf-8");
			PrintWriter out = ServletActionContext.getResponse().getWriter();
			try {
				String imageName = "";
				String checks[] = check.split(",");
				if (null == dataImgId || "".equals(dataImgId)) {
					dataImgTable.setData_sub(1);
					dataImgTable.setImgUrl(imgUrl[0]);
					String id = lockerService.save(dataImgTable);
					lockerService.save(dataImgTable);
					for (String ck : checks) {
						Data_tag dt = new Data_tag();
						dt.setTag_id(Integer.parseInt(ck.trim()));
						dt.setData_id(Integer.parseInt(id));
						lockerService.save(dt);
					}
					if (image != null && image[0] != null) {
						String[] imgNamess = imgNames.substring(0,
								imgNames.length() - 1).split("#");
						if (image.length == 1 && image[0] != null) {
							imageName = uploadImg(imgNamess[0], image[0]);
							Data_img_table entity = lockerService
									.getDataImgById(id);
							entity.setUrl("http://cos.myqcloud.com/11000436/data/image/"
									+ imageName);
							entity.setImgUrl(imgUrl[0]);
							if (!"gif".equals(imageName.substring(
									imageName.indexOf(".") + 1).toLowerCase())
									&& !"html".equals(imageName.substring(
											imageName.indexOf(".") + 1)
											.toLowerCase())) {
								entity.setData_type("singleImg");
							}
							Img img = new Img();
							img.setContent(imgUrl[0]);
							img.setImageUrl("http://cos.myqcloud.com/11000436/data/image/"
									+ imageName);
							lockerService.save(img);
							Data_img dataImg = new Data_img();
							dataImg.setData_id(entity.getId());
							dataImg.setImg_id(img.getId());
							lockerService.save(dataImg);
							lockerService.save(entity);
						} else if (image.length > 1) {
							for (int i = 0; i < image.length; i++) {
								if (image[i] != null) {
									imageName = uploadImg(imgNamess[i],
											image[i]);
									Img img = new Img();
									Data_img dataImg = new Data_img();
									img.setImageUrl("http://cos.myqcloud.com/11000436/data/image/"
											+ imageName);
									img.setContent(imgUrl[i]);
									String img_id = lockerService.save(img);
									dataImg.setData_id(Integer.parseInt(id));
									dataImg.setImg_id(Integer.parseInt(img_id));
									lockerService.save(dataImg);
									if (i == 0) {
										Data_img_table entity = lockerService
												.getDataImgById(id);
										entity.setUrl("http://cos.myqcloud.com/11000436/data/image/"
												+ imageName);
										entity.setImgUrl("http://nb.hdlocker.com/pandora/locker!viewDataImg.action?id="
												+ Integer.parseInt(id) + "");
										entity.setData_type("multiImg");
										lockerService.save(entity);
									}
								}
							}
						}
					} else {
						Img img = new Img();
						img.setContent(imgUrl[0]);
						img.setImageUrl(dataImgTable.getUrl());
						lockerService.save(img);
						Data_img dataImg = new Data_img();
						dataImg.setData_id(dataImgTable.getId());
						dataImg.setImg_id(img.getId());
						lockerService.save(dataImg);
					}
					int bl = lockerService.insertDataImg(dataImgTable);
					if (bl > 0)
						out.print("{\"result\":\"success\"}");
					else
						out.print("{\"result\":\"error\"}");
				}
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
				out.print("{\"result\":\"error\"}");
			}
		}
	}

	/**
	 * 批量保存参数
	 * 
	 * @throws IOException
	 * @author 张加宁
	 */
	public void saveParams() throws IOException {
		ServletActionContext.getResponse().setContentType(
				"text/html; charset=utf-8");
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		try {
			String ids = ServletActionContext.getRequest().getParameter("ids");
			String edit_date = StringUtil.encodingUrl(ServletActionContext
					.getRequest().getParameter("edit_date"));
			Map filterMap = new HashMap();// 存储参数的map
			if (edit_date != null && !"".equals(edit_date)) {
				filterMap.put("edit_date", edit_date);
			}
			if (ids != null && !"".equals(ids)) {
				filterMap.put("ids", ids);
			}
			if (filterMap.size() > 1) {
				int count = lockerService.saveParams(filterMap);
				if (count > 0) {
					out.print("{\"result\":\"success\"}");
				} else {
					out.print("{\"result\":\"empty\"}");
				}

			} else {
				out.print("{\"result\":\"empty\"}");
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			out.print("{\"result\":\"error\"}");
		}
	}

	/**
	 * 查询标签数据
	 * 
	 * @author 张加宁
	 */
	public void queryTagTable() {
		try {
			ServletActionContext.getResponse().setContentType(
					"text/html; charset=utf-8");
			String tag_name = StringUtil.encodingUrl(ServletActionContext
					.getRequest().getParameter("tag_name"));
			PrintWriter out = ServletActionContext.getResponse().getWriter();
			Map<String, String> filterMap = getPagerMap();
			String[] viewArray = { "id", "tag_name" };
			if (tag_name != null && !"".equals(tag_name)) {
				filterMap.put("tag_name", tag_name);
			}
			PageResult result = lockerService.queryTagTable(filterMap);
			String returnStr = getColumnJson(result, viewArray);
			out.print(returnStr);
		} catch (IOException e) {
			logger.error(e);
			e.printStackTrace();
		}

	}

	/**
	 * 修改标签数据
	 * 
	 * @throws IOException
	 * @author 张加宁
	 */
	public String editTag() throws IOException {
		ServletActionContext.getResponse().setContentType(
				"text/html; charset=utf-8");
		String id = ServletActionContext.getRequest().getParameter("id");
		tag = lockerService.getTagById(id);
		return "editTag";
	}

	/**
	 * 保存标签信息
	 * 
	 * @throws IOException
	 * @author 张加宁
	 */
	public void saveTag() throws IOException {
		ServletActionContext.getResponse().setContentType(
				"text/html; charset=utf-8");
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		try {
			if (0 == tag.getId()) {
				lockerService.saveOrUpdate(tag);
			} else {
				Tag entity = lockerService.getTagById(String.valueOf(tag
						.getId()));
				entity.setId(tag.getId());
				entity.setTag_name(tag.getTag_name());
				lockerService.saveOrUpdate(entity);
			}
			out.print("{\"result\":\"success\"}");

		} catch (Exception e) {
			out.print("{\"result\":\"error\"}");
			logger.error(e);
			e.printStackTrace();
		}

	}

	/**
	 * 根据ID删除标签
	 * 
	 * @throws IOException
	 * @author 张加宁
	 */
	public void deleteTagById() throws IOException {
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		try {
			String ids = ServletActionContext.getRequest().getParameter("ids");
			lockerService.deleteTagById(ids);
			out.print("{\"result\":\"success\"}");
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			out.print("{\"result\":\"error\"}");
		}
	}

	/**
	 * 上传图片云存储
	 * 
	 * @param imageNameStr
	 * @param imageFile
	 * @return
	 * @throws Exception
	 * @author 张加宁
	 */
	public String uploadImg(String imageNameStr, File imageFile)
			throws Exception {
		// 用户定义变量
		int accessId = 11000436; // accessId
		String accessKey = "7OgnLklEIptHNwZCS0RDNk1rUXrxXJfP"; // accessKey
		String bucketId = "data"; // bucket id
		String secretId = "AKIDBvY9dcNUS2LeFTxI2ThzgrKxuWuNROIr";
		Cos cos = null;
		try {
			cos = new CosImpl(accessId, accessKey, Common.COS_HOST,
					Common.DOWNLOAD_HOST, secretId);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		String imgType = "";
		String uploadImg = "";
		if (imageFile != null && imageNameStr != null) {
			imgType = imageNameStr.substring(imageNameStr.lastIndexOf("."));
			uploadImg = imageNameStr + new Date().getTime();
			MD5 md5 = new MD5();
			uploadImg = md5.getMD5ofStr(uploadImg);
			// 返回消息体, 包含错误码和错误消息
			Message msg = new Message();
			// System.out.println("----------------------uploadFileContent----------------------\n");
			Map<String, Object> inParams = new HashMap<String, Object>();
			String fileName = uploadImg + imgType;
			inParams.put("compressBucketId", bucketId);
			inParams.put("compressFilePath", "/image/" + fileName);
			inParams.put("zoomType", 1);// 等比缩放
			inParams.put("width", 720);// 缩放后宽度
			inParams.put("height", 720);// 缩放后高度
			Map<String, CosFile> files = new HashMap<String, CosFile>();
			files.put("compressFile", new CosFile());
			cos.uploadFileContentWithCompress(inParams,
					FileUtils.readFileToByteArray(imageFile), files, msg);
			System.out.println(files);
			System.out.println(msg);
		}
		return uploadImg + imgType;
	}

	/**
	 * 查询壁纸数据
	 * 
	 * @throws IOException
	 * @author louxiaojian
	 */
	public void queryWallPaper() throws IOException {
		try {
			ServletActionContext.getResponse().setContentType(
					"text/html; charset=utf-8");
			String p_name = StringUtil.encodingUrl(ServletActionContext
					.getRequest().getParameter("p_name"));
			String start_date = StringUtil.encodingUrl(ServletActionContext
					.getRequest().getParameter("start_date"));
			String end_date = StringUtil.encodingUrl(ServletActionContext
					.getRequest().getParameter("end_date"));
			String imageEXT = StringUtil.encodingUrl(ServletActionContext
					.getRequest().getParameter("imageEXT"));
			String data_sub = StringUtil.encodingUrl(ServletActionContext
					.getRequest().getParameter("data_sub"));
			PrintWriter out = ServletActionContext.getResponse().getWriter();
			Map<String, String> filterMap = getPagerMap();
			String[] viewArray = { "ID", "name", "desc", "author", "thumbURL",
					"imageURL", "imageNAME", "imageEXT", "publishDATE",
					"data_sub:[{'0':'审核中','1':'审核通过','2':'审核未通过'}]" };
			if (p_name != null && !"".equals(p_name)) {
				filterMap.put("p_name", p_name);
			}
			if (start_date != null && !"".equals(start_date)) {
				filterMap.put("start_date", start_date);
			}
			if (end_date != null && !"".equals(end_date)) {
				filterMap.put("end_date", end_date);
			}
			if (imageEXT != null && !"".equals(imageEXT)) {
				filterMap.put("imageEXT", imageEXT);
			}
			if (data_sub != null && !"".equals(data_sub)) {
				filterMap.put("data_sub", data_sub);
			}

			PageResult result = lockerService.queryWallPaper(filterMap);
			String returnStr = getColumnJson(result, viewArray);
			out.print(returnStr);
		} catch (IOException e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	/**
	 * 跳转至编辑壁纸页
	 * 
	 * @return
	 * @throws IOException
	 */
	public String editWallPaper() throws IOException {
		ServletActionContext.getResponse().setContentType(
				"text/html; charset=utf-8");
		String id = ServletActionContext.getRequest().getParameter("id");
		if (!"".equals(id) && id != null) {
			wallPaper = (WallPaper) lockerService.getObjectById(
					WallPaper.class, Integer.parseInt(id));
		}
		return "editWallPaper";
	}

	/**
	 * 保存壁纸到本地
	 * 
	 * @throws IOException
	 */
	public void saveWallPaper() throws IOException {
		ServletActionContext.getResponse().setContentType(
				"text/html; charset=utf-8");
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		try {
			String imageName = "";
			if (image[0] != null) {
				imageName = uploadWallPaper();
			}
			if (0 == wallPaper.getId()) {
				if (image[0] != null) {
					wallPaper
							.setImageURL("http://cos.myqcloud.com/11000436/wallpaper/image/"
									+ imageName);
					wallPaper
							.setThumbURL("http://cos.myqcloud.com/11000436/wallpaper/thumb/"
									+ imageName);
					String imageNAME = imgName.substring(0,
							imgName.lastIndexOf("."));
					wallPaper.setImageNAME(imageNAME);
					String imageEXT = imgName.substring(imgName
							.lastIndexOf("."));
					wallPaper.setImageEXT(imageEXT);
				}
				wallPaper.setData_sub(0);// 保存至本地
				lockerService.save(wallPaper);
			} else {
				WallPaper wPaper = (WallPaper) lockerService.getObjectById(
						WallPaper.class, wallPaper.getId());
				if (image[0] != null) {
					wPaper.setImageURL("http://cos.myqcloud.com/11000436/wallpaper/image/"
							+ imageName);
					wPaper.setThumbURL("http://cos.myqcloud.com/11000436/wallpaper/thumb/"
							+ imageName);
					wPaper.setImageNAME(imgName);
					wPaper.setImageEXT(imgName.substring(imgName
							.lastIndexOf(".")));
				}
				wPaper.setP_author(wallPaper.getP_author());
				wPaper.setP_desc(wallPaper.getP_desc());
				wPaper.setP_name(wallPaper.getP_name());
				wPaper.setPublishDATE(wallPaper.getPublishDATE());
				wPaper.setData_sub(0);// 保存至本地
				lockerService.saveOrUpdate(wPaper);
			}
			out.print("{\"result\":\"success\"}");

		} catch (Exception e) {
			out.print("{\"result\":\"error\"}");
			logger.error(e);
			e.printStackTrace();
		}

	}

	/**
	 * 保存壁纸到云数据库
	 * 
	 * @throws IOException
	 */
	public void saveInsertWallPaper() throws IOException {
		ServletActionContext.getResponse().setContentType(
				"text/html; charset=utf-8");
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		try {
			String imageName = "";
			if (image[0] != null) {
				imageName = uploadWallPaper();
			}
			int flag = 0;
			if (0 == wallPaper.getId()) {
				if (image[0] != null) {
					wallPaper
							.setImageURL("http://cos.myqcloud.com/11000436/wallpaper/image/"
									+ imageName);
					wallPaper
							.setThumbURL("http://cos.myqcloud.com/11000436/wallpaper/thumb/"
									+ imageName);
					String imageNAME = imgName.substring(0,
							imgName.lastIndexOf("."));
					wallPaper.setImageNAME(imageNAME);
					String imageEXT = imgName.substring(imgName
							.lastIndexOf("."));
					wallPaper.setImageEXT(imageEXT);
				}
				wallPaper.setData_sub(1);// 已上传至云服务器
				lockerService.save(wallPaper);
				flag = lockerService.insertWallPaper(wallPaper);
			} else {
				WallPaper wPaper = (WallPaper) lockerService.getObjectById(
						WallPaper.class, wallPaper.getId());
				if (image[0] != null) {
					wPaper.setImageURL("http://cos.myqcloud.com/11000436/wallpaper/image/"
							+ imageName);
					wPaper.setThumbURL("http://cos.myqcloud.com/11000436/wallpaper/thumb/"
							+ imageName);
					wPaper.setImageNAME(imgName);
					wPaper.setImageEXT(imgName.substring(imgName
							.lastIndexOf(".")));
				}
				wPaper.setP_author(wallPaper.getP_author());
				wPaper.setP_desc(wallPaper.getP_desc());
				wPaper.setP_name(wallPaper.getP_name());
				wPaper.setPublishDATE(wallPaper.getPublishDATE());
				wPaper.setData_sub(1);// 已上传至云服务器
				lockerService.saveOrUpdate(wPaper);
				flag = lockerService.insertWallPaper(wPaper);

			}
			if (flag > 0) {
				out.print("{\"result\":\"success\"}");
			} else {
				out.print("{\"result\":\"error\"}");
			}

		} catch (Exception e) {
			out.print("{\"result\":\"error\"}");
			logger.error(e);
			e.printStackTrace();
		}

	}

	/**
	 * 上传壁纸并生成缩略图
	 * 
	 * @return
	 * @throws Exception
	 */
	public String uploadWallPaper() throws Exception {
		// 用户定义变量
		int accessId = 11000436; // accessId
		String accessKey = "7OgnLklEIptHNwZCS0RDNk1rUXrxXJfP"; // accessKey
		String bucketId = "wallpaper"; // bucket id
		String secretId = "AKIDBvY9dcNUS2LeFTxI2ThzgrKxuWuNROIr";
		Cos cos = null;
		try {
			cos = new CosImpl(accessId, accessKey, Common.COS_HOST,
					Common.DOWNLOAD_HOST, secretId);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		String imgType = "";
		String uploadImg = "";
		if (null != imgName && !"".equals(imgName)) {
			imgType = imgName.substring(imgName.lastIndexOf("."));
			uploadImg = imgName + new Date().getTime();
			MD5 md5 = new MD5();
			uploadImg = md5.getMD5ofStr(uploadImg);
		}
		// 返回消息体, 包含错误码和错误消息
		Message msg = new Message();
		// System.out.println("----------------------uploadFileContent----------------------\n");
		Map<String, Object> inParams = new HashMap<String, Object>();
		// inParams.put("bucketId", bucketId);
		String fileName = uploadImg + imgType;
		inParams.put("uploadBucketId", bucketId);
		inParams.put("compressBucketId", bucketId);
		inParams.put("uploadFilePath", "/image/" + fileName);
		inParams.put("compressFilePath", "/thumb/" + fileName);
		inParams.put("zoomType", 1);// 等比缩放
		inParams.put("width", 180);// 缩放后宽度
		inParams.put("height", 324);// 缩放后高度
		Map<String, CosFile> files = new HashMap<String, CosFile>();
		files.put("uploadFile", new CosFile());
		files.put("compressFile", new CosFile());
		cos.uploadFileContentWithCompress(inParams,
				FileUtils.readFileToByteArray(image[0]), files, msg);
		System.out.println(files);
		System.out.println(msg);

		return fileName;
	}

	/**
	 * 根据id删除壁纸
	 * 
	 * @throws IOException
	 */
	public void deleteWallPaperById() throws IOException {
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		try {
			String ids = ServletActionContext.getRequest().getParameter("ids");
			lockerService.deleteWallPaperById(ids);
			out.print("{\"result\":\"success\"}");
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			out.print("{\"result\":\"error\"}");
		}
	}

	/**
	 * 将制定id的壁纸保存至云服务器中
	 * 
	 * @throws IOException
	 */
	public void insertWallPaper() throws IOException {
		HttpSession session = ServletActionContext.getRequest().getSession();
		String userOrg = (String) session.getAttribute("USER_ORG");
		if ("0".equals(userOrg)) {
			ServletActionContext.getResponse().setContentType(
					"text/html; charset=utf-8");
			PrintWriter out = ServletActionContext.getResponse().getWriter();
			try {
				String ids = ServletActionContext.getRequest().getParameter(
						"ids");
				int bl = lockerService.insertWallPaper(ids);
				if (bl > 0)
					out.print("{\"result\":\"success\"}");
				else
					out.print("{\"result\":\"error\"}");
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
				out.print("{\"result\":\"error\"}");
			}
		}
	}

	/**
	 * 验证是否是多图文数据
	 * 
	 * @throws IOException
	 */
	public void CheckedManyImgsByDataImgTableId() throws IOException {
		ServletActionContext.getResponse().setContentType(
				"text/html; charset=utf-8");
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		try {
			String id = ServletActionContext.getRequest().getParameter("id");
			List list = lockerService
					.queryAllBySql("select t.imageUrl,t.content from img t left join data_img di on di.img_id=t.id where di.data_id= "
							+ id);
			if (list.size() > 1)
				out.print("{\"result\":\"error\"}");
			else
				out.print("{\"result\":\"success\"}");
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			out.print("{\"result\":\"error\"}");
		}
	}

	/**
	 * 修改数据
	 * 
	 * @throws IOException
	 */
	public void updateDataImg() throws IOException {
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		try {
			// 获取所选标签
			String checks[] = check.split(",");
			String ids = ServletActionContext.getRequest().getParameter("ids");
			// 获取要删除的img的id
			String delImgIds = ServletActionContext.getRequest().getParameter(
					"delImgIds");
			// 验证是否是保存并插入数据库1，是
			String flag = ServletActionContext.getRequest()
					.getParameter("flag");
			if (!"".equals(delImgIds) && delImgIds != null) {
				if (!"".equals(delImgIds.substring(0, delImgIds.length() - 1))) {
					// 根据id删除相应img
					lockerService.executeBySQL("delete from img where id in ("
							+ delImgIds.substring(0, delImgIds.length() - 1)
							+ ")");
					// 删除相应的data_img中间表数据
					lockerService
							.executeBySQL("delete from data_img where img_id in ("
									+ delImgIds.substring(0,
											delImgIds.length() - 1) + ")");
				}
			}
			Data_img_table dit = (Data_img_table) lockerService.getObjectById(
					Data_img_table.class, Integer.parseInt(dataImgId));
			dit.setTitle(dataImgTable.getTitle());
			dit.setCollect_website(dataImgTable.getCollect_website());
			dit.setType(dataImgTable.getType());
			dit.setCollect_time(dataImgTable.getCollect_time());
			// 删除数据原有标签
			lockerService.deleteTagByDataId(Integer.parseInt(dataImgId));
			for (String ck : checks) {
				Data_tag dt = new Data_tag();
				dt.setTag_id(Integer.parseInt(ck.trim()));
				dt.setData_id(Integer.parseInt(dataImgId));
				lockerService.saveOrUpdate(dt);
			}
			int uImgCount = 0;
			for (int i = 0; i < imgUrl.length; i++) {
				if (i < oldFile.length) {// 修改原有图文信息
					Img img = (Img) lockerService.getObjectById(Img.class,
							Integer.parseInt(uImgId[i]));
					img.setContent(imgUrl[i]);
					if (oldFile[i] == null || "".equals(oldFile[i])) {
						File file = images.get(uImgCount);
						System.out.println(file.getName());
						System.out.println(file.getAbsoluteFile());
						String imageName = uploadImg(
								imagesFileName.get(uImgCount) + "", file);
						img.setImageUrl("http://cos.myqcloud.com/11000436/data/image/"
								+ imageName);
						uImgCount++;
					}
					lockerService.saveOrUpdate(img);
				} else {// 添加新的图文信息
					if (imgUrl[i] != null && !"".equals(imgUrl[i])) {
						if (imagesFileName != null) {
							if (imagesFileName.get(uImgCount) != null
									&& !"".equals(imagesFileName.get(uImgCount))) {
								Img img = new Img();
								img.setContent(imgUrl[i]);
								if (images.get(uImgCount) != null) {
									File file = images.get(uImgCount);
									String imageName = uploadImg(
											imagesFileName.get(uImgCount) + "",
											file);
									img.setImageUrl("http://cos.myqcloud.com/11000436/data/image/"
											+ imageName);
								}
								lockerService.saveOrUpdate(img);
								Data_img di = new Data_img();
								di.setData_id(dit.getId());
								di.setImg_id(img.getId());
								lockerService.save(di);
								uImgCount++;
							}
						}
					} else {
						Img img = new Img();
						if (imagesFileName != null) {
							if (imagesFileName.get(uImgCount) != null
									&& !"".equals(imagesFileName.get(uImgCount))) {
								File file = images.get(uImgCount);
								String imageName = uploadImg(
										imagesFileName.get(uImgCount) + "",
										file);
								img.setImageUrl("http://cos.myqcloud.com/11000436/data/image/"
										+ imageName);
								lockerService.saveOrUpdate(img);
								Data_img di = new Data_img();
								di.setData_id(dit.getId());
								di.setImg_id(img.getId());
								lockerService.save(di);
								uImgCount++;
							}
						}
					}
				}
			}
			List list = lockerService
					.queryAllBySql("select i.id,i.imageUrl,i.content from img i left join data_img d on d.img_id=i.id where d.data_id="
							+ dit.getId());
			if (list.size() > 0) {
				Object[] obj = (Object[]) list.get(0);
				dit.setUrl(obj[1] + "");
				if (list.size() > 1) {
					dit.setImgUrl("http://nb.hdlocker.com/pandora/locker!viewDataImg.action?id="
							+ Integer.parseInt(dataImgId));
				} else if (list.size() == 1) {
					dit.setImgUrl(obj[2] + "");
				}
			}
			// 保存dit对象
			lockerService.save(dit);
			if ("1".equals(flag)) {
				int num = lockerService.insertDataImg(dit);
				if (num > 0) {
					out.print("{\"result\":\"success\"}");
				} else {
					out.print("{\"result\":\"error\"}");
				}
			} else {
				out.print("{\"result\":\"success\"}");
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			out.print("{\"result\":\"error\"}");
		}
	}
	/**
	 * 加载下载
	 * @author 张加宁
	 * @throws IOException 
	 */
	public void selectInit() throws IOException{
		ServletActionContext.getResponse().setContentType(
				"text/html; charset=utf-8");
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String orgSql = "select user_org from user where user_org not in ('0','1') ";
			List<User> orgList = (List<User>) lockerService.queryAllBySql(orgSql);
			if(orgList.size()>0){
				out.print("{\"result\":\"success\",\"selectData\":"+ JSON.toJSONString(orgList, true) + "}");
			}else{
				out.print("{\"result\":\"null\"}");
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			out.print("{\"result\":\"error\"}");
		}
	}
}
