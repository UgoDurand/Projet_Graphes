import Algorithmes.Liaison;
import Algorithmes.Station;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GenererObjets {

    public static void main(String [] args){

        /* Génération des stations */

        String fichierStation = "data/station.txt";
        ArrayList<Station> sommets = new ArrayList<Station>();

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
                sommets.add(station);
            }
        }

        catch (IOException e) {
            e.printStackTrace();
        }

        /* Génération des laisons entre les stations */

        String fichierLiaison = "data/liaison.txt";
        ArrayList<Liaison> liaisons = new ArrayList<Liaison>();

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

                for (Station station : sommets){
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


        /* Affichage des stations et des liaisons */
        for (Station station : sommets){
            System.out.println(station);
        }
        for (Liaison liaison : liaisons){
            System.out.println(liaison);
        }
    }

}
