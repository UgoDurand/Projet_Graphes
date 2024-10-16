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
        Station stationArrivee = metro.getStations().get(1);

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

        boolean estConnexe = graphe.estConnexe(metro.getStations());
        System.out.println("Le graphe est-il connexe ? " + (estConnexe ? "Oui" : "Non"));

        List<Liaison> arbreCouvrant = graphe.algorithmePrim(stationDepart, stationArrivee);
        if (!arbreCouvrant.isEmpty()) {
            System.out.println("Arbre couvrant minimal (Prim) à partir de " + stationDepart.getNom() + " (Ligne " + stationDepart.getLigne() + ") :");
            for (Liaison liaison : arbreCouvrant) {
                System.out.println(liaison.getStation1().getNom() + " (Ligne " + stationDepart.getLigne() + ")"+ " -> " + liaison.getStation2().getNom() +
                        " (" + liaison.getPoids() + "s)");
            }
        } else {
            System.out.println("Pas d'arbre couvrant trouvé.");
        }

        graphe.afficherItineraire(stationDepart, stationArrivee);
    }
}
