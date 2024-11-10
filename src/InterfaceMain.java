import Algorithmes.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class InterfaceMain extends Application {
    private ArrayList<Station> stations;
    private ArrayList<Liaison> liaisons;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Charger les données
        Metro metroMap = new Metro();
        metroMap.lireStationsAvecCoordonnees();  // Lecture des stations avec coordonnées
        metroMap.lireLiaisonsAvecTemps();       // Lecture des liaisons avec les temps

        stations = metroMap.getStations();
        liaisons = metroMap.getLiaisons();

        // Créer la scène et le panneau
        Pane pane = new Pane();
        drawStations(pane);
        drawLiaisons(pane);

        // Créer la scène JavaFX
        Scene scene = new Scene(pane, 800, 600);
        primaryStage.setTitle("Carte du Métro Parisien");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Appliquer un zoom à la carte
        Scale scale = new Scale(0.5, 0.5); // Valeurs < 1 pour dézoomer
        pane.getTransforms().add(scale);
    }

    private void drawStations(Pane pane) {
        double factor = 4.0; // Augmenter le facteur d'espacement pour mieux espacer les stations
        double translationY = 200; // Décalage plus élevé pour "monter" encore plus les stations

        // Appliquer un zoom/dézoom plus fort
        Scale scale = new Scale(0.5, 0.5);  // Dézoomer encore plus pour voir plus de stations
        pane.getTransforms().add(scale);

        // Appliquer une translation verticale pour déplacer toutes les stations vers le haut
        pane.setTranslateY(-translationY); // Augmenter le décalage vers le haut pour afficher plus d'espace

        for (Station station : stations) {
            // Calculer la position de la station avec le facteur d'espacement
            double x = station.getX() * factor;
            double y = station.getY() * factor + translationY; // Appliquer la translation verticale

            // Dessiner un rectangle autour de la station pour la rendre plus visible
            Rectangle stationRectangle = new Rectangle(x - 10, y - 10, 20, 20); // Création d'un rectangle autour
            stationRectangle.setFill(Color.LIGHTBLUE); // Remplir le rectangle en bleu clair
            pane.getChildren().add(stationRectangle);

            // Dessiner le cercle représentant la station
            Circle circle = new Circle(x, y, 5, Color.BLUE);
            pane.getChildren().add(circle);

            // Créer le texte de la station avec une taille augmentée pour une meilleure lisibilité
            Text stationName = new Text(x + 10, y, station.getNom());
            stationName.setFill(Color.BLACK); // Couleur du texte
            stationName.setFont(new Font("Arial", 25)); // Augmenter la taille du texte
            pane.getChildren().add(stationName);
        }
    }

    private void drawLiaisons(Pane pane) {
        double factor = 4.0; // Augmenter le facteur d'espacement pour les liaisons
        double translationY = 200; // Décalage pour les liaisons afin de les déplacer vers le haut

        for (Liaison liaison : liaisons) {
            Station station1 = liaison.getStation1();
            Station station2 = liaison.getStation2();

            // Récupérer les coordonnées des stations avec le facteur d'espacement
            double x1 = station1.getX() * factor;
            double y1 = station1.getY() * factor + translationY; // Appliquer la translation verticale
            double x2 = station2.getX() * factor;
            double y2 = station2.getY() * factor + translationY; // Appliquer la translation verticale

            // Dessiner la ligne de liaison entre les deux stations
            Line line = new Line(x1, y1, x2, y2);
            line.setStroke(getLineColor(liaison.getLigneMetro())); // Ajouter la couleur de la ligne
            pane.getChildren().add(line);
        }
    }


    private Color getLineColor(String ligne) {
        // Définir les couleurs des lignes de métro
        switch (ligne) {
            case "1":
                return Color.rgb(255, 213, 0); // Jaune pour la ligne 1
            case "2":
                return Color.rgb(0, 133, 199); // Bleu ciel pour la ligne 2
            case "3":
                return Color.rgb(193, 84, 151); // Rose pour la ligne 3
            case "3bis":
                return Color.rgb(129, 198, 131); // Vert clair pour la ligne 3bis
            case "4":
                return Color.rgb(136, 28, 36); // Violet pour la ligne 4
            case "5":
                return Color.rgb(255, 86, 0); // Orange pour la ligne 5
            case "6":
                return Color.rgb(118, 190, 68); // Vert clair pour la ligne 6
            case "7":
                return Color.rgb(201, 170, 46); // Jaune moutarde pour la ligne 7
            case "7bis":
                return Color.rgb(130, 214, 231); // Bleu turquoise pour la ligne 7bis
            case "8":
                return Color.rgb(206, 137, 36); // Marron clair pour la ligne 8
            case "9":
                return Color.rgb(109, 198, 189); // Bleu vert pour la ligne 9
            case "10":
                return Color.rgb(235, 137, 20); // Orange foncé pour la ligne 10
            case "11":
                return Color.rgb(246, 130, 121); // Rose pour la ligne 11
            case "12":
                return Color.rgb(0, 125, 50); // Vert foncé pour la ligne 12
            case "13":
                return Color.rgb(137, 198, 82); // Vert pomme pour la ligne 13
            case "14":
                return Color.rgb(136, 28, 36); // Rouge foncé pour la ligne 14
            default:
                return Color.GRAY; // Couleur par défaut pour les autres lignes
        }
    }
}
