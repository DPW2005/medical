package org.example.medical.service;

import org.example.medical.crud.ConsultationInterface;
import org.example.medical.model.Consultation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ConsultationService extends BaseRepository implements ConsultationInterface {
    @Override
    public List<Consultation> findByPatientId(long patientId) {
        return List.of();
    }

    @Override
    public Consultation create(Consultation entity) {
        return null;
    }

    @Override
    public Optional<Consultation> findById(long id) {
        return Optional.empty();
    }

    @Override
    public List<Consultation> findAll() {
        return List.of();
    }

    @Override
    public Consultation update(Consultation entity) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public Consultation mapResultToEntity(ResultSet rs) throws SQLException {
        return null;
    }
}
