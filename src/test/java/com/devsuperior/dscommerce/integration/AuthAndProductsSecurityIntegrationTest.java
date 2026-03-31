package com.devsuperior.dscommerce.integration;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthAndProductsSecurityIntegrationTest extends AbstractIntegrationTest {

    @Test
    void loginShouldReturnAccessToken() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"alex@gmail.com\",\"password\":\"123456\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").isNotEmpty())
                .andExpect(jsonPath("$.token_type").value("Bearer"));
    }

    @Test
    void oauthTokenShouldReturnAccessTokenWithFormUrlEncoded() throws Exception {
        mockMvc.perform(post("/oauth2/token")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "alex@gmail.com")
                        .param("password", "123456")
                        .param("grant_type", "password"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").isNotEmpty())
                .andExpect(jsonPath("$.token_type").value("Bearer"));
    }

    @Test
    void productsWriteShouldRejectClientRole() throws Exception {
        String clientToken = getToken("maria@gmail.com", "123456");
        String body = "{\"name\":\"Notebook Teste\",\"description\":\"Descricao valida para teste\",\"price\":2000.0,\"imgUrl\":\"http://img\"}";

        mockMvc.perform(post("/products")
                        .header("Authorization", "Bearer " + clientToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isForbidden());

        mockMvc.perform(put("/products/1")
                        .header("Authorization", "Bearer " + clientToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isForbidden());

        mockMvc.perform(delete("/products/1")
                        .header("Authorization", "Bearer " + clientToken))
                .andExpect(status().isForbidden());
    }

    @Test
    void productsWriteShouldAllowAdminRole() throws Exception {
        String adminToken = getToken("alex@gmail.com", "123456");
        String postBody = "{\"name\":\"Produto Admin\",\"description\":\"Descricao valida para admin\",\"price\":1500.0,\"imgUrl\":\"http://img\"}";

        MvcResult createResult = mockMvc.perform(post("/products")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postBody))
                .andExpect(status().isCreated())
                .andReturn();

        JsonNode created = objectMapper.readTree(createResult.getResponse().getContentAsString());
        Long productId = created.get("id").asLong();

        String putBody = "{\"name\":\"Produto Admin Atualizado\",\"description\":\"Descricao valida para update\",\"price\":1700.0,\"imgUrl\":\"http://img2\"}";

        mockMvc.perform(put("/products/" + productId)
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(putBody))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/products/" + productId)
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isNoContent());
    }
}

