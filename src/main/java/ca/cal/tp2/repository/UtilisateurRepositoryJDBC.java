package ca.cal.tp2.repository;

import ca.cal.tp2.model.Emprunteur;
import ca.cal.tp2.model.Prepose;
import ca.cal.tp2.model.Utilisateur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurRepositoryJDBC extends ParentRepository<Utilisateur> {

    @Override
    protected void createTables() {
        executeUpdate("CREATE TABLE IF NOT EXISTS Utilisateur " +
                "(userID INT PRIMARY KEY, " +
                "name VARCHAR(255), " +
                "email VARCHAR(255), " +
                "phoneNumber VARCHAR(20), " +
                "userType VARCHAR(20))");

        executeUpdate("CREATE TABLE IF NOT EXISTS Emprunteur " +
                "(userID INT PRIMARY KEY, " +
                "amendeBalance DOUBLE, " +
                "FOREIGN KEY (userID) REFERENCES Utilisateur(userID))");

        executeUpdate("CREATE TABLE IF NOT EXISTS Prepose " +
                "(userID INT PRIMARY KEY, " +
                "FOREIGN KEY (userID) REFERENCES Utilisateur(userID))");
    }

    public boolean save(Utilisateur utilisateur) {
        String userType = "";
        if (utilisateur instanceof Emprunteur) {
            userType = "Emprunteur";
        } else if (utilisateur instanceof Prepose) {
            userType = "Prepose";
        }

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO Utilisateur (userID, name, email, phoneNumber, userType) VALUES (?, ?, ?, ?, ?)")) {

            pstmt.setLong(1, utilisateur.getUserID());
            pstmt.setString(2, utilisateur.getName());
            pstmt.setString(3, utilisateur.getEmail());
            pstmt.setString(4, utilisateur.getPhoneNumber());
            pstmt.setString(5, userType);

            pstmt.executeUpdate();

            if (utilisateur instanceof Emprunteur) {
                try (PreparedStatement pstmtEmpr = conn.prepareStatement(
                        "INSERT INTO Emprunteur (userID, amendeBalance) VALUES (?, ?)")) {

                    pstmtEmpr.setLong(1, utilisateur.getUserID());
                    pstmtEmpr.setDouble(2, ((Emprunteur) utilisateur).getAmendeBalance());
                    pstmtEmpr.executeUpdate();
                }
            } else if (utilisateur instanceof Prepose) {
                try (PreparedStatement pstmtPrep = conn.prepareStatement(
                        "INSERT INTO Prepose (userID) VALUES (?)")) {

                    pstmtPrep.setLong(1, utilisateur.getUserID());
                    pstmtPrep.executeUpdate();
                }
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Utilisateur get(Long userID) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT * FROM Utilisateur WHERE userID = ?")) {

            pstmt.setLong(1, userID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String userType = rs.getString("userType");

                if ("Emprunteur".equals(userType)) {
                    try (PreparedStatement pstmtEmpr = conn.prepareStatement(
                            "SELECT * FROM Emprunteur WHERE userID = ?")) {

                        pstmtEmpr.setLong(1, userID);
                        ResultSet rsEmpr = pstmtEmpr.executeQuery();

                        if (rsEmpr.next()) {
                            Emprunteur emprunteur = new Emprunteur(
                                    userID,
                                    rs.getString("name"),
                                    rs.getString("email"),
                                    rs.getString("phoneNumber"));

                            emprunteur.setAmendeBalance(rsEmpr.getDouble("amendeBalance"));

                            return emprunteur;
                        }
                    }
                } else if ("Prepose".equals(userType)) {
                    Prepose prepose = new Prepose(
                            userID,
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("phoneNumber"));

                    return prepose;
                }
            }

            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Utilisateur> findAll() {
        List<Utilisateur> utilisateurs = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Utilisateur")) {

            while (rs.next()) {
                Long userID = rs.getLong("userID");
                String userType = rs.getString("userType");

                if ("Emprunteur".equals(userType)) {
                    try (PreparedStatement pstmtEmpr = conn.prepareStatement(
                            "SELECT * FROM Emprunteur WHERE userID = ?")) {

                        pstmtEmpr.setLong(1, userID);
                        ResultSet rsEmpr = pstmtEmpr.executeQuery();

                        if (rsEmpr.next()) {
                            Emprunteur emprunteur = new Emprunteur(
                                    userID,
                                    rs.getString("name"),
                                    rs.getString("email"),
                                    rs.getString("phoneNumber"));

                            emprunteur.setAmendeBalance(rsEmpr.getDouble("amendeBalance"));
                            utilisateurs.add(emprunteur);
                        }
                    }
                } else if ("Prepose".equals(userType)) {
                    Prepose prepose = new Prepose(
                            userID,
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("phoneNumber"));

                    utilisateurs.add(prepose);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return utilisateurs;
    }

    public boolean update(Utilisateur utilisateur) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(
                     "UPDATE Utilisateur SET name = ?, email = ?, phoneNumber = ? WHERE userID = ?")) {

            pstmt.setString(1, utilisateur.getName());
            pstmt.setString(2, utilisateur.getEmail());
            pstmt.setString(3, utilisateur.getPhoneNumber());
            pstmt.setLong(4, utilisateur.getUserID());

            pstmt.executeUpdate();

            if (utilisateur instanceof Emprunteur) {
                try (PreparedStatement pstmtEmpr = conn.prepareStatement(
                        "UPDATE Emprunteur SET amendeBalance = ? WHERE userID = ?")) {

                    pstmtEmpr.setDouble(1, ((Emprunteur) utilisateur).getAmendeBalance());
                    pstmtEmpr.setLong(2, utilisateur.getUserID());
                    pstmtEmpr.executeUpdate();
                }
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Emprunteur> findAllEmprunteurs() {
        List<Emprunteur> emprunteurs = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT u.*, e.amendeBalance FROM Utilisateur u JOIN Emprunteur e ON u.userID = e.userID")) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Emprunteur emprunteur = new Emprunteur(
                        rs.getLong("userID"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phoneNumber"));

                emprunteur.setAmendeBalance(rs.getDouble("amendeBalance"));
                emprunteurs.add(emprunteur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return emprunteurs;
    }
}