package ca.cal.tp2.repository;

import ca.cal.tp2.model.Document;
import ca.cal.tp2.model.Emprunt;
import ca.cal.tp2.model.EmpruntDetail;
import ca.cal.tp2.model.Emprunteur;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmpruntRepositoryJDBC extends ParentRepository<Emprunt> {

    @Override
    protected void createTables() {
        executeUpdate("CREATE TABLE IF NOT EXISTS emprunt (" +
                "id INT PRIMARY KEY, " +
                "emprunteur_id INT, " +
                "date_emprunt TIMESTAMP, " +
                "status VARCHAR(50))");

        executeUpdate("CREATE TABLE IF NOT EXISTS emprunt_detail (" +
                "id INT PRIMARY KEY, " +
                "emprunt_id INT, " +
                "document_id INT, " +
                "date_retour_prevue TIMESTAMP, " +
                "date_retour_actuelle TIMESTAMP, " +
                "status VARCHAR(50))");
    }

    public boolean save(Emprunt emprunt) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO emprunt (id, emprunteur_id, date_emprunt, status) VALUES (?, ?, ?, ?)")) {
            pstmt.setLong(1, emprunt.getBorrowID());
            pstmt.setLong(2, emprunt.getEmprunteur().getUserID());
            pstmt.setTimestamp(3, Timestamp.valueOf(emprunt.getDateEmprunt().atStartOfDay()));
            pstmt.setString(4, emprunt.getStatus());

            int result = pstmt.executeUpdate();

            for (EmpruntDetail detail : emprunt.getEmpruntDetails()) {
                saveEmpruntDetail(detail);
            }

            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Emprunt get(Long id) {
        return null; // Implementation can be added as needed
    }

    private boolean saveEmpruntDetail(EmpruntDetail detail) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO emprunt_detail (id, emprunt_id, document_id, date_retour_prevue, date_retour_actuelle, status) " +
                             "VALUES (?, ?, ?, ?, ?, ?)")) {

            pstmt.setInt(1, detail.getLineItemID());
            pstmt.setLong(2, detail.getEmprunt().getBorrowID());
            pstmt.setLong(3, detail.getDocument().getDocumentID());

            // Convert LocalDate to Timestamp for date_retour_prevue
            if (detail.getDateRetourPrevue() != null) {
                pstmt.setTimestamp(4, Timestamp.valueOf(detail.getDateRetourPrevue().atStartOfDay()));
            } else {
                pstmt.setNull(4, Types.TIMESTAMP);
            }

            // Convert LocalDate to Timestamp for date_retour_actuelle if it's not null
            if (detail.getDateRetourActuelle() != null) {
                pstmt.setTimestamp(5, Timestamp.valueOf(detail.getDateRetourActuelle().atStartOfDay()));
            } else {
                pstmt.setNull(5, Types.TIMESTAMP);
            }

            pstmt.setString(6, detail.getStatus());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Emprunt emprunt) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "UPDATE emprunt SET emprunteur_id = ?, date_emprunt = ?, status = ? WHERE id = ?")) {

            pstmt.setLong(1, emprunt.getEmprunteur().getUserID());
            pstmt.setTimestamp(2, Timestamp.valueOf(emprunt.getDateEmprunt().atStartOfDay()));
            pstmt.setString(3, emprunt.getStatus());
            pstmt.setLong(4, emprunt.getBorrowID());

            int result = pstmt.executeUpdate();

            for (EmpruntDetail detail : emprunt.getEmpruntDetails()) {
                updateEmpruntDetail(detail);
            }

            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean updateEmpruntDetail(EmpruntDetail detail) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "UPDATE emprunt_detail SET emprunt_id = ?, document_id = ?, date_retour_prevue = ?, " +
                             "date_retour_actuelle = ?, status = ? WHERE id = ?")) {

            pstmt.setLong(1, detail.getEmprunt().getBorrowID());
            pstmt.setLong(2, detail.getDocument().getDocumentID());

            // Convert LocalDate to Timestamp for date_retour_prevue
            if (detail.getDateRetourPrevue() != null) {
                pstmt.setTimestamp(3, Timestamp.valueOf(detail.getDateRetourPrevue().atStartOfDay()));
            } else {
                pstmt.setNull(3, Types.TIMESTAMP);
            }

            // Convert LocalDate to Timestamp for date_retour_actuelle
            if (detail.getDateRetourActuelle() != null) {
                pstmt.setTimestamp(4, Timestamp.valueOf(detail.getDateRetourActuelle().atStartOfDay()));
            } else {
                pstmt.setNull(4, Types.TIMESTAMP);
            }

            pstmt.setString(5, detail.getStatus());
            pstmt.setInt(6, detail.getLineItemID());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Emprunt> findByEmprunteur(Emprunteur emprunteur) {
        List<Emprunt> emprunts = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT id, emprunteur_id, date_emprunt, status FROM emprunt WHERE emprunteur_id = ?")) {

            pstmt.setLong(1, emprunteur.getUserID());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Emprunt emprunt = new Emprunt(
                        rs.getLong("id"),
                        emprunteur
                );
                emprunt.setDateEmprunt(rs.getTimestamp("date_emprunt").toLocalDateTime().toLocalDate()); // Convert Timestamp to LocalDate
                emprunt.setStatus(rs.getString("status"));

                loadEmpruntDetails(emprunt);

                emprunts.add(emprunt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return emprunts;
    }

    private void loadEmpruntDetails(Emprunt emprunt) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT ed.*, d.* FROM emprunt_detail ed JOIN Document d ON ed.document_id = d.documentID WHERE ed.emprunt_id = ?"
             )) {

            pstmt.setLong(1, emprunt.getBorrowID());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Document document = loadDocument(rs);

                if (document != null) {
                    EmpruntDetail detail = new EmpruntDetail(
                            rs.getInt("id"),
                            document,
                            emprunt
                    );

                    // Convert Timestamp to LocalDate for date_retour_prevue
                    if (rs.getTimestamp("date_retour_prevue") != null) {
                        detail.setDateRetourPrevue(rs.getTimestamp("date_retour_prevue").toLocalDateTime().toLocalDate());
                    }

                    // Convert Timestamp to LocalDate for date_retour_actuelle
                    if (rs.getTimestamp("date_retour_actuelle") != null) {
                        detail.setDateRetourActuelle(rs.getTimestamp("date_retour_actuelle").toLocalDateTime().toLocalDate());
                    }

                    detail.setStatus(rs.getString("status"));

                    emprunt.addEmpruntDetail(detail);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Document loadDocument(ResultSet rs) throws SQLException {
        DocumentRepositoryJDBC documentRepo = new DocumentRepositoryJDBC();
        return documentRepo.get(rs.getLong("document_id"));
    }

    public List<Emprunt> findAll() {
        List<Emprunt> emprunts = new ArrayList<>();
        UtilisateurRepositoryJDBC utilisateurRepo = new UtilisateurRepositoryJDBC();

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, emprunteur_id, date_emprunt, status FROM emprunt")) {

            while (rs.next()) {
                Emprunteur emprunteur = (Emprunteur) utilisateurRepo.get(rs.getLong("emprunteur_id"));

                if (emprunteur != null) {
                    Emprunt emprunt = new Emprunt(
                            rs.getLong("id"),
                            emprunteur
                    );
                    emprunt.setDateEmprunt(rs.getTimestamp("date_emprunt").toLocalDateTime().toLocalDate());
                    emprunt.setStatus(rs.getString("status"));

                    loadEmpruntDetails(emprunt);

                    emprunts.add(emprunt);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return emprunts;
    }

    public List<Emprunt> findByMonth(int month, int year) {
        List<Emprunt> emprunts = new ArrayList<>();
        UtilisateurRepositoryJDBC utilisateurRepo = new UtilisateurRepositoryJDBC();

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT id, emprunteur_id, date_emprunt, status FROM emprunt WHERE MONTH(date_emprunt) = ? AND YEAR(date_emprunt) = ?")) {

            pstmt.setInt(1, month);
            pstmt.setInt(2, year);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Emprunteur emprunteur = (Emprunteur) utilisateurRepo.get(rs.getLong("emprunteur_id"));

                if (emprunteur != null) {
                    Emprunt emprunt = new Emprunt(
                            rs.getLong("id"),
                            emprunteur
                    );
                    emprunt.setDateEmprunt(rs.getTimestamp("date_emprunt").toLocalDateTime().toLocalDate());
                    emprunt.setStatus(rs.getString("status"));

                    loadEmpruntDetails(emprunt);

                    emprunts.add(emprunt);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return emprunts;
    }

    public List<EmpruntDetail> findUpcomingReturns(int daysAhead) {
        List<EmpruntDetail> upcomingReturns = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT ed.*, e.emprunteur_id FROM emprunt_detail ed " +
                             "JOIN emprunt e ON ed.emprunt_id = e.id " +
                             "WHERE ed.date_retour_prevue BETWEEN ? AND ? AND ed.date_retour_actuelle IS NULL")) {

            pstmt.setTimestamp(1, Timestamp.valueOf(LocalDate.now().atStartOfDay()));
            pstmt.setTimestamp(2, Timestamp.valueOf(LocalDate.now().plusDays(daysAhead).atStartOfDay()));
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                UtilisateurRepositoryJDBC utilisateurRepo = new UtilisateurRepositoryJDBC();
                Emprunteur emprunteur = (Emprunteur) utilisateurRepo.get(rs.getLong("emprunteur_id"));

                if (emprunteur != null) {
                    Emprunt emprunt = new Emprunt(rs.getLong("emprunt_id"), emprunteur);
                    DocumentRepositoryJDBC documentRepo = new DocumentRepositoryJDBC();
                    Document document = documentRepo.get(rs.getLong("document_id"));

                    if (document != null) {
                        EmpruntDetail detail = new EmpruntDetail(
                                rs.getInt("id"),
                                document,
                                emprunt
                        );
                        detail.setDateRetourPrevue(rs.getTimestamp("date_retour_prevue").toLocalDateTime().toLocalDate());
                        detail.setStatus(rs.getString("status"));

                        upcomingReturns.add(detail);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return upcomingReturns;
    }

    public List<Emprunt> findByDocument(Document document) {
        List<Emprunt> emprunts = new ArrayList<>();
        UtilisateurRepositoryJDBC utilisateurRepo = new UtilisateurRepositoryJDBC();

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT DISTINCT e.* FROM emprunt e " +
                             "JOIN emprunt_detail ed ON e.id = ed.emprunt_id " +
                             "WHERE ed.document_id = ?")) {

            pstmt.setLong(1, document.getDocumentID());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Emprunteur emprunteur = (Emprunteur) utilisateurRepo.get(rs.getLong("emprunteur_id"));

                if (emprunteur != null) {
                    Emprunt emprunt = new Emprunt(
                            rs.getLong("id"),
                            emprunteur
                    );
                    emprunt.setDateEmprunt(rs.getTimestamp("date_emprunt").toLocalDateTime().toLocalDate());
                    emprunt.setStatus(rs.getString("status"));

                    loadEmpruntDetails(emprunt);

                    emprunts.add(emprunt);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return emprunts;
    }
}