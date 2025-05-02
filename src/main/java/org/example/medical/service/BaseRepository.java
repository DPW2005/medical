package org.example.medical.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class BaseRepository {

    private static final String JDBC_URL = "jdbc:sqlite:hospital.db" ;

    protected Connection getConnecion() throws SQLException {
        return DriverManager.getConnection(JDBC_URL) ;
    }

    protected void closeQuietly(AutoCloseable closeable){
        if(closeable != null){
            try{
                closeable.close() ;
            }
            catch(Exception e){
                e.printStackTrace() ;
            }
        }
    }

    protected PreparedStatement preparedStatement(Connection connection,String sql,Object... params) throws SQLException {
        PreparedStatement pst = connection.prepareStatement(sql) ;
        for(int i = 0 ; i < params.length ; i++){
            pst.setObject(i+1,params[i]) ;
        }
        return pst ;
    }
}
