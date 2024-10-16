package Algorithmes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Metro {

    public HashMap<String, Station> stations = new HashMap<>();  // Stockage des stations
    public ArrayList<Liaison> liaisons = new ArrayList<>();      // Stockage des liaisons
    private Graphe graphe;

    public Metro() {
        this.graphe = new Graphe();
    }


    public void lireStations(String fichier) {
        try (BufferedReader br = new BufferedReader(new FileReader(fichier))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                // Ignore les lignes vides ou celles qui ne commencent pas par un sommet (V)
                if (ligne.trim().isEmpty() || !ligne.startsWith("V")) {
                    continue;
                }

                // Supprime le 'V' initial et découper la ligne en deux parties : nom de la station + autres données
                ligne = ligne.substring(1).trim();  // Retirer le "V" au début

                // Trouve la position du premier point-virgule (qui sépare le nom des autres valeurs)
                int indexPremierPointVirgule = ligne.indexOf(';');
                if (indexPremierPointVirgule == -1) {
                    System.out.println("Ligne mal formatée (pas de point-virgule trouvé) : " + ligne);
                    continue;
                }

                // Extrait la partie avant le premier point-virgule : numéro + nom de la station
                String partieNom = ligne.substring(0, indexPremierPointVirgule).trim();

                String partieRestante = ligne.substring(indexPremierPointVirgule + 1).trim();

                // Trouve les positions des points-virgules restants
                String[] partiesValeurs = partieRestante.split(";");

                // Vérifie qu'on a bien les 2 valeurs après le nom (numéro de ligne et si terminus)
                if (partiesValeurs.length != 2) {
                    System.out.println("Ligne mal formatée (mauvais nombre d'éléments après le nom) : " + ligne);
                    continue;
                }

                // Extrait les deux premières valeurs et la dernière (après l'espace)
                String numeroLigneStr = partiesValeurs[0].trim();
                String terminusEtBranchement = partiesValeurs[1].trim();

                // terminus et branchement
                String[] terminusEtBranchementParts = terminusEtBranchement.split(" ");
                if (terminusEtBranchementParts.length != 2) {
                    System.out.println("Ligne mal formatée (mauvais nombre d'éléments après terminus) : " + ligne);
                    continue;
                }

                // Sépare le numéro de sommet et le nom de la station
                String[] nomParties = partieNom.split(" ", 2); //
                if (nomParties.length != 2) {
                    System.out.println("Ligne mal formatée (numéro de sommet ou nom manquant) : " + ligne);
                    continue;
                }
                String numSommet = nomParties[0].trim();
                int numero = Integer.parseInt(numSommet);// Numéro de la station
                String nomSommet = nomParties[1].trim(); // Nom de la station

                // Récupère le numéro de ligne
                int numeroLigne = 0;
                try {
                    numeroLigne = Integer.parseInt(numeroLigneStr);
                } catch (NumberFormatException e) {
                    System.out.println("Erreur de conversion pour le numéro de ligne : " + numeroLigneStr);
                    continue;
                }

                // Convertis le statut de terminus en booléen
                boolean siTerminus = false;
                try {
                    siTerminus = Boolean.parseBoolean(terminusEtBranchementParts[0].trim());
                } catch (Exception e) {
                    System.out.println("Erreur de conversion pour le terminus : " + terminusEtBranchementParts[0]);
                    continue;
                }

                // Convertis le branchement en entier
                int branchement = 0;
                try {
                    branchement = Integer.parseInt(terminusEtBranchementParts[1].trim());
                } catch (NumberFormatException e) {
                    System.out.println("Erreur de conversion pour le branchement : " + terminusEtBranchementParts[1]);
                    continue;
                }

                // Ajoute la station au graphe
                Station station = new Station(numSommet, nomSommet, numeroLigne, siTerminus, branchement);
                //System.out.println("Station ajoutée : " + station);
                this.stations.put(String.valueOf(numero), station);

                //System.out.println(this.stations);
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        }
    }

    // Méthode pour lire les liaisons
    public void lireLiaisons(String fichier) {
        try (BufferedReader br = new BufferedReader(new FileReader(fichier))) {
            String ligne;

            while ((ligne = br.readLine()) != null) {
                // Ignore les lignes vides ou celles qui ne commencent pas par une liaison (E)
                if (ligne.trim().isEmpty() || !ligne.startsWith("E")) {
                    continue;
                }

                ligne = ligne.substring(1).trim();  // Retirer le "E" au début

                // Découpe la ligne sur les espaces
                String[] parties = ligne.split(" ");
                if (parties.length != 3) {
                    System.out.println("Ligne mal formatée (mauvais nombre d'éléments pour une liaison) : " + ligne);
                    continue;
                }

                // Extrait les numéros des stations et le temps de trajet
                String idStation1 = parties[0].trim();
                String idStation2 = parties[1].trim();
                int temps = 0;
                try {
                    temps = Integer.parseInt(parties[2].trim());
                } catch (NumberFormatException e) {
                    System.out.println("Erreur de conversion pour le temps de trajet : " + parties[2].trim());
                    continue;
                }

                // Vérifie que les stations existent dans la HashMap
                Station station1 = stations.get(idStation1);
                Station station2 = stations.get(idStation2);

                if (station1 != null && station2 != null) {
                    // Créer l'objet Liaison et l'ajouter à la liste des liaisons
                    Liaison liaison = new Liaison(station1, station2, temps);
                    liaisons.add(liaison);
                    //System.out.println("Liaison ajoutée entre " + station1.getNom() + " et " + station2.getNom() + " en " + temps + " secondes.");
                } else {
                    System.out.println("Stations non trouvées pour la liaison : " + ligne);
                }
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture du fichier : " + fichier);
            e.printStackTrace();
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
        metro.afficherLiaisons();
        metro.graphe.afficherGraphe();
    }
}