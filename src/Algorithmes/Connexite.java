package Algorithmes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
public class Connexite {

    public static boolean estConnexe(Metro metro) {
        if (metro.getStations().isEmpty()) {
            System.out.println("Le graphe est vide.");
            return false;
        }

        Set<Station> visitees = new HashSet<>();
        Station stationDepart = metro.getStations().iterator().next(); // Prendre la première station
        parcours_profondeur(stationDepart, metro.getGraphe(), visitees);

        return visitees.size() == metro.getStations().size();
    }

    private static void parcours_profondeur(Station station, Graphe graphe, Set<Station> visitees) {
        visitees.add(station);
        for (Liaison liaison : graphe.getLiaisons()) {
            Station stationSuivante = liaison.getStation2();
            if (!visitees.contains(stationSuivante)) {
                parcours_profondeur(stationSuivante, graphe, visitees);
            }
        }
    }

    public static void ajouterLiaisonsManquantes(Graphe graphe) {
        Set<Station> visitees = new HashSet<>();
        List<Station> composantes = new ArrayList<>();

        // Parcours pour identifier les composantes connexes
        for (Station station : graphe.getStations()) {
            if (!visitees.contains(station)) {
                parcours_profondeur(station, graphe, visitees);
                composantes.add(station); // Sauvegarde d'une station de chaque composante
            }
        }

        // Si plusieurs composantes existent, on les relie
        if (composantes.size() > 1) {
            for (int i = 1; i < composantes.size(); i++) {
                int distance = 1;
                graphe.ajouterLiaison(composantes.get(i - 1), composantes.get(i), distance);
                System.out.println("Ajout de la liaison entre " + composantes.get(i - 1).getNom() + " et " + composantes.get(i).getNom() + " avec distance " + distance);
            }
        }
    }


} **/
