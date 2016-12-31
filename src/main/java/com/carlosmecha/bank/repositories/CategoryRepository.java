package com.carlosmecha.bank.repositories;

import com.carlosmecha.bank.models.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Category repository.
 *
 * Created by Carlos on 12/25/16.
 */
public interface CategoryRepository extends PagingAndSortingRepository<Category, String> {
}
