package online.store.broker.message;

import java.util.List;

import online.store.model.Order;

public class OrderMessage {

    private List<Order> orderList;

    public OrderMessage() {
    }

    public OrderMessage(List<Order> orderList) {
        this.orderList = orderList;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    @Override
    public String toString() {
        return "OrderMessage [orderList=" + orderList + "]";
    }
   
    
}
