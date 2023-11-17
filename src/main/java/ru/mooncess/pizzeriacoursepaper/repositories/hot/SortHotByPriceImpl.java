package ru.mooncess.pizzeriacoursepaper.repositories.hot;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.mooncess.pizzeriacoursepaper.entities.Hot;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@AllArgsConstructor
public class SortHotByPriceImpl implements SortHotByPrice {
    private final EntityManager em;

    @Override
    public List<Hot> findByOrderByPriceAsc() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Hot> queryCriteriaQuery = builder.createQuery(Hot.class);
        Root<Hot> root = queryCriteriaQuery.from(Hot.class);
        queryCriteriaQuery.select(root).orderBy(builder.asc(root.get("price")));
        return em.createQuery(queryCriteriaQuery).getResultList();
    }

    @Override
    public List<Hot> findByOrderByPriceDesc() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Hot> queryCriteriaQuery = builder.createQuery(Hot.class);
        Root<Hot> root = queryCriteriaQuery.from(Hot.class);
        queryCriteriaQuery.select(root).orderBy(builder.desc(root.get("price")));
        return em.createQuery(queryCriteriaQuery).getResultList();
    }
}
