package ru.mooncess.pizzeriacoursepaper.repositories.snack;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mooncess.pizzeriacoursepaper.entities.Snack;

@Repository
public interface SnackRepository extends JpaRepository<Snack, Long>, SortSnackByPrice {
}
