package online.store.services;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;

import online.store.model.Order;
import online.store.model.Product;
import online.store.model.ProductCategory;
import online.store.repositories.OrderRepository;
import online.store.repositories.ProductCategoryRepository;
import online.store.repositories.ProductRepository;


@ExtendWith(MockitoExtension.class)
public class OrdersServiceTest {

    @Mock 
    OrderRepository orderRepository;

    @InjectMocks
    OrdersService ordersService;

    @Test
    void testPlaceOrders() {
        Iterable<Order> orders = new ArrayList<>();

        ordersService.placeOrders(orders);

        verify(orderRepository).saveAll(any());
    }
}
