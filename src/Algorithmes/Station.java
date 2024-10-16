package Algorithmes;

public class Station {
    private int id;           // Numéro du sommet
    private String nom;          // Nom de la station
    private String ligne;           // Numéro de la ligne de métro
    private boolean estTerminus; // Si la station est un terminus
    private int branchement;     // Informations sur les branchements

    public Station(int id, String nom, String ligne, boolean estTerminus, int branchement) {
        this.id = id;
        this.nom = nom;
        this.ligne = ligne;
        this.estTerminus = estTerminus;
        this.branchement = branchement;
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getLigne() {
        return ligne;
    }

    public boolean isEstTerminus() {
        return estTerminus;
    }

    public int getBranchement() {
        return branchement;
    }

    @Override
    public String toString() {
        return nom + " (Ligne " + ligne + ")";
    }
}
