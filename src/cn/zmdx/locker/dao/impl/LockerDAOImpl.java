package cn.zmdx.locker.dao.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.HibernateTemplate;

import cn.zmdx.locker.dao.interfaces.LockerDAO;
import cn.zmdx.locker.entity.Data_img;
import cn.zmdx.locker.entity.Data_img_table;
import cn.zmdx.locker.entity.Data_tag;
import cn.zmdx.locker.entity.Img;
import cn.zmdx.locker.entity.Notification;
import cn.zmdx.locker.entity.PageResult;
import cn.zmdx.locker.entity.Tag;
import cn.zmdx.locker.entity.WallPaper;
import cn.zmdx.locker.util.GenericsUtils;
import cn.zmdx.locker.util.String2list2mapUtil;

public class LockerDAOImpl extends ParentDAOImpl implements LockerDAO {
	public LockerDAOImpl(HibernateTemplate template) {
		super(template);
	}

	public LockerDAOImpl() {
		// TODO Auto-generated constructor stub
	}

	public PageResult searchBySQL(String countsql, String querysql,
			Map filterMap) {
		int page = (Integer) filterMap.get("page");
		int rows = (Integer) filterMap.get("rows");
		int start = rows * (page - 1); // 开始的记录
		// 获取记录总数
		Query query = getSession().createSQLQuery(countsql);
		List<Number> count = query.list();
		int total = Integer.parseInt(count.get(0).toString());

		// 获取当前页的结果集
		query = getSession().createSQLQuery(querysql);
		query.setFirstResult(start);
		query.setMaxResults(rows);
		List<?> datas = query.list();
		PageResult pm = new PageResult();
		pm.setRowCount(total);
		pm.setData(datas);
		return pm;
	}

	@Override
	public PageResult queryDataTable(Map<String, String> filterMap) {
		StringBuffer queryString = new StringBuffer();
		StringBuffer queryCountString = new StringBuffer();
		queryCountString
				.append("select count(*) from (select id,title,url,data_type from data_img_table  where 1=1  ");
		queryString
				.append("select id,title,url,data_type from data_img_table  where 1=1 ");
		if (filterMap != null && !filterMap.isEmpty()) {
			if (null != filterMap.get("title")
					&& !"".equals(filterMap.get("title"))) {
				queryCountString.append("and title like '%"
						+ filterMap.get("title") + "%' ");
				queryString.append("and title like '%" + filterMap.get("title")
						+ "%' ");
			}
			if (null != filterMap.get("data_type")
					&& !"".equals(filterMap.get("data_type"))) {
				queryCountString.append("and data_type = '"
						+ filterMap.get("data_type") + "'  ");
				queryString.append("and data_type = '"
						+ filterMap.get("data_type") + "'  ");
			}
			if (null != filterMap.get("start_date")
					&& !"".equals(filterMap.get("start_date"))) {
				queryCountString.append("and collect_time > '"
						+ filterMap.get("start_date") + "' ");
				queryString.append("and collect_time > '"
						+ filterMap.get("start_date") + "' ");
			}
			if (null != filterMap.get("end_date")
					&& !"".equals(filterMap.get("end_date"))) {
				queryCountString.append("and collect_time < '"
						+ filterMap.get("end_date") + "' ");
				queryString.append("and collect_time < '"
						+ filterMap.get("end_date") + "' ");
			}
			queryCountString.append(")");
		}
		return searchBySQL(queryCountString.toString(), queryString.toString(),
				filterMap);
	}

