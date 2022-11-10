package online.store.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import online.store.exceptions.CreditCardValidationException;
import online.store.model.ProductInfo;
import online.store.model.request.CheckoutRequest;
import online.store.services.CreditCardValidationService;
import online.store.services.OrdersService;
import online.store.services.ProductsService;

@WebMvcTest(controllers = CheckoutController.class)
public class CheckoutControlletTest {
    
    @MockBean
    ProductsService productsService;

    @MockBean
    OrdersService ordersService;

    @MockBean
    CreditCardValidationService creditCardValidationService;

    @Autowired
    MockMvc mockMvc;

    List<online.store.model.ProductInfo> products;

    @BeforeEach
    void setUp() {
        products = new ArrayList<>();
        ProductInfo productInfo1 = new ProductInfo();
        productInfo1.setProductId(1);
        productInfo1.setQuantity(3);
        ProductInfo productInfo2 = new ProductInfo();
        productInfo2.setProductId(4);
        productInfo2.setQuantity(1);

        products.add(productInfo1);
        products.add(productInfo2);
    }

    @AfterEach
    void tearDown() {
        reset(productsService);
    }

    @DisplayName("Test - Creating an Order")
    @Test
    void testCheckout() throws Exception{
        CheckoutRequest checkoutRequest1 = new CheckoutRequest();
        checkoutRequest1.setFirstName("Name 1");
        checkoutRequest1.setLastName("LastName 1");
        checkoutRequest1.setCreditCard("1234567891011121");
        checkoutRequest1.setEmail("correo@mail.com");
        checkoutRequest1.setShippingAddress("Waven 149 St");
        checkoutRequest1.setProducts(products);

        
        mockMvc.perform(post("/checkout").content(asJsonString(checkoutRequest1)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("success"));

    }

    @Test
    void testCheckoutWithoutFirstName() throws Exception{
        CheckoutRequest checkoutRequest2 = new CheckoutRequest();
        checkoutRequest2.setLastName("LastName 2");
        checkoutRequest2.setCreditCard("1234567891011121");
        checkoutRequest2.setEmail("correo@mail.com");
        checkoutRequest2.setShippingAddress("Waven 149 St");
        checkoutRequest2.setProducts(products);

        
        mockMvc.perform(post("/checkout").content(asJsonString(checkoutRequest2)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("First name is missing"));
    }

    @Test
    void testCheckoutWithoutLastName() throws Exception{
        CheckoutRequest checkoutRequest3 = new CheckoutRequest();
        checkoutRequest3.setFirstName("Name 3");
        checkoutRequest3.setCreditCard("1234567891011121");
        checkoutRequest3.setEmail("correo@mail.com");
        checkoutRequest3.setShippingAddress("Waven 149 St");
        checkoutRequest3.setProducts(products);

        
        mockMvc.perform(post("/checkout").content(asJsonString(checkoutRequest3)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("Last name is missing"));
    }

    @Test
    void testCheckoutWithoutCreditCard() throws Exception{
        CheckoutRequest checkoutRequest4 = new CheckoutRequest();
        checkoutRequest4.setFirstName("Name 4");
        checkoutRequest4.setLastName("LastName 4");
        checkoutRequest4.setEmail("correo@mail.com");
        checkoutRequest4.setShippingAddress("Waven 149 St");
        checkoutRequest4.setProducts(products);

        
        mockMvc.perform(post("/checkout").content(asJsonString(checkoutRequest4)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isPaymentRequired())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("Credit card information is missing"));
    }

    @Test
    void testCheckoutWithStolenOrInvalidCreditCard() throws Exception{
        CheckoutRequest checkoutRequest5 = new CheckoutRequest();
        checkoutRequest5.setFirstName("Name 5");
        checkoutRequest5.setLastName("LastName 5");
        checkoutRequest5.setCreditCard("1111111111111111");
        checkoutRequest5.setEmail("correo@mail.com");
        checkoutRequest5.setShippingAddress("Waven 149 St");
        checkoutRequest5.setProducts(products);

        willThrow(new CreditCardValidationException("is a stolen/invalid credit card")).given(creditCardValidationService).validate(anyString());

        
        mockMvc.perform(post("/checkout").content(asJsonString(checkoutRequest5)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CreditCardValidationException))
                .andExpect(result -> assertEquals("is a stolen/invalid credit card", result.getResolvedException().getMessage()));
    }

    private static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }  
}
