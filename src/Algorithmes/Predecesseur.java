package Algorithmes;

/**
 * @author Nam
 * La classe Predecesseur représente un élément utilisé dans le contexte des algorithmes de recherche de chemin (comme Dijkstra ou A*).
 * Elle associe une station à son prédécesseur, permettant ainsi de reconstruire le chemin le plus court à partir de la station de destination.
 */
class Predecesseur {

    // Station actuelle
    Station station;

    // Station précédente (prédécesseur) dans le chemin le plus court
    Station predecesseur;

    /**
     * Constructeur de la classe Predecesseur.
     *
     * @param station La station actuelle pour laquelle le prédécesseur est enregistré.
     * @param predecesseur La station précédente qui mène à la station actuelle.
     */
    public Predecesseur(Station station, Station predecesseur) {
        this.station = station;
        this.predecesseur = predecesseur;
    }
}