	@Override
	public PageResult queryDataImgTable(Map<String, String> filterMap) {
		StringBuffer queryString = new StringBuffer();
		StringBuffer queryCountString = new StringBuffer();
		queryCountString
				.append("select count(*) from (select id,title,url,imgUrl,type,collect_time,data_sub,collect_website from data_img_table  where 1=1  ");
		queryString
				.append("select id,title,url,imgUrl,type,collect_time,collect_website,data_sub from data_img_table  where 1=1 ");
		if (filterMap != null && !filterMap.isEmpty()) {
			if (null != filterMap.get("title")
					&& !"".equals(filterMap.get("title"))) {
				queryCountString.append("and title like '%"
						+ filterMap.get("title") + "%' ");
				queryString.append("and title like '%" + filterMap.get("title")
						+ "%' ");
			}
			if (null != filterMap.get("type")
					&& !"".equals(filterMap.get("type"))) {
				queryCountString.append("and type = '" + filterMap.get("type")
						+ "'  ");
				queryString.append("and type = '" + filterMap.get("type")
						+ "'  ");
			}
			if (null != filterMap.get("start_date")
					&& !"".equals(filterMap.get("start_date"))) {
				queryCountString.append("and collect_time > '"
						+ filterMap.get("start_date") + "' ");
				queryString.append("and collect_time > '"
						+ filterMap.get("start_date") + "' ");
			}
			if (null != filterMap.get("end_date")
					&& !"".equals(filterMap.get("end_date"))) {
				queryCountString.append("and collect_time < '"
						+ filterMap.get("end_date") + "' ");
				queryString.append("and collect_time < '"
						+ filterMap.get("end_date") + "' ");
			}
			if (null != filterMap.get("edit_date")
					&& !"".equals(filterMap.get("edit_date"))) {
				queryCountString.append("and collect_time like '%"
						+ filterMap.get("edit_date") + "%' ");
				queryString.append("and collect_time like '%"
						+ filterMap.get("edit_date") + "%' ");
			}
			if (null != filterMap.get("data_sub")
					&& !"".equals(filterMap.get("data_sub"))) {
				queryCountString.append("and data_sub = "
						+ filterMap.get("data_sub") + " ");
				queryString.append("and data_sub = "
						+ filterMap.get("data_sub") + " ");
			}
			if (null != filterMap.get("collect_website")
					&& !"".equals(filterMap.get("collect_website"))) {
				if ("0".equals(filterMap.get("collect_website"))) {
					queryCountString
							.append("and collect_website not in('0') and collect_website in (select user_org from user ) ");
					queryString
							.append("and collect_website not in('0') and collect_website in (select user_org from user ) ");
				} else {
					queryCountString.append("and collect_website = '"
							+ filterMap.get("collect_website") + "' ");
					queryString.append("and collect_website = '"
							+ filterMap.get("collect_website") + "' ");
				}
			}
			if (!"0".equals(filterMap.get("userOrg"))
					&& null != filterMap.get("userOrg")
					&& !"".equals(filterMap.get("userOrg"))) {
				queryCountString.append("and userid = '"
						+ filterMap.get("userid") + "'");
				queryString.append("and userid = '" + filterMap.get("userid")
						+ "'");
			}
		}
		queryCountString.append(") as t");
		queryString.append(" order by id desc");
		return searchBySQL(queryCountString.toString(), queryString.toString(),
				filterMap);
	}

	@Override
	public void deleteDataImgById(String ids) {
		String idss[] = ids.split(",");
		for (String id : idss) {
			Data_img_table transientInstance = getDataImgById(id);
			this.getHibernateTemplate().delete(transientInstance);
		}

	}

	@Override
	public String saveDataImg(Data_img_table dit) {
		String id = this.getHibernateTemplate().save(dit).toString();
		return id;

	}

	@Override
	public void saveOrUpdate(Object entity) {
		this.getHibernateTemplate().saveOrUpdate(entity);

	}

	@Override
	public String save(Object entity) {
		String id = this.getHibernateTemplate().save(entity).toString();
		return id;
	}

	@Override
	public Data_img_table getDataImgById(String id) {
		Data_img_table dit = (Data_img_table) this.getHibernateTemplate().get(
				Data_img_table.class, Integer.parseInt(id));
		return dit;
	}

	public Img getImgById(String id) {
		Img im = (Img) this.getHibernateTemplate().get(Img.class,
				Integer.parseInt(id));
		return im;
	}

	public List<Data_img> getData_ImgById(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("from Data_img where data_id =" + id);
		List<Data_img> diList = getHibernateTemplate().find(sql.toString());
		return diList;
	}

	public List<Data_tag> getData_TagById(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("from Data_tag where data_id =" + id);
		List<Data_tag> dgList = getHibernateTemplate().find(sql.toString());
		return dgList;
	}

	@Override
	public void updateDataImg(Data_img_table dit) {
		this.getHibernateTemplate().update(dit);
	}

