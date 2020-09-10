package com.abhrak.tarfileoperation.service.impl;

import com.abhrak.tarfileoperation.constants.IConstants;
import com.abhrak.tarfileoperation.service.ITarFileOperationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Log4j2
public class TarFileOperationServiceImpl implements ITarFileOperationService {

    @Value("${file.location}")
    private String fileBasePath;

    /**
     *
     * method used to import tar files
     *
     * @param files
     * @return list of files imported and the respective download urls
     * @throws Exception
     */

    @Override
    public List<String> importTarFiles(MultipartFile[] files) throws Exception {
        List<String > fileDownloadUrls = new ArrayList<>();
        try {
            for (MultipartFile file : files) {
                if (!(file.getOriginalFilename().endsWith(IConstants.TAR_FILE_EXTENSION) || file.getOriginalFilename().endsWith(IConstants.TAR_FILE_EXTENSION.toUpperCase()))) {
                    throw new Exception(IConstants.NOT_A_TAR_FILE_EXCEPTION);
                }
            }
            log.info("Files are getting stored under: " + fileBasePath);
            Arrays.asList(files)
                    .stream()
                    .forEachOrdered(file -> {
                        try {
                            fileDownloadUrls.add(uploadToLocalFileSystem(file));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
        } catch (Exception e) {
            throw e;
        }
        return fileDownloadUrls;
    }

    /**
     *
     * method used to get a tar file based on the name
     *
     * @param fileName
     * @return file resource
     * @throws Exception
     */

    @Override
    public Resource getTarFile(String fileName) throws Exception {
        Path path = null;
        Resource resource = null;
        try {
            path = Paths.get(fileBasePath + fileName);
            log.info("Looking for " + fileName + " inside " + path.getParent());
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException malformedURLException) {
            log.error(IConstants.MALFORMED_URL_EXCEPTION);
            throw malformedURLException;
        } catch (InvalidPathException invalidPathException) {
            log.error(IConstants.INVALID_PATH_EXCEPTION);
            throw invalidPathException;
        } catch (Exception e) {
            log.error(IConstants.EXCEPTION_OCCURRED_WHILE_GETTING_TAR_FILE);
            throw e;
        }
        return resource;
    }

    /**
     *
     * method used to retrieve all the tar files
     *
     * @return list of all tar file names
     * @throws Exception
     */

    @Override
    public List<String> getAllTarFiles() throws Exception {
        List<String> fileLists = new ArrayList<>();
        try {
            File folder = new File(fileBasePath);
            log.info("Looking for files inside: " + fileBasePath);
            for (final File file : folder.listFiles()) {
                if (file.isFile()) {
                    fileLists.add(file.getName());
                }
            }
        } catch (Exception e) {
            log.error(IConstants.EXCEPTION_OCCURRED_WHILE_PROCESSING);
            throw e;
        }
        return fileLists;
    }

    /**
     *
     * method used to save imported tar file(s) in the local system
     *
     * @param file
     * @return file name along with download url
     * @throws Exception
     */

    private String uploadToLocalFileSystem(MultipartFile file) throws Exception {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            Path path = Paths.get(fileBasePath + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            log.info(fileName + IConstants.SUCCESSFULLY_IMPORTED);
        } catch (Exception e) {
            log.error(IConstants.EXCEPTION_OCCURRED_WHILE_SAVING_TAR_FILE);
            throw e;
        }
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(IConstants.TAR_FILE_DOWNLOAD_API)
                .path(fileName)
                .toUriString();
        return fileName + ": " + fileDownloadUri;
    }
}
