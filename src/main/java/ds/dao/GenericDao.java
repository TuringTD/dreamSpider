package ds.dao;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.TypeLiteral;
import com.google.inject.persist.Transactional;
import ninja.jpa.UnitOfWork;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class GenericDao<T>
{

    protected final Class< T > type;

    @Inject
    Provider<EntityManager> provider;

    @Inject
    public GenericDao(TypeLiteral<T> type)
    {
        this.type = (Class<T>)type.getRawType();
    }

    @Transactional
    public T create(T t)
    {
        EntityManager em = provider.get();
        em.persist(t);
        em.flush();
        return t;
    }

    @Transactional
    public void delete(T t)
    {
        t = this.provider.get().merge(t);
        this.provider.get().remove(t);
    }

    @UnitOfWork
    public T find(final Long id)
    {
        return this.provider.get().find(type, id);
    }

    @Transactional
    public T update(T t)
    {
        EntityManager em = provider.get();
        em.merge(t);
        em.flush();
        return t;
    }

    @UnitOfWork
    public long count() {
        final StringBuffer queryString = new StringBuffer(
                "SELECT count(o) from ");
        queryString.append(type.getSimpleName()).append(" o ");
        final Query query = this.provider.get().createQuery(queryString.toString());

        Long count =  (Long) query.getSingleResult();
        return count;
    }


    @UnitOfWork
    public List<T> findBy(T example) {
        Example ex = Example.create(example);
        final Session session = (Session)this.provider.get().getDelegate();
        final Criteria criteria = session.createCriteria(example.getClass()).add(ex);
        return criteria.list();
    }

    @UnitOfWork
    public List<T> findLike(T example) {
        Example ex = Example.create(example);
        final Session session = (Session)this.provider.get().getDelegate();
        final Criteria criteria = session.createCriteria(example.getClass()).add(ex);
        return criteria.list();
    }

    @UnitOfWork
    public List<T> findAll(){
        final StringBuffer queryString = new StringBuffer(
                "SELECT o from ");
        queryString.append(type.getSimpleName()).append(" o order by o.id desc");
        final Query query = this.provider.get().createQuery(queryString.toString());
        return query.getResultList();
    }

    @Transactional
    public Integer deleteAll() {
        final StringBuffer queryString = new StringBuffer(
                "DELETE  from ");
        queryString.append(type.getSimpleName()).append(" o ");
        final Query query = this.provider.get().createQuery(queryString.toString());

        Integer count =  (Integer) query.executeUpdate();
        return count;
    }
}
