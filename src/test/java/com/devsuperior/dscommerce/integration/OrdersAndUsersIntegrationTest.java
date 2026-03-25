package com.devsuperior.dscommerce.integration;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrdersAndUsersIntegrationTest extends AbstractIntegrationTest {

    @Test
    void usersMeShouldReturnAuthenticatedUser() throws Exception {
        String token = getToken("maria@gmail.com", "123456");

        mockMvc.perform(get("/users/me")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("maria@gmail.com"));
    }

    @Test
    void usersMeShouldRequireAuthentication() throws Exception {
        mockMvc.perform(get("/users/me"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void orderAccessShouldRespectOwnerOrAdminRule() throws Exception {
        String mariaToken = getToken("maria@gmail.com", "123456");
        String alexToken = getToken("alex@gmail.com", "123456");

        mockMvc.perform(get("/orders/2")
                        .header("Authorization", "Bearer " + mariaToken))
                .andExpect(status().isForbidden());

        mockMvc.perform(get("/orders/2")
                        .header("Authorization", "Bearer " + alexToken))
                .andExpect(status().isOk());
    }

    @Test
    void createOrderShouldWorkForAuthenticatedClient() throws Exception {
        String token = getToken("bob@gmail.com", "123456");
        String body = "{\"items\":[{\"productId\":1,\"quantity\":2},{\"productId\":3,\"quantity\":1}]}";

        mockMvc.perform(post("/orders")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.items").isArray());
    }
}

