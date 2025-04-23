package org.example.medical;

import jade.core.Runtime ;
import jade.core.Profile ;
import jade.core.ProfileImpl ;
import jade.util.leap.Properties ;
import jade.util.ExtendedProperties ;
import jade.wrapper.AgentContainer ;
import jade.wrapper.AgentController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.medical.model.Consultation;

public class PatientContainer extends Application {

    public static void main(String[] args){
        launch(PatientContainer.class) ;
    }

    @Override
    public void start(Stage stage) throws Exception {
        startContainer();
        VBox layout = new VBox(10) ;
        layout.setSpacing(15);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color : #f5f5f5 ;");
        Label title = new Label("Patient App") ;
        title.setStyle("-fx-font-size : 18 ; -fx-font-weight : bold ;");

        GridPane form = new GridPane() ;
        form.setVgap(10) ;
        form.setHgap(10) ;

        TextField nameField = new TextField() ;
        TextField firstNameField = new TextField() ;
        TextField ageField = new TextField() ;
        TextField weightField = new TextField() ;
        TextField heightField = new TextField() ;
        TextField temperatureField = new TextField() ;
        TextField tensionField = new TextField() ;

        ToggleGroup sexGroup = new ToggleGroup() ;
        RadioButton femaleButton = new RadioButton("F") ;
        RadioButton maleButton = new RadioButton("M") ;
        femaleButton.setToggleGroup(sexGroup) ;
        maleButton.setToggleGroup(sexGroup) ;

        form.addRow(0,new Label("Nom : "),nameField) ;
        form.addRow(1,new Label("Prenom : "),firstNameField) ;
        form.addRow(2,new Label("Age : "),ageField) ;
        form.addRow(3,new Label("Sexe : "),new HBox(10,femaleButton,maleButton)) ;
        form.addRow(4,new Label("Poids (Kg) : "),weightField) ;
        form.addRow(5,new Label("Taille (cm) : "),heightField) ;
        form.addRow(6,new Label("Temperature (°C) : "),temperatureField) ;
        form.addRow(7,new Label("Tension arterielle : "),tensionField) ;

        Button askingButton = new Button("Demande de consultation") ;

        TableColumn<Consultation,String> medecinCol = new TableColumn<>("Medecin") ;
        TableColumn<Consultation,String> idCol = new TableColumn<>("ID") ;
        TableColumn<Consultation,String> placeCol = new TableColumn<>("Lieu") ;
        TableColumn<Consultation,String> hourCol = new TableColumn<>("Heure") ;

        TableView<Consultation> consultationTable = new TableView<>() ;
        consultationTable.getColumns().addAll(medecinCol, idCol, placeCol, hourCol) ;
        consultationTable.setPlaceholder(new Label("Aucun contenu dans la table"));

        layout.getChildren().addAll(title,form,new Label("Information sur la consultation"),consultationTable,askingButton) ;
        Scene scene = new Scene(layout,600,600) ;
        stage.setScene(scene) ;
        stage.show() ;
    }

    public void startContainer() throws Exception {
        Runtime runtime = Runtime.instance() ;
        Properties properties = new ExtendedProperties() ;
        properties.setProperty(Profile.GUI, "false") ;
        Profile profile = new ProfileImpl(properties) ;
        AgentContainer patientContainer = runtime.createAgentContainer(profile) ;
        AgentController agentController = patientContainer.createNewAgent("patient", PatientAgent.class.getName(), new Object[]{}) ;
        agentController.start() ;
    }
}
