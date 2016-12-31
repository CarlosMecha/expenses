package com.carlosmecha.bank.controllers;

import com.carlosmecha.bank.models.User;
import com.carlosmecha.bank.services.BankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

/**
 * Categories controller.
 * Thymeleaf and @RequestMapping don't work at class level.
 *
 * Created by Carlos on 12/29/16.
 */
@Controller
public class CategoriesController {

    private final static Logger logger = LoggerFactory.getLogger(CategoriesController.class);

    private BankService bank;

    @Autowired
    public CategoriesController(BankService bank) {
        this.bank = bank;
    }

    /**
     * Shows all categories and a form to create a new one.
     * @param model View model.
     * @param principal Authentication.
     * @return Template name.
     */
    @GetMapping("/categories")
    public String index(Model model, Principal principal) {
        User user = getLoggerUser(principal);
        model.addAttribute("name", user.getName());
        model.addAttribute("categories", bank.getCategories());
        model.addAttribute("category", new CategoryForm());
        return "categories";
    }

    /**
     * Create a new category and redirects to the main page.
     * @param category Category form.
     * @param redirectAttributes Redirect attributes.
     * @param principal Authentication.
     * @return Redirection.
     */
    @PostMapping("/categories")
    public String create(@ModelAttribute CategoryForm category,
                         RedirectAttributes redirectAttributes,
                         Principal principal) {
        User user = getLoggerUser(principal);
        logger.debug("User {} is trying to create category {}", user.getLoginName(), category.getName());

        if(category.getName() == null || category.getName().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "At least the name must be something!");
            return "redirect:/categories";
        }

        bank.createCategory(category.getCode(), category.getName());
        redirectAttributes.addFlashAttribute("message", "Category " + category.getName() + " created.");

        return "redirect:/categories";
    }

    private User getLoggerUser(Principal principal) {
        return bank.getUser(principal.getName());
    }

    /**
     * Category form model.
     */
    public static class CategoryForm {

        private String code;
        private String name;

        public CategoryForm() {
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


}
