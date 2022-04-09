package com.caster.ocr.controller;

import com.caster.ocr.clean.CleanServiceAbs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Author: Minchang Hsu (Caster)
 * Date: 2022/4/9
 * Comment:
 */
@RequestMapping("/clean")
@RestController
@RequiredArgsConstructor
public class CleanVerifyImageController {

	@Autowired
	ApplicationContext context;

	@PostMapping("")
	public ResponseEntity cleanImage(MultipartFile file) throws InterruptedException, IOException {
		CleanServiceAbs abs = (CleanServiceAbs)context.getBean("chunghWa");
		InputStream inputStream = new ByteArrayInputStream(file.getBytes());
		// 中華郵政郵遞區號3+2
		String code = abs.executeOCRMainProcess(inputStream, 4);
		return ResponseEntity.ok(code);
	}
}
