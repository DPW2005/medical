package org.example.medical.service;

import org.example.medical.crud.SecretaireInterface;
import org.example.medical.model.Secretaire;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SecretaireService extends BaseRepository implements SecretaireInterface {
    @Override
    public Secretaire findByName(String name) {
        return null;
    }

    @Override
    public Secretaire create(Secretaire entity) {
        return null;
    }

    @Override
    public Optional<Secretaire> findById(long id) {
        return Optional.empty();
    }

    @Override
    public List<Secretaire> findAll() {
        return List.of();
    }

    @Override
    public Secretaire update(Secretaire entity) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public Secretaire mapResultToEntity(ResultSet rs) throws SQLException {
        return null;
    }
}
