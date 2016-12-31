package com.carlosmecha.bank.models;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Category model.
 *
 * Created by Carlos on 12/25/16.
 */
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @NotEmpty
    private String code;
    @NotEmpty
    private String name;
    private Date createdOn;

    public Category() {
    }

    public Category(String name) {
        this(nameToCode(name), name);
    }

    public Category(String code, String name) {
        this(code, name, new Date());
    }

    public Category(String code, String name, Date createdOn) {
        this();
        this.code = code;
        this.name = name;
        this.createdOn = createdOn;
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

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public String toString() {
        return String.format("Category %s: %s", code, name);
    }

    private static String nameToCode(String name) {
        String normalized = name.toLowerCase();
        return normalized.replaceAll("(\\s|\\.|_|-)", "");
    }
}
