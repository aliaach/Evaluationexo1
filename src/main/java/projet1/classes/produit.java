package projet1.classes;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@NamedQueries({
        @NamedQuery(
                name = "Produit.findByPrixSuperieur",
                query = "select p from produit p where p.prix > :prix"
        )
})
@Entity
public class produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String reference;
    private float prix;


    @ManyToOne(cascade = CascadeType.ALL)
    private projet1.classes.categorie categorie;

    @OneToMany(mappedBy = "produit")
    private List<lignecommande> ligneCommandes = new ArrayList<>();

    public produit() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public projet1.classes.categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(projet1.classes.categorie categorie) {
        this.categorie = categorie;
    }

    public List<lignecommande> getLigneCommandes() {
        return ligneCommandes;
    }

    public void setLigneCommandes(List<lignecommande> ligneCommandes) {
        this.ligneCommandes = ligneCommandes;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof produit)) return false;
        produit produit = (projet1.classes.produit) o;
        return id == produit.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}

