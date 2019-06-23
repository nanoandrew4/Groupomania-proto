package com.greenapper.controllers;

import com.greenapper.services.FileSystemStorageService;
import com.greenapper.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/images")
public class ImageFetcherController {

	@Autowired
	private SessionService sessionService;

	@Autowired
	private FileSystemStorageService fileSystemStorageService;

	@GetMapping
	public @ResponseBody
	byte[] findImage(@RequestParam final String fileName) {
		return fileSystemStorageService.readImage(fileName).orElse(null);
	}
}
