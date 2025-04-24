package org.example.medical;

import jade.wrapper.AgentContainer;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.AgentController;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.medical.model.Patient;
import org.json.JSONException;
import org.json.JSONObject;

public class MedecinContainer extends Application {
    private  AgentContainer agentContainer ;
    private ObservableList<Patient> patients = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) {
        // Initialisation JADE (à adapter)
        initJADE();
        // 1. Formulaire d'identification du médecin
        TextField idMedecinField = new TextField();
        TextField nomMedecinField = new TextField();
        GridPane formulaireMedecin = new GridPane();
        formulaireMedecin.addRow(0, new Label("ID Médecin:"), idMedecinField);
        formulaireMedecin.addRow(1, new Label("Nom:"), nomMedecinField);

        // 2. Tableau des patients avec données médicales
        TableView<Patient> tablePatients = new TableView<>();

        // Colonnes
        TableColumn<Patient, String> colNom = new TableColumn<>("Nom");
        TableColumn<Patient, String> colPrenom = new TableColumn<>("Prénom");
        TableColumn<Patient, String> colAge = new TableColumn<>("Âge");
        TableColumn<Patient, String> colSexe = new TableColumn<>("Sexe");
        TableColumn<Patient, String> colTaille = new TableColumn<>("Taille (cm)");
        TableColumn<Patient, String> colTemperature = new TableColumn<>("Temp (°C)");
        TableColumn<Patient, String> colTension = new TableColumn<>("Tension (mmHg)");

        // Liaison des données
        colNom.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().name));
        colPrenom.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().firstName));
        colAge.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().age));
        colSexe.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().sexe));
        colTaille.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().height));
        colTemperature.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().temperature));
        colTension.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().tension));

        tablePatients.getColumns().addAll(colNom, colPrenom, colAge, colSexe, colTaille, colTemperature, colTension);
        tablePatients.setItems(patients);

        // 3. Bouton pour actualiser la liste (simule la réception via JADE)
        Button actualiserBtn = new Button("Actualiser la liste");
        actualiserBtn.setOnAction(e -> {
            try {
                // Envoyer les données à l'agent Patient (qui les transmettra au secrétaire)
                AgentController patientAgent = agentContainer.createNewAgent(
                        "MedecinAgent", "MedecinAgent", new Object[]{}
                );
                patientAgent.start();
                System.out.println("Données envoyées !");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Layout
        VBox root = new VBox(10,
                new Label("Espace Médecin"),
                formulaireMedecin,
                new Label("Patients à consulter:"),
                tablePatients,
                actualiserBtn
        );

        stage.setScene(new Scene(root, 900, 600));
        stage.show();


    }

    // Méthode pour ajouter un patient reçu via JADE
    public void ajouterPatient(JSONObject patientJson) throws JSONException {
        Patient patient = new Patient(
                patientJson.getString("nom"),
                patientJson.getString("prenom"),
                patientJson.getString("age"),
                patientJson.getString("sexe"),
                patientJson.getString("taille"),
                patientJson.getString("temperature"),
                patientJson.getString("tension")
        );
        patients.add(patient);
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