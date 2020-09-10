package com.abhrak.tarfileoperation.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class TarFileOperationServiceImplTest {

    @InjectMocks
    private TarFileOperationServiceImpl service;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        String fileBasePath = "D:\\Nokia\\Files\\";
        Field fileBasePathField = TarFileOperationServiceImpl.class.getDeclaredField("fileBasePath");
        fileBasePathField.setAccessible(true);
        fileBasePathField.set(service, fileBasePath);
    }

    @Test
    public void importTarFiles() throws Exception {
        File file1 = new File("./src/test/resources/Files/file1.tar.gz");
        File file2 = new File("./src/test/resources/Files/file2.tar.gz");

        InputStream stream1 =  new FileInputStream(file1);
        InputStream stream2 =  new FileInputStream(file2);

        MockMultipartFile multipartFile1 = new MockMultipartFile("file1.tar.gz", file1.getName(), MediaType.TEXT_HTML_VALUE, stream1);
        MockMultipartFile multipartFile2 = new MockMultipartFile("file2.tar.gz", file2.getName(), MediaType.TEXT_HTML_VALUE, stream2);

        MultipartFile[] files = new MultipartFile[]{multipartFile1, multipartFile2};

        List<String> urlList = service.importTarFiles(files);

        assertNotNull(urlList);
    }

    @Test (expected = Exception.class)
    public void importTarFilesNegativeTest() throws Exception {
        File file = new File("./src/test/resources/Files/file3.txt");

        InputStream stream =  new FileInputStream(file);

        MockMultipartFile multipartFile = new MockMultipartFile("file3.txt", file.getName(), MediaType.TEXT_HTML_VALUE, stream);

        MultipartFile[] files = new MultipartFile[]{multipartFile};

        List<String> urlList = service.importTarFiles(files);

        assertNotNull(urlList);
    }

    @Test
    public void getTarFile() throws Exception {
        Resource resource = service.getTarFile("file1.tar.gz");

        assertNotNull(resource);
    }

    @Test
    public void getAllTarFiles() throws Exception {
        List<String> urlList = service.getAllTarFiles();

        assertNotNull(urlList);
    }
}