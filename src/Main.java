import Algorithmes.*;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        Metro metro = new Metro();
        metro.lireStations();
        metro.lireLiaisons();
        metro.afficherStations();
        metro.afficherLiaisons();

        Graphe graphe = new Graphe();
        graphe.construireGraphe(metro.getStations(), metro.getLiaisons());
        graphe.afficherGraphe();

        Station stationDepart = metro.getStations().get(0);
        Station stationArrivee = metro.getStations().get(2);

        List<Station> chemin = graphe.plusCourtChemin(stationDepart, stationArrivee);

        if (!chemin.isEmpty()) {
            System.out.println("Plus court chemin de " + stationDepart.getNom() + " à " + stationArrivee.getNom() + " :");
            for (Station station : chemin) {
                System.out.print(station.getNom() + " ligne " + station.getLigne() + " -> ");
            }
            System.out.println("T'es arrivé chef !");
        } else {
            System.out.println("Aucun chemin trouvé entre " + stationDepart.getNom() + " et " + stationArrivee.getNom());
        }

        System.out.println(graphe.estConnexe(metro.getStations()));
    }
}