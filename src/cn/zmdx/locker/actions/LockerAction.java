package cn.zmdx.locker.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import cn.zmdx.locker.entity.Data_img_table;
import cn.zmdx.locker.entity.Data_table;
import cn.zmdx.locker.entity.PageResult;
import cn.zmdx.locker.service.impl.LockerServiceImpl;
import cn.zmdx.locker.util.StringUtil;
import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class LockerAction extends ActionSupport {
	Logger logger = Logger.getLogger(LockerAction.class);
	private Data_table dataTable;
	private LockerServiceImpl lockerService;
	private Data_img_table dataImgTable;
	private String result;
	private String page;
	private String rows;
	private String sidx;
	private String sord;

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
					"text/json; charset=utf-8");
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
					"text/json; charset=utf-8");
			String title = StringUtil.encodingUrl(ServletActionContext
					.getRequest().getParameter("data_title"));
			String start_date = StringUtil.encodingUrl(ServletActionContext
					.getRequest().getParameter("start_date"));
			String end_date = StringUtil.encodingUrl(ServletActionContext
					.getRequest().getParameter("end_date"));
			String data_type = StringUtil.encodingUrl(ServletActionContext
					.getRequest().getParameter("data_type"));
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
					"data_type:[{'joke':'搞笑','news':'新闻','film':'电影','game':'游戏','gif':'动态图','html':'HTML'}]",
					"collect_time", "data_sub" };
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
				"text/json; charset=utf-8");
		HttpSession session = ServletActionContext.getRequest().getSession();
		String userOrg = (String) session.getAttribute("USER_ORG");
		Data_img_table dataImgTable = new Data_img_table();
		if (!"0".equals(userOrg)) {
			dataImgTable.setCollect_website(userOrg);
			session.setAttribute("dataImgTable", dataImgTable);
		} else {
			session.setAttribute("userOrg", userOrg);
		}
		return "editDataImg";
	}

	/**
	 * 修改数据
	 * 
	 * @throws IOException
	 * @author 张加宁
	 */
	public String editDataImg() throws IOException {
		ServletActionContext.getResponse().setContentType(
				"text/json; charset=utf-8");
		String id = ServletActionContext.getRequest().getParameter("id");
		dataImgTable = lockerService.getDataImgById(id);
		HttpSession session = ServletActionContext.getRequest().getSession();
		String userOrg = (String) session.getAttribute("USER_ORG");
		session.setAttribute("userOrg", userOrg);
		return "editDataImg";
	}

	/**
	 * 保存数据
	 * @throws IOException
	 * @author 张加宁
	 */
	public void saveDataImg() throws IOException {
		ServletActionContext.getResponse().setContentType(
				"text/json; charset=utf-8");
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		try {
			if (0 == dataImgTable.getId()) {
				dataImgTable.setData_sub(0);
				String u = lockerService.saveDataImg(dataImgTable);
			} else {
				Data_img_table entity = lockerService.getDataImgById(String
						.valueOf(dataImgTable.getId()));
				entity.setId(dataImgTable.getId());
				entity.setTitle(dataImgTable.getTitle());
				entity.setUrl(dataImgTable.getUrl());
				entity.setImgUrl(dataImgTable.getImgUrl());
				entity.setCollect_website(dataImgTable.getCollect_website());
				entity.setData_type(dataImgTable.getData_type());
				entity.setCollect_time(dataImgTable.getCollect_time());
				entity.setData_sub(0);
				lockerService.updateDataImg(entity);
			}
			out.print("{\"result\":\"success\"}");

		} catch (Exception e) {
			out.print("{\"result\":\"error\"}");
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
					"text/json; charset=utf-8");
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
					"text/json; charset=utf-8");
			PrintWriter out = ServletActionContext.getResponse().getWriter();
			try {
				if (0 == dataImgTable.getId()) {
					dataImgTable.setData_sub(1);
					String u = lockerService.saveDataImg(dataImgTable);
				} else {
					Data_img_table entity = lockerService.getDataImgById(String
							.valueOf(dataImgTable.getId()));
					entity.setId(dataImgTable.getId());
					entity.setTitle(dataImgTable.getTitle());
					entity.setUrl(dataImgTable.getUrl());
					entity.setImgUrl(dataImgTable.getImgUrl());
					entity.setCollect_website(dataImgTable.getCollect_website());
					entity.setData_type(dataImgTable.getData_type());
					entity.setCollect_time(dataImgTable.getCollect_time());
					entity.setData_sub(1);
					lockerService.updateDataImg(entity);
				}
				int bl = lockerService.insertDataImg(dataImgTable);
				if (bl > 0)
					out.print("{\"result\":\"success\"}");
				else
					out.print("{\"result\":\"error\"}");
			} catch (Exception e) {
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
				"text/json; charset=utf-8");
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
			e.printStackTrace();
			out.print("{\"result\":\"error\"}");
		}
	}
}
