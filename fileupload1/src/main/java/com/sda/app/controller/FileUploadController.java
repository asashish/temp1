package com.sda.app.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.sda.app.model.Document;
import com.sda.app.repository.DocumentRepo;
import com.sda.app.service.FileUploadService;

@RestController
public class FileUploadController {

	@Autowired
	private FileUploadService fileUploadService;

	@Autowired
	private DocumentRepo documentDao;

	@Autowired
	private Document doc;

	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

	@GetMapping("/hello")
	public void healthCheck() {
		System.out.println("Hello");
	}

	  @PostMapping("/sda/save-arch") 
	  public ResponseEntity uploadToDB(
			  @RequestParam("file") MultipartFile file) throws IOException { 
		  String fileName = StringUtils.cleanPath(file.getOriginalFilename()); 
		  doc.setDocName(fileName);
		  doc.setFile(file.getBytes());
		  doc.setDocDesc("XXX");
		  //	doc.setDocDesc(file.getDocDesc()); 
		  //service class call 
		  String downloadUri = fileUploadService.saveFileToDB(doc, fileName); return
		ResponseEntity.ok(downloadUri); 
	  }

//	@PostMapping(value = "/sda/save-arch", consumes= MediaType.MULTIPART_FORM_DATA_VALUE) 
//	public ResponseEntity uploadToDB(
//			@RequestParam(value="file") MultipartFile file,
//			@RequestParam(value="data", required = true) Document data) throws JsonParseException, JsonMappingException, IOException {
//		String fileName = StringUtils.cleanPath(file.getOriginalFilename()); 
//		doc.setDocName(fileName);
//		doc.setFile(file.getBytes());
////		Document dd = (Document) data.getDocDesc();
//		doc.setDocDesc(data.getDocDesc()); 
//		//service class call 
//		String downloadUri= fileUploadService.saveFileToDB(doc, fileName); 
//		return ResponseEntity.ok(downloadUri); 
//	  }
	
//	@PostMapping(value = "/sda/save-arch") 
//	public ResponseEntity uploadToDB(
//			@RequestPart(value="file") MultipartFile file,
//			@RequestPart(value="data") Document data) throws JsonParseException, JsonMappingException, IOException {
//		String fileName = StringUtils.cleanPath(file.getOriginalFilename()); 
//		doc.setDocName(fileName);
//		doc.setFile(file.getBytes());
//		doc.setDocDesc(data.getDocDesc()); 
//		//service class call 
//		String downloadUri= fileUploadService.saveFileToDB(doc, fileName); 
//		return ResponseEntity.ok(downloadUri); 
//	  }
//	
//	@PostMapping(value = "/upload") 
//	public ResponseEntity uploadData(
//			@RequestBody Document data) throws JsonParseException, JsonMappingException, IOException {
//		doc.setDocName(data.getDocName());
//		doc.setFile(data.getFile());
//		doc.setDocDesc(data.getDocDesc()); 
//		documentDao.save(doc);
//		//service class call 
//		 
//		return ResponseEntity.ok("Okay");
//	  }

	@GetMapping("/download/{fileName:.+}/db")
	public ResponseEntity downloadFromDB(@PathVariable String fileName) {
		// service class call
		Document document = fileUploadService.downloadFromDBService(fileName);
		return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/json"))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
				.body(document.getFile());
	}
}
