package com.abhrak.tarfileoperation.controller;

import com.abhrak.tarfileoperation.constants.IConstants;
import com.abhrak.tarfileoperation.service.ITarFileOperationService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TarFileOperationControllerTest {

    @InjectMocks
    private TarFileOperationController tarFileOperationController;

    @Mock
    private ITarFileOperationService service;

    @Mock
    private Resource resource;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(tarFileOperationController).build();
    }

    @Test
    public void importTarFiles() throws Exception {
        List<String> urlList = new ArrayList<>();
        urlList.add("Url1");
        File tarFile = new File("./src/test/resources/Files/file1.tar.gz");
        when(service.importTarFiles(Mockito.any(MultipartFile[].class))).thenReturn(urlList);
        mockMvc.perform(post(IConstants.IMPORT_API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf((tarFile))))
                .andExpect(status().isOk());
        assertNotNull(urlList);
    }

    @Test
    public void getTarFile() throws Exception {
        when(service.getTarFile("file1.tar.gz")).thenReturn(resource);
        tarFileOperationController.getTarFile("file1.tar.gz");
        assertNotNull(resource);
    }

    @Test
    public void getAllTarFiles() throws Exception {
        List<String> urlList = new ArrayList<>();
        urlList.add("Url1");
        when(service.getAllTarFiles()).thenReturn(urlList);
        mockMvc.perform(get(IConstants.GET_ALL_TAR_FILES_API)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        assertNotNull(urlList);
    }
}