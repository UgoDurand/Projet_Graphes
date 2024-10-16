package Algorithmes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Metro {

    HashMap<String, Station> stations = new HashMap<>();  // Stockage des stations
    ArrayList<Liaison> liaisons = new ArrayList<>();      // Stockage des liaisons

    // Méthode pour lire les liaisons
    public void lireLiaisons(String fichier) {
        try (BufferedReader br = new BufferedReader(new FileReader(fichier))) {
            String ligne;

            while ((ligne = br.readLine()) != null) {
                // Ignorer les lignes vides ou celles qui ne sont pas des liaisons
                if (ligne.trim().isEmpty() || !ligne.startsWith("E")) {
                    continue;
                }

                // Traitement de la ligne des liaisons
                ligne = ligne.substring(1).trim();  // Retirer le 'E' initial
                String[] parties = ligne.split(";");

                if (parties.length < 3) {
                    //System.err.println("Lignes : " + ligne);
                    continue;
                }

                String idStation1 = parties[0].trim();
                String idStation2 = parties[1].trim();
                int temps = Integer.parseInt(parties[2].trim());

                // Vérifier que les stations existent dans la HashMap
                Station station1 = stations.get(idStation1);
                Station station2 = stations.get(idStation2);

                if (station1 != null && station2 != null) {
                    // Créer l'objet Liaison et l'ajouter à la liste
                    Liaison liaison = new Liaison(station1, station2, temps);
                    liaisons.add(liaison);
                } else {
                    System.err.println("Stations non trouvées pour la liaison : " + ligne);
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier : " + fichier);
            e.printStackTrace();
        }
    }

    public void lireStations(String fichier) {
        try (BufferedReader br = new BufferedReader(new FileReader(fichier))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                // Ignorer les lignes vides
                if (ligne.trim().isEmpty()) {
                    continue;
                }

                // Découper la ligne en parties en utilisant le délimiteur ";"
                String[] parties = ligne.split(";");

                // Vérifier qu'on a bien le nombre attendu d'éléments
                if (parties.length < 5) {
                    System.out.println("Ligne mal formatée : " + ligne);
                    continue;  // Ignorer cette ligne mal formatée
                }

                // Analyser les valeurs
                String type = parties[0].trim();  // "V"
                String numSommet = parties[1].trim();  // Identifiant de la station
                String nomSommet = parties[2].trim();  // Nom de la station

                // S'il y a des espaces dans le nom de la station, les traiter
                if (parties.length > 3) {
                    for (int i = 3; i < parties.length - 2; i++) {
                        nomSommet += " " + parties[i].trim();
                    }
                }

                // Récupérer le numéro de ligne
                int numeroLigne = 0;
                try {
                    numeroLigne = Integer.parseInt(parties[parties.length - 3].trim());  // Dernier champ avant terminus
                } catch (NumberFormatException e) {
                    System.out.println("Erreur de conversion pour le numéro de ligne : " + parties[parties.length - 3].trim());
                    continue;  // Passer à la ligne suivante
                }

                // Convertir le statut de terminus en booléen
                boolean siTerminus = false;
                try {
                    siTerminus = Boolean.parseBoolean(parties[parties.length - 2].trim());
                } catch (Exception e) {
                    System.out.println("Erreur de conversion pour le terminus : " + parties[parties.length - 2].trim());
                    continue;  // Passer à la ligne suivante
                }

                // Convertir le branchement en entier
                int branchement = 0;
                try {
                    branchement = Integer.parseInt(parties[parties.length - 1].trim());
                } catch (NumberFormatException e) {
                    System.out.println("Erreur de conversion pour le branchement : " + parties[parties.length - 1].trim());
                    continue;  // Passer à la ligne suivante
                }

                // Ajouter la station au graphe
                Station station = new Station(numSommet, nomSommet, "numeroLigne", siTerminus, branchement);
                System.out.println("Station ajoutée : " + station);  // Debug station ajoutée
                this.stations.put(numSommet, station);
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        }
    }

    public void afficherStations() {
        for (Station station : stations.values()) {
            System.out.println(station);
        }
    }

    public void afficherLiaisons() {
        for (Liaison liaison : liaisons) {
            System.out.println(liaison);
        }
    }

    public static void main(String[] args) {
        Metro metro = new Metro();
        metro.lireStations("data/metro.txt");
        metro.lireLiaisons("data/metro.txt");
        metro.afficherStations();
    }
}
