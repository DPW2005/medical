package org.example.medical;

import jade.core.AID;
import jade.core.Agent ;
import jade.lang.acl.ACLMessage;
import jade.wrapper.ControllerException;

public class PatientAgent extends Agent {
    @Override
    protected void setup() {
        String patientData = (String) getArguments()[0]; // Récupère les données JSON

        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.addReceiver(new AID("SecretaireAgent", AID.ISLOCALNAME));
        msg.setContent(patientData); // Envoie le JSON
        send(msg);
    }

    @Override
    protected void takeDown(){
        System.out.println("Destruction de l'agent") ;
    }

    @Override
    protected void beforeMove(){
        try{
            System.out.println("Avant la migration......du container "+this.getContainerController().getContainerName()) ;
        }
        catch(ControllerException e){
            e.printStackTrace() ;
        }
    }

    @Override
    protected void afterMove(){
        try{
            System.out.println("Apres la migration......du container "+this.getContainerController().getContainerName()) ;
        }
        catch(ControllerException e){
            e.printStackTrace() ;
        }
    }
}
