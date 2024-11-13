import Algorithmes.Graphe;
import Algorithmes.Liaison;
import Algorithmes.Metro;
import Algorithmes.Station;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * Partie Bonus
 * Application JavaFX représentant une carte du métro parisien avec les stations et les liaisons entre elles.
 */
public class InterfaceMain extends Application {
    private static final int ITEMS_PER_PAGE = 20;
    private ArrayList<Station> stations;
    private ArrayList<Liaison> liaisons;
    private double initialX;
    private double initialY;
    private double offsetX = 0;
    private double offsetY = 0;
    private double zoomFactor = 1.0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Metro metroMap = new Metro();
        metroMap.lireStationsAvecCoordonnees();
        metroMap.lireLiaisonsAvecTemps();

        stations = metroMap.getStations();
        liaisons = metroMap.getLiaisons();

        Pane pane = new Pane();
        drawStations(pane);
        drawLiaisons(pane);

        ListView<String> stationDepartListView = new ListView<>();
        stationDepartListView.setPrefHeight(200);
        stationDepartListView.setLayoutX(2200);
        stationDepartListView.setLayoutY(150);
        loadStations(stationDepartListView, 0);

        ListView<String> stationArriveeListView = new ListView<>();
        stationArriveeListView.setPrefHeight(200);
        stationArriveeListView.setLayoutX(2200);
        stationArriveeListView.setLayoutY(300);
        loadStations(stationArriveeListView, 0);

        stationDepartListView.setOnMouseClicked(event -> {
            String selectedStation = stationDepartListView.getSelectionModel().getSelectedItem();
            System.out.println("Station de départ sélectionnée : " + selectedStation);
        });

        stationArriveeListView.setOnMouseClicked(event -> {
            String selectedStation = stationArriveeListView.getSelectionModel().getSelectedItem();
            System.out.println("Station d'arrivée sélectionnée : " + selectedStation);
        });

        VBox inputBox = new VBox(10, stationDepartListView, stationArriveeListView);
        inputBox.setLayoutY(150);
        inputBox.setLayoutX(2200);

        Button treeButton = new Button("Arbre couvrant du graphe");
        Button parcoursButton = new Button("Pluc court chemin");

        VBox buttonBox = new VBox(10, parcoursButton, treeButton);
        double graphHeight = 4000;
        buttonBox.setLayoutY(graphHeight + 20);
        buttonBox.setLayoutX(2200);

        buttonBox.setStyle("-fx-background-color: white; -fx-padding: 10; -fx-border-radius: 10; -fx-background-radius: 10; -fx-border-color: white; -fx-border-width: 2;");

        Label messageLabel = new Label();
        messageLabel.setLayoutX(2200);
        messageLabel.setLayoutY(500);
        messageLabel.setTextFill(Color.LIGHTBLUE);
        messageLabel.setFont(new Font("Arial", 16));

        ScrollPane scrollPane = new ScrollPane(pane);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setPrefSize(1200, 800);

        parcoursButton.setOnAction(e -> {
            String departNom = stationDepartListView.getSelectionModel().getSelectedItem();
            String arriveeNom = stationArriveeListView.getSelectionModel().getSelectedItem();

            if (departNom == null || arriveeNom == null) {
                messageLabel.setText("Erreur : Veuillez sélectionner des stations valides.");
                messageLabel.setTextFill(Color.RED);
            } else {
                Station stationDepart = null;
                Station stationArrivee = null;

                for (Station station : stations) {
                    if (station.getNom().equals(departNom)) {
                        stationDepart = station;
                    }
                    if (station.getNom().equals(arriveeNom)) {
                        stationArrivee = station;
                    }
                }

                if (stationDepart == null || stationArrivee == null) {
                    messageLabel.setText("Erreur : Les stations spécifiées n'ont pas été trouvées.");
                    messageLabel.setTextFill(Color.RED);
                } else {
                    afficherCheminBellmanFord(pane, stationDepart, stationArrivee);
                    messageLabel.setText("Chemin trouvé entre " + departNom + " et " + arriveeNom + ".");
                    messageLabel.setTextFill(Color.GREEN);
                }
            }
        });

