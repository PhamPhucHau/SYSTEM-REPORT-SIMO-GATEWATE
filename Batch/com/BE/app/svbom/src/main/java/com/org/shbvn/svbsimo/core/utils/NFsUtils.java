package com.org.shbvn.svbsimo.core.utils;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.xfile.XFile;
import com.sun.xfile.XFileInputStream;
import com.sun.xfile.XFileOutputStream;

public class NFsUtils {
	
	protected static final Logger logger = LoggerFactory.getLogger(NFsUtils.class);
	
	public static void main(String[] args) {
		// uploadFileToNfs();
		// downLoadFileFromNfs();
		// downloadViaNFS("\\\\svfcfs01\\OMS_UAT$", "", "", "\\omsrecon","C:/Dung Pham/Project/OMS/Imp/tmp");
		// uploadViaNFS("\\\\svfcfs01\\OMS_UAT$", "", "", "\\bkuprecon", "C:/Dung Pham/Project/OMS/Imp/tmp", "VCB898 04.05.2021.xls");
		
//		logger.info("========= UAT ============");
//		testURL("//10.148.46.29/OMS_UAT$");
//		logger.info("=====================");
//		testURL("//10.148.46.29/OMS_UAT$/omsrecon");
//
//		logger.info("========= PRD ============");
//		testURL("\\\\10.148.46.29\\oms_prod$");
//		logger.info("=====================");
//		testURL("//10.148.46.29/oms_prod$");
//		logger.info("=====================");
//		testURL("//10.148.46.29/oms_prod$/omsrecon");
//		logger.info("=====================");
//		testURL("//10.148.46.29/oms_prod$//omsrecon");
//		logger.info("=====================");
		// testURL("nfs://10.148.46.29/OMS_UAT$");
		// logger.info("=====================");
		//testURL("smb://10.148.46.29/oms_prod$");
		//logger.info("=====================");
	}

	public static void testURL(final String url) {
		try {   
			logger.info("Original URL : " + url);
			XFile xf = new XFile(url);
			logger.info("exists : " + xf.exists());
			logger.info("isDirectory : " + xf.isDirectory());
			
			if (xf.exists()) {
				logger.info("URL is Okie!");
			} else {
				logger.info("URL is not exists!");
				return;
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
    public static void downloadViaNFS(final String ip,final String user,final String password,final String dir, String destPath)
	{
		try {	 
			
			String url = ip + dir;
			XFile xf = new XFile(url);
     		if (xf.exists())
     		{
     			logger.info(url + ": URL is OK!");
     		}else
     		{
     			logger.info(url + ": URL is bad!");
     			return;
     		}
     		 
     		String [] fileList = xf.list();
     		XFile temp = null;
     		long startTime = System.currentTimeMillis();
     		int filesz = 0;
            for(String file:fileList)
            {
            	temp = new XFile(url+"/"+file);
            	XFileInputStream  in  = new XFileInputStream(temp);
            	File tmp  = new File(destPath + "/" + file); 					// to make temporary directory
                XFileOutputStream out = new XFileOutputStream(destPath + "/" + file);
 
                int c;        
                byte[] buf = new byte[8196];           
                
                		
 
                while ((c = in.read(buf)) > 0) {                
                     filesz += c;                
                     out.write(buf, 0, c);                     
                }            
 
                logger.info(file +" is downloaded!");       
                in.close();            
                out.close();        
                if (temp.canWrite())
                {
                	temp.delete();
                	logger.info(file + " is deleted!");
                }else
                {
                	logger.info(file + " can not be deleted!");
                }
            }
                long endTime = System.currentTimeMillis();
                long timeDiff = endTime - startTime;
                int rate = (int) ((filesz /1000) / (timeDiff / 1000.0));
                logger.info(filesz + " bytes copied @ " + rate + "Kb/sec");
           
            }catch (Exception e) {	    
            	logger.info("NFS Download Error");        
            }
		
	}
    public static void uploadViaNFS(final String ip,final String user,final String password,final String dir, String resPath,String resFileName)
    {
    	try {	 
    		String url = ip + dir;
    		XFile temp = null;
    		long startTime = System.currentTimeMillis();
    		int filesz = 0;
    		temp = new XFile(resPath + "/" + resFileName);
			XFileInputStream  in  = new XFileInputStream(temp);					// to make temporary directory
			XFileOutputStream out = new XFileOutputStream(url + "/" +  resFileName);
			logger.info(url +" uploaded Path");  
			int c;        
			byte[] buf = new byte[8196];           
			
			
			
			while ((c = in.read(buf)) > 0) {                
				filesz += c;                
				out.write(buf, 0, c);                     
			}            
			
			logger.info(resFileName +" is uploaded!");       
			in.close();            
			out.close();        
			if (temp.canWrite())
			{
				temp.delete();
				logger.info(resFileName + " is deleted!");
			}else
			{
				logger.info(resFileName + " can not be delted!");
			}
    		long endTime = System.currentTimeMillis();
    		long timeDiff = endTime - startTime;
    		int rate = (int) ((filesz /1000) / (timeDiff / 1000.0));
    		logger.info(filesz + " bytes copied @ " + rate + "Kb/sec");
    		
    	}catch (Exception e) {	    
    		logger.info("NFS Upload Error");        
    	}
    	
    }
}
