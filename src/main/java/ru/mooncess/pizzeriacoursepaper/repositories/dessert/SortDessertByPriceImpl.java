package ru.mooncess.pizzeriacoursepaper.repositories.dessert;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.mooncess.pizzeriacoursepaper.entities.Dessert;

import java.util.List;

@Repository
@AllArgsConstructor
public class SortDessertByPriceImpl implements SortDessertByPrice {
    private final EntityManager em;

    @Override
    public List<Dessert> findByOrderByPriceAsc() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Dessert> queryCriteriaQuery = builder.createQuery(Dessert.class);
        Root<Dessert> root = queryCriteriaQuery.from(Dessert.class);
        queryCriteriaQuery.select(root).orderBy(builder.asc(root.get("price")));
        return em.createQuery(queryCriteriaQuery).getResultList();
    }

    @Override
    public List<Dessert> findByOrderByPriceDesc() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Dessert> queryCriteriaQuery = builder.createQuery(Dessert.class);
        Root<Dessert> root = queryCriteriaQuery.from(Dessert.class);
        queryCriteriaQuery.select(root).orderBy(builder.desc(root.get("price")));
        return em.createQuery(queryCriteriaQuery).getResultList();
    }
}
