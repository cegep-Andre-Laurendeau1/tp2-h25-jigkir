package ca.cal.tp2.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ParentRepository {
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:mem:tp2leotran;DB_CLOSE_DELAY=-1";
    static final String USER = "sa";
    static final String PASS = "";
    static Connection conn;

    static {
        String documentSQL = "(id INTEGER NOT NULL," +
                " titre VARCHAR(255), " +
                " anneePublication DATE, " +
                " nombreExemplaire INTEGER, ";

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();

            String sql = "CREATE TABLE CD " +
                    documentSQL +
                    " artiste VARCHAR(255), " +
                    " duree INTEGER, " +
                    " genre VARCHAR(255), " +
                    " PRIMARY KEY (id)" +
                    ")";
            stmt.executeUpdate(sql);

            // Table LIVRE
            stmt = conn.createStatement();
            sql = "CREATE TABLE LIVRE " +
                    documentSQL +
                    " ISBN VARCHAR(255), " +
                    " auteur VARCHAR(255), " +
                    " editeur VARCHAR(255), " +
                    " nombrePages INTEGER, " +
                    " PRIMARY KEY (id)" +
                    ")";
            stmt.executeUpdate(sql);

            stmt = conn.createStatement();
            sql = "CREATE TABLE DVD " +
                    documentSQL +
                    " directeur VARCHAR(255), " +
                    " duree INTEGER, " +
                    " genre VARCHAR(255), " +
                    " PRIMARY KEY (id)" +
                    ")";
            stmt.executeUpdate(sql);

            System.out.println("Création des tables terminée");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}