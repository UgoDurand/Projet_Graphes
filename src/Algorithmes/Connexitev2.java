package Algorithmes;

import java.util.ArrayList;
import java.util.List;

public class Connexitev2 {
    private static void parcoursProfondeur(int sommet, boolean[] visite, List<List<Integer>> graphe){
        visite[sommet] = true;
        for (int voisin : graphe.get(sommet)) {
            if (!visite[voisin]) {
                parcoursProfondeur(voisin, visite, graphe);
            }
        }
    }

    public static boolean estConnexe(int nbSommet, List<List<Integer>> graphe) {
        boolean[] visite = new boolean[nbSommet];

        parcoursProfondeur(0, visite, graphe);

        for (boolean visites : visite){
            if (!visites){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        // Exemple de graphe avec 4 sommets (0, 1, 2, 3)
        int nombreSommets = 4;
        List<List<Integer>> graphe = new ArrayList<>();

        // Initialiser les listes d'adjacence pour chaque sommet
        for (int i = 0; i < nombreSommets; i++) {
            graphe.add(new ArrayList<>());
        }

        // Ajouter des arêtes (graphe non orienté)
        graphe.get(0).add(1);
        graphe.get(1).add(0);
        graphe.get(0).add(2);
        graphe.get(2).add(0);
        graphe.get(1).add(3);
        graphe.get(3).add(1);

        // Vérifier si le graphe est connexe
        if (estConnexe(nombreSommets, graphe)) {
            System.out.println("Le graphe est connexe.");
        } else {
            System.out.println("Le graphe n'est pas connexe.");
        }
    }
}
