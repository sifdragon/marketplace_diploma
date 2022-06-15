package com.example.marketplace_diploma.controller;

import com.example.marketplace_diploma.dto.ProductDto;
import com.example.marketplace_diploma.model.Cart;
import com.example.marketplace_diploma.model.Product;
import com.example.marketplace_diploma.model.User;
import com.example.marketplace_diploma.repository.CartRepo;
import com.example.marketplace_diploma.repository.ProductRepo;
import com.example.marketplace_diploma.service.AuthenticationService;
import com.example.marketplace_diploma.service.ProductService;
import com.example.marketplace_diploma.service.RecsService;
import com.example.marketplace_diploma.service.StringService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

@RestController
@RequestMapping("/recs")
public class RecsController {

    @Autowired
    RecsService recsService;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    StringService stringService;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    ProductService productService;

    @Autowired
    CartRepo cartRepo;


    @GetMapping("/get")
    public List<ProductDto> getRecs(@RequestParam("token") String token) throws IOException {
        authenticationService.authenticateToken(token);

        User user = authenticationService.getUserFromToken(token);
        final String uri = "http://127.0.0.1:8000/recs";
        Map<String, Map<String, Integer>> map = recsService.getRecs();


        Gson gson = new Gson();
        StringEntity postString = new StringEntity(gson.toJson(map));
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(uri);
        post.setEntity(postString);
        post.setHeader("Content-type", "application/json");
        post.setHeader("user", user.getEmail());
        HttpResponse response = httpClient.execute(post);
        HttpEntity responseJsonEntity = response.getEntity();
        String responseString = EntityUtils.toString(responseJsonEntity, "UTF-8");
        List<String> result = stringService.parseResponse(responseString);
        List<ProductDto> recProducts = new ArrayList<>();
        for (String name : result) {
            Product productIn = productRepo.getProductByName(name);
            ProductDto productDtoIn = productService.transformProductToDto(productIn);
            recProducts.add(productDtoIn);
        }

        return recProducts;
    }

    @GetMapping("/getCartRecs")
    public List<ProductDto> getCartRecs(@RequestParam("token") String token) throws IOException {
        authenticationService.authenticateToken(token);

        User user = authenticationService.getUserFromToken(token);

        final String uri = "http://127.0.0.1:8000/cartRecs";
        Map<String, Map<String, Integer>> map = recsService.getRecs();

        Gson gson = new Gson();
        StringEntity postString = new StringEntity(gson.toJson(map));
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(uri);
        post.setEntity(postString);
        post.setHeader("Content-type", "application/json");
        HttpResponse response = httpClient.execute(post);
        HttpEntity responseJsonEntity = response.getEntity();
        String responseString = EntityUtils.toString(responseJsonEntity, "UTF-8");
        Map<String, ArrayList<ArrayList<Object>>> result =
                new ObjectMapper().readValue(responseString, HashMap.class);

        List<Cart> cartItems = cartRepo.findAllByUserOrderByCreatedDateDesc(user);
        List<String> productsToRec = new ArrayList<>();
        for (Cart cartItem : cartItems) {
            productsToRec.add(cartItem.getProduct().getName());
        }
        result.keySet().retainAll(productsToRec);

        Map<String, Double> result2 = new HashMap<>();
        for (ArrayList<ArrayList<Object>> outerList : result.values()) {
            for (ArrayList<Object> innerList : outerList) {
                result2.put(
                        (String) innerList.get(1),
                        result2.getOrDefault((String) innerList.get(1), (Double) innerList.get(0))
                                + (Double) innerList.get(0));

            }
        }

        List<ProductDto> productDtoList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Map.Entry<String, Double> maxEntry = null;
            for (Map.Entry<String, Double> entry : result2.entrySet()) {
                if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
                    maxEntry = entry;
                }
            }
            String maxKey = maxEntry.getKey();
            Product productIn = productRepo.getProductByName(maxKey);
            ProductDto productDtoIn = productService.transformProductToDto(productIn);
            productDtoList.add(productDtoIn);
            result2.remove(maxKey);

        }

        return productDtoList;
    }
}
