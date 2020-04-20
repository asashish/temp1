package com.sda.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sda.app.model.Document;

@Repository
public interface DocumentRepo extends CrudRepository<Document, Long> {

	public Document findByDocName(String fileName);

}
