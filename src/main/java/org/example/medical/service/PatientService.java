package org.example.medical.service;

import org.example.medical.crud.PatientInterface;
import org.example.medical.model.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PatientService extends BaseRepository implements PatientInterface {
    @Override
    public Patient findByName(String name) {
        String sql = "SELECT * FROM patients WHERE name = ?" ;
        Connection connection = null ;
        PreparedStatement pst = null ;
        ResultSet rs = null ;
        Patient patient = new Patient() ;
        try{
            connection = getConnecion() ;
            pst = preparedStatement(connection,sql,name) ;
            rs = pst.executeQuery() ;
            patient = mapResultToEntity(rs) ;
            return patient ;
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
    public Patient create(Patient entity) {
        String sql = "INSERT INTO patients (name,sexe,age,weight,height,tension,temperature) VALUES (?,?,?,?,?,?,?,?)" ;
        Connection connection = null ;
        PreparedStatement pst = null ;
        ResultSet rs = null ;
        try{
            connection = getConnecion() ;
            pst = connection.prepareCall(sql) ;
            pst.setString(1, entity.getName()) ;
            pst.setString(2,entity.getSexe()) ;
            pst.setString(3,entity.getAge()+"") ;
            pst.setString(4,entity.getWeight()+"") ;
            pst.setString(5,entity.getHeight()+"") ;
            pst.setString(6,entity.getTension()+"") ;
            pst.setString(6,entity.getTemperature()+"");
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
    public Optional<Patient> findById(long id) {
        String sql = "SELECT * FROM patients WHERE id = ?" ;
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
    public List<Patient> findAll() {
        String sql = "SELECT * FROM patients" ;
        Connection connection = null ;
        Statement st = null ;
        ResultSet rs = null ;
        List<Patient> patients = new ArrayList<>() ;
        try{
            connection = getConnecion() ;
            st = connection.createStatement() ;
            rs = st.executeQuery(sql) ;
            while (rs.next()){
                patients.add(mapResultToEntity(rs)) ;
            }
            return patients ;
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
    public Patient update(Patient entity) {
        String sql = "UPDATE patients SET name = ?, sexe = ?, age = ?, weight = ?, height = ?, tension = ?, temperature = ? WHERE id = ?" ;
        Connection connection = null ;
        PreparedStatement pst = null ;
        try{
            connection = getConnecion() ;
            pst = preparedStatement(connection,sql,entity.getName(),entity.getSexe(),entity.getAge(),entity.getWeight(),entity.getHeight(),entity.getTension(),entity.getTemperature(),entity.getId()) ;
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
        String sql = "DELETE FROM patients WHERE id = ?" ;
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
    public Patient mapResultToEntity(ResultSet rs) throws SQLException{
        Patient patient = new Patient() ;
        patient.setId(rs.getLong("id")) ;
        patient.setName(rs.getString("name")) ;
        patient.setSexe(rs.getString("sexe")) ;
        patient.setAge(Integer.parseInt(rs.getString("age"))) ;
        patient.setWeight(Double.parseDouble(rs.getString("weight"))) ;
        patient.setHeight(Double.parseDouble(rs.getString("height"))) ;
        patient.setTension(Double.parseDouble(rs.getString("tension"))) ;
        patient.setTemperature(Double.parseDouble(rs.getString("temperature"))) ;
        return patient ;
    }
}
