package projet1.service;

import projet1.dao.IDao;
import projet1.classes.lignecommande;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class lignecommandeservice implements IDao<lignecommande> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public boolean create(lignecommande ligneCommande) {
        Session session = sessionFactory.getCurrentSession();
        session.save(ligneCommande);
        return true;
    }

    @Override
    @Transactional
    public boolean delete(lignecommande ligneCommande) {
        sessionFactory.getCurrentSession().delete(ligneCommande);
        return true;
    }

    @Override
    @Transactional
    public boolean update(lignecommande ligneCommande) {
        sessionFactory.getCurrentSession().update(ligneCommande);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public lignecommande findById(int id) {
        return sessionFactory.getCurrentSession().get(lignecommande.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<lignecommande> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from lignecommande", lignecommande.class)
                .list();
    }
}
