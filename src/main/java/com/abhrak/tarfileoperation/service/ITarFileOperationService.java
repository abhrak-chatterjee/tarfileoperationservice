package com.abhrak.tarfileoperation.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ITarFileOperationService {
    List<String> importTarFiles(MultipartFile[] files) throws Exception;

    Resource getTarFile(String fileName) throws Exception;

    List<String> getAllTarFiles() throws Exception;
}
