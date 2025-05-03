package org.example.medical.service;

import org.example.medical.crud.SecretaireInterface;
import org.example.medical.model.Medecin;
import org.example.medical.model.Patient;
import org.example.medical.model.Secretaire;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SecretaireService extends BaseRepository implements SecretaireInterface {
    @Override
    public Secretaire findByName(String name) {
        String sql = "SELECT * FROM patients WHERE name = ?" ;
        Connection connection = null ;
        PreparedStatement pst = null ;
        ResultSet rs = null ;
        Secretaire secretaire = new Secretaire() ;
        try{
            connection = getConnecion() ;
            pst = preparedStatement(connection,sql,name) ;
            rs = pst.executeQuery() ;
            secretaire = mapResultToEntity(rs) ;
            return secretaire ;
        }
        catch(SQLException e){
            e.printStackTrace() ;
            return null ;
        }
        finally {
            closeQuietly(rs) ;
            closeQuietly(pst) ;
            closeQuietly(connection) ;
        }
    }

    @Override
    public Secretaire create(Secretaire entity) {
        String sql = "INSERT INTO secretaires (name,service) VALUES (?,?)" ;
        Connection connection = null ;
        PreparedStatement pst = null ;
        ResultSet rs = null ;
        try{
            connection = getConnecion() ;
            pst = connection.prepareCall(sql) ;
            pst.setString(1, entity.getName()) ;
            pst.setString(2,entity.getService()) ;
            pst.executeUpdate() ;
            rs = pst.getGeneratedKeys() ;
            if(rs.next()){
                entity.setId(rs.getLong(1));
            }
            return entity ;
        }
        catch(SQLException e){
            e.printStackTrace() ;
            return null ;
        }
        finally {
            closeQuietly(rs) ;
            closeQuietly(pst) ;
            closeQuietly(connection) ;
        }
    }

    @Override
    public Optional<Secretaire> findById(long id) {
        String sql = "SELECT * FROM secretaires WHERE id = ?" ;
        Connection connection = null ;
        PreparedStatement pst = null ;
        ResultSet rs = null ;
        try{
            connection = getConnecion() ;
            pst = preparedStatement(connection,sql,id) ;
            rs = pst.executeQuery() ;
            if(rs.next()){
                return Optional.of(mapResultToEntity(rs)) ;
            }
            return Optional.empty() ;
        }
        catch(SQLException e){
            e.printStackTrace() ;
            return Optional.empty() ;
        }
        finally {
            closeQuietly(rs) ;
            closeQuietly(pst) ;
            closeQuietly(connection) ;
        }
    }

    @Override
    public List<Secretaire> findAll() {
        String sql = "SELECT * FROM secretaires" ;
        Connection connection = null ;
        Statement st = null ;
        ResultSet rs = null ;
        List<Secretaire> secretaires = new ArrayList<>() ;
        try{
            connection = getConnecion() ;
            st = connection.createStatement() ;
            rs = st.executeQuery(sql) ;
            while (rs.next()){
                secretaires.add(mapResultToEntity(rs)) ;
            }
            return secretaires ;
        }
        catch (SQLException e){
            e.printStackTrace() ;
            return new ArrayList<>() ;
        }
        finally {
            closeQuietly(rs) ;
            closeQuietly(st) ;
            closeQuietly(connection) ;
        }
    }

    @Override
    public Secretaire update(Secretaire entity) {
        String sql = "UPDATE secretaires SET name = ?, service = ? WHERE id = ?" ;
        Connection connection = null ;
        PreparedStatement pst = null ;
        try{
            connection = getConnecion() ;
            pst = preparedStatement(connection,sql,entity.getName(),entity.getService()) ;
            int affectedRows = pst.executeUpdate() ;
            if(affectedRows > 0){
                return entity ;
            }
            return null ;
        }
        catch (SQLException e){
            e.printStackTrace();
            return null ;
        }
        finally {
            closeQuietly(pst) ;
            closeQuietly(connection) ;
        }
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM secretaires WHERE id = ?" ;
        Connection connection = null ;
        PreparedStatement pst = null ;
        ResultSet rs = null ;
        try{
            connection = getConnecion() ;
            pst = preparedStatement(connection,sql,id) ;
            pst.executeUpdate() ;
        }
        catch (SQLException e){
            e.printStackTrace() ;
        }
        finally {
            closeQuietly(pst) ;
            closeQuietly(connection) ;
        }
    }

    @Override
    public Secretaire mapResultToEntity(ResultSet rs) throws SQLException {
        Secretaire secretaire = new Secretaire() ;
        secretaire.setId(rs.getLong("id")) ;
        secretaire.setName(rs.getString("name")) ;
        secretaire.setService(rs.getString("service")) ;
        return secretaire;
    }
}
