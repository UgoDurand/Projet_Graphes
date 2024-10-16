package Algorithmes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Metro {

    ArrayList<Station> stations = new ArrayList<>();  // Stockage des stations
    ArrayList<Liaison> liaisons = new ArrayList<>();      // Stockage des liaisons

    // Méthode pour lire les liaisons
    public void lireLiaisons() {
        String fichierLiaison = "data/liaison.txt";

        try {
            BufferedReader bf = new BufferedReader(new FileReader(fichierLiaison));
            String ligne;
            while ((ligne = bf.readLine()) != null){
                String[] parts = ligne.split(";");

                int Idstation1 = Integer.parseInt(parts[0].trim());
                int Idstation2 = Integer.parseInt(parts[1].trim());
                int temps = Integer.parseInt(parts[2].trim());

                Station station1 = null;
                Station station2 = null;

                for (Station station : stations){
                    if (station.getId() == Idstation1){
                        station1 = station;
                    }
                    if (station.getId() == Idstation2){
                        station2 = station;
                    }
                }

                Liaison liaison = new Liaison(station1, station2, temps );
                liaisons.add(liaison);
            }
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void lireStations() {
        String fichierStation = "data/station.txt";

        try {
            BufferedReader bf = new BufferedReader(new FileReader(fichierStation));
            String ligne;
            while ((ligne = bf.readLine()) != null){
                String[] parts = ligne.split(";");

                int id = Integer.parseInt(parts[0].trim());
                String nom = parts[1];
                String ligneMetro = parts[2];
                boolean estTerminus = Boolean.parseBoolean(parts[3]);
                int branchement = Integer.parseInt(parts[4].trim());

                Station station = new Station(id, nom, ligneMetro, estTerminus, branchement);
                stations.add(station);
            }
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void afficherStations() {
        for (Station station : stations) {
            System.out.println(station);
        }
    }

    public void afficherLiaisons() {
        for (Liaison liaison : liaisons) {
            System.out.println(liaison);
        }
    }

    public ArrayList<Station> getStations() {
        return stations;
    }

    public void setStations(ArrayList<Station> stations) {
        this.stations = stations;
    }

    public ArrayList<Liaison> getLiaisons() {
        return liaisons;
    }

    public void setLiaisons(ArrayList<Liaison> liaisons) {
        this.liaisons = liaisons;
    }
}
