import Algorithmes.Graphe;
import Algorithmes.Liaison;
import Algorithmes.Metro;
import Algorithmes.Station;

public class Main {

    public static void main(String[] args) {

        Metro metro = new Metro();
        metro.lireStations();
        metro.lireLiaisons();
        metro.afficherStations();
        metro.afficherLiaisons();

    }
}