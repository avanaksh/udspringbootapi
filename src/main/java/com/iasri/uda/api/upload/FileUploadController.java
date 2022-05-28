package com.iasri.uda.api.upload;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {
	 @Autowired
	 private CandidateRepository candidateRepo;
	@PostMapping("/uploadFile")
	public ResponseEntity<FileUploadResponse> uploadFile(@RequestParam("file") MultipartFile multipartFile ) throws IOException
	{		
		String fileName=StringUtils.cleanPath(multipartFile.getOriginalFilename());
		long size=multipartFile.getSize();
		
		String fileCode=FileUploadUtil.saveFile(fileName, multipartFile);
		
		FileUploadResponse response=new FileUploadResponse();
		response.setFileName(fileName);
		response.setSize(size);
		response.setDownloadUri("/downloadFile/"+fileCode);
		
		return new ResponseEntity<FileUploadResponse>(response,HttpStatus.OK);
	}
	
	@PostMapping("/upload_multiple")
    public String handleFormSubmit(Candidate candidate,
            @RequestParam("profilePictureFile") MultipartFile multipartFile1,
            @RequestParam("photoIdFile") MultipartFile multipartFile2,
            @RequestParam("documentFile") MultipartFile multipartFile3) throws IOException {
         
        String profilePictureFileName = StringUtils.cleanPath(multipartFile1.getOriginalFilename());
        String photoIdFileName = StringUtils.cleanPath(multipartFile2.getOriginalFilename());
        String documentFileName = StringUtils.cleanPath(multipartFile3.getOriginalFilename());
         
        candidate.setProfilePicture(profilePictureFileName);
        candidate.setPhotoId(photoIdFileName);
        candidate.setDocument(documentFileName);
         
        Candidate savedCandidate = candidateRepo.save(candidate);
        String uploadDir = "candidates/" + savedCandidate.getId();
         
        FileUploadUtil.saveFile(uploadDir, profilePictureFileName, multipartFile1);
        FileUploadUtil.saveFile(uploadDir, photoIdFileName, multipartFile2);
        FileUploadUtil.saveFile(uploadDir, documentFileName, multipartFile3);
         
        return "message";
    }
	
	@PostMapping("/upload_multiples")
    public String handleFormSubmitMultiple(Candidate candidate,
            @RequestParam("files") MultipartFile[] multipartFile
             ) throws IOException {
         
        String profilePictureFileName = StringUtils.cleanPath(multipartFile[0].getOriginalFilename());
        String photoIdFileName = StringUtils.cleanPath(multipartFile[1].getOriginalFilename());
        String documentFileName = StringUtils.cleanPath(multipartFile[2].getOriginalFilename());
         
        candidate.setProfilePicture(profilePictureFileName);
        candidate.setPhotoId(photoIdFileName);
        candidate.setDocument(documentFileName);
         
        Candidate savedCandidate = candidateRepo.save(candidate);
        String uploadDir = "candidates/" + savedCandidate.getId();
         
        for (int i=0;i<multipartFile.length;i++) {
        	FileUploadUtil.saveFile(uploadDir,StringUtils.cleanPath(multipartFile[i].getOriginalFilename()), multipartFile[i]);
           // FileUploadUtil.saveFile(uploadDir, photoIdFileName, multipartFile[1]);
           // FileUploadUtil.saveFile(uploadDir, documentFileName, multipartFile[2]);
		}
        
         
        return "message";
    }
}
