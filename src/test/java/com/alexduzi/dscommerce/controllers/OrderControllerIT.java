package com.alexduzi.dscommerce.controllers;

import com.alexduzi.dscommerce.util.TokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class OrderControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private ObjectMapper objectMapper;

    private String adminAccessToken;
    private String clientAccessToken;

    @BeforeEach
    void setUp() throws Exception {
        String adminUsername = "alex@gmail.com";
        String adminPassword = "123456";
        String clientUsername = "maria@gmail.com";
        String clientPassword = "123456";

        adminAccessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);

        clientAccessToken = tokenUtil.obtainAccessToken(mockMvc, clientUsername, clientPassword);
    }

    @Test
    void findByIdShouldReturnOrderDTOWhenIdExistsAndAdminLogged() throws Exception {
        ResultActions result = mockMvc.perform(get("/orders/{id}", 1L)
                .header("Authorization", "Bearer " + adminAccessToken)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").value(1L));
        result.andExpect(jsonPath("$.moment").value("2022-07-25T13:00:00Z"));
        result.andExpect(jsonPath("$.status").value("PAID"));
    }
}