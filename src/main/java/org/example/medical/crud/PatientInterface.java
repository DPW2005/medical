package org.example.medical.crud;

import org.example.medical.model.Patient;

import java.util.List;

public interface PatientInterface extends CrudOperations<Patient> {

    Patient findByName(String name);
}
