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
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.medical.model.Medecin;
import org.example.medical.model.Patient;

public class SecretaireContainer extends Application {

    AgentContainer secretaireContainer ;

    public static void main(String[] args){
        launch(SecretaireContainer.class) ;
    }

    @Override
    public void start(Stage stage) throws Exception {
        startContainer();
        HBox table = new HBox(10) ;
        table.setSpacing(15) ;
        table.setPadding(new Insets(20)) ;
        VBox info = new VBox(10) ;
        info.setSpacing(15);
        info.setPadding(new Insets(20));
        info.setStyle("-fx-background-color : #f5f5f5 ;");
        VBox tableau = new VBox(10) ;
        tableau.setSpacing(15);
        tableau.setPadding(new Insets(20));
        tableau.setStyle("-fx-background-color : #f5f5f5 ;");
        Label title = new Label("Secretary App") ;
        title.setStyle("-fx-font-size : 18 ; -fx-font-weight : bold ;");
        Label patientTitle = new Label("Consultation a faire") ;
        patientTitle.setStyle("-fx-font-size : 16 ; -fx-font-weight : bold ;");
        Label medecinTitle = new Label("Consultation a faire") ;
        medecinTitle.setStyle("-fx-font-size : 16 ; -fx-font-weight : bold ;");

        GridPane form = new GridPane() ;
        form.setVgap(10) ;
        form.setHgap(10) ;

        TextField idField = new TextField() ;
        TextField nameField = new TextField() ;

        form.addRow(0,new Label("ID : "),idField) ;
        form.addRow(1,new Label("Nom : "),nameField) ;

        Button connexionButton = new Button("Envoyer information") ;
        connexionButton.setAlignment(Pos.CENTER) ;
        Button askingButton = new Button("Affecter consultation") ;

        askingButton.setOnAction(event -> {
            try{
                AgentController agentController = secretaireContainer.createNewAgent("secretaire", SecretaireAgent.class.getName(), new Object[]{}) ;
                agentController.start() ;
            }
            catch(Exception e){
                e.printStackTrace() ;
            }
        });

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
        info.getChildren().addAll(title,form,connexionButton) ;
        tableau.getChildren().addAll(title,table,askingButton) ;
        Scene scene1 = new Scene(info,300,300) ;
        Scene scene2 = new Scene(tableau,600,600) ;
        stage.setScene(scene1) ;
        stage.show() ;
        connexionButton.setOnAction(event ->{
            stage.close();
            stage.setScene(scene2);
            stage.show();
        });
    }

    public void startContainer() throws Exception {
        Runtime runtime = Runtime.instance() ;
        Properties properties = new ExtendedProperties() ;
        properties.setProperty(Profile.GUI, "false") ;
        Profile profile = new ProfileImpl(properties) ;
        secretaireContainer = runtime.createAgentContainer(profile) ;
    }
}
