package com.example.marketplace_diploma.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class StringService {

    public List<String> parseResponse(String response) {
        List<String> result = new ArrayList<>();

        String replacedResponse = response.replaceAll("\\[", "");
        String replacedResponse2 = replacedResponse.replaceAll("]", "");
        String replacedResponse3 = replacedResponse2.replaceAll("\"", "");

        String[] tokens = replacedResponse3.split(",");

        result.addAll(Arrays.asList(tokens));
        return result;
    }
}
