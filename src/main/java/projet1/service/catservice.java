package projet1.service;

import projet1.dao.IDao;
import projet1.classes.categorie;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class catservice implements IDao<categorie> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public boolean create(categorie categorie) {
        Session session = sessionFactory.getCurrentSession();
        session.save(categorie);
        return true;
    }

    @Override
    @Transactional
    public boolean delete(categorie categorie) {
        sessionFactory.getCurrentSession().delete(categorie);
        return true;
    }

    @Override
    @Transactional
    public boolean update(categorie categorie) {
        sessionFactory.getCurrentSession().update(categorie);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public categorie findById(int id) {
        return sessionFactory.getCurrentSession().get(categorie.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<categorie> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from categorie", categorie.class)
                .list();
    }
}
