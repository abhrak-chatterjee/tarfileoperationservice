package com.abhrak.tarfileoperation.controller;

import com.abhrak.tarfileoperation.constants.IConstants;
import com.abhrak.tarfileoperation.service.ITarFileOperationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Log4j2
@Api(value = IConstants.API_VALUE)
@CrossOrigin
public class TarFileOperationController {

    @Autowired
    ITarFileOperationService service;

    @PostMapping(value = IConstants.IMPORT_API, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(httpMethod = IConstants.HTTP_METHOD_POST, value = IConstants.IMPORT_API_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, response = String.class)
    public ResponseEntity importTarFiles(@RequestBody MultipartFile[] files) throws Exception {
        ResponseEntity response = null;
        try {
            response = new ResponseEntity(service.importTarFiles(files), HttpStatus.OK);
        } catch (Exception e) {
            log.error(IConstants.EXCEPTION_OCCURRED_WHILE_PROCESSING, e);
            response = new ResponseEntity(IConstants.EXCEPTION_OCCURRED_WHILE_PROCESSING, HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @GetMapping(value = IConstants.GET_TAR_FILE_API, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ApiOperation(httpMethod = IConstants.HTTP_METHOD_GET, value = IConstants.GET_TAR_FILE_API_VALUE, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE, response = Resource.class)
    public ResponseEntity getTarFile(@PathVariable String fileName) throws Exception {
        ResponseEntity response = null;
        try {
            Resource resource = service.getTarFile(fileName);
            response = ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (Exception e) {
            log.error(IConstants.EXCEPTION_OCCURRED_WHILE_PROCESSING, e);
            response = new ResponseEntity(IConstants.EXCEPTION_OCCURRED_WHILE_PROCESSING, HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @GetMapping(value = IConstants.GET_ALL_TAR_FILES_API, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(httpMethod = IConstants.HTTP_METHOD_GET, value = IConstants.GET_ALL_TAR_FILES_API_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, response = String.class)
    public ResponseEntity getAllTarFiles() throws Exception {
        ResponseEntity response = null;
        try {
            response = new ResponseEntity(service.getAllTarFiles(), HttpStatus.OK);
        } catch (Exception e) {
            log.error(IConstants.EXCEPTION_OCCURRED_WHILE_PROCESSING, e);
            response = new ResponseEntity(IConstants.EXCEPTION_OCCURRED_WHILE_PROCESSING, HttpStatus.BAD_REQUEST);
        }
        return response;
    }

}
