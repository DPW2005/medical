package org.example.medical.service;

import org.example.medical.crud.ConsultationInterface;
import org.example.medical.model.Consultation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConsultationService extends BaseRepository implements ConsultationInterface {

    private PatientService patientService ;
    private MedecinService medecinService ;

    @Override
    public List<Consultation> findByPatientId(long patientId) {
        String sql = "SELECT * FROM consultations WHERE patientId = ?" ;
        Connection connection = null ;
        PreparedStatement pst = null ;
        ResultSet rs = null ;
        List<Consultation> consultations = new ArrayList<>() ;
        try{
            connection = getConnecion() ;
            pst = preparedStatement(connection,sql,patientId) ;
            rs = pst.executeQuery() ;
            while (rs.next()){
                consultations.add(mapResultToEntity(rs)) ;
            }
            return consultations ;
        }
        catch(SQLException e){
            e.printStackTrace() ;
            return new ArrayList<>() ;
        }
        finally {
            closeQuietly(rs) ;
            closeQuietly(pst) ;
            closeQuietly(connection) ;
        }
    }

    @Override
    public List<Consultation> findByMedecinId(long medecinId) {
        String sql = "SELECT * FROM consultations WHERE medecinId = ?" ;
        Connection connection = null ;
        PreparedStatement pst = null ;
        ResultSet rs = null ;
        List<Consultation> consultations = new ArrayList<>() ;
        try{
            connection = getConnecion() ;
            pst = preparedStatement(connection,sql,medecinId) ;
            rs = pst.executeQuery() ;
            while (rs.next()){
                consultations.add(mapResultToEntity(rs)) ;
            }
            return consultations ;
        }
        catch(SQLException e){
            e.printStackTrace() ;
            return new ArrayList<>() ;
        }
        finally {
            closeQuietly(rs) ;
            closeQuietly(pst) ;
            closeQuietly(connection) ;
        }
    }

    @Override
    public Consultation create(Consultation entity) {
        String sql = "INSERT INTO consultations (hour,date,medecinId,patientId) VALUES (?,?,?,?)" ;
        Connection connection = null ;
        PreparedStatement pst = null ;
        ResultSet rs = null ;
        try{
            connection = getConnecion() ;
            pst = connection.prepareCall(sql) ;
            pst.setString(1, entity.getHour()) ;
            pst.setString(2,entity.getDate()) ;
            pst.setString(3,String.valueOf(entity.getMedecin().getId())) ;
            pst.setString(4,String.valueOf(entity.getPatient().getId())) ;
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
    public Optional<Consultation> findById(long id) {
        String sql = "SELECT * FROM consultations WHERE id = ?" ;
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
    public List<Consultation> findAll() {
        String sql = "SELECT * FROM consultations" ;
        Connection connection = null ;
        Statement st = null ;
        ResultSet rs = null ;
        List<Consultation> consultations = new ArrayList<>() ;
        try{
            connection = getConnecion() ;
            st = connection.createStatement() ;
            rs = st.executeQuery(sql) ;
            while (rs.next()){
                consultations.add(mapResultToEntity(rs)) ;
            }
            return consultations ;
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
    public Consultation update(Consultation entity) {
        String sql = "UPDATE consultations SET hour = ?, date = ?, medecinId = ?, patientId = ? WHERE id = ?" ;
        Connection connection = null ;
        PreparedStatement pst = null ;
        try{
            connection = getConnecion() ;
            pst = preparedStatement(connection,sql,entity.getHour(),entity.getDate(),entity.getMedecin().getId(),entity.getPatient().getId()) ;
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
        String sql = "DELETE FROM consultations WHERE id = ?" ;
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
    public Consultation mapResultToEntity(ResultSet rs) throws SQLException {
        Consultation consultation = new Consultation() ;
        consultation.setId(rs.getLong("id")) ;
        consultation.setHour(rs.getString("hour")) ;
        consultation.setDate(rs.getString("date")) ;
        consultation.setMedecin(medecinService.findById(rs.getLong("medecinId")).get()) ;
        consultation.setPatient(patientService.findById(rs.getLong("patientId")).get()); ;
        return consultation;
    }
}
