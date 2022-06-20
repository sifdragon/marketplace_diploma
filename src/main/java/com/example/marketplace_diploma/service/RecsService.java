package com.example.marketplace_diploma.service;

import com.example.marketplace_diploma.model.ItemUserRating;
import com.example.marketplace_diploma.model.Product;
import com.example.marketplace_diploma.model.User;
import com.example.marketplace_diploma.repository.ItemUserRatingRepo;
import com.example.marketplace_diploma.repository.ProductRepo;
import com.example.marketplace_diploma.repository.UserRepo;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RecsService {

    @Autowired
    ItemUserRatingRepo itemUserRatingRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    ProductRepo productRepo;

    public Map<String, Map<String, Integer>> getRecs() {

        Map<String, Map<String, Integer>> outerUserAndProduct;

        List<ItemUserRating> itemUserRating = itemUserRatingRepo.getAll();

        outerUserAndProduct = itemUserRating.stream()
                .collect(Collectors.groupingBy((ItemUserRating itemUserRating1) -> itemUserRating1.getUser().getEmail(),
                        Collectors.toMap((ItemUserRating itemUserRating1) -> itemUserRating1.getProduct().getName(), ItemUserRating::getRating)));


    return outerUserAndProduct;
    }

}

//
//       {
//         'kekw@mail.ru': {'Black watch IWATCH 1000': 3, 'Apple Iphone 10': 4, 'Smart TV Samsung 200': 5,
//        'Apple Iwatch 2': 2, 'Combat rifle ak-47': 1, 'Film Titanic': 5},
//        'kekw1@mail.ru': {'Black watch IWATCH 1000': 1, 'Apple Iphone 10': 2, 'Smart TV Samsung 200': 3,
//        'Apple Iwatch 2': 4, 'Combat rifle ak-47': 5, 'Film Titanic': 1},
//        'kekw2@mail.ru': {'Black watch IWATCH 1000': 5, 'Smart TV Samsung 200': 2,
//        'Apple Iwatch 2': 3, 'Combat rifle ak-47': 4, 'Film Titanic': 5},
//        'kekw3@mail.ru': {'Black watch IWATCH 1000': 2, 'Apple Iphone 10': 4, 'Smart TV Samsung 200': 2,
//        'Apple Iwatch 2': 3, 'Film Titanic': 1},
//        'kekw4@mail.ru': {'Black watch IWATCH 1000': 1, 'Apple Iphone 10': 4, 'Smart TV Samsung 200': 2,
//        'Apple Iwatch 2': 3, 'Film Titanic': 5, 'Robot Samsung': 4, 'Toy Story Film': 3},
//        'kekw5@mail.ru': {'Apple Iphone 10': 3, 'Smart TV Samsung 200': 3,
//        'Apple Iwatch 2': 5, 'Film Titanic': 3, 'Cocktail maker 100R': 5}
//        }