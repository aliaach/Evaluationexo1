package projet1.service;

import projet1.dao.IDao;
import projet1.classes.commande;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class commandeservice implements IDao<commande> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public boolean create(commande commande) {
        Session session = sessionFactory.getCurrentSession();
        session.save(commande);
        return true;
    }

    @Override
    @Transactional
    public boolean delete(commande commande) {
        sessionFactory.getCurrentSession().delete(commande);
        return true;
    }

    @Override
    @Transactional
    public boolean update(commande commande) {
        sessionFactory.getCurrentSession().update(commande);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public commande findById(int id) {
        return sessionFactory.getCurrentSession().get(commande.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<commande> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from commande", commande.class)
                .list();
    }
}
