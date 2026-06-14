package com.cloudstore.backend.controller;

import com.cloudstore.backend.client.FakeStoreClient;
import com.cloudstore.backend.model.Product;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authorization.method.AuthorizeReturnObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
//just for now, will delete this line and create an official CORS-file
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {
    private final FakeStoreClient fakeStoreClient;

    public ProductController(FakeStoreClient fakeStoreClient){
        this.fakeStoreClient = fakeStoreClient;
    }

    //...no need to mention as it's written in SecurityConfig
    //@PreAuthorize("permitAll()")
    @GetMapping
    public List<Product> getProducts(){
        return fakeStoreClient.fetchProducts();
    }
}
