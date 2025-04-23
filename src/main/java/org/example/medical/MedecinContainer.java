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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.medical.model.Patient;

public class MedecinContainer extends Application {

    AgentContainer medecinContainer ;

    public static void main(String[] args){
        launch(MedecinContainer.class) ;
    }

    @Override
    public void start(Stage stage) throws Exception {
        startContainer();
        VBox info = new VBox(10) ;
        info.setSpacing(15);
        info.setPadding(new Insets(20));
        info.setStyle("-fx-background-color : #f5f5f5 ;");
        VBox tableau = new VBox(10) ;
        tableau.setSpacing(15);
        tableau.setPadding(new Insets(20));
        tableau.setStyle("-fx-background-color : #f5f5f5 ;");
        Label title = new Label("Medecin App") ;
        title.setStyle("-fx-font-size : 18 ; -fx-font-weight : bold ;");
        Label tableTitle = new Label("Consultation a faire") ;
        tableTitle.setStyle("-fx-font-size : 16 ; -fx-font-weight : bold ;");

        GridPane form = new GridPane() ;
        form.setVgap(10) ;
        form.setHgap(10) ;

        TextField nameField = new TextField() ;
        TextField serviceField = new TextField() ;

        CheckBox disponibleCheck = new CheckBox("Disponible") ;

        form.addRow(0,new Label("Nom : "),nameField) ;
        form.addRow(1,new Label("Service : "),serviceField) ;

        Button connexionButton = new Button("Envoyer information") ;
        connexionButton.setAlignment(Pos.CENTER) ;
        Button askingButton = new Button("Consulter") ;

        askingButton.setOnAction(event -> {
            try{
                AgentController agentController = medecinContainer.createNewAgent("medecin", MedecinAgent.class.getName(), new Object[]{}) ;
                agentController.start() ;
            }
            catch(Exception e){
                e.printStackTrace() ;
            }
        });

        TableColumn<Patient,String> nameCol = new TableColumn<>("Nom") ;
        TableColumn<Patient,String> firstNameCol = new TableColumn<>("Prenom") ;
        TableColumn<Patient,String> ageCol = new TableColumn<>("Age") ;
        TableColumn<Patient,String> sexeCol = new TableColumn<>("Sexe") ;
        TableColumn<Patient,String> weightCol = new TableColumn<>("Poids") ;
        TableColumn<Patient,String> heightCol = new TableColumn<>("Taille") ;
        TableColumn<Patient,String> temperatureCol = new TableColumn<>("Temperature") ;
        TableColumn<Patient,String> tensionCol = new TableColumn<>("Tension") ;

        TableView<Patient> patientTable = new TableView<>() ;
        patientTable.getColumns().addAll(nameCol,firstNameCol,ageCol,sexeCol,weightCol,heightCol,temperatureCol,tensionCol) ;
        patientTable.setPlaceholder(new Label("Aucun contenu dans la table"));

        info.getChildren().addAll(title,form,connexionButton) ;
        tableau.getChildren().addAll(title,tableTitle,patientTable,askingButton) ;
        Scene scene1 = new Scene(info,300,300) ;
        Scene scene2 = new Scene(tableau,400,400) ;
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
        medecinContainer = runtime.createAgentContainer(profile) ;
    }
}
