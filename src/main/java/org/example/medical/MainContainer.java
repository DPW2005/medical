package org.example.medical;

import jade.core.Runtime ;
import jade.core.Profile ;
import jade.core.ProfileImpl ;
import jade.util.leap.Properties ;
import jade.util.ExtendedProperties ;
import jade.wrapper.AgentContainer ;

public class MainContainer {
    public static void main(String[] args) throws Exception {
        Runtime runtime = Runtime.instance() ;
        Properties properties = new ExtendedProperties() ;
        properties.setProperty(Profile.GUI, "true") ;
        Profile profile = new ProfileImpl(properties) ;
        AgentContainer mainContainer = runtime.createMainContainer(profile) ;
        mainContainer.start() ;
    }
}