        treeButton.setOnAction(e -> {
            afficherParcours(pane, stations.get(0));
        });

        HBox mainLayout = new HBox(20);
        mainLayout.getChildren().addAll(scrollPane, buttonBox);

        VBox layout = new VBox(20, mainLayout, inputBox, messageLabel);
        Scene scene = new Scene(layout, 1200, 800);

        primaryStage.setTitle("Carte du Métro Parisien");
        primaryStage.setScene(scene);
        primaryStage.show();

        Scale scale = new Scale(zoomFactor, zoomFactor);
        pane.getTransforms().add(scale);

        // glissement souris
        pane.setOnMousePressed(event -> {
            initialX = event.getSceneX();
            initialY = event.getSceneY();
        });

        pane.setOnMouseDragged(event -> {
            double deltaX = event.getSceneX() - initialX;
            double deltaY = event.getSceneY() - initialY;

            offsetX += deltaX;
            offsetY += deltaY;
            pane.setTranslateX(offsetX);
            pane.setTranslateY(offsetY);

            initialX = event.getSceneX();
            initialY = event.getSceneY();
        });

        // zoom
        scene.setOnScroll(event -> {
            if (event.getDeltaY() != 0) {
                double zoomFactorChange = event.getDeltaY() > 0 ? 1.1 : 0.9;
                zoomFactor *= zoomFactorChange;
                zoomFactor = Math.max(0.1, Math.min(zoomFactor, 10));
                scale.setX(zoomFactor);
                scale.setY(zoomFactor);
            }
        });
    }


    private void loadStations(ListView<String> listView, int startIndex) {
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, stations.size());
        for (int i = startIndex; i < endIndex; i++) {
            listView.getItems().add(stations.get(i).getNom());
        }
    }


    private void drawStations(Pane pane) {
        double factor = 4.0;
        double translationY = 200;

        Scale scale = new Scale(0.5, 0.5);
        pane.getTransforms().add(scale);

        pane.setTranslateY(-translationY);

        for (Station station : stations) {
            double x = station.getX() * factor;
            double y = station.getY() * factor + translationY;

            Rectangle stationRectangle = new Rectangle(x - 10, y - 10, 20, 20);
            stationRectangle.setFill(Color.LIGHTBLUE);
            pane.getChildren().add(stationRectangle);

            Circle circle = new Circle(x, y, 5, Color.BLUE);
            pane.getChildren().add(circle);

            Text stationName = new Text(x + 10, y, station.getNom());
            stationName.setFill(Color.BLACK);
            stationName.setFont(new Font("Arial", 25));
            pane.getChildren().add(stationName);
        }
    }

    private void afficherArbreCouvrant(Pane pane, Station station1, Station station2) {
        Graphe graphe = new Graphe();
        graphe.construireGraphe(stations, liaisons);
        List<Liaison> arbreCouvrantGraphe = graphe.algorithmePrim(station1, station2);

        if (!arbreCouvrantGraphe.isEmpty()) {
            for (Liaison liaison : arbreCouvrantGraphe) {
                Station stationA = liaison.getStation1();
                Station stationB = liaison.getStation2();
                double x1 = stationA.getX() * 4.0;
                double y1 = stationA.getY() * 4.0 + 200;
                double x2 = stationB.getX() * 4.0;
                double y2 = stationB.getY() * 4.0 + 200;

                Line line = new Line(x1, y1, x2, y2);
                line.setStroke(Color.GREEN);
                line.setStrokeWidth(3);
                pane.getChildren().add(line);
            }
        } else {
            System.out.println("Pas d'arbre couvrant trouvé.");
        }
    }

    private void drawLiaisons(Pane pane) {
        double factor = 4.0;
        double translationY = 200;

        for (Liaison liaison : liaisons) {
            Station station1 = liaison.getStation1();
            Station station2 = liaison.getStation2();

            double x1 = station1.getX() * factor;
            double y1 = station1.getY() * factor + translationY;
            double x2 = station2.getX() * factor;
            double y2 = station2.getY() * factor + translationY;

            Line line = new Line(x1, y1, x2, y2);
            line.setStroke(getLineColor(liaison.getLigneMetro()));
            pane.getChildren().add(line);
        }
    }

    private Color getLineColor(String ligne) {
        switch (ligne) {
            case "1":
                return Color.rgb(255, 213, 0);
            case "2":
                return Color.rgb(0, 133, 199);
            case "3":
                return Color.rgb(193, 84, 151);
            case "3bis":
                return Color.rgb(129, 198, 131);
            case "4":
                return Color.rgb(136, 28, 36);
            case "5":
                return Color.rgb(255, 86, 0);
            case "6":
                return Color.rgb(118, 190, 68);
            case "7":
                return Color.rgb(201, 170, 46);
            case "7bis":
                return Color.rgb(130, 214, 231);
            case "8":
                return Color.rgb(206, 137, 36);
            case "9":
                return Color.rgb(109, 198, 189);
            case "10":
                return Color.rgb(235, 137, 20);
            case "11":
                return Color.rgb(246, 130, 121);
            case "12":
                return Color.rgb(0, 125, 50);
            case "13":
                return Color.rgb(137, 198, 82);
            case "14":
                return Color.rgb(136, 28, 36);
            default:
                return Color.GRAY;
        }
    }

    private void afficherParcours(Pane pane, Station stationInitiale) {
        Graphe graphe = new Graphe();
        graphe.construireGraphe(stations, liaisons);

        List<Station> visites = new ArrayList<>();
        List<Liaison> liaisonsParcourues = new ArrayList<>();
        parcours_profondeur(stationInitiale, visites, liaisonsParcourues, pane);
    }

    private void parcours_profondeur(Station currentStation, List<Station> visites, List<Liaison> liaisonsParcourues, Pane pane) {
        visites.add(currentStation);

        Graphe graphe = new Graphe();
        graphe.construireGraphe(stations, liaisons);
        for (Liaison liaison : graphe.getLiaisonsDeStation(currentStation)) {
            if (!visites.contains(liaison.getStation2())) {
                animateEdge(liaison, pane);
                parcours_profondeur(liaison.getStation2(), visites, liaisonsParcourues, pane);
            }
        }
    }

    private void animateEdge(Liaison liaison, Pane pane) {
        Station station1 = liaison.getStation1();
        Station station2 = liaison.getStation2();

        double x1 = station1.getX() * 4.0;
        double y1 = station1.getY() * 4.0 + 200;
        double x2 = station2.getX() * 4.0;
        double y2 = station2.getY() * 4.0 + 200;

        Line line = new Line(x1, y1, x2, y2);
        line.setStroke(Color.RED);
        line.setStrokeWidth(3);

        pane.getChildren().add(line);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(2.0), new KeyValue(line.strokeWidthProperty(), 6)),
                new KeyFrame(Duration.seconds(2), new KeyValue(line.strokeWidthProperty(), 3))
        );
        timeline.setCycleCount(1);
        timeline.play();
    }

    private void afficherCheminBellmanFord(Pane pane, Station stationDepart, Station stationArrivee) {
        Graphe graphe = new Graphe();
        graphe.construireGraphe(stations, liaisons);

        List<Station> chemin = graphe.bellmanFord(stationDepart, stationArrivee);

        if (chemin.isEmpty()) {
            System.out.println("Aucun chemin trouvé entre les deux stations.");
        } else {
            for (int i = 0; i < chemin.size() - 1; i++) {
                Station stationA = chemin.get(i);
                Station stationB = chemin.get(i + 1);

                double x1 = stationA.getX() * 4.0;
                double y1 = stationA.getY() * 4.0 + 200;
                double x2 = stationB.getX() * 4.0;
                double y2 = stationB.getY() * 4.0 + 200;

                Line line = new Line(x1, y1, x2, y2);
                line.setStroke(Color.RED);
                line.setStrokeWidth(3);

                pane.getChildren().add(line);

                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.seconds(2.0), new KeyValue(line.strokeWidthProperty(), 6)),
                        new KeyFrame(Duration.seconds(2), new KeyValue(line.strokeWidthProperty(), 3))
                );
                timeline.setCycleCount(1);
                timeline.play();
            }
        }
    }


}
