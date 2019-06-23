package com.greenapper.services;

import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface FileSystemStorageService {
	String saveImage(final MultipartFile image);

	Optional<byte[]> readImage(final String name);
}
