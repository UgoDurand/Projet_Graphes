package Algorithmes;

import java.util.*;

public class Graphe {
    private ArrayList<Liaison> adjacences;  // pour stocker toutes les liaisons

    /**
     * Constructeur de la classe Graphe.
     * Initialise la liste des liaisons.
     */
    public Graphe() {
        adjacences = new ArrayList<>();
    }

    /**
     * Ajoute une liaison bidirectionnelle entre deux stations avec un poids donné (temps).
     *
     * @param station1 Première station de la liaison.
     * @param station2 Deuxième station de la liaison.
     * @param temps    Temps de trajet entre les deux stations en secondes.
     */
    public void ajouterLiaison(Station station1, Station station2, int temps, String ligneNumero) {
        Liaison liaison = new Liaison(station1, station2, temps, ligneNumero);
        adjacences.add(liaison);

        Liaison liaisonInverse = new Liaison(station2, station1, temps, ligneNumero);
        adjacences.add(liaisonInverse);
    }

    /**
     * Construit le graphe en ajoutant une liste de liaisons entre les stations.
     *
     * @param stations Liste des stations du graphe.
     * @param liaisons Liste des liaisons à ajouter au graphe.
     */
    public void construireGraphe(ArrayList<Station> stations, ArrayList<Liaison> liaisons) {
        for (Liaison liaison : liaisons) {
            ajouterLiaison(liaison.getStation1(), liaison.getStation2(), liaison.getPoids(), liaison.getLigneMetro());
        }
    }

    /**
     * @param stations Liste des stations du graphe.
     * @return true si le graphe est connexe, sinon false.
     * @autor : Nam
     * Vérifie si le graphe est connexe (toutes les stations sont accessibles les unes depuis les autres).
     */
    public boolean estConnexe(List<Station> stations) {
        if (stations.isEmpty()) {
            return true;
        }

        Set<Station> visites = new HashSet<>();
        Station premiereStation = stations.get(0);
        parcours_profondeur(premiereStation, visites);
        return visites.size() == stations.size();
    }

    /**
     * @param station Station de départ pour le parcours.
     * @param visites Ensemble des stations déjà visitées.
     * @autor : Sahkana
     * Effectue un parcours en profondeur à partir d'une station donnée.
     */
    private void parcours_profondeur(Station station, Set<Station> visites) {
        visites.add(station);

        for (Liaison liaison : adjacences) {
            if (liaison.getStation1().equals(station) && !visites.contains(liaison.getStation2())) {
                parcours_profondeur(liaison.getStation2(), visites);
            }
        }
    }

    /**
     * @param depart  Station de départ.
     * @param arrivee Station d'arrivée.
     * @return Liste ordonnée des stations pour le chemin le plus court, ou une liste vide si aucun chemin n'existe.
     * @autor : Sahkana
     * Calcule le plus court chemin entre deux stations en utilisant l'algorithme de Dijkstra.
     */
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

    //Méthodes pour la gestion des distances et des prédécesseurs

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

    /**
     * @autor : Nam
     * Affiche toutes les liaisons du graphe.
     */
    public void afficherGraphe() {
        for (Liaison liaison : adjacences) {
            System.out.println(liaison.getStation1().getNom() + " (Ligne " + liaison.getStation1().getLigne() + ") --> " +
                    liaison.getStation2().getNom() + " (" + liaison.getPoids() + "s)");
        }
    }

    /**
     * @param depart Station de départ.
     * @return Liste des liaisons formant l'arbre couvrant minimum jusqu'à la station d'arrivée.
     * @autor : Ugo
     * Algorithme de Prim pour construire un arbre couvrant minimum depuis une station de départ.
     */
    public List<Liaison> algorithmePrim(Station depart) {
        Set<Station> arbreCouvrant = new HashSet<>();
        List<Liaison> liaisonsArbre = new ArrayList<>();
        PriorityQueue<Liaison> filePriorite = new PriorityQueue<>(Comparator.comparingInt(Liaison::getPoids));

        int poidsTotal = 0;

        arbreCouvrant.add(depart);

        for (Liaison liaison : adjacences) {
            if (liaison.getStation1().equals(depart) || liaison.getStation2().equals(depart)) {
                filePriorite.add(liaison);
            }
        }

        while (!filePriorite.isEmpty()) {
            Liaison liaison = filePriorite.poll();
            Station station1 = liaison.getStation1();
            Station station2 = liaison.getStation2();

            Station nouvelleStation = null;
            if (!arbreCouvrant.contains(station1)) {
                nouvelleStation = station1;
            } else if (!arbreCouvrant.contains(station2)) {
                nouvelleStation = station2;
            }

            if (nouvelleStation != null) {
                arbreCouvrant.add(nouvelleStation);
                liaisonsArbre.add(liaison);
                poidsTotal += liaison.getPoids();

                for (Liaison prochaineLiaison : adjacences) {
                    if ((prochaineLiaison.getStation1().equals(nouvelleStation) && !arbreCouvrant.contains(prochaineLiaison.getStation2())) ||
                            (prochaineLiaison.getStation2().equals(nouvelleStation) && !arbreCouvrant.contains(prochaineLiaison.getStation1()))) {
                        filePriorite.add(prochaineLiaison);
                    }
                }
            }
        }

        System.out.println("Poids total de l'arbre couvrant minimal : " + poidsTotal + " secondes.");
        return liaisonsArbre;
    }


