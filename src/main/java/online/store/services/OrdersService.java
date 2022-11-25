package online.store.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import online.store.model.Order;
import online.store.model.request.CheckoutRequest;
import online.store.actions.PaymentAction;
import online.store.repositories.OrderRepository;

@Service
public class OrdersService {

    @Value("${products.service.max-number-of-items:25}")
    private long maxNumberOfItems;

    private final OrderRepository orderRepository;

    private final  PaymentAction paymentAction;
    
    

    public OrdersService(OrderRepository orderRepository, PaymentAction paymentAction) {
        this.orderRepository = orderRepository;
        this.paymentAction = paymentAction;
    }

    public void placeOrders(Iterable<Order> orders){
        validateNumberOfItemsOrdered(orders);
        orderRepository.saveAll(orders);
    }

    public void preparePaymentOrder(Set<Order> orders, String uuid, CheckoutRequest checkoutRequest){

        BigDecimal totalOrder = new BigDecimal(Float.toString(
            orders.stream().map(order -> order.getQuantity() * order.getProduct().getPriceUSD()).reduce(0f, (partial, total) -> partial + total)));

        paymentAction.publishToBroker(uuid, checkoutRequest.getFirstName(), checkoutRequest.getLastName(), checkoutRequest.getCreditCard(), totalOrder);

        placeOrders(orders);
    }

    public List<Order> retrieveOrderPlacedByUuId(String uuid){
        return orderRepository.findByUuid(uuid);
    }

    private void validateNumberOfItemsOrdered(Iterable<Order> orders) {
        long totalNumberOfItems = 0;
        for (Order order: orders)  {
            totalNumberOfItems += order.getQuantity();
        }
        if (totalNumberOfItems > maxNumberOfItems) {
            throw new IllegalStateException(String.format("Number of products %d exceeded the limit of %d",
                    totalNumberOfItems, maxNumberOfItems));
        }
    }
}