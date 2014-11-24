package cn.zmdx.locker.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import com.tencent.cos.Cos;
import com.tencent.cos.CosImpl;
import com.tencent.cos.bean.Bucket;
import com.tencent.cos.bean.CosDirectory;
import com.tencent.cos.bean.CosFile;
import com.tencent.cos.bean.CosObject;
import com.tencent.cos.bean.ListFileNum;
import com.tencent.cos.bean.Message;
import com.tencent.cos.constant.Common;
import com.tencent.cos.constant.ErrCode;

public class UploadCOS {
	public static void createBucket(Cos cos, String bucketId, Message msg) {
		Map<String, Object> inParams = new HashMap<String, Object>();
		System.out
				.println("----------------------createBucket----------------------\n");
		inParams.put("bucketId", bucketId);
		cos.createBucket(inParams, msg);
		System.out.println(msg);
	}

	public static void listBucket(Cos cos, Message msg) {
		System.out
				.println("----------------------listBucket----------------------\n");
		Map<String, Object> inParams = new HashMap<String, Object>();
		inParams.put("offset", 0);
		inParams.put("count", 10);
		List<Bucket> buckets = new Vector<Bucket>();
		cos.listBucket(inParams, buckets, msg);
		System.out.println(buckets);
		System.out.println(msg);
	}

	public static void getBucketInfo(Cos cos, String bucketId, Message msg) {
		System.out
				.println("----------------------getBucketInfo----------------------\n");
		Map<String, Object> inParams = new HashMap<String, Object>();
		Bucket b = new Bucket();
		inParams.put("bucketId",bucketId);
		cos.getBucketInfo(inParams, b, msg);
		System.out.println(b);
	}

	public static void setBucketInfo(Cos cos, String bucketId, Message msg) {
		System.out
				.println("----------------------setBucketInfo----------------------\n");

		Map<String, Object> inParams = new HashMap<String, Object>();
		Bucket b = new Bucket();
		b.setAcl(1);
		b.setReferer("*.qq.com");
		inParams.put("bucketId", bucketId);
		cos.getBucketInfo(inParams, b, msg);
		System.out.println(msg);

		b.setAcl(0);
		b.setReferer("");
		inParams.put("bucketId", bucketId);
		cos.getBucketInfo(inParams, b, msg);
		System.out.println(msg);
	}

	public static void createDir(Cos cos, String bucketId, Message msg) {
		System.out
				.println("----------------------createDir----------------------\n");
		Map<String, Object> inParams = new HashMap<String, Object>();
		inParams.put("bucketId", bucketId);
		inParams.put("path", "/path02");
		cos.createDir(inParams, msg);
		System.out.println(msg);

		inParams.put("path", "/path02/path_022");
		inParams.put("expires", 8888); //
		inParams.put("cacheControl", "max-age=72000, must-revalidate");
		inParams.put("contentEncoding", "utf-8");
		inParams.put("contentLanguage", "zh-CN");
		inParams.put("contentDisposition", "attachment; filename=tempfile");
		cos.createDir(inParams, msg);
		System.out.println(msg);
	}

	public static void uploadFileContent(Cos cos, String bucketId, Message msg) {
		System.out
				.println("----------------------uploadFileContent----------------------\n");
		Map<String, Object> inParams = new HashMap<String, Object>();
		inParams.put("bucketId", bucketId);
		inParams.put("path", "/path02");
		inParams.put("cosfile", "file021");
		byte[] a = "yhgfhgfdgsgfsfgdsfgdsfgds".getBytes();
		CosFile file = new CosFile();
		cos.uploadFileContent(inParams, a, file, msg);
		System.out.println(file);
		System.out.println(msg);
	}

	public static void uploadFile(Cos cos, String bucketId, String localFile,
			Message msg) {
		System.out
				.println("----------------------uploadFile----------------------\n");
		Map<String, Object> inParams = new HashMap<String, Object>();
		inParams.put("bucketId", bucketId);
		inParams.put("path", "/image");
		inParams.put("cosfile", "test001");
		inParams.put("localfile", localFile);
		CosFile file = new CosFile();
		cos.uploadFile(inParams, file, msg);
		System.out.println(file);
		System.out.println(msg);
	}

