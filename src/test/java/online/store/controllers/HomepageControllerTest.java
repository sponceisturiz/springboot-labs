package online.store.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import online.store.model.Product;
import online.store.services.ProductsService;



import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = HomepageController.class)
public class HomepageControllerTest {

    @MockBean
    ProductsService productsService;

    @Autowired
    MockMvc mockMvc;

    List<Product> products;
    List<String> categories;

    @BeforeEach
    void setUp() {
        products = new ArrayList<>();
        products.add(new Product("Product 1", "This is a Product 1", null, 10, "toys"));
        products.add(new Product("Product 2", "This is a Product 2", null, 20, "toys"));
        products.add(new Product("Product 3", "This is a Product 3", null, 30, "toys"));

        categories = new ArrayList<>();
        categories.add("category 1");
        categories.add("category 2");
    }

    @AfterEach
    void tearDown() {
        reset(productsService);
    }
   
    @DisplayName("Test - List of Product with Deals of the day")
    @Test
    void testGetDealsOfTheDayy() throws Exception{
        given(productsService.getDealsOfTheDay(anyInt())).willReturn(products);

        mockMvc.perform(get("/deals_of_the_day/"+anyInt()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.products", hasSize(3)));
    }

    @DisplayName("Test - List of Categories in String")
    @Test
    void testGetProductCatefories() throws Exception{
        given(productsService.getAllSupportedCategories()).willReturn(categories);

        mockMvc.perform(get("/categories"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("text/plain;charset=UTF-8"));
    }

    @DisplayName("Test - get category by parameter")
    @Test
    void testGetProductsForCategoryWithParameter()throws Exception{
        given(productsService.getProductsByCategory(anyString())).willReturn(products);

        mockMvc.perform(get("/products").param("category", "categoria"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.products", hasSize(3)));
    }

    @DisplayName("Test - get category without parameter")
    @Test
    void testGetProductsForCategoryNoParameter() throws Exception{
        given(productsService.getAllProducts()).willReturn(products);

        mockMvc.perform(get("/products"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.products", hasSize(3)));
    }
}
