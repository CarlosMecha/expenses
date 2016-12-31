package com.carlosmecha.bank.repositories;

import com.carlosmecha.bank.models.Expense;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Carlos on 12/25/16.
 */
public interface ExpenseRepository extends PagingAndSortingRepository<Expense, Long> {
}
