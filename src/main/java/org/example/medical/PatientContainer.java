package org.example.medical;

import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import org.example.medical.model.Consultation;
import org.json.JSONException;
import org.json.JSONObject; // Pour structurer les données

public class PatientContainer extends Application {
    private AgentContainer agentContainer;
    private ObservableList<Consultation> consultations = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        // 1. Initialisation de JADE
        initJADE();

        // 2. Création des champs de formulaire
        TextField nomField = new TextField();
        TextField prenomField = new TextField();
        TextField ageField = new TextField();
        ComboBox<String> sexeCombo = new ComboBox<>(FXCollections.observableArrayList("Homme", "Femme", "Autre"));
        TextField tailleField = new TextField();
        TextField temperatureField = new TextField();
        TextField tensionField = new TextField();

        // 3. Tableau des consultations (médecin, heure, lieu)
        TableView<Consultation> table = new TableView<>();
        TableColumn<Consultation, String> medecinCol = new TableColumn<>("Médecin");
        TableColumn<Consultation, String> heureCol = new TableColumn<>("Heure");
        TableColumn<Consultation, String> lieuCol = new TableColumn<>("Lieu");
        medecinCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().medecin.name));
        heureCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().heure));
        lieuCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().lieu));
        table.getColumns().addAll(medecinCol, heureCol, lieuCol);
        table.setItems(consultations);

        // 4. Bouton de soumission
        Button submitBtn = new Button("Envoyer la demande");
        submitBtn.setOnAction(e -> {
            JSONObject patientData = new JSONObject();
            try {
                patientData.put("nom", nomField.getText());
                patientData.put("prenom", prenomField.getText());
                patientData.put("age", ageField.getText());
                patientData.put("sexe", sexeCombo.getValue());
                patientData.put("taille", tailleField.getText());
                patientData.put("temperature", temperatureField.getText());
                patientData.put("tension", tensionField.getText());
                patientData.put("consultations", consultationsToJson()); // Convertir le tableau en JSON
            } catch (JSONException ex) {
                throw new RuntimeException(ex);
            }

            try {
                // Envoyer les données à l'agent Patient (qui les transmettra au secrétaire)
                AgentController patientAgent = agentContainer.createNewAgent(
                        "PatientAgent", "PatientAgent", new Object[]{patientData.toString()}
                );
                patientAgent.start();
                System.out.println("Données envoyées !");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // 5. Layout
        GridPane formGrid = new GridPane();
        formGrid.addRow(0, new Label("Nom:"), nomField);
        formGrid.addRow(1, new Label("Prénom:"), prenomField);
        formGrid.addRow(2, new Label("Âge:"), ageField);
        formGrid.addRow(3, new Label("Sexe:"), sexeCombo);
        formGrid.addRow(4, new Label("Taille (cm):"), tailleField);
        formGrid.addRow(5, new Label("Température (°C):"), temperatureField);
        formGrid.addRow(6, new Label("Tension (mmHg):"), tensionField);

        VBox root = new VBox(10, formGrid, new Label("Consultations:"), table, submitBtn);
        Scene scene = new Scene(root, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Interface Patient");
        primaryStage.show();
    }

    // Convertir la liste des consultations en JSON
    private String consultationsToJson() throws JSONException {
        JSONObject json = new JSONObject();
        for (int i = 0; i < consultations.size(); i++) {
            JSONObject consult = new JSONObject();
            consult.put("medecin", consultations.get(i).medecin.name);
            consult.put("heure", consultations.get(i).heure);
            consult.put("lieu", consultations.get(i).lieu);
            json.put(String.valueOf(i), consult);
        }
        return json.toString();
    }

    private void initJADE() {
        Runtime rt = Runtime.instance();
        Properties properties = new ExtendedProperties() ;
        properties.setProperty(Profile.GUI, "false") ;
        Profile profile = new ProfileImpl(properties);
        agentContainer = rt.createAgentContainer(profile);
    }

    public static void main(String[] args) {
        launch(args);
    }
}