package projet1.service;

import projet1.classes.categorie;
import projet1.classes.commande;
import projet1.dao.IDao;
import projet1.classes.produit;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public class produitservice implements IDao<produit> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public boolean create(produit produit) {
        Session session = sessionFactory.getCurrentSession();
        session.save(produit);
        return true;
    }

    @Override
    @Transactional
    public boolean delete(produit produit) {
        sessionFactory.getCurrentSession().delete(produit);
        return true;
    }

    @Override
    @Transactional
    public boolean update(produit produit) {
        sessionFactory.getCurrentSession().update(produit);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public produit findById(int id) {
        return sessionFactory.getCurrentSession().get(produit.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<produit> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from produit", produit.class)
                .list();
    }


    @Transactional(readOnly = true)
    public List<produit> findByCategorie(categorie categorie) {
        return sessionFactory.getCurrentSession()
                .createQuery("from produit p where p.categorie = :categorie", produit.class)
                .setParameter("categorie", categorie)
                .list();
    }




    @Transactional(readOnly = true)
    public List<produit> findByDateCommande(LocalDate startDate, LocalDate endDate) {
        List<produit> produits = sessionFactory.getCurrentSession()
                .createQuery(
                        "select distinct p from produit p " +
                                "join p.ligneCommandes l " +
                                "join l.commande c " +
                                "where c.date between :start and :end",
                        produit.class
                )
                .setParameter("start", startDate)
                .setParameter("end", endDate)
                .list();

        if (!produits.isEmpty()) {

            commande commande = produits.get(0).getLigneCommandes().stream()
                    .map(l -> l.getCommande())
                    .filter(c -> !c.getDate().isBefore(startDate) && !c.getDate().isAfter(endDate))
                    .findFirst()
                    .orElse(null);

            if (commande != null) {
                System.out.println("Commande : " + commande.getId() + "\tDate: " + commande.getDate());
                System.out.println("Liste des produits :");
                System.out.println("Référence\t\tPrix\t\tQuantité");

                for (produit p : produits) {

                    int quantite = p.getLigneCommandes().stream()
                            .filter(l -> l.getCommande().getId() == commande.getId())
                            .mapToInt(l -> l.getQuantite())
                            .sum();


                    System.out.printf("%s\t        %.0f DH\t    %d%n", p.getReference(), p.getPrix(), quantite);
                }
            }
        }
        return produits;
    }








    @Transactional(readOnly = true)
    public List<produit> findByCommande(int commandeId) {
        List<produit> produits = sessionFactory.getCurrentSession()
                .createQuery(
                        "select distinct p from produit p " +
                                "join p.ligneCommandes l " +
                                "join l.commande c " +
                                "where c.id = :commandeId",
                        produit.class
                )
                .setParameter("commandeId", commandeId)
                .list();

        if (!produits.isEmpty()) {

            commande commande = produits.get(0).getLigneCommandes().stream()
                    .map(l -> l.getCommande())
                    .filter(c -> c.getId() == commandeId)
                    .findFirst()
                    .orElse(null);

            if (commande != null) {
                System.out.println("Commande : " + commande.getId() + "\tDate : " + commande.getDate());
                System.out.println("Liste des produits :");
                System.out.println("Référence\t\tPrix\t\tQuantité");

                for (produit p : produits) {

                    int quantite = p.getLigneCommandes().stream()
                            .filter(l -> l.getCommande().getId() == commandeId)
                            .mapToInt(l -> l.getQuantite())
                            .sum();

                    System.out.printf("%s\t        %.0f DH\t    %d%n", p.getReference(), p.getPrix(), quantite);
                }
            }
        }


        return produits;
    }





    @Transactional(readOnly = true)
    public List<produit> findProduitsPrixSuperieur(float prixSeuil) {
        List<produit> produits = sessionFactory.getCurrentSession()
                .createNamedQuery("Produit.findByPrixSuperieur", produit.class)
                .setParameter("prix", prixSeuil)
                .list();

        if (!produits.isEmpty()) {
            System.out.println("Liste des produits dont le prix est supérieur à " + prixSeuil + " DH :");
            System.out.println("Référence\t\tPrix\t\tCatégorie");

            for (produit p : produits) {
                String categorie = p.getCategorie() != null ? p.getCategorie().getLibelle() : "N/A";
                System.out.printf("%s\t        %.0f DH\t    %s%n", p.getReference(), p.getPrix(), categorie);
            }
        }

        return produits;
    }


}
