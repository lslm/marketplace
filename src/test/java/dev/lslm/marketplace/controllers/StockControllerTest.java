package dev.lslm.marketplace.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class StockControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldCreateANewStock() throws Exception {
        String productRequestBody = "{\n" +
                "    \"description\": \"Banana\",\n" +
                "    \"price\": 4.99\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/marketplace/products")
                .content(productRequestBody)
                .contentType("application/json"));

        String requestBody = "{\n" +
                "    \"product\": {\n" +
                "        \"id\": 1\n" +
                "    },\n" +
                "    \"quantity\": 0\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/marketplace/stock")
                .content(requestBody)
                .contentType("application/json")).andExpect(status().isCreated());
    }

    @Test
    public void shouldIncreaseStock() throws Exception {
        // create product
        String productRequestBody = "{\n" +
                "    \"description\": \"Banana\",\n" +
                "    \"price\": 4.99\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/marketplace/products")
                .content(productRequestBody)
                .contentType("application/json"));

        // create stock with 0 quantity
        String createStockRequestBody = "{\n" +
                "    \"product\": {\n" +
                "        \"id\": 1\n" +
                "    },\n" +
                "    \"quantity\": 0\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/marketplace/stock")
                .content(createStockRequestBody)
                .contentType("application/json"));

        // increase stock
        String increaseRequestBody = "{\n" +
                "    \"quantity\": 1\n" +
                "}";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .patch("/api/marketplace/stock/1/increase")
                .content(increaseRequestBody)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        String expectedResponse = "{" +
                "\"id\":1," +
                "\"product\":{" +
                "\"id\":1," +
                "\"description\":\"Banana\"," +
                "\"price\":4.99" +
                "}," +
                "\"quantity\":1" +
                "}";

        assertEquals(expectedResponse, result.getResponse().getContentAsString());
    }

    @Test
    public void cannotCreateDuplicatedStocks() throws Exception {
        String productRequestBody = "{\n" +
                "    \"description\": \"Banana\",\n" +
                "    \"price\": 4.99\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/marketplace/products")
                .content(productRequestBody)
                .contentType("application/json"));

        String requestBody = "{\n" +
                "    \"product\": {\n" +
                "        \"id\": 1\n" +
                "    },\n" +
                "    \"quantity\": 0\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/marketplace/stock")
                .content(requestBody)
                .contentType("application/json"));

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/marketplace/stock")
                .content(requestBody)
                .contentType("application/json")).andExpect(status().isUnprocessableEntity());
    }
}
