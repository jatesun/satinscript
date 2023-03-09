package com.jatesun.satinscript.controller;

import com.jatesun.satinscript.service.UploadService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
public class UploadFileController {
    @Autowired
    private UploadService uploadService;

    @RequestMapping("/upload")
    @CrossOrigin
    public String uploadFile(MultipartFile[] file, HttpServletRequest request) {
//        String transId = UUID.randomUUID().toString();
        String sessionId = request.getSession().getId();
        String fileSize = uploadService.tmpSaveMultiFile(sessionId, List.of(file));
        return fileSize + "," + sessionId;
    }

}
