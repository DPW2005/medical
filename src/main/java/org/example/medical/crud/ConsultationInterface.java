package org.example.medical.crud;

import org.example.medical.model.Consultation;

import java.util.List;

public interface ConsultationInterface extends CrudOperations<Consultation> {

    List<Consultation> findByPatientId(long patientId);
    List<Consultation> findByMedecinId(long medecinId) ;
}
