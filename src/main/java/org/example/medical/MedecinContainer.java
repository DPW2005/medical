package org.example.medical;

import jade.core.Runtime ;
import jade.core.Profile ;
import jade.core.ProfileImpl ;
import jade.util.leap.Properties ;
import jade.util.ExtendedProperties ;
import jade.wrapper.AgentContainer ;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException ;
import javafx.application.Application;
import javafx.stage.Stage;

public class MedecinContainer extends Application {
    public static void main(String[] args){
        launch(MedecinContainer.class) ;
    }

    @Override
    public void start(Stage primaryStage){
        startContainer();
        primaryStage.setTitle("Medecin Form") ;
    }

    public void startContainer(){
        try{
            Runtime runtime = Runtime.instance() ;
            Properties properties = new ExtendedProperties() ;
            properties.setProperty(Profile.GUI, "false") ;
            Profile profile = new ProfileImpl(properties) ;
            AgentContainer medecinContainer = runtime.createAgentContainer(profile) ;
            AgentController agentController = medecinContainer.createNewAgent("medecin", MedecinAgent.class.getName(), new Object[]{}) ;
            agentController.start() ;
        }
        catch(ControllerException e){
            e.printStackTrace() ;
        }
    }
}
