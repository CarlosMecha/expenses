package com.carlosmecha.bank.repositories;

import com.carlosmecha.bank.models.Tag;
import org.springframework.data.repository.CrudRepository;

/**
 * Tag repository.
 *
 * Created by Carlos on 12/25/16.
 */
public interface TagRepository extends CrudRepository<Tag, String> {
}
