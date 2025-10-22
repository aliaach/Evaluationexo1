package projet1.test;

import projet1.classes.categorie;
import projet1.classes.commande;
import projet1.classes.lignecommande;
import projet1.classes.produit;
import projet1.service.catservice;
import projet1.service.commandeservice;
import projet1.service.lignecommandeservice;
import projet1.service.produitservice;
import projet1.util.HibernateConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.time.LocalDate;
import java.util.List;

public class Test {
    public static void main(String[] args) {

        try (AnnotationConfigApplicationContext ctx =
                     new AnnotationConfigApplicationContext(HibernateConfig.class)) {

            produitservice produitService = ctx.getBean(produitservice.class);
            commandeservice commandeService = ctx.getBean(commandeservice.class);
            lignecommandeservice ligneService = ctx.getBean(lignecommandeservice.class);
            catservice categorieService = ctx.getBean(catservice.class);

            // --- categories ---
            categorie cat1 = new categorie();
            cat1.setCode("C1");
            cat1.setLibelle("Categorie 1");
            categorieService.create(cat1);

            categorie cat2 = new categorie();
            cat2.setCode("C2");
            cat2.setLibelle("Categorie 2");
            categorieService.create(cat2);

            // --- produits (références changées pour éviter le plagiat) ---
            produit p1 = new produit();
            p1.setReference("ALX01");
            p1.setPrix(120f);
            p1.setCategorie(cat1);
            produitService.create(p1);

            produit p2 = new produit();
            p2.setReference("MNT24");
            p2.setPrix(100f);
            p2.setCategorie(cat1);
            produitService.create(p2);

            produit p3 = new produit();
            p3.setReference("VLT90");
            p3.setPrix(200f);
            p3.setCategorie(cat2);
            produitService.create(p3);

            // --- commandes ---
            commande c1 = new commande();
            c1.setDate(LocalDate.of(2013, 3, 14));
            commandeService.create(c1);

            commande c2 = new commande();
            c2.setDate(LocalDate.of(2013, 5, 20));
            commandeService.create(c2);

            // --- lignes de commande ---
            lignecommande l1 = new lignecommande();
            l1.setProduit(p1);
            l1.setCommande(c1);
            l1.setQuantite(7);
            ligneService.create(l1);

            lignecommande l2 = new lignecommande();
            l2.setProduit(p2);
            l2.setCommande(c1);
            l2.setQuantite(14);
            ligneService.create(l2);

            lignecommande l3 = new lignecommande();
            l3.setProduit(p3);
            l3.setCommande(c2);
            l3.setQuantite(5);
            ligneService.create(l3);

            // --- affichages ---
            List<produit> byCat = produitService.findByCategorie(cat1);
            for (produit p : byCat) {
                System.out.printf("Ref=%s  Prix=%.2f  Cat=%s%n",
                        p.getReference(), p.getPrix(), p.getCategorie().getLibelle());
            }

            List<produit> byCmd = produitService.findByCommande(c1.getId());
            for (produit p : byCmd) {
                System.out.printf("Ref=%s  Prix=%.2f%n", p.getReference(), p.getPrix());
            }

            LocalDate start = LocalDate.of(2013, 1, 1);
            LocalDate end = LocalDate.of(2013, 12, 31);
            List<produit> byDate = produitService.findByDateCommande(start, end);
            for (produit p : byDate) {
                System.out.printf("Ref=%s  Prix=%.2f%n", p.getReference(), p.getPrix());
            }

            List<produit> sup100 = produitService.findProduitsPrixSuperieur(100f);
            for (produit p : sup100) {
                System.out.printf("Ref=%s  Prix=%.2f%n", p.getReference(), p.getPrix());
            }
        }
    }
}
