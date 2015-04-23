package cn.zmdx.locker.actions;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

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
import cn.zmdx.locker.entity.Notification;
import cn.zmdx.locker.entity.PageResult;
import cn.zmdx.locker.entity.Tag;
import cn.zmdx.locker.entity.User;
import cn.zmdx.locker.entity.WallPaper;
import cn.zmdx.locker.service.impl.LockerServiceImpl;
import cn.zmdx.locker.util.FileUtil;
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
	private Notification notify;
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

	public Notification getNotify() {
		return notify;
	}

	public void setNotify(Notification notify) {
		this.notify = notify;
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
			String custom_user = StringUtil.encodingUrl(ServletActionContext
					.getRequest().getParameter("custom_user"));
			String type = StringUtil.encodingUrl(ServletActionContext
					.getRequest().getParameter("type"));
			String edit_date = StringUtil.encodingUrl(ServletActionContext
					.getRequest().getParameter("edit_date"));
			String data_sub = StringUtil.encodingUrl(ServletActionContext
					.getRequest().getParameter("data_sub"));
			String collect_website = StringUtil
					.encodingUrl(ServletActionContext.getRequest()
							.getParameter("collect_website"));
			String stick = StringUtil
					.encodingUrl(ServletActionContext.getRequest()
							.getParameter("stick"));
			PrintWriter out = ServletActionContext.getResponse().getWriter();
			Map<String, String> filterMap = getPagerMap();
			String[] viewArray = { "ID", "title", "url", "imgUrl",
					"type:[{'1':'头条','2':'八卦','3':'微精选','4':'美女','5':'搞笑'}]",
					"collect_time", "collect_website",
					"data_sub:[{'0':'审核中','1':'审核通过','2':'审核未通过'}]",
					"user_org", "username", "data_view","stick:[{'0':'否','1':'是'}]" };
			if (title != null && !"".equals(title)) {
				filterMap.put("title", title);
			}
			if (start_date != null && !"".equals(start_date)) {
				filterMap.put("start_date", start_date);
			}
			if (end_date != null && !"".equals(end_date)) {
				filterMap.put("end_date", end_date);
			}
			if (custom_user != null && !"".equals(custom_user)) {
				filterMap.put("custom_user", custom_user);
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
			if (stick != null && !"".equals(stick)) {
				filterMap.put("stick", stick);
			}
			HttpSession session = ServletActionContext.getRequest()
					.getSession();
			String userOrg = session.getAttribute("USER_ORG").toString();
			// String userRole = (String) session.getAttribute("USER_ROLE");
			if (!"".equals(userOrg) && null != userOrg) {
				filterMap.put("userOrg", userOrg);
				filterMap.put("userid", session.getAttribute("USER_ID")
						.toString());
			}
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
		HttpSession session = ServletActionContext.getRequest().getSession();
		String imageName = "";
		String checks[] = check.split(",");
		String content1 = StringUtil.encodingUrl(ServletActionContext.getRequest().getParameter(
				"content1"));
		String stick = ServletActionContext.getRequest().getParameter(
				"stick");
		try {
			if (null == dataImgId || "".equals(dataImgId)
					|| "0".equals(dataImgId)) {
				dataImgTable.setData_sub(0);
				dataImgTable.setUserid(Integer.parseInt(session.getAttribute(
						"USER_ID").toString()));
				if (content1.startsWith("http")) {
					dataImgTable.setData_type("html");
				}
				// if (dataImgTable.getData_type() == null
				// || "".equals(dataImgTable.getData_type())) {
				// dataImgTable.setData_type("singleImg");
				// }
				int top = (int) Math.round(Math.random() * 50 );
				int views = top + (int) Math.round(Math.random() * 1100 + 555);
				dataImgTable.setViews(views);
				dataImgTable.setTop(top);
				dataImgTable.setStick(stick);
				String id = lockerService.save(dataImgTable);
				// 添加标签
				for (String ck : checks) {
					Data_tag dt = new Data_tag();
					dt.setTag_id(Integer.parseInt(ck.trim()));
					dt.setData_id(Integer.parseInt(id));
					lockerService.saveOrUpdate(dt);
				}
				if (image != null && image[0] != null) {// 多图文
					// 封面图
					imageName = uploadImg(
							imgNames.substring(0, imgNames.length() - 1),
							image[0], 0,stick);
					Img img = new Img();
					img.setContent(content1);
					System.out.println(content1);
					System.out.println(content1.length());
					String img_id = lockerService.save(img);

					Data_img dataImg = new Data_img();
					dataImg.setData_id(Integer.parseInt(id));
					dataImg.setImg_id(Integer.parseInt(img_id));
					lockerService.save(dataImg);
					dataImgTable
							.setUrl("http://cos.myqcloud.com/11000436/data/image/"
									+ imageName);
					if (content1.startsWith("http")) {
						dataImgTable.setData_type("html");
					} else {
						dataImgTable
								.setImgUrl("http://nb.hdlocker.com/pandora/locker!viewDataImg.action?id="
										+ Integer.parseInt(id) + "");
						dataImgTable.setData_type("multiImg");
					}
				} else {
					dataImgTable.setImgUrl(content1);
				}
				lockerService.saveOrUpdate(dataImgTable);
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
			String imageName = "";
			String checks[] = check.split(",");
			String content1 = StringUtil.encodingUrl(ServletActionContext.getRequest().getParameter(
					"content1"));
			String stick = ServletActionContext.getRequest().getParameter(
					"stick");
			try {
				if (null == dataImgId || "".equals(dataImgId)
						|| "0".equals(dataImgId)) {
					dataImgTable.setData_sub(0);
					dataImgTable.setUserid(Integer.parseInt(session
							.getAttribute("USER_ID").toString()));
					if (content1.startsWith("http")) {
						dataImgTable.setData_type("html");
					}
					// if (dataImgTable.getData_type() == null
					// || "".equals(dataImgTable.getData_type())) {
					// dataImgTable.setData_type("singleImg");
					// }
					int top = (int) Math.round(Math.random() * 50 );
					int views = top
							+ (int) Math.round(Math.random() * 1100 + 555);
					dataImgTable.setViews(views);
					dataImgTable.setTop(top);
					String id = lockerService.save(dataImgTable);
					// 添加标签
					for (String ck : checks) {
						Data_tag dt = new Data_tag();
						dt.setTag_id(Integer.parseInt(ck.trim()));
						dt.setData_id(Integer.parseInt(id));
						lockerService.saveOrUpdate(dt);
					}
					if (image != null && image[0] != null) {// 多图文
						// 封面图
						imageName = uploadImg(
								imgNames.substring(0, imgNames.length() - 1),
								image[0], 0,stick);
						Img img = new Img();
						img.setContent(content1);
						String img_id = lockerService.save(img);

						Data_img dataImg = new Data_img();
						dataImg.setData_id(Integer.parseInt(id));
						dataImg.setImg_id(Integer.parseInt(img_id));
						lockerService.save(dataImg);
						dataImgTable
								.setUrl("http://cos.myqcloud.com/11000436/data/image/"
										+ imageName);
						if (content1.startsWith("http")) {
							dataImgTable.setData_type("html");
						} else {
							dataImgTable
									.setImgUrl("http://nb.hdlocker.com/pandora/locker!viewDataImg.action?id="
											+ Integer.parseInt(id) + "");
							dataImgTable.setData_type("multiImg");
						}
					} else {
						dataImgTable.setImgUrl(content1);
					}
					lockerService.saveOrUpdate(dataImgTable);
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
			String data_sub = ServletActionContext.getRequest().getParameter(
					"data_sub");
			String edit_date = StringUtil.encodingUrl(ServletActionContext
					.getRequest().getParameter("edit_date"));
			Map filterMap = new HashMap();// 存储参数的map
			if (edit_date != null && !"".equals(edit_date)) {
				filterMap.put("edit_date", edit_date);
			}
			if (ids != null && !"".equals(ids)) {
				filterMap.put("ids", ids);
			}
			if (data_sub != null && !"".equals(data_sub)) {
				filterMap.put("data_sub", data_sub);
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
	 * @param type 0封面，1内容
	 * @param stick 0不置顶，1置顶
	 * @return
	 * @throws Exception
	 * @author 张加宁
	 */
	public String uploadImg(String imageNameStr, File imageFile, int type,String stick)
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
			if (type == 0) {
				inParams.put("compressFilePath", "/image/" + fileName);
				inParams.put("zoomType", 1);// 等比缩放
				if("1".equals(stick)){
					inParams.put("width", 700);// 缩放后宽度
					inParams.put("height", 480);// 缩放后高度
				}else{
					inParams.put("width", 400);// 缩放后宽度
					inParams.put("height", 400);// 缩放后高度
				}
			}
			inParams.put("compress", 0);// 是否需要压缩(质量为 85),(默认压缩) 0: 不压缩 1:
										// 压缩(default)
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
			String publishdate = StringUtil.encodingUrl(ServletActionContext
					.getRequest().getParameter("publishdate"));
			String imageEXT = StringUtil.encodingUrl(ServletActionContext
					.getRequest().getParameter("imageEXT"));
			String data_sub = StringUtil.encodingUrl(ServletActionContext
					.getRequest().getParameter("data_sub"));
			PrintWriter out = ServletActionContext.getResponse().getWriter();
			Map<String, String> filterMap = getPagerMap();
			String[] viewArray = { "ID", "p_name", "p_desc", "p_author",
					"thumbURL", "imageURL", "imageNAME", "imageEXT",
					"publishDATE",
					"data_sub:[{'0':'审核中','1':'审核通过','2':'审核未通过'}]" };
			if (p_name != null && !"".equals(p_name)) {
				filterMap.put("p_name", p_name);
			}
			if (publishdate != null && !"".equals(publishdate)) {
				filterMap.put("publishdate", publishdate);
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
					int top = (int) Math.round(Math.random() * 100 + 23);
					wallPaper.setTop(top);
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
					int top = (int) Math.round(Math.random() * 100 + 23);
					wallPaper.setTop(top);
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
		inParams.put("width", 720);// 缩放后宽度
		inParams.put("height", 720);// 缩放后高度
		// inParams.put("compress", 0);
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
		HttpSession session = ServletActionContext.getRequest().getSession();
		try {
			// 获取所选标签
			String checks[] = check.split(",");
			// 获取要删除的img的id
			String delImgIds = ServletActionContext.getRequest().getParameter(
					"delImgIds");
			// 封面图名称
			String imageName = "";
			// 原封面图URL
			String cover = ServletActionContext.getRequest().getParameter(
					"cover");
			// 新闻内容
			String content1 = StringUtil.encodingUrl(ServletActionContext.getRequest().getParameter(
					"content1"));
			String stick = ServletActionContext.getRequest().getParameter(
					"stick");
			// 验证是否是保存并插入数据库1，是
			String flag = ServletActionContext.getRequest()
					.getParameter("flag");
			Data_img_table dit = (Data_img_table) lockerService.getObjectById(
					Data_img_table.class, Integer.parseInt(dataImgId));
			dit.setTitle(dataImgTable.getTitle());
			dit.setUserid(Integer.parseInt(session.getAttribute("USER_ID")
					.toString()));
			dit.setCollect_website(dataImgTable.getCollect_website());
			dit.setType(dataImgTable.getType());
			dit.setCollect_time(dataImgTable.getCollect_time());
			// 删除数据原有标签
			lockerService.deleteTagByDataId(Integer.parseInt(dataImgId));
			if (content1.startsWith("http")) {
				dit.setData_type("html");
			}
			// if (dataImgTable.getData_type() == null
			// || "".equals(dataImgTable.getData_type())) {
			// dataImgTable.setData_type("singleImg");
			// }
			// 添加标签
			for (String ck : checks) {
				Data_tag dt = new Data_tag();
				dt.setTag_id(Integer.parseInt(ck.trim()));
				dt.setData_id(Integer.parseInt(dataImgId));
				lockerService.saveOrUpdate(dt);
			}
			if (cover != null && !"".equals(cover)) {// 原多图文
				// if
				// (dataImgTable.getUrl()==null||"".equals(dataImgTable.getUrl()))
				// {//多图文
				if (dataImgTable.getUrl().indexOf(
						"http://cos.myqcloud.com/11000436/data/image/") < 0) {// 多图文改为单图文
					// 删除原有的图文记录
					// 根据id删除相应img
					lockerService.executeBySQL("delete from img where id ="
							+ dataImgId);
					// 删除相应的data_img中间表数据
					lockerService
							.executeBySQL("delete from data_img where data_id ="
									+ dataImgId);
					dit.setImgUrl(content1);
				} else {
					// 删除原有的图文记录
					// 根据id删除相应img
					lockerService.executeBySQL("delete from img where id ="
							+ dataImgId);
					// 删除相应的data_img中间表数据
					lockerService
							.executeBySQL("delete from data_img where data_id ="
									+ dataImgId);
					Img img = new Img();
					img.setContent(content1);
					String img_id = lockerService.save(img);

					Data_img dataImg = new Data_img();
					dataImg.setData_id(Integer.parseInt(dataImgId));
					dataImg.setImg_id(Integer.parseInt(img_id));
					lockerService.save(dataImg);
					if (imgNames != null && !"".equals(imgNames)) {
						// 封面图
						imageName = uploadImg(
								imgNames.substring(0, imgNames.length() - 1),
								image[0], 0,stick);
						dit.setUrl("http://cos.myqcloud.com/11000436/data/image/"
								+ imageName);
					}
					if (content1.startsWith("http")) {
						dit.setData_type("html");
					} else {
						dit.setImgUrl("http://nb.hdlocker.com/pandora/locker!viewDataImg.action?id="
								+ Integer.parseInt(dataImgId) + "");
						dit.setData_type("multiImg");
					}
				}
			} else {// 原单图文
				if (image != null && image[0] != null) {// 单图文改为多图文
					// 封面图
					imageName = uploadImg(
							imgNames.substring(0, imgNames.length() - 1),
							image[0], 0,stick);
					Img img = new Img();
					img.setContent(content1);
					String img_id = lockerService.save(img);

					Data_img dataImg = new Data_img();
					dataImg.setData_id(Integer.parseInt(dataImgId));
					dataImg.setImg_id(Integer.parseInt(img_id));
					lockerService.save(dataImg);
					dit.setUrl("http://cos.myqcloud.com/11000436/data/image/"
							+ imageName);
					if (content1.startsWith("http")) {
						dit.setData_type("html");
					} else {
						dit.setImgUrl("http://nb.hdlocker.com/pandora/locker!viewDataImg.action?id="
								+ Integer.parseInt(dataImgId) + "");
						dit.setData_type("multiImg");
					}
				} else {
					dit.setImgUrl(content1);
				}
			}
			lockerService.saveOrUpdate(dit);
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
	 * 加载来源下拉内容
	 * 
	 * @author 张加宁
	 * @throws IOException
	 */
	public void selectInit() throws IOException {
		ServletActionContext.getResponse().setContentType(
				"text/html; charset=utf-8");
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		try {
			String orgSql = "select user_org from user where user_org not in ('0','1') ";
			String cusUserSql = "select userid,username from user where user_org = '1' ";
			List<User> orgList = (List<User>) lockerService
					.queryAllBySql(orgSql);
			List<User> cusUserList = (List<User>) lockerService
					.queryAllBySql(cusUserSql);
			if (orgList.size() > 0) {
				out.print("{\"result\":\"success\",\"selectData\":"
						+ JSON.toJSONString(orgList, true)
						+ ",\"cusUserData\":"
						+ JSON.toJSONString(cusUserList, true) + "}");
			} else {
				out.print("{\"result\":\"null\"}");
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			out.print("{\"result\":\"error\"}");
		}
	}

	/**
	 * 查询通知数据
	 * 
	 * @author 张加宁
	 */
	public void queryNotifyTable() {
		try {
			ServletActionContext.getResponse().setContentType(
					"text/html; charset=utf-8");
			String title = StringUtil.encodingUrl(ServletActionContext
					.getRequest().getParameter("title"));
			String start_time = StringUtil.encodingUrl(ServletActionContext
					.getRequest().getParameter("start_time"));
			String end_time = StringUtil.encodingUrl(ServletActionContext
					.getRequest().getParameter("end_time"));
			String data_sub = StringUtil.encodingUrl(ServletActionContext
					.getRequest().getParameter("data_sub"));
			PrintWriter out = ServletActionContext.getResponse().getWriter();
			Map<String, String> filterMap = getPagerMap();
			String[] viewArray = { "id", "title", "content", "type", "url",
					"application", "start_time", "end_time", "level", "icon",
					"times", "lastModified",
					"data_sub:[{'0':'审核中','1':'审核通过','2':'审核未通过'}]" };
			if (title != null && !"".equals(title)) {
				filterMap.put("title", title);
			}
			if (start_time != null && !"".equals(start_time)) {
				filterMap.put("start_time", start_time);
			}
			if (end_time != null && !"".equals(end_time)) {
				filterMap.put("end_time", end_time);
			}
			if (data_sub != null && !"".equals(data_sub)) {
				filterMap.put("data_sub", data_sub);
			}
			PageResult result = lockerService.queryNotifyTable(filterMap);
			String returnStr = getColumnJson(result, viewArray);
			out.print(returnStr);
		} catch (IOException e) {
			logger.error(e);
			e.printStackTrace();
		}

	}

	/**
	 * 修改通知数据
	 * 
	 * @throws IOException
	 * @author 张加宁
	 */
	public String editNotify() throws IOException {
		ServletActionContext.getResponse().setContentType(
				"text/html; charset=utf-8");
		String id = ServletActionContext.getRequest().getParameter("id");
		notify = lockerService.getNotifyById(id);
		return "editNotify";
	}

	/**
	 * 保存通知信息
	 * 
	 * @throws IOException
	 * @author 张加宁
	 */
	public void saveNotify() throws IOException {
		ServletActionContext.getResponse().setContentType(
				"text/html; charset=utf-8");
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		try {
			if (0 == notify.getId()) {
				notify.setLastModified(new Timestamp(System.currentTimeMillis()));
				if (image.length > 0 && image[0] != null) {
					notify.setIcon(FileUtil.encodeBase64File(image[0]));
				}
				lockerService.saveOrUpdate(notify);
			} else {
				Notification entity = lockerService.getNotifyById(String
						.valueOf(notify.getId()));
				entity.setId(notify.getId());
				entity.setTitle(notify.getTitle());
				entity.setContent(notify.getContent());
				entity.setType(notify.getType());
				entity.setUrl(notify.getUrl());
				entity.setApplication(notify.getApplication());
				entity.setStart_time(notify.getStart_time());
				entity.setEnd_time(notify.getEnd_time());
				entity.setLevel(notify.getLevel());
				if (image.length > 0 && image[0] != null) {
					notify.setIcon(FileUtil.encodeBase64File(image[0]));
				}
				entity.setTimes(notify.getTimes());
				entity.setLastModified(new Timestamp(System.currentTimeMillis()));
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
	 * 根据ID删除通知
	 * 
	 * @throws IOException
	 * @author 张加宁
	 */
	public void deleteNotifyById() throws IOException {
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		try {
			String ids = ServletActionContext.getRequest().getParameter("ids");
			lockerService.deleteNotifyById(ids);
			out.print("{\"result\":\"success\"}");
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			out.print("{\"result\":\"error\"}");
		}
	}

	/**
	 * 根据ID将通知插入云数据库中
	 * 
	 * @throws IOException
	 */
	public void insertNotify() throws IOException {
		HttpSession session = ServletActionContext.getRequest().getSession();
		String userOrg = (String) session.getAttribute("USER_ORG");
		if ("0".equals(userOrg)) {
			ServletActionContext.getResponse().setContentType(
					"text/html; charset=utf-8");
			PrintWriter out = ServletActionContext.getResponse().getWriter();
			try {
				String ids = ServletActionContext.getRequest().getParameter(
						"ids");
				int bl = lockerService.insertNotify(ids);
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
	 * 保存通知到云数据库
	 * 
	 * @throws IOException
	 */
	public void saveInsertNotify() throws IOException {
		ServletActionContext.getResponse().setContentType(
				"text/html; charset=utf-8");
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		try {
			if (0 == notify.getId()) {
				notify.setLastModified(new Timestamp(System.currentTimeMillis()));
				notify.setData_sub(1);
				lockerService.saveOrUpdate(notify);
				lockerService.insertNotify(notify);
			} else {
				Notification entity = lockerService.getNotifyById(String
						.valueOf(notify.getId()));
				entity.setId(notify.getId());
				entity.setTitle(notify.getTitle());
				entity.setContent(notify.getContent());
				entity.setType(notify.getType());
				entity.setUrl(notify.getUrl());
				entity.setApplication(notify.getApplication());
				entity.setStart_time(notify.getStart_time());
				entity.setEnd_time(notify.getEnd_time());
				entity.setLevel(notify.getLevel());
				entity.setIcon(notify.getIcon());
				entity.setTimes(notify.getTimes());
				entity.setLastModified(new Timestamp(System.currentTimeMillis()));
				entity.setData_sub(1);
				lockerService.saveOrUpdate(entity);
				lockerService.insertNotify(entity);
			}
			out.print("{\"result\":\"success\"}");

		} catch (Exception e) {
			out.print("{\"result\":\"error\"}");
			logger.error(e);
			e.printStackTrace();
		}
	}

	/**
	 * 删除图片过高的新闻数据
	 * 
	 * @author louxiaojian
	 * @date： 日期：2015-3-26 时间：上午10:53:15
	 * @throws IOException
	 */
	public void delDataImgTableByTooHeight() throws IOException {
		ServletActionContext.getResponse().setContentType(
				"text/html; charset=utf-8");
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		try {
			String ids = ServletActionContext.getRequest().getParameter("ids");
			int count = lockerService.delDataImgTableByTooHeight(ids);
			if (count > 0) {
				out.print("{\"result\":\"" + count + "\"}");
			} else {
				out.print("{\"result\":\"0\"}");
			}
		} catch (Exception e) {
			out.print("{\"result\":\"error\"}");
			logger.error(e);
			e.printStackTrace();
		}
	}

	/**
	 * 修改云存储中图片
	 * @author louxiaojian
	 * @date： 日期：2015-3-30 时间：下午3:42:09
	 */
	public void replaceCosImage(){
		String ids=lockerService.getIdsByEntity("data_img_table");
		if(ids!=null&&!"".equals(ids)){
			List list=lockerService.queryAllBySql("select id,url from data_img_table where id in ("+ids+")");
			for (int i = 0; i < list.size(); i++) {
				Object [] obj=(Object [])list.get(i);
	        	String imgName=(String) obj[1];
	        	if(imgName.indexOf("http://cos.myqcloud.com/11000436/data/temp/")!=-1){
		        	imgName=imgName.substring(imgName.lastIndexOf("/")+1);
		    		int accessId = 11000436; // accessId
		    		String accessKey = "7OgnLklEIptHNwZCS0RDNk1rUXrxXJfP"; // accessKey
		    		String bucketId = "data"; // bucket id
		    		String secretId = "AKIDBvY9dcNUS2LeFTxI2ThzgrKxuWuNROIr";
		    		Cos cos = null;
		    		try {
		    			cos = new CosImpl(accessId, accessKey, Common.COS_HOST,
		    					Common.DOWNLOAD_HOST, secretId);
		    		} catch (Exception e) {
		    			e.printStackTrace();
		    		}
		    		// 返回消息体, 包含错误码和错误消息
		    		Message msg = new Message();
		    		
		    		Map<String, Object> inParams = new HashMap<String, Object>();
		    		inParams.put("srcBucketId", bucketId);
		    		inParams.put("srcFilePath", "/temp/" + imgName);
		    		inParams.put("dstBucketId", bucketId);
		    		inParams.put("dstFilePath", "/image/" + imgName);
		    		inParams.put("zoomType", 1);// 等比缩放
		    		inParams.put("width", 400);// 缩放后宽度
		    		inParams.put("height", 400);// 缩放后高度
		    		cos.compress_online_file(inParams, msg);
		    		System.out.println(msg); 
		    		
		    		//删除原有文件
		    		Map<String, Object> deParams = new HashMap<String, Object>();
		    		deParams.put("bucketId", bucketId);
		    		deParams.put("path", "/temp");
		    		List<String> fileList = new Vector<String>();
		    		fileList.clear();
		    		fileList.add(imgName);
		    		cos.deleteFile(deParams, fileList, msg);
		    		System.out.println(msg);
	        	}
	    	}
		}
	}
	
	/**
	 * 新闻置顶
	 * @author louxiaojian
	 * @date： 日期：2015-4-20 时间：下午4:20:23
	 * @throws IOException
	 */
	public void stickByIds() throws IOException{
		ServletActionContext.getResponse().setContentType(
				"text/json; charset=utf-8");
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		try {
			String ids=ServletActionContext.getRequest().getParameter("ids");
			int count=lockerService.stickByIds(ids);
			if(count>0){
				out.print("{\"state\":\"success\"}");
			}else{
				out.print("{\"state\":\"error\"}");
			}
		} catch (Exception e) {
			out.print("{\"state\":\"error\"}");
			logger.error(e);
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}
	
	/**
	 * 取消新闻置顶
	 * @author louxiaojian
	 * @date： 日期：2015-4-20 时间：下午4:20:23
	 * @throws IOException
	 */
	public void cancelStickByIds() throws IOException{
		ServletActionContext.getResponse().setContentType(
				"text/json; charset=utf-8");
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		try {
			String ids=ServletActionContext.getRequest().getParameter("ids");
			int count=lockerService.cancelStickByIds(ids);
			if(count>0){
				out.print("{\"state\":\"success\"}");
			}else{
				out.print("{\"state\":\"error\"}");
			}
		} catch (Exception e) {
			out.print("{\"state\":\"error\"}");
			logger.error(e);
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}
}
