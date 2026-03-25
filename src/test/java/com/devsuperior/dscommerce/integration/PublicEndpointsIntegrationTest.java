package com.devsuperior.dscommerce.integration;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PublicEndpointsIntegrationTest extends AbstractIntegrationTest {

    @Test
    void publicEndpointsShouldBeAccessibleWithoutLogin() throws Exception {
        mockMvc.perform(get("/products")).andExpect(status().isOk());
        mockMvc.perform(get("/products/1")).andExpect(status().isOk());
        mockMvc.perform(get("/categories")).andExpect(status().isOk());
    }
}

