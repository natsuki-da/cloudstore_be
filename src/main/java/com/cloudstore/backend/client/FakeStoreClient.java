package com.cloudstore.backend.client;

import com.cloudstore.backend.model.Product;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class FakeStoreClient {
    private final RestTemplate restTemplate;

    public FakeStoreClient(){
        this.restTemplate = new RestTemplate();
    }

    public List<Product> fetchProducts(){
        String url = "https://fakestoreapi.com/products";
        Product[] response = restTemplate.getForObject(
                url,
                Product[].class
        );
        if (response != null){
            return Arrays.asList(response);
        } else {
            return new ArrayList<>();
        }
    }

}
