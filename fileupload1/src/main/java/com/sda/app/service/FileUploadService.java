package com.sda.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sda.app.model.Document;
import com.sda.app.repository.DocumentRepo;

@Service
public class FileUploadService {

	@Autowired
	private DocumentRepo documentDao;

	public String saveFileToDB(Document doc, String fileName) {
		documentDao.save(doc);
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/download/")
				.path(fileName).path("/db").toUriString();
		return fileDownloadUri;
	}

	public Document downloadFromDBService(String fileName) {
		return documentDao.findByDocName(fileName);
	}
	
}
