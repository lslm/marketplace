package dev.lslm.marketplace.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldCreateANewProduct() throws Exception {
        String requestBody = "{\n" +
                "    \"description\": \"Banana\",\n" +
                "    \"price\": 4.99\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/marketplace/products")
                .content(requestBody)
                .contentType("application/json")).andExpect(status().isCreated());
    }

    @Test
    public void shouldRetrieveById() throws Exception {
        String requestBody = "{\n" +
                "    \"description\": \"Banana\",\n" +
                "    \"price\": 4.99\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/marketplace/products")
                .content(requestBody)
                .contentType("application/json")).andExpect(status().isCreated());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/marketplace/products/1")
                .contentType("application/json")).andReturn();

        String expectedResponse = "{" +
                "\"id\":1," +
                "\"description\":\"Banana\"," +
                "\"price\":4.99" +
                "}";

        assertEquals(expectedResponse, result.getResponse().getContentAsString());
    }
}
