package org.example.medical;

import jade.core.AID;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.lang.acl.ACLMessage;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.medical.model.Consultation;
import org.example.medical.model.Medecin;
import org.example.medical.model.Patient;

public class SecretaireContainer extends Application {
    private AgentContainer agentContainer;
    private ObservableList<Medecin> medecinsDisponibles = FXCollections.observableArrayList();
    private ObservableList<Patient> patientsEnAttente = FXCollections.observableArrayList();
    private ObservableList<Consultation> consultationsPlanifiees = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) {
        initJADE();
        // 1. Formulaire d'identification
        TextField idField = new TextField();
        TextField nomField = new TextField();
        GridPane formulaire = new GridPane();
        formulaire.addRow(0, new Label("ID Secrétaire:"), idField);
        formulaire.addRow(1, new Label("Nom:"), nomField);

        // 2. Tableau des médecins disponibles
        TableView<Medecin> tableMedecins = new TableView<>();
        TableColumn<Medecin, String> colMedecinId = new TableColumn<>("ID");
        TableColumn<Medecin, String> colMedecinNom = new TableColumn<>("Nom");
        colMedecinId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().id));
        colMedecinNom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().name));
        tableMedecins.getColumns().addAll(colMedecinId, colMedecinNom);
        tableMedecins.setItems(medecinsDisponibles);

        // 3. Tableau des patients en attente
        TableView<Patient> tablePatients = new TableView<>();
        TableColumn<Patient, String> colPatientNom = new TableColumn<>("Nom");
        TableColumn<Patient, String> colPatientPrenom = new TableColumn<>("Prénom");
        TableColumn<Patient, String> colPatientAge = new TableColumn<>("Âge");
        colPatientNom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().name));
        colPatientPrenom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().firstName));
        colPatientAge.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().age));
        tablePatients.getColumns().addAll(colPatientNom, colPatientPrenom, colPatientAge);
        tablePatients.setItems(patientsEnAttente);

        // 4. Bouton pour assigner un médecin
        Button assignerBtn = new Button("Planifier Consultation");
        assignerBtn.setOnAction(e -> {
            Patient patient = tablePatients.getSelectionModel().getSelectedItem();
            Medecin medecin = tableMedecins.getSelectionModel().getSelectedItem();

            if (patient != null && medecin != null) {
                Consultation consultation = new Consultation(medecin, patient, "10h00", "Cabinet 1");
                consultationsPlanifiees.add(consultation);
                envoyerVersAgentSecretaire(consultation);
            }

            try {
                // Envoyer les données à l'agent Patient (qui les transmettra au secrétaire)
                AgentController patientAgent = agentContainer.createNewAgent(
                        "SecretaireAgent", "SecretaireAgent", new Object[]{}
                );
                patientAgent.start();
                System.out.println("Données envoyées !");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // 5. Layout principal
        VBox root = new VBox(10,
                new Label("Espace Secrétaire"),
                formulaire,
                new Label("Médecins Disponibles:"),
                tableMedecins,
                new Label("Patients en Attente:"),
                tablePatients,
                assignerBtn
        );

        stage.setScene(new Scene(root, 800, 600));
        stage.show();

        // Charger des données fictives (à remplacer par JADE)
    }

    private void envoyerVersAgentSecretaire(Consultation consultationJson) {
        try {
            Runtime rt = Runtime.instance();
            AgentContainer container = rt.createAgentContainer(new ProfileImpl(false));

            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg.addReceiver(new AID("SecretaireAgent", AID.ISLOCALNAME));
            msg.setContent(consultationJson.toString());

            AgentController agent = container.createNewAgent(
                    "SecretaireGUIAgent", "SecretaireGUIAgent", null
            );
            agent.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