	public static void multipart_upload(Cos cos, String bucketId, Message msg) {
		System.out
				.println("----------------------multipart_upload----------------------\n");
		Map<String, Object> inParams = new HashMap<String, Object>();
		inParams.put("bucketId", bucketId);
		inParams.put("path", "/path02");
		inParams.put("cosfile", "file023");
		inParams.put("offset", 0);
		CosFile file = new CosFile();
		byte[] data = new byte[64 * 1024];// 6400K
		for (int i = 0; i < data.length; i++) {
			data[i] = 'a';
		}

		for (int j = 0; j < 10; j++) {
			inParams.put("offset", j * data.length);
			cos.multipart_upload(inParams, data, file, msg);
		}

		System.out.println(file);
		System.out.println(msg);
	}

	public static void complete_multipart_upload(Cos cos, String bucketId,
			Message msg) {
		System.out
				.println("----------------------complete_multipart_upload----------------------\n");
		Map<String, Object> inParams = new HashMap<String, Object>();
		inParams.put("bucketId", bucketId);
		inParams.put("path", "/path02/file023");
		cos.complete_multipart_upload(inParams, msg);
		System.out.println(msg);
	}

	public static void listFile(Cos cos, String bucketId, Message msg) {
		System.out
				.println("----------------------listFile----------------------\n");
		Map<String, Object> inParams = new HashMap<String, Object>();
		inParams.put("bucketId", bucketId);
		inParams.put("path", "/path02");
		inParams.put("count", 10);
		inParams.put("offset", 0);
		List<CosFile> files = new Vector<CosFile>();
		List<CosDirectory> dirs = new Vector<CosDirectory>();
		ListFileNum listFileNum = new ListFileNum();
		cos.listFile(inParams, files, dirs, listFileNum, msg);
		System.out.println(dirs);
		System.out.println(files);
		System.out.println(msg);
	}

	public static void getMeta(Cos cos, String bucketId, Message msg) {
		System.out
				.println("----------------------getMeta----------------------\n");
		Map<String, Object> inParams = new HashMap<String, Object>();
		inParams.put("bucketId", bucketId);
		inParams.put("path", "/path02/file023");
		CosObject obj = new CosFile();
		cos.getMeta(inParams, obj, msg);
		System.out.println(obj);
	}

	public static void setMeta(Cos cos, String bucketId, Message msg) {
		System.out
				.println("----------------------setMeta----------------------\n");
		Map<String, Object> inParams = new HashMap<String, Object>();
		inParams.put("bucketId", bucketId);
		inParams.put("path", "/path02");
		inParams.put("expires", 8888);
		cos.setMeta(inParams, msg);
		System.out.println(msg);
	}

	public static void deleteFile(Cos cos, String bucketId, Message msg) {
		System.out
				.println("----------------------deleteFile----------------------\n");
		Map<String, Object> inParams = new HashMap<String, Object>();
		inParams.put("bucketId", bucketId);
		inParams.put("path", "/path02");

		List<String> fileList = new Vector<String>();
		fileList.add("file021");
		cos.deleteFile(inParams, fileList, msg);
		System.out.println(msg);

		fileList.clear();
		fileList.add("file022");
		cos.deleteFile(inParams, fileList, msg);
		System.out.println(msg);

		fileList.clear();
		fileList.add("file023");
		cos.deleteFile(inParams, fileList, msg);
		System.out.println(msg);

		fileList.clear();
		fileList.add("file024.jpg");
		cos.deleteFile(inParams, fileList, msg);
		System.out.println(msg);

		fileList.clear();
		fileList.add("file024zip.jpg");
		cos.deleteFile(inParams, fileList, msg);
		System.out.println(msg);

		fileList.clear();
		fileList.add("file025.jpg");
		cos.deleteFile(inParams, fileList, msg);
		System.out.println(msg);

		fileList.clear();
		fileList.add("file025zip.jpg");
		cos.deleteFile(inParams, fileList, msg);
		System.out.println(msg);

		fileList.clear();
		fileList.add("file026zip.jpg");
		cos.deleteFile(inParams, fileList, msg);
		System.out.println(msg);
	}

