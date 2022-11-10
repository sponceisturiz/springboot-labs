package online.store.services;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import online.store.model.Product;
import online.store.model.ProductCategory;
import online.store.repositories.ProductCategoryRepository;
import online.store.repositories.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductsServiceTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    ProductCategoryRepository productCategoryRepository;

    @InjectMocks
    ProductsService productsService;

    List<Product> products;

    List<ProductCategory> categories;

    Product product;

    @BeforeEach
    void setUp() {
        product = new Product("Product 1", "This is a Product 1", null, 10, "toys");
        products = new ArrayList<>();
        products.add(product);
        products.add(new Product("Product 2", "This is a Product 2", null, 20, "toys"));
        products.add(new Product("Product 3", "This is a Product 3", null, 30, "toys"));

        categories = new ArrayList<>();
        categories.add(new ProductCategory("category 1"));
        categories.add(new ProductCategory("category 2"));
    }

    @Test
    void testGetAllProducts() {

        when(productRepository.findAll()).thenReturn(products);

        List<Product> foundList = productsService.getAllProducts();

        verify(productRepository).findAll();

        assertThat(foundList).hasSize(3);
        assertThat(foundList).element(1).isExactlyInstanceOf(Product.class);
        assertThat(foundList).element(2).extracting("name").isEqualTo("Product 3");
    }

    @Test
    void testGetAllSupportedCategories() {
        when(productCategoryRepository.findAll()).thenReturn(categories);

        List<String> foundList = productsService.getAllSupportedCategories();

        verify(productCategoryRepository).findAll();

        assertThat(foundList).hasSize(2);
        assertThat(foundList).element(1).isExactlyInstanceOf(String.class);
    }

    @Test
    void testGetDealsOfTheDay() {
        when(productRepository.findAtMostNumberOfProducts(anyInt())).thenReturn(products);

        List<Product> foundList = productsService.getDealsOfTheDay(2);

        verify(productRepository).findAtMostNumberOfProducts(anyInt());

        assertThat(foundList).isNotEmpty();
        assertThat(foundList).hasSize(3);
        assertThat(foundList).element(0).isExactlyInstanceOf(Product.class);
        assertThat(foundList).element(1).extracting("name").isEqualTo("Product 2");
    }

    @Test
    void testGetProductById() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        Product foundProduct = productsService.getProductById(1L);

        verify(productRepository).findById(anyLong());

        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct).extracting("name").isEqualTo("Product 1");
        assertThat(foundProduct).isExactlyInstanceOf(Product.class);
    }

    @Test
    void testGetProductsByCategory() {
        when(productRepository.findByCategory(anyString())).thenReturn(products);

        List<Product> foundProducts = productsService.getProductsByCategory("categoria");

        verify(productRepository).findByCategory(anyString());

        assertThat(foundProducts).isNotEmpty();
        assertThat(foundProducts).element(0).extracting("name").isEqualTo("Product 1");
        assertThat(foundProducts).element(0).isExactlyInstanceOf(Product.class);
    }
}
