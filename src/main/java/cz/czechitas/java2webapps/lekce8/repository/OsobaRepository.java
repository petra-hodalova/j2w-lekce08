package cz.czechitas.java2webapps.lekce8.repository;

import cz.czechitas.java2webapps.lekce8.entity.Osoba;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository pro přístup k databázové tabulce {@code osoba}.
 *
 * Repository automaticky implementuje metody pro operace Create, Read, Update, Delete.
 */
@Repository
public interface OsobaRepository extends CrudRepository<Osoba, Long> {
}
