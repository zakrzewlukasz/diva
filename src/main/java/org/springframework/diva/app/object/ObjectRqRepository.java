package org.springframework.diva.app.object;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.diva.app.model.ObjectRq;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface ObjectRqRepository extends Repository<ObjectRq, Integer> {

	/**
	 * Retrieve {@link Owner}s from the data store by last name, returning all owners
	 * whose last name <i>starts</i> with the given name.
	 *
	 * @param lastName Value to search for
	 * @return a Collection of matching {@link Owner}s (or an empty Collection if none
	 * found)
	 */
/*
	@Query("SELECT DISTINCT objectRq FROM ObjectRq objectRq left join fetch objectRq WHERE objectRq.nameObj LIKE :nameObj%")
	@Transactional(readOnly = true)
	Collection<ObjectRq> findByLastName(@Param("nameObj") String nameObj);
*/

	/**
	 * Retrieve an {@link ObjectRq} from the data store by id.
	 *
	 * @param id the id to search for
	 * @return the {@link ObjectRq} if found
	 */
	@Query("SELECT objectRq FROM ObjectRq objectRq WHERE objectRq.id =:id")
	@Transactional(readOnly = true)
	ObjectRq findById(@Param("id") Integer id);

	/**
	 * Save an {@link ObjectRq} to the data store, either inserting or updating it.
	 *
	 * @param objectRq the {@link ObjectRq} to save
	 */

	 void save(ObjectRq objectRq);


}
