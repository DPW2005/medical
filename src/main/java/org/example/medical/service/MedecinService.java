package org.example.medical.service;

import org.example.medical.crud.MedecinInterface;
import org.example.medical.model.Medecin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MedecinService extends BaseRepository implements MedecinInterface {
    @Override
    public List<Medecin> findByService(String service) {
        String sql = "SELECT * FROM medecins WHERE service = ?" ;
        Connection connection = null ;
        PreparedStatement pst = null ;
        ResultSet rs = null ;
        List<Medecin> medecins = new ArrayList<>() ;
        try{
            connection = getConnecion() ;
            pst = preparedStatement(connection,sql,service) ;
            rs = pst.executeQuery() ;
            while (rs.next()){
                medecins.add(mapResultToEntity(rs)) ;
            }
            return medecins ;
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
    public List<Medecin> findByAbility() {
        String sql = "SELECT * FROM medecins WHERE isAble = ?" ;
        Connection connection = null ;
        PreparedStatement pst = null ;
        ResultSet rs = null ;
        List<Medecin> medecins = new ArrayList<>() ;
        try{
            connection = getConnecion() ;
            pst = preparedStatement(connection,sql,0) ;
            rs = pst.executeQuery() ;
            while (rs.next()){
                medecins.add(mapResultToEntity(rs)) ;
            }
            return medecins ;
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
    public Medecin create(Medecin entity) {
        String sql = "INSERT INTO medecins (name,service,isAble) VALUES (?,?,?)" ;
        Connection connection = null ;
        PreparedStatement pst = null ;
        ResultSet rs = null ;
        try{
            connection = getConnecion() ;
            pst = connection.prepareCall(sql) ;
            pst.setString(1, entity.getName()) ;
            pst.setString(2,entity.getService()) ;
            if(entity.getAble()){
                pst.setString(3,0+"") ;
            }
            else{
                pst.setString(3,1+"") ;
            }
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
    public Optional<Medecin> findById(long id) {
        String sql = "SELECT * FROM medecins WHERE id = ?" ;
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
    public List<Medecin> findAll() {
        String sql = "SELECT * FROM medecins" ;
        Connection connection = null ;
        Statement st = null ;
        ResultSet rs = null ;
        List<Medecin> medecins = new ArrayList<>() ;
        try{
            connection = getConnecion() ;
            st = connection.createStatement() ;
            rs = st.executeQuery(sql) ;
            while (rs.next()){
                medecins.add(mapResultToEntity(rs)) ;
            }
            return medecins ;
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
    public Medecin update(Medecin entity) {
        String sql = "UPDATE patients SET name = ?, service = ?, isAble = ? WHERE id = ?" ;
        Connection connection = null ;
        PreparedStatement pst = null ;
        try{
            connection = getConnecion() ;
            String bool = String.valueOf(entity.getAble() ? 0 : 1);
            pst = preparedStatement(connection,sql,entity.getName(),entity.getService(),bool) ;
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
        String sql = "DELETE FROM medecins WHERE id = ?" ;
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
    public Medecin mapResultToEntity(ResultSet rs) throws SQLException {
        Medecin medecin = new Medecin() ;
        medecin.setId(rs.getLong("id")) ;
        medecin.setName(rs.getString("name")) ;
        medecin.setService(rs.getString("service")) ;
        if(rs.getInt("isAble") == 0){
            medecin.setAble(true) ;
        }
        else{
            medecin.setAble(false) ;
        }
        return medecin ;
    }
}
