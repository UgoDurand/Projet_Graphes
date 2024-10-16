import Algorithmes.Station;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GenererSommets {

    public static void main(String [] args){
        String fichier = "data/station.txt";
        ArrayList<Station> sommets = new ArrayList<Station>();

        try {
            BufferedReader bf = new BufferedReader(new FileReader(fichier));
            String ligne;
            while ((ligne = bf.readLine()) != null){
                String[] parts = ligne.split(";");

                String id = parts[0];
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

        for (Station station : sommets){
            System.out.println(station);
        }
    }

}
