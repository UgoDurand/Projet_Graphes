package Algorithmes;

import java.util.*;

public class Graphe {
    private ArrayList<Liaison> adjacences;  // pour stocker toutes les liaisons

    public Graphe() {
        adjacences = new ArrayList<>();
    }

    public void ajouterLiaison(Station station1, Station station2, int temps) {
        Liaison liaison = new Liaison(station1, station2, temps);
        adjacences.add(liaison);

        Liaison liaisonInverse = new Liaison(station2, station1, temps);
        adjacences.add(liaisonInverse);
    }

    public void construireGraphe(ArrayList<Station> stations, ArrayList<Liaison> liaisons) {
        for (Liaison liaison : liaisons) {
            ajouterLiaison(liaison.getStation1(), liaison.getStation2(), liaison.getPoids());
        }
    }

    public List<Station> plusCourtChemin(Station depart, Station arrivee) {
        List<StationDistance> distances = new ArrayList<>();
        List<Predecesseur> predecesseurs = new ArrayList<>();
        PriorityQueue<StationDistance> filePriorite = new PriorityQueue<>(Comparator.comparingInt(sd -> sd.distance));
        Set<Station> visites = new HashSet<>();

        distances.add(new StationDistance(depart, 0));
        filePriorite.add(new StationDistance(depart, 0));

        while (!filePriorite.isEmpty()) {
            StationDistance current = filePriorite.poll();
            Station currentStation = current.station;

            if (visites.contains(currentStation)) continue;

            visites.add(currentStation);

            if (currentStation.equals(arrivee)) {
                return reconstruireChemin(predecesseurs, arrivee);
            }

            for (Liaison liaison : adjacences) {
                if (liaison.getStation1().equals(currentStation)) {
                    Station voisin = liaison.getStation2();
                    int nouvelleDistance = getDistance(distances, currentStation) + liaison.getPoids();

                    if (nouvelleDistance < getDistance(distances, voisin)) {
                        updateDistance(distances, voisin, nouvelleDistance);
                        predecesseurs.add(new Predecesseur(voisin, currentStation));
                        filePriorite.add(new StationDistance(voisin, nouvelleDistance));
                    }
                }
            }
        }

        return new ArrayList<>();
    }

    private int getDistance(List<StationDistance> distances, Station station) {
        for (StationDistance stationDistance : distances) {
            if (stationDistance.station.equals(station)) {
                return stationDistance.distance;
            }
        }
        return Integer.MAX_VALUE;
    }

    private void updateDistance(List<StationDistance> distances, Station station, int nouvelleDistance) {
        boolean updated = false;
        for (StationDistance sd : distances) {
            if (sd.station.equals(station)) {
                sd.distance = nouvelleDistance;
                updated = true;
                break;
            }
        }
        if (!updated) {
            distances.add(new StationDistance(station, nouvelleDistance));
        }
    }

    private List<Station> reconstruireChemin(List<Predecesseur> predecesseurs, Station arrivee) {
        List<Station> chemin = new ArrayList<>();
        Station stationActuelle = arrivee;

        while (stationActuelle != null) {
            chemin.add(0, stationActuelle);
            stationActuelle = getPredecesseur(predecesseurs, stationActuelle);
        }

        return chemin;
    }

    private Station getPredecesseur(List<Predecesseur> predecesseurs, Station station) {
        for (Predecesseur p : predecesseurs) {
            if (p.station.equals(station)) {
                return p.predecesseur;
            }
        }
        return null;
    }

    public void afficherGraphe() {
        for (Liaison liaison : adjacences) {
            System.out.println(liaison.getStation1().getNom() + " (Ligne " + liaison.getStation1().getLigne() + ") --> " +
                    liaison.getStation2().getNom() + " (" + liaison.getPoids() + "s)");
        }
    }
}