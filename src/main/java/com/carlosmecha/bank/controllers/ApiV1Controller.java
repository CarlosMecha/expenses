package com.carlosmecha.bank.controllers;

import com.carlosmecha.bank.models.Category;
import com.carlosmecha.bank.models.Tag;
import com.carlosmecha.bank.services.BankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Api controller.
 *
 * Created by Carlos on 12/28/16.
 */
@RestController
@RequestMapping("/api/v1")
public class ApiV1Controller {

    private final static Logger logger = LoggerFactory.getLogger(ApiV1Controller.class);

    private BankService bank;

    @Autowired
    public ApiV1Controller(BankService bank) {
        this.bank = bank;
    }

    /**
     * Retrieves all stored categories.
     * @return List of categories.
     */
    @RequestMapping("/categories")
    public Iterable<Category> getCategories() {
        return bank.getCategories();
    }

    /**
     * Retrieves all stored tags.
     * @return List of tags.
     */
    @RequestMapping("/tags")
    public Iterable<Tag> getTags() {
        return bank.getTags();
    }

}
