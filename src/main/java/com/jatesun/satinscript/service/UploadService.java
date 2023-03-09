package com.jatesun.satinscript.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 文件服务类
 */
@Service
public class UploadService {

    private Map<String, List<File>> tmpFileMap;
    private Map<String, Boolean> folderPathChecker;

    @PostConstruct
    private void init() {
        tmpFileMap = new ConcurrentHashMap<>();
        folderPathChecker = new HashMap<>();
    }

    @Autowired
    private UtilService utilService;

    /**
     * 临时保存多文件
     *
     * @param fileList
     * @param transId  代表当前进行交易的id号
     */
    public String tmpSaveMultiFile(String transId, List<MultipartFile> fileList) {
        String folderPath = this.getSaveFolderPath();
        File folder = new File(folderPath);
        Long totalSize = 0L;
        tmpFileMap.remove(transId);//先删除之前的记录
        for (MultipartFile file : fileList) {
            totalSize = totalSize + file.getSize();
            if (!tmpSaveSingleFile(transId, folder, file)) {
                return "file.getName()+ upload failed!";
            }
        }
        return String.valueOf(totalSize);
    }

    /**
     * 临时保存单文件
     * 1、将上传的文件保存到临时内存，等待铭刻。
     *
     * @param file
     */
    public boolean tmpSaveSingleFile(String transId, File folder, MultipartFile file) {
        //获取上传文件的源文件名称
        String oldName = file.getOriginalFilename();
        String newName = getPreFileName() + oldName;
        //判断目录是否存在，不存在则创建目录
        if (!folder.exists()) {
            folder.mkdirs();
        }
        try {
            File fileToSave = new File(folder, newName);
            this.saveTmpFileList(transId, fileToSave);
            file.transferTo(fileToSave);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void saveTmpFileList(String transId, File file) {
        List<File> fileList = tmpFileMap.get(transId);
        if (fileList == null || fileList.isEmpty()) {
            fileList = new ArrayList<>();
            fileList.add(file);
        }
        tmpFileMap.put(transId, fileList);
    }

    private String getSaveFolderPath() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String timeFolder = dateFormat.format(date.getTime());
        String realFolder = "/Users/jatesun/satins/" + timeFolder;
        return realFolder;
    }

    private String getPreFileName() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HHmmssSSS");
        String preFileName = dateFormat.format(date.getTime());
//        String preFileName = dateFormat.format(date.getTime()) + utilService.getRandom(8);
        return preFileName;
    }


}
