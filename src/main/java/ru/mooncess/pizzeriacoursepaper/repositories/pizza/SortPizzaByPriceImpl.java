package ru.mooncess.pizzeriacoursepaper.repositories.pizza;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.mooncess.pizzeriacoursepaper.entities.Pizza;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@AllArgsConstructor
public class SortPizzaByPriceImpl implements SortPizzaByPrice {
    private final EntityManager em;

    @Override
    public List<Pizza> findByOrderByPriceAsc() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Pizza> queryCriteriaQuery = builder.createQuery(Pizza.class);
        Root<Pizza> root = queryCriteriaQuery.from(Pizza.class);
        queryCriteriaQuery.select(root).orderBy(builder.asc(root.get("price")));
        return em.createQuery(queryCriteriaQuery).getResultList();
    }

    @Override
    public List<Pizza> findByOrderByPriceDesc() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Pizza> queryCriteriaQuery = builder.createQuery(Pizza.class);
        Root<Pizza> root = queryCriteriaQuery.from(Pizza.class);
        queryCriteriaQuery.select(root).orderBy(builder.desc(root.get("price")));
        return em.createQuery(queryCriteriaQuery).getResultList();
    }
}
