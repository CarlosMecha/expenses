package com.carlosmecha.bank.repositories;

import com.carlosmecha.bank.models.Expense;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;

/**
 * Created by Carlos on 12/25/16.
 */
public interface ExpenseRepository extends PagingAndSortingRepository<Expense, Long> {

    /**
     * Finds all expenses by date range.
     * @param startDate Start date.
     * @param endDate End date.
     * @return An iterable.
     */
    @Query("SELECT e FROM Expense e WHERE e.date >= :startDate AND e.date <= :endDate ORDER BY e.date")
    Iterable<Expense> findAllByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
