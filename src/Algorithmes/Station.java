package Algorithmes;

/**
 * @author Ugo
 * La classe Station représente une station de métro avec ses caractéristiques
 * telles que son identifiant, son nom, la ligne sur laquelle elle se trouve, si elle est un terminus,
 * et les informations de branchement.
 */
public class Station {
    private int id;             // Identifiant unique de la station
    private String nom;         // Nom de la station
    private String ligne;       // Ligne de métro à laquelle la station appartient
    private boolean estTerminus; // Indique si la station est un terminus
    private int branchement;    // Code de branchement pour la station
    private double x;
    private double y;

    /**
     * Constructeur de la classe Station.
     *
     * @param id           Identifiant unique de la station.
     * @param nom          Nom de la station.
     * @param ligne        Ligne de métro à laquelle la station appartient.
     * @param estTerminus  Indique si la station est un terminus.
     * @param branchement  Code de branchement de la station.
     */
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

    /**
     * Retourne l'identifiant unique de la station.
     *
     * @return L'identifiant de la station.
     */
    public int getId() {
        return id;
    }

    /**
     * Retourne le nom de la station.
     *
     * @return Nom de la station.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Retourne la ligne de métro à laquelle la station appartient.
     *
     * @return Ligne de la station.
     */
    public String getLigne() {
        return ligne;
    }

    /**
     * Indique si la station est un terminus.
     *
     * @return true si la station est un terminus, sinon false.
     */
    public boolean isEstTerminus() {
        return estTerminus;
    }

    /**
     * Retourne les informations de branchement de la station.
     *
     * @return Code de branchement de la station.
     */
    public int getBranchement() {
        return branchement;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    /**
     * Retourne une représentation textuelle de la station, incluant son nom et sa ligne.
     *
     * @return Chaîne de caractères représentant la station sous la forme "nom (Ligne ligne)".
     */
    @Override
    public String toString() {
        return nom + " (Ligne " + ligne + ")";
    }
}
