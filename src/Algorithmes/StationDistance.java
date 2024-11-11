package Algorithmes;

/**
 * @author Ugo
 * La classe StationDistance représente une station ainsi que la distance associée à cette station
 * dans le cadre d'un algorithme de recherche de plus court chemin (comme Dijkstra).
 * Elle est utilisée pour stocker la station ainsi que sa distance actuelle par rapport à la station de départ.
 */
class StationDistance {

    // Station associée à cette distance
    Station station;

    // Distance actuelle de cette station par rapport à la station de départ
    int distance;

    /**
     * Constructeur de la classe StationDistance.
     *
     * @param station La station associée à la distance.
     * @param distance La distance entre la station et la station de départ.
     */
    public StationDistance(Station station, int distance) {
        this.station = station;
        this.distance = distance;
    }
}
