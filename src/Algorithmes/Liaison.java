package Algorithmes;

public class Liaison {
    private Station station1;  // Première station
    private Station station2;  // Deuxième station
    private int temps;         // Temps en secondes entre les deux stations

    public Liaison(Station station1, Station station2, int temps) {
        this.station1 = station1;
        this.station2 = station2;
        this.temps = temps;  // Le poids
    }

   public Station getStation1() {
        return station1;
   }

   public Station getStation2() {
        return station2;
   }

   public int getPoids() {
        return temps;
   }

    @Override
    public String toString() {
        return station1 + " -- > " + station2 + " en " + temps + " s.";
    }
}
