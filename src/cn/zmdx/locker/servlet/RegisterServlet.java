package cn.zmdx.locker.servlet;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.zmdx.locker.entity.User;
import cn.zmdx.locker.service.impl.UserServiceImpl;
import cn.zmdx.locker.servlet.base.BaseServlet;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterServlet extends BaseServlet{

	public void service(HttpServletRequest request , 
			HttpServletResponse response)
			throws IOException , ServletException
		{  
		String worker_phone="";
		String worker_name="";
		String idcard="";
		String area_no="";
		String password="";
		/*worker_phone=request.getParameter("phoneno");
		worker_name=request.getParameter("name");
		idcard=request.getParameter("idcardno");
		area_no=request.getParameter("areano");
		 password=request.getParameter("pass");*/
		
		String json=IOUtils.toString(request.getInputStream());
		json=URLDecoder.decode(json,"UTF-8");
		try {
			JSONObject reg=new  JSONObject(json);
			 worker_phone=reg.getString("phoneno");
			 worker_name=reg.getString("name");
			 idcard=reg.getString("idcardno");
			 area_no=reg.getString("dareaid");
			 password=reg.getString("pwd");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(json);
		System.out.println(worker_phone);
		System.out.println(worker_name);
		System.out.println(idcard);
		System.out.println(area_no);
		System.out.println(password);
		// ��ȡϵͳ��ҵ���߼����
		UserServiceImpl userService = (UserServiceImpl)getCtx().getBean("userService");
		// ��֤�û���¼
		User info=new User();
//		info.setWorker_phone(worker_phone);
//		info.setWorker_name(worker_name);
//		info.setIdentitycard_no(idcard);
//		info.setArea_no(area_no);
		info.setPassword(password);
		long userId=0;
		try {
			userId=userService.addUser(info);
//		System.out.println(info.getWorker_phone());
//		System.out.println(info.getWorker_name());
//		System.out.println(info.getIdentitycard_no());
//		System.out.println(info.getArea_no());
		System.out.println(userId);
		response.setContentType("text/html; charset=GBK");
		// ��¼�ɹ�
		if (userId>0)
		{
			request.getSession(true).setAttribute("userId" , userId);
		}
		try
		{
			// ����֤��userId��װ��JSONObject
			JSONObject jsonObj = new JSONObject().put("userId" , userId);
			// �����Ӧ
			response.getWriter().println(jsonObj.toString()); 
		}
		catch (JSONException ex)
		{
			ex.printStackTrace();
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		}
