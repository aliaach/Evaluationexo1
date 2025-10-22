package projet1.classes;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

@Entity
public class commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate date;

    @OneToMany(mappedBy = "commande")
    private List<lignecommande> ligneCommandes = new ArrayList<>();

    public commande() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<lignecommande> getLigneCommandes() {
        return ligneCommandes;
    }

    public void setLigneCommandes(List<lignecommande> ligneCommandes) {
        this.ligneCommandes = ligneCommandes;
    }
}