	public static void rename(Cos cos, String bucketId, Message msg) {
		System.out
				.println("----------------------rename----------------------\n");
		Map<String, Object> inParams = new HashMap<String, Object>();
		inParams.put("bucketId", bucketId);
		inParams.put("spath", "/path02/path_022");
		inParams.put("dpath", "/path02/path_022new");
		cos.rename(inParams, msg);
		System.out.println(msg);
	}

	public static void deleteDir(Cos cos, String bucketId, Message msg) {
		System.out
				.println("----------------------deleteDir----------------------\n");
		Map<String, Object> inParams = new HashMap<String, Object>();
		inParams.put("bucketId", bucketId);
		inParams.put("path", "/path02/path_022new");
		cos.deleteDir(inParams, msg);
		System.out.println(msg);

		inParams.put("path", "/path02");
		cos.deleteDir(inParams, msg);
		System.out.println(msg);
	}

	public static void deleteBucket(Cos cos, String bucketId, Message msg) {
		System.out
				.println("----------------------deleteBucket----------------------\n");
		Map<String, Object> inParams = new HashMap<String, Object>();
		inParams.put("bucketId", bucketId);
		cos.deleteBucket(inParams, msg);
		System.out.println(msg);
	}

	public static void uploadFileWithCompress(Cos cos, String bucketId,
			String localFilePath, Message msg) {
		System.out
				.println("----------------------uploadFileWithCompress----------------------\n");
		Map<String, Object> inParams = new HashMap<String, Object>();
		inParams.put("compressBucketId", bucketId);
		inParams.put("compressFilePath", "/file024zip.jpg");
		inParams.put("uploadBucketId", bucketId);
		inParams.put("uploadFilePath", "/file024.jpg");
		inParams.put("localfile", localFilePath);

		inParams.put("WMFontType", "仿宋");
		inParams.put("zoomType", "2");
		inParams.put("height", "200");
		inParams.put("width", "200");
		inParams.put("WMText", "你好");

		Map<String, CosFile> files = new HashMap<String, CosFile>();
		files.put("uploadFile", new CosFile());
		files.put("compressFile", new CosFile());
		cos.uploadFileWithCompress(inParams, files, msg);
		System.out.println(files);
		System.out.println(msg);
	}

