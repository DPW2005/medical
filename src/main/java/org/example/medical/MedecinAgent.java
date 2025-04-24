package org.example.medical;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.ControllerException ;
import jade.core.Agent ;
import org.json.JSONException;
import org.json.JSONObject;

public class MedecinAgent extends Agent {
    private MedecinContainer gui;

    @Override
    protected void setup() {
        // Récupère la référence de la GUI passée en argument
        gui = (MedecinContainer) getArguments()[0];

        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                // Écoute les messages INFORM (patients envoyés par le secrétaire)
                ACLMessage msg = receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
                if (msg != null) {
                    JSONObject patientJson = null;
                    try {
                        patientJson = new JSONObject(msg.getContent());
                        gui.ajouterPatient(patientJson);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    } // Met à jour la GUI
                } else {
                    block();
                }
            }
        });
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
            e.printStackTrace();
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