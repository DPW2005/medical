package org.example.medical;

import jade.core.Runtime ;
import jade.core.Profile ;
import jade.core.ProfileImpl ;
import jade.util.leap.Properties ;
import jade.util.ExtendedProperties ;
import jade.wrapper.AgentContainer ;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException ;

public class PatientContainer {
    public static void main(String[] args){
        try{
            Runtime runtime = Runtime.instance() ;
            Properties properties = new ExtendedProperties() ;
            properties.setProperty(Profile.GUI, "false") ;
            Profile profile = new ProfileImpl(properties) ;
            AgentContainer patientContainer = runtime.createAgentContainer(profile) ;
            AgentController agentController = patientContainer.createNewAgent("patient", PatientAgent.class.getName(), new Object[]{}) ;
            agentController.start() ;
        }
        catch(ControllerException e){
            e.printStackTrace() ;
        }
    }
}