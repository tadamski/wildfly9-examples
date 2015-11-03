package org.tadamski.examples.js;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Named("fooRepository")
public class FooRepository {

    @PersistenceContext
    private EntityManager em;


    public Foo findById(Long id) {
        return em.find(Foo.class, id);
    }

    public List<Foo> findAllOrderedByName() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Foo> criteria = cb.createQuery(Foo.class);
        Root<Foo> member = criteria.from(Foo.class);
        criteria.select(member).orderBy(cb.asc(member.get("name")));
        return em.createQuery(criteria).getResultList();
    }

    public void save(Foo foo) {
        em.persist(foo);
    }
}
