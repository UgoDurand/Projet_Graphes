import Algorithmes.Graphe;
import Algorithmes.Liaison;
import Algorithmes.Metro;
import Algorithmes.Station;

import java.util.List;
import java.util.Scanner;

/**
 * Classe principale du programme qui permet de simuler la recherche du plus court chemin
 * entre deux stations de métro, ainsi que d'afficher les informations relatives aux stations,
 * liaisons, et calculer l'arbre couvrant minimal via l'algorithme de Prim.
 */
public class Main {

    /**
     * Méthode principale pour exécuter le programme de simulation du métro.
     * Elle permet de :
     * - Charger les stations et les liaisons depuis les fichiers de données.
     * - Afficher les stations et les liaisons.
     * - Permettre à l'utilisateur de sélectionner une station de départ et d'arrivée,
     * puis de calculer et afficher le plus court chemin entre ces stations.
     * - Vérifier si le graphe est connexe.
     * - Afficher l'arbre couvrant minimal à partir de la première et dernière station.
     *
     * @param args Arguments de la ligne de commande (non utilisés dans ce programme).
     */
    public static void main(String[] args) {

        Metro metro = new Metro();
        metro.lireStations();
        metro.lireLiaisons();
        //metro.afficherStations();
        //metro.afficherLiaisons();

        Graphe graphe = new Graphe();
        graphe.construireGraphe(metro.getStations(), metro.getLiaisons());
        //graphe.afficherGraphe();

        boolean estConnexe = graphe.estConnexe(metro.getStations());
        System.out.println("Le graphe est-il connexe ? " + (estConnexe ? "Oui" : "Non"));

        System.out.println("Sélectionnez une station de départ parmi les stations suivantes :");
        List<Station> stations = metro.getStations();
        for (int i = 0; i < stations.size(); i++) {
            System.out.println(i + ": " + stations.get(i).getNom() + " (Ligne " + stations.get(i).getLigne() + ")");
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Entrez le numéro de la station de départ :");
        int depart = scanner.nextInt();
        Station stationDepart = stations.get(depart);

        System.out.println("Entrez le numéro de la station d'arrivée :");
        int arrivee = scanner.nextInt();
        Station stationArrivee = stations.get(arrivee);

        List<Station> chemin = graphe.bellmanFord(stationDepart, stationArrivee);

        if (!chemin.isEmpty()) {
            System.out.println("Plus court chemin de " + stationDepart.getNom() + " à " + stationArrivee.getNom() + " :");
            for (Station station : chemin) {
                System.out.print(station.getNom() + " ligne " + station.getLigne() + " -> ");
            }
            System.out.println("T'es arrivé !");
        } else {
            System.out.println("Aucun chemin trouvé entre " + stationDepart.getNom() + " et " + stationArrivee.getNom());
        }

        String itineraire = graphe.afficherItineraire(chemin);
        System.out.println(itineraire);

        Station station1 = stations.getFirst();
        afficherACPM(graphe, station1);
    }

    /**
     * @param graphe   Le graphe dans lequel calculer l'arbre couvrant.
     * @param station1 La station de départ de l'algorithme de Prim.
     * @author Sahkana
     * Affiche l'arbre couvrant minimal calculé par l'algorithme de Prim.
     */
    private static void afficherACPM(Graphe graphe, Station station1) {
        List<Liaison> arbreCouvrantGraphe = graphe.algorithmePrim(station1);

        if (!arbreCouvrantGraphe.isEmpty()) {
            System.out.println("Arbre couvrant minimal (Prim) à partir de " + station1.getNom() + " (Ligne " + station1.getLigne() + ") :");
            for (Liaison liaison : arbreCouvrantGraphe) {
                System.out.println(liaison.getStation1().getNom() + " (Ligne " + station1.getLigne() + ") -> " + liaison.getStation2().getNom() +
                        " (" + liaison.getPoids() + "s)");
            }
        } else {
            System.out.println("Pas d'arbre couvrant trouvé.");
        }
    }
}