	@Override
	public int insertDataImg(String ids) {
		GenericsUtils genericsUtils = new GenericsUtils();
		PreparedStatement pstmt = null;
		Connection dbConn = null;
		try {
			Properties p = genericsUtils.loadProperty("c3p0.properties");
			String url = p.getProperty("c3p0.cloudTestUrl");
			String driverName = p.getProperty("c3p0.cloudTestDriverClassName");
			String userName = p.getProperty("c3p0.cloudTestUsername");
			String password = p.getProperty("c3p0.cloudTestPassword");
			Class.forName(driverName).newInstance();
			dbConn = DriverManager.getConnection(url, userName, password);
			dbConn.setAutoCommit(false);
			String idss[] = ids.split(",");
			Data_img_table dit;
			Img im;
			StringBuffer sql = new StringBuffer();
			StringBuffer data_img_sql = new StringBuffer();
			StringBuffer img_sql = new StringBuffer();
			StringBuffer data_tag_sql = new StringBuffer();
			sql.append("insert into data_img_table(id,title,url,imgUrl,data_type,collect_time,collect_website,type) values");
			data_img_sql
					.append("insert into data_img(id,img_id,data_id) values");
			img_sql.append("insert into img(id,imageUrl,content) values");
			data_tag_sql
					.append("insert into data_tag(id,data_id,tag_id) values");
			for (String id : idss) {
				dit = getDataImgById(id);
				dit.setData_sub(1);
				this.getHibernateTemplate().update(dit);
				List<Data_img> diList = getData_ImgById(id);
				if (diList.size() > 1) {
					dit.setImgUrl("http://pandora.hdlocker.com:8080/essay/locker!viewDataImg.action?id="
							+ Integer.parseInt(id) + "");
				}
				sql.append("('" + dit.getId() + "','" + dit.getTitle() + "','"
						+ dit.getUrl() + "','"
						+ dit.getImgUrl().replace("'", "''") + "','"
						+ dit.getData_type() + "','" + dit.getCollect_time()
						+ "','" + dit.getCollect_website() + "','"
						+ dit.getType() + "'),");
				List<Data_tag> dgList = getData_TagById(id);
				if (diList.size() > 0) {
					for (Data_img di : diList) {
						data_img_sql.append("('" + di.getId() + "','"
								+ di.getImg_id() + "','" + di.getData_id()
								+ "'),");
						im = getImgById(String.valueOf(di.getImg_id()));
						img_sql.append("('" + im.getId() + "','"
								+ im.getImageUrl() + "','"
								+ im.getContent().replace("'", "''") + "'),");
					}
				}
				if (dgList.size() > 0) {
					for (Data_tag dg : dgList) {
						data_tag_sql.append("('" + dg.getId() + "','"
								+ dg.getData_id() + "','" + dg.getTag_id()
								+ "'),");
					}

				}
			}
			pstmt = (PreparedStatement) dbConn.prepareStatement(String
					.valueOf(img_sql.substring(0, img_sql.length() - 1)));
			pstmt.executeUpdate();
			pstmt = (PreparedStatement) dbConn.prepareStatement(String
					.valueOf(data_img_sql.substring(0,
							data_img_sql.length() - 1)));
			pstmt.executeUpdate();

			pstmt = (PreparedStatement) dbConn.prepareStatement(String
					.valueOf(data_tag_sql.substring(0,
							data_tag_sql.length() - 1)));
			pstmt.executeUpdate();

			pstmt = (PreparedStatement) dbConn.prepareStatement(String
					.valueOf(sql.substring(0, sql.length() - 1)));
			int count = pstmt.executeUpdate();
			dbConn.commit();
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				dbConn.rollback();
				System.out.println("JDBC Transaction rolled back successfully");
			} catch (SQLException e1) {
				System.out.println("SQLException in rollback" + e.getMessage());
			}
		} finally {
			try {
				if (dbConn != null)
					pstmt.close();
				dbConn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	public int insertDataImg(Data_img_table dit) {
		GenericsUtils genericsUtils = new GenericsUtils();
		PreparedStatement pstmt = null;
		Connection dbConn = null;
		try {
			Properties p = genericsUtils.loadProperty("c3p0.properties");
			String url = p.getProperty("c3p0.cloudTestUrl");
			String driverName = p.getProperty("c3p0.cloudTestDriverClassName");
			String userName = p.getProperty("c3p0.cloudTestUsername");
			String password = p.getProperty("c3p0.cloudTestPassword");
			Class.forName(driverName).newInstance();
			dbConn = DriverManager.getConnection(url, userName, password);
			dbConn.setAutoCommit(false);
			Img im;
			StringBuffer sql = new StringBuffer();
			StringBuffer data_img_sql = new StringBuffer();
			StringBuffer img_sql = new StringBuffer();
			StringBuffer data_tag_sql = new StringBuffer();
			sql.append("insert into data_img_table(id,title,url,imgUrl,data_type,collect_time,collect_website,type) values");
			data_img_sql
					.append("insert into data_img(id,img_id,data_id) values");
			img_sql.append("insert into img(id,imageUrl,content) values");
			data_tag_sql
					.append("insert into data_tag(id,data_id,tag_id) values");
			List<Data_img> diList = getData_ImgById(String.valueOf(dit.getId()));
			List<Data_tag> dgList = getData_TagById(String.valueOf(dit.getId()));
			if (diList.size() > 1) {
				sql.append("('"
						+ dit.getId()
						+ "','"
						+ dit.getTitle()
						+ "','"
						+ dit.getUrl()
						+ "','http://pandora.hdlocker.com:8080/essay/locker!viewDataImg.action?id="
						+ dit.getId() + "','" + dit.getData_type() + "','"
						+ dit.getCollect_time() + "','"
						+ dit.getCollect_website() + "','" + dit.getType()
						+ "'),");
			} else {
				sql.append("('" + dit.getId() + "','" + dit.getTitle() + "','"
						+ dit.getUrl() + "','"
						+ dit.getImgUrl().replace("'", "''") + "','"
						+ dit.getData_type() + "','" + dit.getCollect_time()
						+ "','" + dit.getCollect_website() + "','"
						+ dit.getType() + "'),");
			}
			pstmt = (PreparedStatement) dbConn.prepareStatement(String
					.valueOf(sql.substring(0, sql.length() - 1)));
			int count = pstmt.executeUpdate();
			if (diList.size() > 0) {
				for (Data_img di : diList) {
					data_img_sql.append("('" + di.getId() + "','"
							+ di.getImg_id() + "','" + di.getData_id() + "'),");
					im = getImgById(String.valueOf(di.getImg_id()));
					img_sql.append("('" + im.getId() + "','" + im.getImageUrl()
							+ "','" + im.getContent().replace("'", "''")
							+ "'),");
				}
				PreparedStatement img_pstmt = (PreparedStatement) dbConn
						.prepareStatement(String.valueOf(img_sql.substring(0,
								img_sql.length() - 1)));
				img_pstmt.executeUpdate();
				PreparedStatement data_img_pstmt = (PreparedStatement) dbConn
						.prepareStatement(String.valueOf(data_img_sql
								.substring(0, data_img_sql.length() - 1)));
				data_img_pstmt.executeUpdate();
			}
			if (dgList.size() > 0) {
				for (Data_tag dg : dgList) {
					data_tag_sql.append("('" + dg.getId() + "','"
							+ dg.getData_id() + "','" + dg.getTag_id() + "'),");
				}
				PreparedStatement data_tag_pstmt = (PreparedStatement) dbConn
						.prepareStatement(String.valueOf(data_tag_sql
								.substring(0, data_tag_sql.length() - 1)));
				data_tag_pstmt.executeUpdate();
			}
			dbConn.commit();
			return count;

		} catch (Exception e) {
			e.printStackTrace();
			try {
				dbConn.rollback();
				System.out.println("JDBC Transaction rolled back successfully");
			} catch (SQLException e1) {
				System.out.println("SQLException in rollback" + e.getMessage());
			}
		} finally {

			try {
				if (dbConn != null)
					pstmt.close();
				dbConn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	@Override
	public int saveParams(Map<String, String> filterMap) {
		StringBuffer sql = new StringBuffer();
		sql.append("update data_img_table t set t.collect_time = '"
				+ filterMap.get("edit_date") + "' where t.id in ( :idList ) ");
		Query query = getSession().createSQLQuery(String.valueOf(sql));
		query.setParameterList("idList",
				String2list2mapUtil.StringToList(filterMap.get("ids"))
						.toArray());
		int count = query.executeUpdate();
		return count;
	}

	@Override
	public PageResult queryTagTable(Map<String, String> filterMap) {
		StringBuffer queryString = new StringBuffer();
		StringBuffer queryCountString = new StringBuffer();
		queryCountString
				.append("select count(*) from (select id,tag_name from tag  where 1=1  ");
		queryString.append("select id,tag_name from tag  where 1=1 ");
		if (filterMap != null && !filterMap.isEmpty()) {
			if (null != filterMap.get("tag_name")
					&& !"".equals(filterMap.get("tag_name"))) {
				queryCountString.append("and tag_name like '%"
						+ filterMap.get("tag_name") + "%' ");
				queryString.append("and tag_name like '%"
						+ filterMap.get("tag_name") + "%' ");
			}
			queryCountString.append(") as t");
			queryString.append(" order by id desc");
		}
		return searchBySQL(queryCountString.toString(), queryString.toString(),
				filterMap);
	}

	@Override
	public String saveTag(Tag tag) {
		String id = this.getHibernateTemplate().save(tag).toString();
		return id;
	}

	@Override
	public void updateTag(Tag tag) {
		this.getHibernateTemplate().update(tag);
	}

	@Override
	public Tag getTagById(String id) {
		Tag tag = (Tag) this.getHibernateTemplate().get(Tag.class,
				Integer.parseInt(id));
		return tag;
	}

	@Override
	public void deleteTagById(String ids) {
		String idss[] = ids.split(",");
		for (String id : idss) {
			Tag tag = getTagById(id);
			this.getHibernateTemplate().delete(tag);
		}
	}

	@Override
	public void deleteNotifyById(String ids) {
		String idss[] = ids.split(",");
		for (String id : idss) {
			Notification notify = getNotifyById(id);
			this.getHibernateTemplate().delete(notify);
		}
	}

	@Override
	public List<?> queryAllBySql(String sql) {
		Query query = getSession().createSQLQuery(sql);
		return query.list();
	}

	@Override
	public int executeBySQL(String sql) {
		Query query = getSession().createSQLQuery(sql);
		int count = query.executeUpdate();
		return count;
	}

	@Override
	public void deleteTagByDataId(int id) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from data_tag where data_id = " + id + "");
		Query query = getSession().createSQLQuery(sql.toString());
		query.executeUpdate();
	}

	@Override
	public PageResult queryWallPaper(Map<String, String> filterMap) {
		StringBuffer queryString = new StringBuffer();
		StringBuffer queryCountString = new StringBuffer();
		queryCountString
				.append("select count(*) from (select ID,p_name,p_desc,p_author,thumbURL,imageURL,imageNAME,imageEXT,publishDATE,data_sub from wallpaper as t where 1=1  ");
		queryString
				.append("select ID,p_name,p_desc,p_author,thumbURL,imageURL,imageNAME,imageEXT,publishDATE,data_sub from wallpaper as t where 1=1");
		if (filterMap != null && !filterMap.isEmpty()) {
			if (null != filterMap.get("p_name")
					&& !"".equals(filterMap.get("p_name"))) {
				queryCountString.append(" and p_name like '%"
						+ filterMap.get("p_name") + "%'");
				queryString.append(" and p_name like'%"
						+ filterMap.get("p_name") + "%'");
			}
			if (null != filterMap.get("start_date")
					&& !"".equals(filterMap.get("start_date"))) {
				queryCountString.append("and publishDATE > '"
						+ filterMap.get("start_date") + "' ");
				queryString.append("and publishDATE > '"
						+ filterMap.get("start_date") + "' ");
			}
			if (null != filterMap.get("end_date")
					&& !"".equals(filterMap.get("end_date"))) {
				queryCountString.append("and publishDATE < '"
						+ filterMap.get("end_date") + "' ");
				queryString.append("and publishDATE < '"
						+ filterMap.get("end_date") + "' ");
			}
			if (null != filterMap.get("imageEXT")
					&& !"".equals(filterMap.get("imageEXT"))) {
				queryCountString.append(" and imageEXT='"
						+ filterMap.get("imageEXT") + "'");
				queryString.append(" and imageEXT='"
						+ filterMap.get("imageEXT") + "'");
			}
			if (null != filterMap.get("data_sub")
					&& !"".equals(filterMap.get("data_sub"))) {
				queryCountString.append(" and data_sub='"
						+ filterMap.get("data_sub") + "'");
				queryString.append(" and data_sub='"
						+ filterMap.get("data_sub") + "'");
			}
			queryCountString.append(") as a");
			queryString.append(" order by id desc");
		}
		return searchBySQL(queryCountString.toString(), queryString.toString(),
				filterMap);
	}

	@Override
	public Object getObjectById(Class clazz, int id) {
		return this.getHibernateTemplate().get(clazz, id);
	}

	@Override
	public int insertWallPaper(WallPaper wallPaper) {
		GenericsUtils genericsUtils = new GenericsUtils();
		PreparedStatement pstmt = null;
		Connection dbConn = null;
		StringBuffer sql = new StringBuffer();
		sql.append("insert into wallpaper (p_name,p_desc,p_author,thumbURL,imageURL,imageNAME,imageEXT,publishDATE,data_sub) VALUES ");
		sql.append("('" + wallPaper.getP_name() + "','" + wallPaper.getP_desc()
				+ "','" + wallPaper.getP_author() + "','"
				+ wallPaper.getThumbURL() + "','" + wallPaper.getImageURL()
				+ "','" + wallPaper.getImageNAME() + "','"
				+ wallPaper.getImageEXT() + "','" + wallPaper.getPublishDATE()
				+ "'," + wallPaper.getData_sub() + ")");
		try {
			Properties p = genericsUtils.loadProperty("c3p0.properties");
			String url = p.getProperty("c3p0.cloudTestUrl");
			String driverName = p.getProperty("c3p0.cloudTestDriverClassName");
			String userName = p.getProperty("c3p0.cloudTestUsername");
			String password = p.getProperty("c3p0.cloudTestPassword");
			Class.forName(driverName).newInstance();
			dbConn = DriverManager.getConnection(url, userName, password);
			pstmt = (PreparedStatement) dbConn.prepareStatement(String
					.valueOf(sql));
			return pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {

			try {
				if (dbConn != null)
					pstmt.close();
				dbConn.close();
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}
	}

	@Override
	public void delete(Object obj) {
		this.getHibernateTemplate().delete(obj);
	}

	@Override
	public int insertWallPaper(String ids) {
		GenericsUtils genericsUtils = new GenericsUtils();
		PreparedStatement pstmt = null;
		Connection dbConn = null;
		StringBuffer sql = new StringBuffer();
		sql.append("insert into wallpaper (p_name,p_desc,p_author,thumbURL,imageURL,imageNAME,imageEXT,publishDATE,data_sub) VALUES ");
		String[] idss = ids.split(",");
		for (String id : idss) {
			WallPaper wallPaper = (WallPaper) getObjectById(WallPaper.class,
					Integer.parseInt(id));
			wallPaper.setData_sub(1);
			saveOrUpdate(wallPaper);// 将壁纸修改为已发布
			sql.append("('" + wallPaper.getP_name() + "','"
					+ wallPaper.getP_desc() + "','" + wallPaper.getP_author()
					+ "','" + wallPaper.getThumbURL() + "','"
					+ wallPaper.getImageURL() + "','"
					+ wallPaper.getImageNAME() + "','"
					+ wallPaper.getImageEXT() + "','"
					+ wallPaper.getPublishDATE() + "',"
					+ wallPaper.getData_sub() + "),");
		}
		try {
			Properties p = genericsUtils.loadProperty("c3p0.properties");
			String url = p.getProperty("c3p0.cloudTestUrl");
			String driverName = p.getProperty("c3p0.cloudTestDriverClassName");
			String userName = p.getProperty("c3p0.cloudTestUsername");
			String password = p.getProperty("c3p0.cloudTestPassword");
			Class.forName(driverName).newInstance();
			dbConn = DriverManager.getConnection(url, userName, password);
			pstmt = (PreparedStatement) dbConn.prepareStatement(String
					.valueOf(sql.substring(0, sql.length() - 1)));
			return pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {

			try {
				if (dbConn != null)
					pstmt.close();
				dbConn.close();
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}
	}

	@Override
	public PageResult queryNotifyTable(Map<String, String> filterMap) {
		StringBuffer queryString = new StringBuffer();
		StringBuffer queryCountString = new StringBuffer();
		queryCountString
				.append("select count(*) from (select id from notification  where 1=1  ");
		queryString
				.append("select id,title,content,type,url,application,start_time,end_time,level,icon,times,lastModified,data_sub from notification  where 1=1 ");
		if (filterMap != null && !filterMap.isEmpty()) {
			if (null != filterMap.get("title")
					&& !"".equals(filterMap.get("title"))) {
				queryCountString.append("and title like '%"
						+ filterMap.get("title") + "%' ");
				queryString.append("and title like '%" + filterMap.get("title")
						+ "%' ");
			}
			if (null != filterMap.get("start_time")
					&& !"".equals(filterMap.get("start_time"))) {
				queryCountString.append("and start_time > '"
						+ filterMap.get("start_time") + "' ");
				queryString.append("and start_time > '" + filterMap.get("start_time")
						+ "' ");
			}
			if (null != filterMap.get("end_time")
					&& !"".equals(filterMap.get("end_time"))) {
				queryCountString.append("and end_time < '"
						+ filterMap.get("end_time") + "' ");
				queryString.append("and end_time < '" + filterMap.get("end_time")
						+ "' ");
			}
			if (null != filterMap.get("data_sub")
					&& !"".equals(filterMap.get("data_sub"))) {
				queryCountString.append("and data_sub = '"
						+ filterMap.get("data_sub") + "' ");
				queryString.append("and data_sub = '" + filterMap.get("data_sub")
						+ "' ");
			}
			queryCountString.append(") as t");
			queryString.append(" order by id desc");
		}
		return searchBySQL(queryCountString.toString(), queryString.toString(),
				filterMap);
	}

	@Override
	public Notification getNotifyById(String id) {
		Notification notify = (Notification) this.getHibernateTemplate().get(Notification.class, Integer.parseInt(id));
		return notify;
	}

	@Override
	public int insertNotify(String ids) {
		GenericsUtils genericsUtils = new GenericsUtils();
		PreparedStatement pstmt = null;
		Connection dbConn = null;
		StringBuffer sql = new StringBuffer();
		sql.append("insert into notification (title,content,type,url,application,start_time,end_time,level,icon,times,lastModified,data_sub) VALUES ");
		String[] idss = ids.split(",");
		for (String id : idss) {
			Notification notify = getNotifyById(id);
			notify.setData_sub(1);
			saveOrUpdate(notify);// 将壁纸修改为已发布
			sql.append("('" + notify.getTitle() + "','" + notify.getContent()
					+ "','" + notify.getType() + "','" + notify.getUrl()
					+ "','" + notify.getApplication() + "','"
					+ notify.getStart_time() + "','" + notify.getEnd_time()
					+ "','" + notify.getLevel() + "','" + notify.getIcon()
					+ "','" + notify.getTimes() + "','"
					+ notify.getLastModified() + "',1),");
		}
		try {
			Properties p = genericsUtils.loadProperty("c3p0.properties");
			String url = p.getProperty("c3p0.cloudTestUrl");
			String driverName = p.getProperty("c3p0.cloudTestDriverClassName");
			String userName = p.getProperty("c3p0.cloudTestUsername");
			String password = p.getProperty("c3p0.cloudTestPassword");
			Class.forName(driverName).newInstance();
			dbConn = DriverManager.getConnection(url, userName, password);
			pstmt = (PreparedStatement) dbConn.prepareStatement(String
					.valueOf(sql.substring(0, sql.length() - 1)));
			return pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {

			try {
				if (dbConn != null)
					pstmt.close();
				dbConn.close();
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}
	}

	@Override
	public int insertNotify(Notification notify) {
		GenericsUtils genericsUtils = new GenericsUtils();
		PreparedStatement pstmt = null;
		Connection dbConn = null;
		StringBuffer sql = new StringBuffer();
		sql.append("insert into notification (title,content,type,url,application,start_time,end_time,level,icon,times,lastModified,data_sub) VALUES ");
		sql.append("('" + notify.getTitle() + "','" + notify.getContent()
				+ "','" + notify.getType() + "','" + notify.getUrl() + "','"
				+ notify.getApplication() + "','" + notify.getStart_time()
				+ "','" + notify.getEnd_time() + "','" + notify.getLevel()
				+ "','" + notify.getIcon() + "','" + notify.getTimes() + "','"
				+ notify.getLastModified() + "',1)");
		try {
			Properties p = genericsUtils.loadProperty("c3p0.properties");
			String url = p.getProperty("c3p0.cloudTestUrl");
			String driverName = p.getProperty("c3p0.cloudTestDriverClassName");
			String userName = p.getProperty("c3p0.cloudTestUsername");
			String password = p.getProperty("c3p0.cloudTestPassword");
			Class.forName(driverName).newInstance();
			dbConn = DriverManager.getConnection(url, userName, password);
			pstmt = (PreparedStatement) dbConn.prepareStatement(String
					.valueOf(sql));
			return pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {

			try {
				if (dbConn != null)
					pstmt.close();
				dbConn.close();
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}
	}
}
