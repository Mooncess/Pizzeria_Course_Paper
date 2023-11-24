package ru.mooncess.pizzeriacoursepaper.repositories.additive;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.mooncess.pizzeriacoursepaper.entities.Additive;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@AllArgsConstructor
public class SortAdditiveByPriceImpl implements SortAdditiveByPrice{
    private final EntityManager em;

    @Override
    public List<Additive> findByOrderByPriceAsc() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Additive> queryCriteriaQuery = builder.createQuery(Additive.class);
        Root<Additive> root = queryCriteriaQuery.from(Additive.class);
        queryCriteriaQuery.select(root).orderBy(builder.asc(root.get("price")));
        return em.createQuery(queryCriteriaQuery).getResultList();
    }

    @Override
    public List<Additive> findByOrderByPriceDesc() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Additive> queryCriteriaQuery = builder.createQuery(Additive.class);
        Root<Additive> root = queryCriteriaQuery.from(Additive.class);
        queryCriteriaQuery.select(root).orderBy(builder.desc(root.get("price")));
        return em.createQuery(queryCriteriaQuery).getResultList();
    }
}
