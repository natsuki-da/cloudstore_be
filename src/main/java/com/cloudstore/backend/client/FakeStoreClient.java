package com.cloudstore.backend.client;

import com.cloudstore.backend.model.Product;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class FakeStoreClient {
    private final ObjectMapper objectMapper;

    public FakeStoreClient() {
        this.objectMapper = new ObjectMapper();
    }

    public List<Product> fetchProducts() {
        try {
            ClassPathResource resource = new ClassPathResource("product.json");
            Product[] response = objectMapper.readValue(resource.getInputStream(), Product[].class);
                    return Arrays.asList(response);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    //FakeStore API is not working
//    private final RestTemplate restTemplate;
//
//    public FakeStoreClient(){
//        this.restTemplate = new RestTemplate();
//    }
//
//    public List<Product> fetchProducts(){
//        String url = "http://yahyatesting-env.eba-sarnymwd.eu-north-1.elasticbeanstalk.com/products";
//        Product[] response = restTemplate.getForObject(
//                url,
//                Product[].class
//        );
//        if (response != null){
//            return Arrays.asList(response);
//        } else {
//            return new ArrayList<>();
//        }
//    }

}