    /**
     * @param chemin Liste ordonnée des stations pour le chemin le plus court.
     * @autor : Ugo
     * Affiche l'itinéraire optimal entre une station de départ et une station d'arrivée.
     */
    public String afficherItineraire(List<Station> chemin) {
        if (chemin == null || chemin.isEmpty()) {
            return "Aucun itinéraire disponible.";
        }

        int totalTemps = 0;
        Liaison liaisonPrecedente = null;
        StringBuilder itineraireMessage = new StringBuilder();

        itineraireMessage.append("Vous êtes à ").append(chemin.get(0).getNom()).append(".\n");
        itineraireMessage.append("- Prenez la ligne ").append(chemin.get(0).getLigne())
                .append(" direction ").append(chemin.get(1).getNom()).append(".\n");

        String ligneActuelle = chemin.get(0).getLigne();

        for (int i = 0; i < chemin.size() - 1; i++) {
            Station stationCourante = chemin.get(i);
            Station stationSuivante = chemin.get(i + 1);

            for (Liaison liaison : adjacences) {
                if (liaison.getStation1().equals(stationCourante) && liaison.getStation2().equals(stationSuivante)) {
                    totalTemps += liaison.getPoids();
                    if (liaisonPrecedente == null || !liaison.getStation1().getLigne().equals(liaisonPrecedente.getStation1().getLigne())) {
                        liaisonPrecedente = liaison;
                    }
                    break;
                }
            }

            if (!stationSuivante.getLigne().equals(ligneActuelle)) {
                itineraireMessage.append("- À ").append(stationCourante.getNom())
                        .append(", changez et prenez la ligne ")
                        .append(stationSuivante.getLigne()).append(".\n");
                ligneActuelle = stationSuivante.getLigne();
            }
        }

        Station destination = chemin.get(chemin.size() - 1);
        String formattedTime = formatTemps(totalTemps);
        itineraireMessage.append("- Vous devriez arriver à ").append(destination.getNom())
                .append(" dans environ ").append(formattedTime).append(".\n");

        return itineraireMessage.toString();
    }


    /**
     * @param totalSecondes Temps total en secondes.
     * @return Temps formaté en chaînes de caractères (secondes, minutes ou heures).
     * @autor : Sahkana
     * Formate le temps en secondes en minutes ou heures.
     */
    public String formatTemps(int totalSecondes) {
        if (totalSecondes < 60) {
            return totalSecondes + " secondes";
        } else if (totalSecondes < 3600) {
            int minutes = totalSecondes / 60;
            int seconds = totalSecondes % 60;
            return minutes + " minute" + (minutes > 1 ? "s" : "") + (seconds > 0 ? " et " + seconds + " seconde" + (seconds > 1 ? "s" : "") : "");
        } else {
            int hours = totalSecondes / 3600;
            int minutes = (totalSecondes % 3600) / 60;
            return hours + " heure" + (hours > 1 ? "s" : "") + (minutes > 0 ? " et " + minutes + " minute" + (minutes > 1 ? "s" : "") : "");
        }
    }

    /**
     * Retourne toutes les liaisons connectées à une station donnée.
     *
     * @param station La station pour laquelle nous recherchons les liaisons.
     * @return Liste des liaisons impliquant la station.
     */
    public List<Liaison> getLiaisonsDeStation(Station station) {
        List<Liaison> liaisonsDeStation = new ArrayList<>();

        for (Liaison liaison : adjacences) {
            if (liaison.getStation1().equals(station) || liaison.getStation2().equals(station)) {
                liaisonsDeStation.add(liaison);
            }
        }

        return liaisonsDeStation;
    }

    public List<Station> getStations() {
        Set<Station> stationsSet = new HashSet<>();
        for (Liaison liaison : adjacences) {
            stationsSet.add(liaison.getStation1());
            stationsSet.add(liaison.getStation2());
        }
        return new ArrayList<>(stationsSet);
    }

    public List<Station> bellmanFord(Station depart, Station arrivee) {
        Map<Station, Integer> distances = new HashMap<>();
        Map<Station, Station> predecesseurs = new HashMap<>();
        List<Station> stations = getStations();

        for (Station station : stations) {
            distances.put(station, Integer.MAX_VALUE);
            predecesseurs.put(station, null);
        }

        distances.put(depart, 0);

        for (int i = 0; i < stations.size() - 1; i++) {
            for (Liaison liaison : adjacences) {
                Station station1 = liaison.getStation1();
                Station station2 = liaison.getStation2();
                int poids = liaison.getPoids();

                if (distances.get(station1) != Integer.MAX_VALUE && distances.get(station1) + poids < distances.get(station2)) {
                    distances.put(station2, distances.get(station1) + poids);
                    predecesseurs.put(station2, station1);
                }
            }
        }

        for (Liaison liaison : adjacences) {
            Station station1 = liaison.getStation1();
            Station station2 = liaison.getStation2();
            int poids = liaison.getPoids();

            if (distances.get(station1) != Integer.MAX_VALUE && distances.get(station1) + poids < distances.get(station2)) {
                System.out.println("Il y a un cycle négatif dans le graphe.");
                return new ArrayList<>();
            }
        }

        List<Station> chemin = new ArrayList<>();
        Station current = arrivee;
        while (current != null) {
            chemin.add(0, current);
            current = predecesseurs.get(current);
        }

        if (distances.get(arrivee) == Integer.MAX_VALUE) {
            return new ArrayList<>();
        }

        return chemin;
    }


}
