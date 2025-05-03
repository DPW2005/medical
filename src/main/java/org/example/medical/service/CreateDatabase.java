package org.example.medical.service;

import java.sql.*;

public class CreateDatabase {
    public static void main(String[] args){
        String url = "jdbc:mysql://localhost:3306/hospital?serverTimezone=UTC" ;
        String user = "root" ;
        String password = "" ;
        Connection connection = null ;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver") ;
            connection = DriverManager.getConnection(url,user,password) ;
            if(connection != null){
                DatabaseMetaData metaData = connection.getMetaData() ;
                System.out.println("Le pilote JDBC est "+metaData.getDriverName()) ;
                System.out.println("Une nouvelle base de donnees nommee 'hospital' a ete trouvee.") ;
                Statement st = connection.createStatement() ;
                String patient = "CREATE TABLE IF NOT EXISTS patients (id INTEGER PRIMARY KEY AUTO_INCREMENT,name TEXT NOT NULL,sexe TEXT NOT NULL,age INTEGER NOT NULL,weight FLOAT NOT NULL,height FLOAT NOT NULL,tension FLOAT NOT NULL,temperature FLOAT NOT NULL)" ;
                String secretaire = "CREATE TABLE IF NOT EXISTS secretaires(id INTEGER PRIMARY KEY AUTO_INCREMENT,name TEXT NOT NULL,service TEXT NOT NULL)" ;
                String consultation = "CREATE TABLE IF NOT EXISTS consultations(id INTEGER PRIMARY KEY AUTO_INCREMENT,hour TEXT NOT NULL,date TEXT NOT NULL,medecinId INTEGER NOT NULL,patientId INTEGER NOT NULL,FOREIGN KEY(patientId) REFERENCES patients(id),FOREIGN KEY(medecinId) REFERENCES medecins(id))" ;
                String medecin = "CREATE TABLE IF NOT EXISTS medecins(id INTEGER PRIMARY KEY AUTO_INCREMENT,name TEXT NOT NULL,service TEXT NOT NULL,isAble INTEGER NOT NULL)" ;
                st.execute(patient) ;
                System.out.println("La table 'patients' a ete creee");
                st.execute(secretaire) ;
                System.out.println("La table 'secretaires' a ete creee");
                st.execute(consultation) ;
                System.out.println("La table 'consultations' a ete creee");
                st.execute(medecin) ;
                System.out.println("La table 'medecin' a ete creee");
            }
        }
        catch(SQLException | ClassNotFoundException e){
            //System.out.println(e.getMessage()) ;
            e.printStackTrace();
        }
        finally {
            try {
                connection.close() ;
                System.out.println("Connexion fermee") ;
            }
            catch (SQLException e) {
                e.printStackTrace() ;
            }
        }
    }
}
