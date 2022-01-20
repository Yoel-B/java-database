package yoel.bider.javadb.erstedatenbank;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class GUI extends Application {

    Datenbanksynchronisierung dbsync = new Datenbanksynchronisierung();
    ObservableList<ObservableList> data;

    Connection con = null;
    String url = "jdbc:mysql://127.0.0.1:3306/mydb";
    String user = "root";
    String password = "";

    String oldGPN;


    @Override
    public void start(Stage primaryStage) throws Exception {

        //Home Pane
        BorderPane homePane = new BorderPane();

        //Menu
        HBox navigationBox = new HBox(10);
        Button homeButton = new Button("Home");
        Button hinzufuegenButton = new Button("Hinzufügen");
        Button editButton = new Button("Editieren");
        Button loeschenButton = new Button("Löschen");
        Button anzeigeButton = new Button("Anzeigen");

        //CSS
        homeButton.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        homeButton.setPrefSize(100, 50);
        hinzufuegenButton.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        hinzufuegenButton.setPrefSize(220, 50);
        editButton.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        editButton.setPrefSize(200, 50);
        loeschenButton.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        loeschenButton.setPrefSize(200, 50);
        anzeigeButton.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        anzeigeButton.setPrefSize(220, 50);
        navigationBox.setPadding(new Insets(370));

        //Buttons hinzufügen
        navigationBox.getChildren().addAll(hinzufuegenButton, editButton, loeschenButton, anzeigeButton);

        //Hinzufügen Pane
        GridPane hinzufuegenPane = new GridPane();
        hinzufuegenPane.setVisible(false);


        //Label und Textfelder
        Label gpnLabel = new Label("GPN-Nummer");
        Label nameLabel = new Label("Name");
        Label vornameLabel = new Label("Vorname");
        Label abteilungLabel = new Label("Abteilung");
        Label emailLabel = new Label("Emailadresse");
        Label geburtsdatumLabel = new Label("Geburtsdatum");
        Label lohnLabel = new Label("Lohn");
        Label alterLabel = new Label("Alter");
        Label aktivLabel = new Label("Aktiv");

        //Schriftgrösse
        gpnLabel.setFont(new Font(12));
        nameLabel.setFont(new Font(12));
        vornameLabel.setFont(new Font(12));
        abteilungLabel.setFont(new Font(12));
        emailLabel.setFont(new Font(12));
        geburtsdatumLabel.setFont(new Font(12));
        lohnLabel.setFont(new Font(12));
        alterLabel.setFont(new Font(12));
        aktivLabel.setFont(new Font(12));

        //GPNNummer Textfeld
        TextArea gpnText = new TextArea();
        gpnText.setPrefWidth(350);
        gpnText.setPrefHeight(10);

        //Name Textfeld
        TextArea nameText = new TextArea();
        nameText.setPrefWidth(350);
        nameText.setPrefHeight(10);

        //Vorname Textfeld
        TextArea vornameText = new TextArea();
        vornameText.setPrefWidth(350);
        vornameText.setPrefHeight(10);

        //Abteilung Textfeld
        TextArea abteilungText = new TextArea();
        abteilungText.setPrefWidth(350);
        abteilungText.setPrefHeight(10);

        //Email Textfeld
        TextArea emailText = new TextArea();
        emailText.setPrefWidth(350);
        emailText.setPrefHeight(10);

        //Geburtstag Datapicker
        DatePicker datePicker = new DatePicker();
        datePicker.setPrefWidth(350);
        datePicker.setPrefHeight(40);

        //Lohn Textfeld
        TextArea lohnText = new TextArea();
        lohnText.setPrefWidth(350);
        lohnText.setPrefHeight(10);

        //Alter Textfeld
        TextArea alterText = new TextArea();
        alterText.setPrefWidth(350);
        alterText.setPrefHeight(10);

        //Aktiv Textfeld
        TextArea aktivText = new TextArea();
        aktivText.setPrefWidth(350);
        aktivText.setPrefHeight(10);

        //Speicher Button
        Button speicherButton = new Button("Speichern");
        speicherButton.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        speicherButton.setPrefSize(100, 50);

        hinzufuegenPane.setHgap(10);
        hinzufuegenPane.setVgap(10);

        hinzufuegenPane.add(homeButton, 0, 0);

        hinzufuegenPane.add(gpnLabel, 1, 8);
        hinzufuegenPane.add(gpnText, 2, 8);
        hinzufuegenPane.add(nameLabel, 1, 9);
        hinzufuegenPane.add(nameText, 2, 9);
        hinzufuegenPane.add(vornameLabel, 1, 10);
        hinzufuegenPane.add(vornameText, 2, 10);
        hinzufuegenPane.add(abteilungLabel, 1, 11);
        hinzufuegenPane.add(abteilungText, 2, 11);
        hinzufuegenPane.add(emailLabel, 1, 12);
        hinzufuegenPane.add(emailText, 2, 12);
        hinzufuegenPane.add(geburtsdatumLabel, 1, 13);
        hinzufuegenPane.add(datePicker, 2, 13);
        hinzufuegenPane.add(lohnLabel, 1, 14);
        hinzufuegenPane.add(lohnText, 2, 14);
        hinzufuegenPane.add(alterLabel, 1, 15);
        hinzufuegenPane.add(alterText, 2, 15);
        hinzufuegenPane.add(aktivLabel, 1, 16);
        hinzufuegenPane.add(aktivText, 2, 16);

        hinzufuegenPane.add(speicherButton, 2, 17);


        //Bereiche von BorderPane zuteilen
        homePane.setCenter(navigationBox);


        //Anzeige Pane
        BorderPane anzeigePane = new BorderPane();
        anzeigePane.setVisible(false);

        TableView anzeigeTabelle = new TableView();
        anzeigeTabelle.setEditable(true);

        Button homeButton2 = new Button("Home");
        homeButton2.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        homeButton2.setPrefSize(100, 50);


        anzeigePane.setTop(homeButton2);
        anzeigePane.setCenter(anzeigeTabelle);


        //Edit Pane
        HBox editPane = new HBox();
        GridPane editPane2 = new GridPane();
        editPane.setVisible(false);

        Button homeButton3 = new Button("Home");
        homeButton3.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        homeButton3.setPrefSize(100, 50);

        editPane2.add(homeButton3, 0, 0);

        //Label und Textfelder
        Label gpnLabel2 = new Label("GPN-Nummer");
        Label nameLabel2 = new Label("Name");
        Label vornameLabel2 = new Label("Vorname");
        Label abteilungLabel2 = new Label("Abteilung");
        Label emailLabel2 = new Label("Emailadresse");
        Label geburtsdatumLabel2 = new Label("Geburtsdatum");
        Label lohnLabel2 = new Label("Lohn");
        Label alterLabel2 = new Label("Alter");
        Label aktivLabel2 = new Label("Aktiv");

        //Schriftgrösse
        gpnLabel2.setFont(new Font(12));
        nameLabel2.setFont(new Font(12));
        vornameLabel2.setFont(new Font(12));
        abteilungLabel2.setFont(new Font(12));
        emailLabel2.setFont(new Font(12));
        geburtsdatumLabel2.setFont(new Font(12));
        lohnLabel2.setFont(new Font(12));
        alterLabel2.setFont(new Font(12));
        aktivLabel2.setFont(new Font(12));

        //GPNNummer Textfeld
        TextArea gpnText2 = new TextArea();
        gpnText2.setPrefWidth(350);
        gpnText2.setPrefHeight(10);
        gpnText2.setMaxHeight(10);

        //Name Textfeld
        TextArea nameText2 = new TextArea();
        nameText2.setPrefWidth(350);
        nameText2.setPrefHeight(10);

        //Vorname Textfeld
        TextArea vornameText2 = new TextArea();
        vornameText2.setPrefWidth(350);
        vornameText2.setPrefHeight(10);

        //Abteilung Textfeld
        TextArea abteilungText2 = new TextArea();
        abteilungText2.setPrefWidth(350);
        abteilungText2.setPrefHeight(10);

        //Email Textfeld
        TextArea emailText2 = new TextArea();
        emailText2.setPrefWidth(350);
        emailText2.setPrefHeight(10);

        //Geburtstag Datapicker
        DatePicker datePicker2 = new DatePicker();
        datePicker2.setPrefWidth(350);
        datePicker2.setMinHeight(40);

        //Lohn Textfeld
        TextArea lohnText2 = new TextArea();
        lohnText2.setPrefWidth(350);
        lohnText2.setPrefHeight(10);

        //Alter Textfeld
        TextArea alterText2 = new TextArea();
        alterText2.setPrefWidth(350);
        alterText2.setPrefHeight(10);

        //Aktiv Textfeld
        TextArea aktivText2 = new TextArea();
        aktivText2.setPrefWidth(350);
        aktivText2.setPrefHeight(10);

        //Speicher Button
        Button speicherButton2 = new Button("Speichern");
        speicherButton2.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        speicherButton2.setPrefSize(100, 50);

        editPane2.setHgap(10);
        editPane2.setVgap(10);


        editPane2.add(gpnLabel2, 1, 8);
        editPane2.add(gpnText2, 2, 8);
        editPane2.add(nameLabel2, 1, 9);
        editPane2.add(nameText2, 2, 9);
        editPane2.add(vornameLabel2, 1, 10);
        editPane2.add(vornameText2, 2, 10);
        editPane2.add(abteilungLabel2, 1, 11);
        editPane2.add(abteilungText2, 2, 11);
        editPane2.add(emailLabel2, 1, 12);
        editPane2.add(emailText2, 2, 12);
        editPane2.add(geburtsdatumLabel2, 1, 13);
        editPane2.add(datePicker2, 2, 13);
        editPane2.add(lohnLabel2, 1, 14);
        editPane2.add(lohnText2, 2, 14);
        editPane2.add(alterLabel2, 1, 15);
        editPane2.add(alterText2, 2, 15);
        editPane2.add(aktivLabel2, 1, 16);
        editPane2.add(aktivText2, 2, 16);

        editPane2.add(speicherButton2, 2, 17);

        ListView<String> editList = new ListView<String>();

        editList.translateXProperty().set(30);
        editList.translateYProperty().set(130);
        editList(editList);

        Pane editListe = new Pane();
        editListe.getChildren().add(editList);

        editPane.getChildren().add(editPane2);
        editPane.getChildren().add(editListe);


        //Aktionen
        hinzufuegenButton.setOnAction((action) -> {
            hinzufuegenPane.setVisible(true);
            homePane.setVisible(false);
            anzeigePane.setVisible(false);
            editPane.setVisible(false);
        });

        homeButton.setOnAction((action) -> {
            homePane.setVisible(true);
            hinzufuegenPane.setVisible(false);
            anzeigePane.setVisible(false);
            editPane.setVisible(false);
        });

        homeButton2.setOnAction((action) -> {
            homePane.setVisible(true);
            hinzufuegenPane.setVisible(false);
            anzeigePane.setVisible(false);
            editPane.setVisible(false);
        });

        homeButton3.setOnAction((action) -> {
            homePane.setVisible(true);
            hinzufuegenPane.setVisible(false);
            anzeigePane.setVisible(false);
            editPane.setVisible(false);
        });


        anzeigeButton.setOnAction((action) -> {
            homePane.setVisible(false);
            anzeigePane.setVisible(true);
            hinzufuegenPane.setVisible(false);
            editPane.setVisible(false);
            anzeigen(anzeigeTabelle);
        });

        editButton.setOnAction((action) -> {
            homePane.setVisible(false);
            anzeigePane.setVisible(false);
            hinzufuegenPane.setVisible(false);
            editPane.setVisible(true);
        });

        editList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                ObservableList<String> index = editList.getSelectionModel().getSelectedItems();
                oldGPN = index.toString();
                oldGPN = oldGPN.replace("[", "");
                oldGPN = oldGPN.replace("]", "");

                try {
                    con = DriverManager.getConnection(url, user, password);

                    PreparedStatement pstm = con.prepareStatement("select * from mitarbeiter where GPNNummer='" + oldGPN + "'");
                    ResultSet result = pstm.executeQuery();


                    while (result.next()) {
                        gpnText2.setText(result.getString("GPNNummer"));
                        nameText2.setText(result.getString("Name"));
                        vornameText2.setText(result.getString("Vorname"));
                        abteilungText2.setText(result.getString("Abteilung"));
                        emailText2.setText(result.getString("Emailadresse"));
                        String datum = result.getString("Geburtsdatum");
                        String[] datumOhneZeit = datum.split(" ");
                        datePicker2.setValue(LocalDate.parse(datumOhneZeit[0]));
                        lohnText2.setText(result.getString("Lohn"));
                        alterText2.setText(result.getString("Age"));
                        aktivText2.setText(result.getString("Aktiv"));
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }


        });


        speicherButton.setOnAction((action) -> {
            String ueberpruefung = gpnText.getText();


            if (ueberpruefung != null) {


                Mitarbeiter t = new Mitarbeiter();

                t.setGpnnummer(gpnText.getText());
                t.setName(nameText.getText());
                t.setVorname(vornameText.getText());
                t.setAbteilung((abteilungText.getText()));
                t.setEmail((emailText.getText()));
                LocalDate geburtsdatum = datePicker.getValue();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                geburtsdatum.format(dtf);
                t.setGeburtsdatum(geburtsdatum);

                float lohn = Float.parseFloat(lohnText.getText());
                t.setLohn(lohn);
                int alter = Integer.parseInt(alterText.getText());
                t.setAlter(alter);
                String aktiv = aktivText.getText();
                if (aktiv.equals("Ja")) {
                    t.setAktiv(1);
                } else {
                    t.setAktiv(0);
                }

                dbsync.hinzufuegen(t);


                gpnText.setText("");
                nameText.setText("");
                vornameText.setText("");
                abteilungText.setText("");
                emailText.setText("");
                datePicker.setValue(null);
                lohnText.setText("");
                alterText.setText("");
                aktivText.setText("");
            }
        });

        speicherButton2.setOnAction((action) -> {
            String ueberpruefung = gpnText2.getText();


            if (ueberpruefung != null) {


                Mitarbeiter u = new Mitarbeiter();

                u.setGpnnummer(gpnText2.getText());
                u.setName(nameText2.getText());
                u.setVorname(vornameText2.getText());
                u.setAbteilung((abteilungText2.getText()));
                u.setEmail((emailText2.getText()));
                LocalDate geburtsdatum = datePicker2.getValue();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                geburtsdatum.format(dtf);
                u.setGeburtsdatum(geburtsdatum);

                float lohn = Float.parseFloat(lohnText2.getText());
                u.setLohn(lohn);
                int alter = Integer.parseInt(alterText2.getText());
                u.setAlter(alter);
                String aktiv = aktivText2.getText();
                if (aktiv.equals("Ja")) {
                    u.setAktiv(1);
                } else if (aktiv.equals("1")) {
                    u.setAktiv(1);
                } else {
                    u.setAktiv(0);
                }

                try {
                    con = DriverManager.getConnection(url, user, password);

                    Statement stm = con.createStatement();
                    stm.executeUpdate("update mitarbeiter set GPNNummer='" + u.getGpnnummer() + "', Name='" + u.getName() + "', Vorname='" + u.getVorname() + "', Abteilung='" + u.getAbteilung() + "', Emailadresse='" + u.getEmail() + "', Geburtsdatum='" + u.getGeburtsdatum() + "', Lohn='" + u.getLohn() + "', Age='" + u.getAlter() + "', Aktiv='" + u.getAktiv() + "' where GPNNummer='" + oldGPN + "'");

                    stm.close();
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }


                gpnText2.setText("");
                nameText2.setText("");
                vornameText2.setText("");
                abteilungText2.setText("");
                emailText2.setText("");
                datePicker2.setValue(null);
                lohnText2.setText("");
                alterText2.setText("");
                aktivText2.setText("");
            }
        });

        //Root Pane
        StackPane rootPane = new StackPane();
        rootPane.getChildren().add(homePane);
        rootPane.getChildren().add(hinzufuegenPane);
        rootPane.getChildren().add(anzeigePane);
        rootPane.getChildren().add(editPane);

        Scene root = new Scene(rootPane, 1080, 650);

        //Ausgabe
        primaryStage.getIcons().add(new Image("https://www.pngitem.com/pimgs/m/576-5768680_avatar-png-icon-person-icon-png-free-transparent.png"));
        primaryStage.setTitle("Datenbank Interface");
        primaryStage.setScene(root);
        primaryStage.show();

    }

    public void anzeigen(TableView anzeigeTabelle) {

        data = FXCollections.observableArrayList();

        try {
            con = DriverManager.getConnection(url, user, password);
            ResultSet rs = con.createStatement().executeQuery("select * from mitarbeiter");

            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnLabel(i + 1));
                switch (rs.getMetaData().getColumnLabel(i + 1)) {
                    case "Abteilung":
                        col.setPrefWidth(80);
                        break;
                    case "Age":
                        col.setPrefWidth(60);
                        break;
                    case "Aktiv":
                        col.setPrefWidth(66);
                        break;
                    case "Geburtsdatum":
                    case "Emailadresse":
                        col.setPrefWidth(200);
                        break;
                    default:
                        col.setPrefWidth(120);
                        break;
                }
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                anzeigeTabelle.getColumns().addAll(col);
            }

            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                data.add(row);

            }


            anzeigeTabelle.setItems(data);

            rs.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editList(ListView<String> editList) {

        try {

            con = DriverManager.getConnection(url, user, password);

            PreparedStatement pstm = con.prepareStatement("select * from mitarbeiter");
            ResultSet result = pstm.executeQuery();

            while (result.next()) {
                String current = result.getString("GPNNummer");
                ObservableList<String> list = FXCollections.observableArrayList(current);
                editList.getItems().addAll(list);
            }

            result.close();
            pstm.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
