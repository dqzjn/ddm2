package cn.zmdx.locker.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import cn.zmdx.locker.entity.User;
import cn.zmdx.locker.service.impl.UserServiceImpl;
import cn.zmdx.locker.util.Encrypter;
import cn.zmdx.locker.util.StringUtil;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class LoginAction extends ActionSupport implements ModelDriven<User> {
	private User user = new User();
	private UserServiceImpl userService;
	private String j_username;
	private String j_password;
	private String psw;

	@Override
	public User getModel() {
		return user;
	}

	public void setUserService(UserServiceImpl userService) {
		this.userService = userService;
	}

	/**
	 * 登陆
	 * * @author 张加宁
	 */
	public String execute() throws Exception {
		HttpSession session = ServletActionContext.getRequest().getSession();
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		try {
			String ids = ServletActionContext.getRequest().getParameter("ids");
			User user = userService.verifyUser(j_username, j_password);
			if (null != user) {
				session.setAttribute("USER", user);
				session.setAttribute("USER_ID", user.getUserid());
				session.setAttribute("username", user.getUsername());
				session.setAttribute("loginname", user.getLoginname());
				session.setAttribute("USER_ORG", user.getUser_org());
				session.setAttribute("USER_ROLE", user.getUser_role());
				session.setAttribute("loginTime", new Date());
				out.print("{\"ajaxResult\":\"success\"}");
				return SUCCESS;
			}
			return "false";
		} catch (Exception e) {
			e.printStackTrace();
			out.print("{\"ajaxResult\":\"error\"}");
			return "false";
		}

	}

	/**
	 * 
	 * 
	 * @throws Exception
	 * @author 张加宁
	 */
	@SuppressWarnings("unused")
	public void updatePwd() throws Exception {
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		try {
			String userid = ServletActionContext.getRequest().getSession()
					.getAttribute("USER_ID").toString();
			User user = userService.findUsersById(Integer.parseInt(userid));
			user.setPassword(Encrypter.md5(psw));
			if (null != user.getPassword()
					&& !"null".equals(user.getPassword())) {
				userService.updateUserInfo(user);
				out.print("{\"result\":\"success\"}");
			} else {
				out.print("{\"result\":\"false\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.print("{\"result\":\"false\"}");
		}
	}

	/**
	 * 验证旧密码是否输入正确
	 * 
	 * @throws IOException
	 *             void
	 * @author 张加宁
	 */
	public void findPwd() throws IOException {
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		try {
			String oldPwd = ServletActionContext.getRequest().getParameter(
					"oldPwd");
			String userid = ServletActionContext.getRequest().getSession()
					.getAttribute("USER_ID").toString();
			User user = userService.findUsersById(Integer.parseInt(userid));
			String Pwd = user.getPassword();
			if (Pwd.equals(Encrypter.md5(oldPwd))) {
				out.print("{\"result\":\"success\"}");
			} else {
				out.print("{\"result\":\"false\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.print("{\"result\":\"false\"}");
		}
	}

	/**
	 * 退出系统
	 * 
	 * @return
	 * @author 张加宁
	 */
	public String userLoginOut() {
		try {
			ServletActionContext.getRequest().getSession()
					.removeAttribute("USER_ID");
			ServletActionContext.getRequest().getSession().invalidate();
			String path = ServletActionContext.getRequest().getContextPath();
			ServletActionContext
					.getResponse()
					.getWriter()
					.write("<script>window.parent.parent.location.href='"
							+ path + "/login.jsp';</script>");
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return null;
	}

	public String getJ_username() {
		return j_username;
	}

	public void setJ_username(String j_username) {
		this.j_username = j_username;
	}

	public String getJ_password() {
		return j_password;
	}

	public void setJ_password(String j_password) {
		this.j_password = j_password;
	}

	public String getPsw() {
		return psw;
	}

	public void setPsw(String psw) {
		this.psw = psw;
	}

}
