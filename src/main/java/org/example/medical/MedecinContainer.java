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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.medical.model.Patient;

public class MedecinContainer extends Application {
    public static void main(String[] args){
        launch(MedecinContainer.class) ;
    }

    @Override
    public void start(Stage stage) throws Exception {
        startContainer();
        VBox layout = new VBox(10) ;
        layout.setSpacing(15);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color : #f5f5f5 ;");
        Label title = new Label("Medecin App") ;
        title.setStyle("-fx-font-size : 18 ; -fx-font-weight : bold ;");

        GridPane form = new GridPane() ;
        form.setVgap(10) ;
        form.setHgap(10) ;

        TextField nameField = new TextField() ;
        TextField serviceField = new TextField() ;

        CheckBox disponibleCheck = new CheckBox("Disponible") ;

        form.addRow(0,new Label("Nom : "),nameField) ;
        form.addRow(1,new Label("Service : "),serviceField) ;

        Button askingButton = new Button("Consulter") ;

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

        layout.getChildren().addAll(title,form,disponibleCheck,new Label("Information sur la consultation"),patientTable,askingButton) ;
        Scene scene = new Scene(layout,600,600) ;
        stage.setScene(scene) ;
        stage.show() ;
    }

    public void startContainer() throws Exception {
        Runtime runtime = Runtime.instance() ;
        Properties properties = new ExtendedProperties() ;
        properties.setProperty(Profile.GUI, "false") ;
        Profile profile = new ProfileImpl(properties) ;
        AgentContainer medecinContainer = runtime.createAgentContainer(profile) ;
        AgentController agentController = medecinContainer.createNewAgent("medecin", MedecinAgent.class.getName(), new Object[]{}) ;
        agentController.start() ;
    }
}
