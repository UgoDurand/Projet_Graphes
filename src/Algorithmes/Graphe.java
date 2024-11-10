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

    public boolean estConnexe(List<Station> stations) {
        if (stations.isEmpty()) {
            return true;
        }

        Set<Station> visites = new HashSet<>();
        Station premiereStation = stations.get(0);
        parcours_profondeur(premiereStation, visites);
        return visites.size() == stations.size();
    }

    private void parcours_profondeur(Station station, Set<Station> visites) {
        visites.add(station);

        for (Liaison liaison : adjacences) {
            if (liaison.getStation1().equals(station) && !visites.contains(liaison.getStation2())) {
                parcours_profondeur(liaison.getStation2(), visites);
            }
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

    public List<Liaison> algorithmePrim(Station depart, Station arrivee) {
        Set<Station> arbreCouvrant = new HashSet<>();
        List<Liaison> liaisonsArbre = new ArrayList<>();
        PriorityQueue<Liaison> filePriorite = new PriorityQueue<>(Comparator.comparingInt(Liaison::getPoids));

        arbreCouvrant.add(depart);

        for (Liaison liaison : adjacences) {
            if (liaison.getStation1().equals(depart)) {
                filePriorite.add(liaison);
            }
        }

        while (!filePriorite.isEmpty()) {
            Liaison liaison = filePriorite.poll();
            Station stationVoisine = liaison.getStation2();

            if (!arbreCouvrant.contains(stationVoisine)) {
                arbreCouvrant.add(stationVoisine);
                liaisonsArbre.add(liaison);

                if (stationVoisine.equals(arrivee)) {
                    return liaisonsArbre;  // Retourner les liaisons utilisées pour atteindre l'arrivée
                }

                for (Liaison prochaineLiaison : adjacences) {
                    if (prochaineLiaison.getStation1().equals(stationVoisine) && !arbreCouvrant.contains(prochaineLiaison.getStation2())) {
                        filePriorite.add(prochaineLiaison);
                    }
                }
            }
        }

        return new ArrayList<>();
    }

    public void afficherItineraire(Station depart, Station arrivee) {
        List<Liaison> liaisonsArbre = algorithmePrim(depart, arrivee);

        if (liaisonsArbre.isEmpty()) {
            System.out.println("Aucun chemin trouvé entre " + depart.getNom() + " et " + arrivee.getNom() + ".");
            return;
        }

        int totalTemps = 0;
        Liaison liaisonPrecedente = null;

        System.out.println("Vous êtes à " + depart.getNom() + ".");

        for (Liaison liaison : liaisonsArbre) {
            totalTemps += liaison.getPoids();

            if (liaisonPrecedente != null && !liaison.getStation1().getLigne().equals(liaisonPrecedente.getStation1().getLigne())) {
                System.out.println("- À " + liaisonPrecedente.getStation2().getNom() + ", changez et prenez la ligne " +
                        liaison.getStation1().getLigne() + " direction " + liaison.getStation2().getNom() + ".");
            } else if (liaisonPrecedente == null) {
                System.out.println("- Prenez la ligne " + liaison.getStation1().getLigne() + " direction " + liaison.getStation2().getNom() + ".");
            }

            liaisonPrecedente = liaison;
        }

        // Convert total time from seconds to a more readable format (minutes or hours)
        String formattedTime = formatTime(totalTemps);

        // Final arrival statement
        System.out.println("- Vous devriez arriver à " + arrivee.getNom() + " dans environ " + formattedTime);
    }

    private String formatTime(int totalSeconds) {
        if (totalSeconds < 60) {
            return totalSeconds + " secondes";
        } else if (totalSeconds < 3600) {
            int minutes = totalSeconds / 60;
            int seconds = totalSeconds % 60;
            return minutes + " minute" + (minutes > 1 ? "s" : "") + (seconds > 0 ? " et " + seconds + " seconde" + (seconds > 1 ? "s" : "") : "");
        } else {
            int hours = totalSeconds / 3600;
            int minutes = (totalSeconds % 3600) / 60;
            return hours + " heure" + (hours > 1 ? "s" : "") + (minutes > 0 ? " et " + minutes + " minute" + (minutes > 1 ? "s" : "") : "");
        }
    }

}
