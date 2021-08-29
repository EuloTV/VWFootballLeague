package controller;

import java.util.Collections;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.NumberTextField;
import model.Team;

public class FootballLeagueView extends Application {

    private final ObservableList<Team> teams = FXCollections.observableArrayList(new Team("SV Sieghausen"),
            new Team("FC Fallenstetten"), new Team("TV Tormacher"), new Team("Eintracht Entenhausen"),
            new Team("SV Schwalbe"));

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        TeamComparator comp = new TeamComparator();
        ObservableList<Team> sortedTeams = this.teams;
        Collections.sort(sortedTeams, comp.reversed());
        Scene scene = new Scene(new Group());
        configureStage(stage);

        final Label label = new Label("Fußballliga");
        label.setFont(new Font("Arial", 24));
        final Label teamsLabel = new Label("Teams:");
        teamsLabel.setFont(new Font("Arial", 12));
        ComboBox<String> firstTeamBox = new ComboBox<>();
        for (Team team : teams) {
            firstTeamBox.getItems().add(team.getName());
        }
        firstTeamBox.getSelectionModel().selectFirst();
        final Label versusLabel = new Label("gegen");
        versusLabel.setFont(new Font("Arial", 12));
        ComboBox<String> secondTeamBox = new ComboBox<>();
        for (Team team : teams) {
            secondTeamBox.getItems().add(team.getName());
        }
        secondTeamBox.getSelectionModel().selectFirst();
        final Label resultLabel = new Label("Ergebnis:");
        resultLabel.setFont(new Font("Arial", 12));
        final NumberTextField goalsFirstTeam = new NumberTextField();
        goalsFirstTeam.setPromptText("0");
        goalsFirstTeam.setPrefWidth(30);
        final Label colonLabel = new Label(":");
        colonLabel.setFont(new Font("Arial", 12));
        final NumberTextField goalsSecondTeam = new NumberTextField();
        goalsSecondTeam.setPromptText("0");
        goalsSecondTeam.setPrefWidth(30);

        final Label labelLeagueTable = new Label("Ligatabelle");
        labelLeagueTable.setFont(new Font("Arial", 18));
        TableView<Team> leagueTable = new TableView<>();
        fillTableView(leagueTable, sortedTeams);
        final Button addMatchButton = new Button("Spiel hinzufügen");
        addMatchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                addMatch(firstTeamBox.getValue(), secondTeamBox.getValue(), goalsFirstTeam.getText(),
                        goalsSecondTeam.getText());
                Collections.sort(sortedTeams, comp.reversed());
                leagueTable.refresh();
            }
        });

        final HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(10, 0, 0, 10));
        hbox.getChildren().addAll(teamsLabel, firstTeamBox, versusLabel, secondTeamBox, resultLabel, goalsFirstTeam,
                colonLabel, goalsSecondTeam, addMatchButton);
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, hbox, labelLeagueTable, leagueTable);
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        stage.setScene(scene);
        stage.show();
    }

    private void configureStage(Stage stage) {

        stage.setTitle("Fußballliga");
        stage.setWidth(700);
        stage.setHeight(300);
        stage.setResizable(false);

    }

    @SuppressWarnings("unchecked")
    private void fillTableView(TableView<Team> leagueTable, ObservableList<Team> sortedTeams) {

        TableColumn<Team, Integer> positionCol = new TableColumn<>("Pl.");
        TableColumn<Team, String> teamCol = new TableColumn<>("Verein");
        TableColumn<Team, Integer> matchesCol = new TableColumn<>("Sp.");
        TableColumn<Team, Integer> winsCol = new TableColumn<>("S");
        TableColumn<Team, Integer> drawsCol = new TableColumn<>("U");
        TableColumn<Team, Integer> lossesCol = new TableColumn<>("N");
        TableColumn<Team, String> goalsCol = new TableColumn<>("Tore");
        TableColumn<Team, String> goalDifferenceCol = new TableColumn<>("Diff.");
        TableColumn<Team, Integer> pointsCol = new TableColumn<>("Punkte");

        leagueTable.getColumns().addAll(positionCol, teamCol, matchesCol, winsCol, drawsCol, lossesCol, goalsCol,
                goalDifferenceCol, pointsCol);

        positionCol.setCellValueFactory(new Callback<CellDataFeatures<Team, Integer>, ObservableValue<Integer>>() {

            @Override
            public ObservableValue<Integer> call(CellDataFeatures<Team, Integer> param) {
                return new ReadOnlyObjectWrapper(leagueTable.getItems().indexOf(param.getValue()) + 1);
            }

        });

        teamCol.setCellValueFactory(new PropertyValueFactory("name"));
        matchesCol.setCellValueFactory(new PropertyValueFactory("matches"));
        winsCol.setCellValueFactory(new PropertyValueFactory("wins"));
        drawsCol.setCellValueFactory(new PropertyValueFactory("draws"));
        lossesCol.setCellValueFactory(new PropertyValueFactory("losses"));
        goalsCol.setCellValueFactory(new PropertyValueFactory("goalsComparison"));
        goalDifferenceCol.setCellValueFactory(new PropertyValueFactory("goalDifference"));
        pointsCol.setCellValueFactory(new PropertyValueFactory("points"));

        leagueTable.setItems(sortedTeams);
        leagueTable.setMaxSize(450, 150);

    }

    private void addMatch(String nameFirstTeam, String nameSecondTeam, String goalsFirstTeam, String goalsSecondTeam) {

        // TODO 4. Aufgabe

        Team firstTeam = null;
        Team secondTeam = null;

        for (Team team : teams) {
            if (team.getName().equals(nameFirstTeam)) {
                firstTeam = team;
            } else if (team.getName().equals(nameSecondTeam)) {
                secondTeam = team;
            }
        }

        firstTeam.addGoals(Integer.parseInt(goalsFirstTeam));
        firstTeam.addGoalsConceded(Integer.parseInt(goalsSecondTeam));

        secondTeam.addGoals(Integer.parseInt(goalsSecondTeam));
        secondTeam.addGoalsConceded(Integer.parseInt(goalsFirstTeam));

        if (Integer.parseInt(goalsFirstTeam) == Integer.parseInt(goalsSecondTeam)) {
            firstTeam.addDraw();
            secondTeam.addDraw();
        } else if (Integer.parseInt(goalsFirstTeam) > Integer.parseInt(goalsSecondTeam)) {
            firstTeam.addWin();
            secondTeam.addLoss();
        } else if (Integer.parseInt(goalsFirstTeam) < Integer.parseInt(goalsSecondTeam)) {
            firstTeam.addLoss();
            secondTeam.addWin();
        }

    }

}
