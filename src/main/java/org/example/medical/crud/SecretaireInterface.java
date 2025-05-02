package org.example.medical.crud;

import org.example.medical.model.Secretaire;

public interface SecretaireInterface extends CrudOperations<Secretaire> {

    Secretaire findByName(String name) ;
}
