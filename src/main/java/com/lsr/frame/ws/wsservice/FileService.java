package com.lsr.frame.ws.wsservice;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.jws.WebService;

import org.apache.commons.io.CopyUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lsr.frame.ws.common.WSService;
import com.lsr.frame.ws.context.WSContext;
import com.lsr.frame.ws.context.WSSession;
import com.lsr.frame.ws.context.WSSessionContext;
import com.lsr.frame.ws.utils.CxfUtil;
import com.lsr.frame.ws.utils.KeyGenerator;
import com.lsr.frame.ws.utils.WSSessionUtils;

@WebService
public class FileService extends WSService{
	public static final String FILE_NAME_FRONT = "tmpFile_";
	public static final String FILE_FOLDER = "uploadfiles/temp/";
	private static final Log log = LogFactory.getLog(FileService.class);
	
	
	/**
	 * 文件上传 
	 * @param file1  byte[]文件1
	 * @param file2  byte[]文件2
	 * @param file3  byte[]文件3
	 * @param file4  byte[]文件4
	 * @param file5  byte[]文件5
	 * @param validateCode session的验证码
	 * @return 返回文件上传到服务器后的临时路径
	 * @throws Exception
	 */
	public String[] upload(byte[] file1, byte[] file2, byte[] file3, byte[] file4, byte[] file5, String validateCode)throws Exception{
		log.debug("文件上传开始:" + System.currentTimeMillis());
		validateSession(validateCode);
		int retStringArraySize = 0;
		Collection fileDataList = new ArrayList();
		if(file1 != null && file1.length > 0){
			fileDataList.add(file1);
			retStringArraySize++;
		}
		if(file2 != null && file2.length > 0){
			fileDataList.add(file2);
			retStringArraySize++;
		}
		if(file3 != null && file3.length > 0){
			fileDataList.add(file3);
			retStringArraySize++;
		}
		if(file4 !=  null && file4.length > 0){
			fileDataList.add(file4);
			retStringArraySize++;
		}
		if(file5 !=  null && file5.length > 0){
			fileDataList.add(file5);
			retStringArraySize++;
		}
		String[] retStringArray = new String[retStringArraySize];
		int index = 0;
		for(Iterator it = fileDataList.iterator(); it.hasNext(); ){
			retStringArray[index++] = doUpload((byte[]) it.next());
		}
		log.debug("文件上传结束:" + System.currentTimeMillis());
		return retStringArray;
	}
	
	/**
	 * 文件下载
	 * @param filePath 需要下载的文件路径
	 * @param validateCode session的验证码
	 * @return 返回下载文件内容的比特数组
	 * @throws Exception
	 */
	public byte[] download(String filePath, String validateCode)throws Exception{
		validateSession(validateCode);
		String realRealPath = CxfUtil.getRealPath() + filePath;
		
		return getFileData(realRealPath);
	}
	
	/**
	 * 上传文件
	 * @param fileData
	 * @return
	 * @throws IOException
	 */
	private String doUpload(byte[] fileData)throws IOException{
		String file = FILE_NAME_FRONT  + KeyGenerator.getUUID() + ".tmp";
		String fileTmpName = getTempFileFolder(FILE_FOLDER) + file;  
		
		log.debug("filePath:::::::::::::::::::::::::::::::::" + fileTmpName);
		
		handleUpload(fileData, fileTmpName);
		return file;
	}
	
	/**
	 * 处理文件上传
	 * @param fileData
	 * @param filePath
	 * @throws IOException
	 */
	public static void handleUpload(byte[] fileData, String filePath)throws IOException{
		File tempFile = new File(filePath);
		if(!tempFile.exists()){
			tempFile.createNewFile();
		}
		
		OutputStream outputStream = null;
		try{
			outputStream = new BufferedOutputStream(new FileOutputStream(tempFile));
			CopyUtils.copy(fileData, outputStream);
		}finally{
			outputStream.close();
			outputStream = null;
		}
	}
	
	/**
	 * 获得文件数据
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static byte[] getFileData(String filePath)throws IOException{
		log.debug("filePath:::::::::::::::::::::::::::::::::" + filePath);
		File file = new File(filePath);
		if(!file.exists()){
			return null;
		}
		InputStream reader = null;
		byte[] buffer = null;
		try{
			reader = new BufferedInputStream(new FileInputStream(file));
			buffer = IOUtils.toByteArray(reader);
		}finally{
			reader.close();
			reader = null;
		}
		return buffer;
	}
	
	/**
	 * 清空文件夹
	 * @param folder
	 * @throws IOException
	 */
	public static void cleanFolder(File folder)throws IOException{
		if(!folder.isDirectory()){
			return;
		}
		if(!folder.exists()){
			return;
		}
		FileUtils.deleteDirectory(folder);
		if(!folder.exists()){
			folder.mkdir();
		}
	}
		
	/**
	 * 验证WsSession
	 * @param validateCode
	 * @throws Exception
	 */
	private void validateSession(String validateCode)throws Exception{
		WSContext.getInstance().setSessionValidCode(validateCode);
		Exception ex = WSSessionUtils.validate((WSSession)WSSessionContext.getInstance().getCurrentSession());
		if(ex != null){
			throw ex;
		}
	}
	
	/**
	 * 获得临时文件夹
	 * @return
	 */
	public static final String getTempFileFolder(String fileFoler){
		String realPath = CxfUtil.getRealPath();
		if(!realPath.endsWith("/")){
			realPath += "/";
		}
		return realPath + fileFoler;
	}
}
