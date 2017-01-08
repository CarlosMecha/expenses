package com.carlosmecha.bank.services;

import com.carlosmecha.bank.models.*;
import com.carlosmecha.bank.repositories.CategoryRepository;
import com.carlosmecha.bank.repositories.ExpenseRepository;
import com.carlosmecha.bank.repositories.TagRepository;
import com.carlosmecha.bank.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * Bank service.
 *
 * Created by Carlos on 12/28/16.
 */
@Service
public class BankService {

    private final static Logger logger = LoggerFactory.getLogger(BankService.class);

    private CategoryRepository categories;
    private ExpenseRepository expenses;
    private TagRepository tags;
    private UserRepository users;

    @Autowired
    public BankService(CategoryRepository categories, ExpenseRepository expenses, TagRepository tags, UserRepository
            users) {
        this.categories = categories;
        this.expenses = expenses;
        this.tags = tags;
        this.users = users;
    }

    /**
     * Creates a category using just the name.
     * @param name Category name.
     * @return New category.
     */
    public Category createCategory(String name) {
        return createCategory(null, name);
    }

    /**
     * Creates the category.
     * @param code Category code.
     * @param name Category name.
     * @return New category.
     */
    @Transactional
    public Category createCategory(String code, String name) {
        logger.debug("Creating category {}", name);
        Category category = (code == null || code.isEmpty()) ? new Category(name) : new Category(code, name);
        categories.save(category);
        return category;
    }

    /**
     * Gets all categories.
     * @return List of categories.
     */
    public Iterable<Category> getCategories() {
        logger.debug("Looking for all categories.");
        return categories.findAll(new PageRequest(0, 1000, Sort.Direction.ASC, "code"));
    }

    /**
     * Gets all tags.
     * @return List of tags.
     */
    public Iterable<Tag> getTags() {
        logger.debug("Looking for all tags.");
        return tags.findAll();
    }

    /**
     * Retrieves an user by login name.
     * @param loginName User login name.
     * @return User if found, <code>null</code> otherwise.
     */
    public User getUser(String loginName) {
        logger.debug("Looking for user with name {}", loginName);
        return users.findOne(loginName);
    }

    /**
     * Gets a limited list of expenses ordered by date and creation date.
     * @param size Size of the list.
     * @return Expenses.
     */
    public Iterable<Expense> getLatestExpenses(int size) {
        return expenses.findAll(new PageRequest(0, size, Sort.Direction.DESC, "date", "createdOn"));
    }

    /**
     * Creates a new expense.
     * @param value Value.
     * @param categoryCode Category code.
     * @param date Date.
     * @param tagCodes A list of tags.
     * @param note Notes (optional).
     * @param requester The user who request the creation.
     * @return The new expense.
     */
    @Transactional
    public Expense createExpense(float value, String categoryCode, Date date, Set<String> tagCodes, String note, User requester) {
        logger.debug("Creating expense of {}", value);
        Category category = categories.findOne(categoryCode);
        if(category == null) {
            // TODO: Complain
            category = new Category(categoryCode, categoryCode);
        }
        Set<Tag> tagSet = new HashSet<>();
        for(String tagCode : tagCodes) {
            Tag tag = tags.findOne(tagCode);
            if(tag == null) {
                tag = new Tag(tagCode);
            }
            tagSet.add(tag);
        }

        Expense expense = new Expense(category, value, date, tagSet, note, requester);
        expenses.save(expense);
        return expense;
    }

    /**
     * Creates a basic report with all expenses between two dates.
     * @param title Report title.
     * @param startDate Start date.
     * @param endDate End date.
     * @return Report.
     */
    public Report createBasicReport(String title, Date startDate, Date endDate) {
        return new Report(title, startDate, endDate, expenses.findAllByDateRange(startDate, endDate));
    }

    /**
     * Creates a tag.
     * @param code Tag code.
     * @return The new tag.
     */
    @Transactional
    public Tag createTag(String code) {
        logger.debug("Creating tag {}", code);
        Tag tag = new Tag(code);
        tags.save(tag);
        return tag;
    }

}
