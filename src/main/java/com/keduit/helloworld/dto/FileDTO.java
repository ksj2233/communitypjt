package com.keduit.helloworld.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class FileDTO {

	private String uploadFile;
	private String contentType;
	
	public FileDTO(String originalFile, String Content) {
		
		this.uploadFile = originalFile;
		this.contentType = Content;
		
	}
}
