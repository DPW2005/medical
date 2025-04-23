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
import org.example.medical.model.Medecin;
import org.example.medical.model.Patient;

public class SecretaireContainer extends Application {
    public static void main(String[] args){
        launch(SecretaireContainer.class) ;
    }

    @Override
    public void start(Stage stage) throws Exception {
        startContainer();
        VBox layout = new VBox(10) ;
        HBox table = new HBox(10) ;
        layout.setSpacing(15) ;
        table.setSpacing(15) ;
        layout.setPadding(new Insets(20)) ;
        table.setPadding(new Insets(20)) ;
        layout.setStyle("-fx-background-color : #f5f5f5 ;");
        Label title = new Label("Secretary App") ;
        title.setStyle("-fx-font-size : 18 ; -fx-font-weight : bold ;");

        GridPane form = new GridPane() ;
        form.setVgap(10) ;
        form.setHgap(10) ;

        TextField idField = new TextField() ;
        TextField nameField = new TextField() ;

        form.addRow(0,new Label("ID : "),idField) ;
        form.addRow(1,new Label("Nom : "),nameField) ;

        Button askingButton = new Button("Affecter consultation") ;

        TableColumn<Patient,String> nameCol = new TableColumn<>("Nom") ;
        TableColumn<Patient,String> firstNameCol = new TableColumn<>("Prenom") ;
        TableColumn<Patient,String> ageCol = new TableColumn<>("Age") ;
        TableColumn<Medecin,String> idMedCol = new TableColumn<>("ID") ;
        TableColumn<Medecin,String> nameMedCol = new TableColumn<>("Nom") ;

        TableView<Patient> patientTable = new TableView<>() ;
        patientTable.getColumns().addAll(nameCol,firstNameCol,ageCol) ;
        patientTable.setPlaceholder(new Label("Aucun contenu dans la table"));
        TableView<Medecin> medecinTable = new TableView<>() ;
        medecinTable.getColumns().addAll(idMedCol,nameMedCol) ;
        medecinTable.setPlaceholder(new Label("Aucun contenu dans la table"));

        table.getChildren().addAll(new VBox(new Label("Patients"),patientTable),new VBox(new Label("Medecins"),medecinTable)) ;
        layout.getChildren().addAll(title,form,table,askingButton) ;
        Scene scene = new Scene(layout,600,600) ;
        stage.setScene(scene) ;
        stage.show() ;
    }

    public void startContainer() throws Exception {
        Runtime runtime = Runtime.instance() ;
        Properties properties = new ExtendedProperties() ;
        properties.setProperty(Profile.GUI, "false") ;
        Profile profile = new ProfileImpl(properties) ;
        AgentContainer secretaireContainer = runtime.createAgentContainer(profile) ;
        AgentController agentController = secretaireContainer.createNewAgent("secretaire", SecretaireAgent.class.getName(), new Object[]{}) ;
        agentController.start() ;
    }
}
