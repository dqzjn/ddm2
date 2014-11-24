package cn.zmdx.locker.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;
import com.tencent.cos.Cos;
import com.tencent.cos.CosImpl;
import com.tencent.cos.bean.CosFile;
import com.tencent.cos.bean.Message;
import com.tencent.cos.constant.Common;

public class UploadFile extends ActionSupport {
	private File image; // 上传的文件
	private String imageFileName; // 文件名称
	private String imageContentType; // 文件类型

	public String execute() throws Exception {
		// 用户定义变量
		int accessId = 11000436; // accessId
		String accessKey = "7OgnLklEIptHNwZCS0RDNk1rUXrxXJfP"; // accessKey
		String bucketId = "bucket_1"; // bucket id
		String localFilePath = "/Users/admin/Desktop/api.png"; // 本地文件路径, 用于文件上传
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
		System.out.println("----------------------uploadFileContent----------------------\n");
		Map<String, Object> inParams = new HashMap<String, Object>();
		inParams.put("bucketId", bucketId);
		inParams.put("path", "/image");
		inParams.put("cosfile", "file021");
		byte[] fStream = new byte[new FileInputStream(getImage()).available()];
		CosFile file = new CosFile();
		cos.uploadFileContent(inParams, fStream, file, msg);
		System.out.println(file);
		System.out.println(msg);
		return "success";
	}

	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public String getImageContentType() {
		return imageContentType;
	}

	public void setImageContentType(String imageContentType) {
		this.imageContentType = imageContentType;
	}

}
