package Algorithmes;

/**
 * @author Nam
 * La classe Liaison représente une connexion entre deux stations avec un poids (temps de trajet en secondes).
 * Elle est utilisée pour modéliser les liaisons entre les stations dans un graphe.
 */
public class Liaison {
    private Station station1;  // Première station de la liaison
    private Station station2;  // Deuxième station de la liaison
    private int temps;         // Temps de trajet en secondes entre station1 et station2

    /**
     * Constructeur de la classe Liaison.
     *
     * @param station1 Première station de la liaison.
     * @param station2 Deuxième station de la liaison.
     * @param temps    Temps de trajet entre station1 et station2 en secondes.
     */
    public Liaison(Station station1, Station station2, int temps) {
        this.station1 = station1;
        this.station2 = station2;
        this.temps = temps;
    }

    /**
     * Retourne la première station de la liaison.
     *
     * @return La station de départ de la liaison.
     */
    public Station getStation1() {
        return station1;
    }

    /**
     * Retourne la deuxième station de la liaison.
     *
     * @return La station d'arrivée de la liaison.
     */
    public Station getStation2() {
        return station2;
    }

    /**
     * Retourne le poids de la liaison, correspondant au temps de trajet entre les deux stations.
     *
     * @return Temps de trajet en secondes entre station1 et station2.
     */
    public int getPoids() {
        return temps;
    }

    /**
     * Retourne une représentation textuelle de la liaison, indiquant les deux stations et le temps de trajet.
     *
     * @return Chaîne de caractères représentant la liaison sous la forme "station1 --> station2 en temps s".
     */
    @Override
    public String toString() {
        return station1 + " --> " + station2 + " en " + temps + " s.";
    }
}
