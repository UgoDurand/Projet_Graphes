package Algorithmes;

public class Station {
    private int id;           // Numéro du sommet
    private String nom;          // Nom de la station
    private String ligne;           // Numéro de la ligne de métro
    private boolean estTerminus; // Si la station est un terminus
    private int branchement;// Informations sur les branchements
    private double x;
    private double y;

    public Station(int id, String nom, String ligne, boolean estTerminus, int branchement) {
        this.id = id;
        this.nom = nom;
        this.ligne = ligne;
        this.estTerminus = estTerminus;
        this.branchement = branchement;
    }

    public Station(String nom, double x, double y) {
        this.nom = nom;
        this.x = x;
        this.y = y;
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

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
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
