package org.example.medical;

import jade.core.Agent ;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.ControllerException ;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class SecretaireAgent extends Agent {
    @Override
    protected void setup() {
        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                ACLMessage msg = receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
                if (msg != null) {
                    try {
                        JSONObject patientData = new JSONObject(msg.getContent());
                        System.out.println("Données reçues: " + patientData);

                        // Exemple: Extraire les consultations
                        JSONObject consultations = patientData.getJSONObject("consultations");
                        for (Iterator it = consultations.keys(); it.hasNext(); ) {
                            String key = (String) it.next();
                            JSONObject consult = consultations.getJSONObject(key);
                            System.out.println("Consultation avec " + consult.getString("medecin"));
                        }

                        // Répondre au patient
                        ACLMessage reply = msg.createReply();
                        reply.setPerformative(ACLMessage.CONFIRM);
                        reply.setContent("Demande enregistrée.");
                        send(reply);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
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