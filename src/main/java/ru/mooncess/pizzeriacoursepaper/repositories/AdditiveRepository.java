package ru.mooncess.pizzeriacoursepaper.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mooncess.pizzeriacoursepaper.entities.Additive;

import java.util.List;

@Repository
public interface AdditiveRepository extends JpaRepository<Additive, Long> {
}
