    package com.devsuperior.dscommerce;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DscommerceApiIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void publicEndpointsShouldBeAccessibleWithoutLogin() throws Exception {
        mockMvc.perform(get("/products")).andExpect(status().isOk());
        mockMvc.perform(get("/products/1")).andExpect(status().isOk());
        mockMvc.perform(get("/categories")).andExpect(status().isOk());
    }

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
    void productWriteShouldRequireAdminRole() throws Exception {
        String clientToken = getToken("maria@gmail.com", "123456");
        String adminToken = getToken("alex@gmail.com", "123456");

        String body = "{\"name\":\"Notebook X\",\"description\":\"Descricao valida para teste\",\"price\":2000.0,\"imgUrl\":\"http://img\"}";

        mockMvc.perform(post("/products")
                        .header("Authorization", "Bearer " + clientToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isForbidden());

        mockMvc.perform(post("/products")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated());
    }

    @Test
    void usersMeShouldReturnAuthenticatedUser() throws Exception {
        String token = getToken("maria@gmail.com", "123456");

        mockMvc.perform(get("/users/me")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("maria@gmail.com"));
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

    private String getToken(String username, String password) throws Exception {
        MvcResult result = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}"))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode node = objectMapper.readTree(result.getResponse().getContentAsString());
        return node.get("access_token").asText();
    }
}

