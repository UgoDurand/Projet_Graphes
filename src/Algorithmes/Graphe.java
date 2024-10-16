package Algorithmes;

import java.util.*;

public class Graphe {

    private Map<Station, List<Liaison>> adjacence;
    private Map<String, Station> stations;
    private List<Liaison> liaisons;

    public Graphe() {
        adjacence = new HashMap<>();
        stations = new HashMap<>();
        liaisons = new ArrayList<>();
    }

    public void ajouterStation(Station station) {
        adjacence.putIfAbsent(station, new ArrayList<>());
        stations.putIfAbsent(station.getNom(), station);
        System.out.println("Station ajoutée : " + station.getNom());
    }

    public Station getStation(String nom) {
        return stations.get(nom);
    }

    public void ajouterLiaison(Station station1, Station station2, int poids) {
        adjacence.get(station1).add(new Liaison(station1, station2, poids));
        adjacence.get(station2).add(new Liaison(station2, station1, poids));
    }

    public List<Liaison> getLiaisons(Station station) {
        return adjacence.get(station);
    }

    public Set<Station> getStations() {
        return adjacence.keySet();
    }

    // Algorithme de Dijkstra pour trouver le plus court chemin
    public List<Station> plusCourtChemin(Station depart, Station arrivee) {

        Map<Station, Integer> distances = new HashMap<>();
        Map<Station, Station> precedents = new HashMap<>();
        PriorityQueue<Station> file = new PriorityQueue<>(Comparator.comparingInt(distances::get));

        // Initialiser les distances avec l'infini
        for (Station station : adjacence.keySet()) {
            distances.put(station, Integer.MAX_VALUE);
        }
        distances.put(depart, 0);
        file.add(depart);

        while (!file.isEmpty()) {
            Station stationActuelle = file.poll();  // Station avec la distance minimale

            // Si on atteint la station d'arrivée, on peut arrêter
            if (stationActuelle.equals(arrivee)) {
                break;
            }

            // Parcourir les voisins de la station actuelle
            for (Liaison liaison : adjacence.get(stationActuelle)) {
                Station voisin = liaison.getStation2();
                int nouvelleDistance = distances.get(stationActuelle) + liaison.getPoids();

                // Si on trouve un chemin plus court vers ce voisin, on met à jour
                if (nouvelleDistance < distances.get(voisin)) {
                    distances.put(voisin, nouvelleDistance);
                    precedents.put(voisin, stationActuelle);
                    file.add(voisin);
                }
            }
        }

        // Reconstruire le chemin depuis la station d'arrivée
        List<Station> chemin = new ArrayList<>();
        for (Station station = arrivee; station != null; station = precedents.get(station)) {
            chemin.add(station);
        }
        Collections.reverse(chemin);  // Le chemin est reconstruit à l'envers

        return chemin.isEmpty() || chemin.get(0) != depart ? new ArrayList<>() : chemin;  // Retourner le chemin ou une liste vide si aucun chemin trouvé
    }

    public void afficherGraphe() {
        // Afficher chaque station et ses liaisons
        for (Station station : adjacence.keySet()) {
            System.out.println("Station: " + station.getNom());
            List<Liaison> liaisonsStation = adjacence.get(station);
            if (liaisonsStation.isEmpty()) {
                System.out.println("  Pas de liaisons.");
            } else {
                System.out.println("  Liaisons:");
                for (Liaison liaison : liaisonsStation) {
                    Station stationVoisin = liaison.getStation2();
                    int poids = liaison.getPoids();
                    System.out.println("    -> " + stationVoisin.getNom() + " (Poids: " + poids + ")");
                }
            }
        }

        System.out.println("Graphe vide");
    }

}