	public static boolean uploadFileContentWithCompress(Cos cos,
			String bucketId, String localfile, Message message) {
		System.out
				.println("----------------------uploadFileContentWithCompress----------------------\n");
		Map<String, Object> inParams = new HashMap<String, Object>();
		inParams.put("compressBucketId", bucketId);
		inParams.put("compressFilePath", "/path02/file025zip.jpg");
		inParams.put("uploadBucketId", bucketId);
		inParams.put("uploadFilePath", "/path02/file025.jpg");

		File f = new File(localfile);
		if (!f.canRead()) {
			message.setCode(ErrCode.COS_ERROR_FILE_CAN_NOT_READ);
			message.setMessage("Can't read file! Please check file path and rights! File path: "
					+ localfile);
			System.out.println(message.getMessage());
			return false;
		}

		if (!f.isFile()) {
			message.setCode(ErrCode.COS_ERROR_OBJ_NOT_FILE);
			message.setMessage("Object is not normal file! path: " + localfile);
			return false;
		}

		long length = f.length();
		if (length > Common.FILE_MAX_LENGTH) {
			message.setCode(ErrCode.COS_ERROR_FILE_TOO_LARGE);
			message.setMessage("File is too large! File path: " + localfile);
			return false;
		}

		byte[] data = new byte[(int) length];
		try {
			FileInputStream fis = new FileInputStream(f);
			fis.read(data, 0, (int) length);
			fis.close();

			FileOutputStream fout = new FileOutputStream("D:/test");
			fout.write(data);
			fout.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// String content = new String(data);
		Map<String, CosFile> files = new HashMap<String, CosFile>();
		files.put("uploadFile", new CosFile());
		files.put("compressFile", new CosFile());
		cos.uploadFileContentWithCompress(inParams, data, files, message);
		System.out.println(message);
		System.out.println(files);
		return true;
	}

	public static void compressOnlineFile(Cos cos, String bucketId, Message msg) {
		System.out
				.println("----------------------compressOnlineFile----------------------\n");
		Map<String, Object> inParams = new HashMap<String, Object>();
		inParams.put("srcBucketId", bucketId);
		inParams.put("srcFilePath", "/path02/file024.jpg");
		inParams.put("dstBucketId", bucketId);
		inParams.put("dstFilePath", "/path02/file026zip.jpg");
		cos.compress_online_file(inParams, msg);
		System.out.println(msg);
	}

	public static void getDownloadUrl(Cos cos, String bucketId, Message msg) {
		System.out
				.println("----------------------getDownloadUrl----------------------\n");
		Map<String, Object> inParams = new HashMap<String, Object>();
		inParams.put("bucketId", bucketId);
		inParams.put("path", "/path02/file023");
		StringBuffer resUrl = new StringBuffer();
		cos.getDownloadUrl(inParams, resUrl, msg);
		System.out.println(msg);
		System.out.println(resUrl);
	}

	public static void main(String[] args) {

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

		// 创建 bucket
		// UploadCOS.createBucket(cos, bucketId ,msg);
		// 获取bucket列表
		// UploadCOS.listBucket(cos, msg);

		// 获取bucket信息
//		 UploadCOS.getBucketInfo(cos, bucketId ,msg);
		// // 设置bucket信息
		// UploadCOS.setBucketInfo(cos, bucketId ,msg);
		// // 获取bucket信息
//		UploadCOS.getBucketInfo(cos, bucketId, msg);
		// // 创建目录
		// UploadCOS.createDir(cos, bucketId ,msg);
//		 // 上传文件
//		 UploadCOS.uploadFile(cos, bucketId, localFilePath, msg);
		// // 上传文件内容
		 UploadCOS.uploadFileContent(cos, bucketId ,msg);
		// // 分片上传文件内容
		// UploadCOS.multipart_upload(cos, bucketId ,msg);
		// // 设置分片上传结束标识
		// UploadCOS.complete_multipart_upload(cos, bucketId ,msg);
		// // 获取文件列表
//		 UploadCOS.listFile(cos, bucketId ,msg);
		// // 获取文件或目录 meta信息
//		 UploadCOS.getMeta(cos, bucketId ,msg);
		// // 设置目录meta信息
		// UploadCOS.setMeta(cos, bucketId ,msg);
		// // 获取文件或目录 meta信息
//		 UploadCOS.getMeta(cos, bucketId ,msg);
		// 上传并压缩本地图片
		// UploadCOS.uploadFileWithCompress(cos, bucketId, localFilePath, msg);
		// 上传并压缩图片内容
		// UploadCOS.uploadFileContentWithCompress(cos, bucketId, localFilePath,
		// msg);
		// // 压缩线上图片
		// UploadCOS.compressOnlineFile(cos, bucketId ,msg);
		// // 获取下载链接
//		 UploadCOS.getDownloadUrl(cos, bucketId ,msg);
		// // 删除文件
		// UploadCOS.deleteFile(cos, bucketId ,msg);
		// // 重命名文件或空目录
		// UploadCOS.rename(cos, bucketId ,msg);
		// // 删除空目录
		// UploadCOS.deleteDir(cos, bucketId ,msg);
		// // 删除空 bucket
		// UploadCOS.deleteBucket(cos, bucketId ,msg);
	}
}
