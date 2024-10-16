package Algorithmes;

public class Station {
    private String id;           // Numéro du sommet
    private String nom;          // Nom de la station
    private String ligne;           // Numéro de la ligne de métro
    private boolean estTerminus; // Si la station est un terminus
    private int branchement;     // Informations sur les branchements

    public Station(String id, String nom, String ligne, boolean estTerminus, int branchement) {
        this.id = id;
        this.nom = nom;
        this.ligne = ligne;
        this.estTerminus = estTerminus;
        this.branchement = branchement;
    }

    // Getters et setters
    public String getId() {
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
