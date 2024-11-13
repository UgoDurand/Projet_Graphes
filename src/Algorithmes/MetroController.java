package Algorithmes;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import java.util.List;

public class MetroController {
    private Metro metro;
    private Graphe graphe;
    private Pane pane;
    private Station stationDepart;
    private Station stationArrivee;

    public MetroController(Metro metro, Graphe graphe, Pane pane) {
        this.metro = metro;
        this.graphe = graphe;
        this.pane = pane;
    }

    public void afficherReseau() {
        // Affichage des liaisons
        for (Liaison liaison : metro.getLiaisons()) {
            Line line = new Line(
                    liaison.getStation1().getX(),
                    liaison.getStation1().getY(),
                    liaison.getStation2().getX(),
                    liaison.getStation2().getY()
            );
            line.setStroke(Color.GRAY);
            pane.getChildren().add(line);
        }

        // Affichage des stations avec interactions
        for (Station station : metro.getStations()) {
            Circle circle = new Circle(station.getX(), station.getY(), 5, Color.BLUE);
            Text text = new Text(station.getX() + 10, station.getY(), station.getNom());

            circle.setOnMouseClicked(e -> {
                if (stationDepart == null) {
                    stationDepart = station;
                    circle.setFill(Color.GREEN);
                } else if (stationArrivee == null) {
                    stationArrivee = station;
                    circle.setFill(Color.RED);
                    afficherPlusCourtChemin();
                }
            });

            pane.getChildren().addAll(circle, text);
        }
    }

    public void afficherPlusCourtChemin() {
        if (stationDepart != null && stationArrivee != null) {
            List<Station> chemin = graphe.plusCourtChemin(stationDepart, stationArrivee);

            if (!chemin.isEmpty()) {
                Station previous = null;
                for (Station station : chemin) {
                    if (previous != null) {
                        Line line = new Line(
                                previous.getX(),
                                previous.getY(),
                                station.getX(),
                                station.getY()
                        );
                        line.setStroke(Color.YELLOW);
                        pane.getChildren().add(line);
                    }
                    previous = station;
                }
            } else {
                System.out.println("Aucun chemin trouvé entre " + stationDepart.getNom() + " et " + stationArrivee.getNom());
            }
        }
    }
}
