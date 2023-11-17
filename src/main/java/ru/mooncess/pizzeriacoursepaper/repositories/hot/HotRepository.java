package ru.mooncess.pizzeriacoursepaper.repositories.hot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mooncess.pizzeriacoursepaper.entities.Hot;

@Repository
public interface HotRepository extends JpaRepository<Hot, Long>, SortHotByPrice {
}
