package cn.zmdx.locker.servlet;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import cn.zmdx.locker.entity.User;
import cn.zmdx.locker.service.impl.UserServiceImpl;
import cn.zmdx.locker.service.interfaces.UserService;
import cn.zmdx.locker.servlet.base.BaseServlet;
import cn.zmdx.locker.util.Encrypter;


public class LoginServlet extends BaseServlet {
	public void service(HttpServletRequest request , 
			HttpServletResponse response)
			throws IOException , ServletException
		{  
		int worker_no = 0;
		String password="";
		/*worker_no=Integer.parseInt(request.getParameter("user"));
		password=request.getParameter("pass");*/
		String json=IOUtils.toString(request.getInputStream());
		json=URLDecoder.decode(json,"UTF-8");
		try {
			JSONObject reg=new  JSONObject(json);
			worker_no=reg.getInt("user");
			 password=reg.getString("pass");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(json);
		System.out.println(worker_no);
		System.out.println(password);
		// ��ȡϵͳ��ҵ���߼����
		UserServiceImpl userService = (UserServiceImpl)getCtx().getBean("userService");
		// ��֤�û���¼
		User info=new User();
//		info.setWorker_no(worker_no);
		info.setPassword(password);
		int userId=0;
		try {
			if( userService.verifyUser(info))
				userId=1;
		System.out.println(	userService.verifyUser(info));
//		System.out.println(info.getWorker_no());
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

