package com.iasri.uda.api.upload;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {
	public static String saveFile(String fileName,MultipartFile multiPartFile) throws IOException 
	{
		Path uploadDirectory=Paths.get("Files-Upload");		
		String fileCode=RandomStringUtils.random(8);
		try(InputStream inputStream=multiPartFile.getInputStream())
		{
			Path filePath=uploadDirectory.resolve(fileCode+"-"+fileName);
			Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);			
		}catch(IOException ioe) {
			throw new IOException("Error uploading file : "+fileName,ioe);
		}
		
		return fileCode;
	}
	 public static void saveFile(String uploadDir, String fileName,
	            MultipartFile multipartFile) throws IOException {
	        Path uploadPath = Paths.get(uploadDir);
	          
	        if (!Files.exists(uploadPath)) {
	            Files.createDirectories(uploadPath);
	        }
	          
	        try (InputStream inputStream = multipartFile.getInputStream()) {
	            Path filePath = uploadPath.resolve(fileName);
	            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
	        } catch (IOException ioe) {       
	            throw new IOException("Could not save image file: " + fileName, ioe);
	        }     
	    }
}
