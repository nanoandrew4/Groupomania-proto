package com.greenapper.services;

import org.springframework.web.multipart.MultipartFile;

public interface FileSystemStorage {
	void saveImage(final MultipartFile image);

	byte[] readImage(final String url);
}
