package online.store.model.wrappers;

import java.util.Collections;
import java.util.List;

import online.store.model.Product;

public class ProductWrapper {

    private List<Product> products = Collections.EMPTY_LIST;

    public ProductWrapper(List<Product> products) {
        this.products = Collections.unmodifiableList(products);
    }

    public List<Product> getProducts() {
        return products;
    }
}
