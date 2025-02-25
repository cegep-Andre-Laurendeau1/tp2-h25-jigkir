package ca.cal.tp2.repository;

import ca.cal.tp2.model.Amende;
import ca.cal.tp2.model.Emprunteur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AmendeRepository {
    private static final String DB_URL = "jdbc:h2:mem:TP2;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASS = "1";

    public AmendeRepository() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Utilisateur (" +
                    "userID INT PRIMARY KEY, " +
                    "name VARCHAR(255), " +
                    "email VARCHAR(255), " +
                    "phoneNumber VARCHAR(20), " +
                    "userType VARCHAR(20)" +
                    ")");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Amende (" +
                    "fineID INT PRIMARY KEY, " +
                    "montant DOUBLE, " +
                    "dateCreation TIMESTAMP, " +
                    "status BOOLEAN, " +
                    "emprunteurID INT, " +
                    "FOREIGN KEY (emprunteurID) REFERENCES Utilisateur(userID)" +
                    ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean save(Amende amende) {
        if (amende.getEmprunteur() == null) {
            System.err.println("Error: Emprunteur is null. Cannot save Amende.");
            return false;
        }
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO Amende (fineID, montant, dateCreation, status, emprunteurID) VALUES (?, ?, ?, ?, ?)")
        ) {
            pstmt.setInt(1, amende.getFineID());
            pstmt.setDouble(2, amende.getMontant());
            pstmt.setTimestamp(3, new Timestamp(amende.getDateCreation().getTime()));
            pstmt.setBoolean(4, amende.isStatus());
            pstmt.setInt(5, amende.getEmprunteur().getUserID());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Amende> findAll() {
        List<Amende> amendes = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Amende")) {

            while (rs.next()) {
                Amende amende = new Amende(
                        rs.getInt("fineID"),
                        rs.getDouble("montant"),
                        null,  // You can load the Emprunteur separately if needed
                        null   // And any additional details for EmpruntDetail if required
                );
                amende.setStatus(rs.getBoolean("status"));
                amende.setDateCreation(rs.getTimestamp("dateCreation"));
                amendes.add(amende);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return amendes;
    }

    public List<Amende> findUnpaidByEmprunteur(Emprunteur emprunteur) {
        List<Amende> amendes = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Amende WHERE emprunteurID = ? AND status = FALSE")) {
            pstmt.setInt(1, emprunteur.getUserID());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Amende amende = new Amende(
                        rs.getInt("fineID"),
                        rs.getDouble("montant"),
                        emprunteur,
                        null
                );
                amende.setStatus(rs.getBoolean("status"));
                amendes.add(amende);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return amendes;
    }
}