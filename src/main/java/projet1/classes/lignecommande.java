package projet1.classes;
import javax.persistence.*;

@Entity
public class lignecommande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int quantite;

    @ManyToOne
    @JoinColumn(name = "produit_id")
    private projet1.classes.produit produit;

    @ManyToOne
    @JoinColumn(name = "commande_id")
    private projet1.classes.commande commande;

    public lignecommande() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public projet1.classes.produit getProduit() {
        return produit;
    }

    public void setProduit(projet1.classes.produit produit) {
        this.produit = produit;
    }

    public projet1.classes.commande getCommande() {
        return commande;
    }

    public void setCommande(projet1.classes.commande commande) {
        this.commande = commande;
    }
}

