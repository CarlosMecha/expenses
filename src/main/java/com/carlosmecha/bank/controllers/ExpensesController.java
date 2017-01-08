package com.carlosmecha.bank.controllers;

import com.carlosmecha.bank.models.User;
import com.carlosmecha.bank.services.BankService;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Expenses controller.
 *
 * Created by Carlos on 12/28/16.
 */
@Controller
public class ExpensesController {

    private final static Logger logger = LoggerFactory.getLogger(ExpensesController.class);

    private BankService bank;
    private DateFormat formatter;

    @Autowired
    public ExpensesController(BankService bank) {
        this.bank = bank;
        this.formatter = new SimpleDateFormat("yyyy-MM-dd");
    }

    /**
     * Shows this month report and a form to create a new expense.
     * @param model View model.
     * @param principal Authentication.
     * @return Template name.
     */
    @GetMapping("/")
    public String index(Model model, Principal principal) {
        User user = getLoggerUser(principal);

        Calendar date = Calendar.getInstance();
        date.set(Calendar.DAY_OF_MONTH, 1);
        Date startDate = date.getTime();
        date = Calendar.getInstance();
        date.set(Calendar.DAY_OF_MONTH, date.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date endDate = date.getTime();

        model.addAttribute("name", user.getName());
        model.addAttribute("report", bank.createBasicReport("Monthly", startDate, endDate));
        model.addAttribute("expense", new ExpenseForm());
        return "index";
    }

    /**
     * Shows a list of expenses.
     * @param model View model.
     * @param principal Authentication.
     * @return Template name.
     */
    @GetMapping("/latest")
    public String getLatest(Model model, Principal principal) {
        User user = getLoggerUser(principal);
        model.addAttribute("name", user.getName());
        model.addAttribute("expenses", bank.getLatestExpenses(100));
        return "latest";
    }

    /**
     * Creates the new expense a redirects to the main page.
     * @param expense Expense.
     * @param result Result of binding.
     * @param redirectAttributes Redirect attributes.
     * @param principal Authentication.
     * @return Redirection.
     */
    @PostMapping("/")
    public String create(@ModelAttribute ExpenseForm expense,
                         BindingResult result,
                         RedirectAttributes redirectAttributes,
                         Principal principal) {
        if(result.hasErrors()) {
            for(ObjectError e : result.getAllErrors()) {
                logger.debug("Error {}", e.getDefaultMessage());
            }
            redirectAttributes.addFlashAttribute("error", "Missing information!");
            return "redirect:/";
        }
        User user = getLoggerUser(principal);
        logger.debug("User {} is trying to create expense {}", user.getLoginName(), expense.getValue());

        if(expense.categoryCode == null || expense.categoryCode.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Missing information!");
            return "redirect:/";
        }

        bank.createExpense(expense.getValue(), expense.categoryCode,
                stringToDate(expense.getDate()), stringToSet(expense.getTagCodes()),
                expense.getNotes(), user);
        redirectAttributes.addFlashAttribute("message", "Expense created");

        return "redirect:/";
    }

    private User getLoggerUser(Principal principal) {
        return bank.getUser(principal.getName());
    }

    private Set<String> stringToSet(String tags) {
        Set<String> set = new HashSet<>();
        if(tags != null && !tags.isEmpty()) {
            Collections.addAll(set, tags.split(","));
        }
        return set;
    }

    private Date stringToDate(String text) {
        try {
            return formatter.parse(text);
        } catch (ParseException e) {
            // TODO: Complain
            logger.warn("Date invalid {}", text);
            return new Date();
        }
    }

    /**
     * Expense form model.
     */
    public static class ExpenseForm {

        @NotNull
        private float value;
        @NotEmpty
        private String categoryCode;
        @NotNull
        private String date;
        private String tagCodes;
        private String notes;

        public ExpenseForm() {
        }

        public float getValue() {
            return value;
        }

        public void setValue(float value) {
            this.value = value;
        }

        public String getCategoryCode() {
            return categoryCode;
        }

        public void setCategoryCode(String categoryCode) {
            this.categoryCode = categoryCode;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTagCodes() {
            return tagCodes;
        }

        public void setTagCodes(String tagCodes) {
            this.tagCodes = tagCodes;
        }

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }

        @Override
        public String toString() {
            return "ExpenseForm{" +
                    "value=" + value +
                    ", categoryCode='" + categoryCode + '\'' +
                    ", date=" + date +
                    ", tagCodes=" + tagCodes +
                    ", notes='" + notes + '\'' +
                    '}';
        }
    }

}
