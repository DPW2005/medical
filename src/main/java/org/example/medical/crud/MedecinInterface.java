package org.example.medical.crud;

import org.example.medical.model.Medecin;

import java.util.List;

public interface MedecinInterface extends CrudOperations<Medecin> {

    List<Medecin> findByService(String service) ;

    List<Medecin> findByAbility() ;
}
