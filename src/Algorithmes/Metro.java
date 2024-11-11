package Algorithmes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * La classe Metro permet de modéliser un réseau de métro avec ses stations et ses liaisons.
 * Elle fournit des méthodes pour lire les données des stations et des liaisons à partir de fichiers,
 * ainsi que des méthodes pour afficher et obtenir les stations et les liaisons.
 */
public class Metro {

    private ArrayList<Station> stations = new ArrayList<>();  // Liste des stations du métro
    private ArrayList<Liaison> liaisons = new ArrayList<>();  // Liste des liaisons entre les stations

    /**
     * @ author Sahkana
     * Lit les liaisons entre stations à partir d'un fichier texte et les ajoute à la liste des liaisons.
     * Le fichier est supposé contenir des lignes au format "idStation1;idStation2;temps".
     */
    public void lireLiaisons() {
        String fichierLiaison = "data/liaison.txt";

        try {
            BufferedReader bf = new BufferedReader(new FileReader(fichierLiaison));
            String ligne;
            while ((ligne = bf.readLine()) != null) {
                String[] parts = ligne.split(";");

                int Idstation1 = Integer.parseInt(parts[0].trim());
                int Idstation2 = Integer.parseInt(parts[1].trim());
                int temps = Integer.parseInt(parts[2].trim());

                Station station1 = null;
                Station station2 = null;

                for (Station station : stations) {
                    if (station.getId() == Idstation1) {
                        station1 = station;
                    }
                    if (station.getId() == Idstation2) {
                        station2 = station;
                    }
                }

                Liaison liaison = new Liaison(station1, station2, temps);
                liaisons.add(liaison);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @author Sahkana
     * Lit les informations des stations à partir d'un fichier texte et les ajoute à la liste des stations.
     * Le fichier est supposé contenir des lignes au format "id;nom;ligne;estTerminus;branchement".
     */
    public void lireStations() {
        String fichierStation = "data/station.txt";

        try {
            BufferedReader bf = new BufferedReader(new FileReader(fichierStation));
            String ligne;
            while ((ligne = bf.readLine()) != null) {
                String[] parts = ligne.split(";");

                int id = Integer.parseInt(parts[0].trim());
                String nom = parts[1];
                String ligneMetro = parts[2];
                boolean estTerminus = Boolean.parseBoolean(parts[3]);
                int branchement = Integer.parseInt(parts[4].trim());

                Station station = new Station(id, nom, ligneMetro, estTerminus, branchement);
                stations.add(station);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @author Nam
     * Affiche toutes les stations du réseau de métro en utilisant leur méthode toString().
     */
    public void afficherStations() {
        for (Station station : stations) {
            System.out.println(station);
        }
    }

    /**
     * @author Ugo
     * Affiche toutes les liaisons entre les stations du réseau de métro en utilisant leur méthode toString().
     */
    public void afficherLiaisons() {
        for (Liaison liaison : liaisons) {
            System.out.println(liaison);
        }
    }

    /**
     * Retourne la liste des stations du réseau de métro.
     *
     * @return Liste des stations.
     */
    public ArrayList<Station> getStations() {
        return stations;
    }

    /**
     * Retourne la liste des liaisons entre les stations du réseau de métro.
     *
     * @return Liste des liaisons.
     */
    public ArrayList<Liaison> getLiaisons() {
        return liaisons;
    }
}
