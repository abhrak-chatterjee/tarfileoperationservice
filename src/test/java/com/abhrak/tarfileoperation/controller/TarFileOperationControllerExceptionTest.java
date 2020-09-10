package com.abhrak.tarfileoperation.controller;

import com.abhrak.tarfileoperation.constants.IConstants;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TarFileOperationControllerExceptionTest {

    @InjectMocks
    private TarFileOperationController tarFileOperationController;

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
        mockMvc.perform(post(IConstants.IMPORT_API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf((tarFile))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getTarFile() throws Exception {
        tarFileOperationController.getTarFile("file1.tar.gz");
    }

    @Test
    public void getAllTarFiles() throws Exception {
        mockMvc.perform(get(IConstants.GET_ALL_TAR_FILES_API)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}