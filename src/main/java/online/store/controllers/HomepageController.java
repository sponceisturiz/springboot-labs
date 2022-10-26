package online.store.controllers;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import online.store.model.wrappers.ProductWrapper;
import online.store.services.ProductsService;

@RestController
public class HomepageController {

    @Autowired
    private ProductsService productsService;
    
    @GetMapping("/categories")
    public String getProductCatefories(){
        return productsService.getAllSupportedCategories().stream().collect(Collectors.joining(","));
    }

    @GetMapping("/deals_of_the_day/{number_of_products}")
    public ProductWrapper getDealsOfTheDayy(@PathVariable(name="number_of_products") int numberOfProducts){
        return new ProductWrapper(productsService.getDealsOfTheDay(numberOfProducts));
    }

    @GetMapping("/products")
    public ProductWrapper getProductsForCategory(@RequestParam(name="category", required=false) String category){
        if(category != null && !category.isEmpty()){
            return new ProductWrapper(productsService.getProductsByCategory(category));    
        }else{
            return new ProductWrapper(productsService.getAllProducts());
        }
    }
}
