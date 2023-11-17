package ru.mooncess.pizzeriacoursepaper.repositories.combo;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.mooncess.pizzeriacoursepaper.entities.Combo;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@AllArgsConstructor
public class SortComboByPriceImpl implements SortComboByPrice {
    private final EntityManager em;

    @Override
    public List<Combo> findByOrderByPriceAsc() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Combo> queryCriteriaQuery = builder.createQuery(Combo.class);
        Root<Combo> root = queryCriteriaQuery.from(Combo.class);
        queryCriteriaQuery.select(root).orderBy(builder.asc(root.get("price")));
        return em.createQuery(queryCriteriaQuery).getResultList();
    }

    @Override
    public List<Combo> findByOrderByPriceDesc() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Combo> queryCriteriaQuery = builder.createQuery(Combo.class);
        Root<Combo> root = queryCriteriaQuery.from(Combo.class);
        queryCriteriaQuery.select(root).orderBy(builder.desc(root.get("price")));
        return em.createQuery(queryCriteriaQuery).getResultList();
    }
}
