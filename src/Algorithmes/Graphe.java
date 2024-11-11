package Algorithmes;

import java.util.*;

/**
 * La classe Graphe représente un graphe de stations interconnectées par des liaisons (temps de trajet en secondes).
 * Elle inclut des méthodes pour ajouter des liaisons, vérifier la connectivité, trouver des itinéraires optimaux
 * en utilisant Prim et afficher des détails du graphe.
 */
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
    public void ajouterLiaison(Station station1, Station station2, int temps) {
        Liaison liaison = new Liaison(station1, station2, temps);
        adjacences.add(liaison);

        Liaison liaisonInverse = new Liaison(station2, station1, temps);
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
            ajouterLiaison(liaison.getStation1(), liaison.getStation2(), liaison.getPoids());
        }
    }

    /**
     * @autor : Nam
     * Vérifie si le graphe est connexe (toutes les stations sont accessibles les unes depuis les autres).
     *
     * @param stations Liste des stations du graphe.
     * @return true si le graphe est connexe, sinon false.
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
     *  @autor : Sahkana
     * Effectue un parcours en profondeur à partir d'une station donnée.
     *
     * @param station Station de départ pour le parcours.
     * @param visites Ensemble des stations déjà visitées.
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
     *  @autor : Sahkana
     * Calcule le plus court chemin entre deux stations en utilisant l'algorithme de Dijkstra.
     *
     * @param depart  Station de départ.
     * @param arrivee Station d'arrivée.
     * @return Liste ordonnée des stations pour le chemin le plus court, ou une liste vide si aucun chemin n'existe.
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

    // Méthodes privées pour la gestion des distances et des prédécesseurs

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
     *  @autor : Nam
     * Affiche toutes les liaisons du graphe.
     */
    public void afficherGraphe() {
        for (Liaison liaison : adjacences) {
            System.out.println(liaison.getStation1().getNom() + " (Ligne " + liaison.getStation1().getLigne() + ") --> " +
                    liaison.getStation2().getNom() + " (" + liaison.getPoids() + "s)");
        }
    }

    /**
     *  @autor : Ugo
     * Algorithme de Prim pour construire un arbre couvrant minimum depuis une station de départ.
     *
     * @param depart  Station de départ.
     * @param arrivee Station d'arrivée.
     * @return Liste des liaisons formant l'arbre couvrant minimum jusqu'à la station d'arrivée.
     */
    public List<Liaison> algorithmePrim(Station depart, Station arrivee) {
        // Utilisation d'un Set pour représenter les stations visitées (l'arbre couvrant)
        Set<Station> arbreCouvrant = new HashSet<>();
        List<Liaison> liaisonsArbre = new ArrayList<>();
        PriorityQueue<Liaison> filePriorite = new PriorityQueue<>(Comparator.comparingInt(Liaison::getPoids));

        // Ajouter la station de départ à l'arbre
        arbreCouvrant.add(depart);

        // Ajouter toutes les liaisons de la station de départ à la file de priorité
        for (Liaison liaison : adjacences) {
            if (liaison.getStation1().equals(depart)) {
                filePriorite.add(liaison);
            }
        }

        int poidsTotal = 0;

        // Tant qu'il y a des liaisons à traiter et que l'on n'a pas visité toutes les stations
        while (!filePriorite.isEmpty()) {
            // On prend la liaison avec le poids minimal
            Liaison liaison = filePriorite.poll();
            Station stationVoisine = liaison.getStation2();

            // Si la station voisine n'a pas encore été visitée
            if (!arbreCouvrant.contains(stationVoisine)) {
                // Ajouter la station voisine à l'arbre
                arbreCouvrant.add(stationVoisine);
                // Ajouter la liaison à l'arbre couvrant
                liaisonsArbre.add(liaison);
                // Accumuler le poids de cette liaison
                poidsTotal += liaison.getPoids();

                // Si la station voisine est la station d'arrivée, on peut arrêter
                if (stationVoisine.equals(arrivee)) {
                    System.out.println("Poids total de l'arbre couvrant minimum : " + poidsTotal + " secondes.");
                    for (Liaison pro : liaisonsArbre){
                        System.out.println(pro.getStation1().getNom());
                    }
                    return liaisonsArbre;
                }

                // Ajouter toutes les liaisons sortantes de la station voisine dans la file de priorité
                for (Liaison prochaineLiaison : adjacences) {
                    if (prochaineLiaison.getStation1().equals(stationVoisine) && !arbreCouvrant.contains(prochaineLiaison.getStation2())) {
                        filePriorite.add(prochaineLiaison);
                    }
                }
            }
        }

        // Si le programme arrive ici, cela signifie qu'il n'y a pas de chemin complet entre les stations
        System.out.println("Poids total de l'arbre couvrant minimum : " + poidsTotal + " secondes.");
        return new ArrayList<>();
    }



    /**
     *  @autor : Ugo
     * Affiche l'itinéraire optimal entre une station de départ et une station d'arrivée.
     *
     * @param depart  Station de départ.
     * @param arrivee Station d'arrivée.
     */
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

        String formattedTime = formatTemps(totalTemps);

        System.out.println("- Vous devriez arriver à " + arrivee.getNom() + " dans environ " + formattedTime);
    }

    /**
     *  @autor : Sahkana
     * Formate le temps en secondes en minutes ou heures.
     *
     * @param totalSecondes Temps total en secondes.
     * @return Temps formaté en chaînes de caractères (secondes, minutes ou heures).
     */
    private String formatTemps(int totalSecondes) {
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
     * Obtient toutes les liaisons associées à une station donnée.
     *
     * @param station La station dont on veut connaître les liaisons.
     * @return Liste des liaisons associées à la station.
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

    /**
     * Obtient la liaison entre deux stations spécifiques.
     *
     * @param station1 La première station.
     * @param station2 La deuxième station.
     * @return La liaison entre les deux stations, ou null si aucune liaison n'existe entre elles.
     */
    public Liaison getLiaisonBetweenStations(Station station1, Station station2) {
        // Parcourt toutes les liaisons pour vérifier si l'une d'elles relie les deux stations spécifiées
        for (Liaison liaison : adjacences) {
            if ((liaison.getStation1().equals(station1) && liaison.getStation2().equals(station2)) ||
                    (liaison.getStation1().equals(station2) && liaison.getStation2().equals(station1))) {
                return liaison; // Retourne la liaison si les deux stations sont reliées
            }
        }
        return null; // Si aucune liaison n'a été trouvée entre les deux stations
    }



}
