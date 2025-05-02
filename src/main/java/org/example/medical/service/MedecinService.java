package org.example.medical.service;

import org.example.medical.crud.MedecinInterface;
import org.example.medical.model.Medecin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class MedecinService extends BaseRepository implements MedecinInterface {
    @Override
    public List<Medecin> findByService(String service) {
        return List.of();
    }

    @Override
    public List<Medecin> findByAbility() {
        return List.of();
    }

    @Override
    public Medecin create(Medecin entity) {
        return null;
    }

    @Override
    public Optional<Medecin> findById(long id) {
        return Optional.empty();
    }

    @Override
    public List<Medecin> findAll() {
        return List.of();
    }

    @Override
    public Medecin update(Medecin entity) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public Medecin mapResultToEntity(ResultSet rs) throws SQLException {
        return null;
    }
}
