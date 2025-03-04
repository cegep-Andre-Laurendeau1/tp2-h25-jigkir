package ca.cal.tp2.repository;

import ca.cal.tp2.model.Amende;
import ca.cal.tp2.model.Emprunteur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AmendeRepositoryJDBC extends ParentRepository<Amende> {

    @Override
    protected void createTables() {
        executeUpdate("CREATE TABLE IF NOT EXISTS Utilisateur (" +
                "userID INT PRIMARY KEY, " +
                "name VARCHAR(255), " +
                "email VARCHAR(255), " +
                "phoneNumber VARCHAR(20), " +
                "userType VARCHAR(20)" +
                ")");

        executeUpdate("CREATE TABLE IF NOT EXISTS Amende (" +
                "id INT PRIMARY KEY, " +
                "montant DOUBLE, " +
                "dateCreation TIMESTAMP, " +
                "status BOOLEAN, " +
                "emprunteurID INT, " +
                "FOREIGN KEY (emprunteurID) REFERENCES Utilisateur(userID)" +
                ")");
    }

    public boolean save(Amende amende) {
        if (amende.getEmprunteur() == null) {
            System.err.println("Error: Emprunteur is null. Cannot save Amende.");
            return false;
        }
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO Amende (id, montant, dateCreation, status, emprunteurID) VALUES (?, ?, ?, ?, ?)")
        ) {
            pstmt.setInt(1, amende.getid());
            pstmt.setDouble(2, amende.getMontant());
            pstmt.setTimestamp(3, Timestamp.valueOf(amende.getDateCreation().atStartOfDay()));
            pstmt.setBoolean(4, amende.isStatus());
            pstmt.setLong(5, amende.getEmprunteur().getUserID());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Amende get(Long id) {
        return null;
    }

    public List<Amende> findAll() {
        List<Amende> amendes = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Amende")) {

            while (rs.next()) {
                Amende amende = new Amende(
                        rs.getInt("id"),
                        rs.getDouble("montant"),
                        null,
                        null
                );
                amende.setStatus(rs.getBoolean("status"));
                amende.setDateCreation(rs.getTimestamp("dateCreation").toLocalDateTime().toLocalDate());
                amendes.add(amende);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return amendes;
    }

    @Override
    public boolean update(Amende entity) {
        return false;
    }

    public List<Amende> findUnpaidByEmprunteur(Emprunteur emprunteur) {
        List<Amende> amendes = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Amende WHERE emprunteurID = ? AND status = FALSE")) {
            pstmt.setLong(1, emprunteur.getUserID());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Amende amende = new Amende(
                        rs.getInt("id"),
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