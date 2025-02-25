package ca.cal.tp2.repository;

import ca.cal.tp2.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DocumentRepository implements IDocumentRepository {
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:mem:TP2;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASS = "1";

    public DocumentRepository() {
        try {
            Class.forName(JDBC_DRIVER);
            createTables();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void createTables() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            String sql = "CREATE TABLE IF NOT EXISTS Document " +
                    "(documentID INT PRIMARY KEY, " +
                    "titre VARCHAR(255), " +
                    "nombreExemplaires INT, " +
                    "documentType VARCHAR(20))";

            stmt.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS Livre " +
                    "(documentID INT PRIMARY KEY, " +
                    "ISBN VARCHAR(20), " +
                    "auteur VARCHAR(255), " +
                    "editeur VARCHAR(255), " +
                    "nombrePages INT, " +
                    "FOREIGN KEY (documentID) REFERENCES Document(documentID))";

            stmt.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS CD " +
                    "(documentID INT PRIMARY KEY, " +
                    "artiste VARCHAR(255), " +
                    "duree INT, " +
                    "genre VARCHAR(50), " +
                    "FOREIGN KEY (documentID) REFERENCES Document(documentID))";

            stmt.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS DVD " +
                    "(documentID INT PRIMARY KEY, " +
                    "director VARCHAR(255), " +
                    "duree INT, " +
                    "rating VARCHAR(10), " +
                    "FOREIGN KEY (documentID) REFERENCES Document(documentID))";

            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean save(Document document) {
        String documentType = "";
        if (document instanceof Livre) {
            documentType = "Livre";
        } else if (document instanceof CD) {
            documentType = "CD";
        } else if (document instanceof DVD) {
            documentType = "DVD";
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO Document (documentID, titre, nombreExemplaires, documentType) VALUES (?, ?, ?, ?)")) {

            pstmt.setInt(1, document.getDocumentID());
            pstmt.setString(2, document.getTitre());
            pstmt.setInt(3, document.getNombreExemplaires());
            pstmt.setString(4, documentType);

            pstmt.executeUpdate();

            if (document instanceof Livre) {
                Livre livre = (Livre) document;
                try (PreparedStatement pstmtLivre = conn.prepareStatement(
                        "INSERT INTO Livre (documentID, ISBN, auteur, editeur, nombrePages) VALUES (?, ?, ?, ?, ?)")) {

                    pstmtLivre.setInt(1, document.getDocumentID());
                    pstmtLivre.setString(2, livre.getISBN());
                    pstmtLivre.setString(3, livre.getAuteur());
                    pstmtLivre.setString(4, livre.getEditeur());
                    pstmtLivre.setInt(5, livre.getNombrePages());
                    pstmtLivre.executeUpdate();
                }
            } else if (document instanceof CD) {
                CD cd = (CD) document;
                try (PreparedStatement pstmtCD = conn.prepareStatement(
                        "INSERT INTO CD (documentID, artiste, duree, genre) VALUES (?, ?, ?, ?)")) {

                    pstmtCD.setInt(1, document.getDocumentID());
                    pstmtCD.setString(2, cd.getArtiste());
                    pstmtCD.setInt(3, cd.getDuree());
                    pstmtCD.setString(4, cd.getGenre());
                    pstmtCD.executeUpdate();
                }
            } else if (document instanceof DVD) {
                DVD dvd = (DVD) document;
                try (PreparedStatement pstmtDVD = conn.prepareStatement(
                        "INSERT INTO DVD (documentID, director, duree, rating) VALUES (?, ?, ?, ?)")) {

                    pstmtDVD.setInt(1, document.getDocumentID());
                    pstmtDVD.setString(2, dvd.getDirector());
                    pstmtDVD.setInt(3, dvd.getDuree());
                    pstmtDVD.setString(4, dvd.getRating());
                    pstmtDVD.executeUpdate();
                }
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Document findById(Integer documentID) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Document WHERE documentID = ?")) {

            pstmt.setInt(1, documentID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String documentType = rs.getString("documentType");

                if ("Livre".equals(documentType)) {
                    try (PreparedStatement pstmtLivre = conn.prepareStatement(
                            "SELECT * FROM Livre WHERE documentID = ?")) {

                        pstmtLivre.setInt(1, documentID);
                        ResultSet rsLivre = pstmtLivre.executeQuery();

                        if (rsLivre.next()) {
                            Livre livre = new Livre(
                                    documentID,
                                    rs.getString("titre"),
                                    rs.getInt("nombreExemplaires"),
                                    rsLivre.getString("ISBN"),
                                    rsLivre.getString("auteur"),
                                    rsLivre.getString("editeur"),
                                    rsLivre.getInt("nombrePages"));

                            return livre;
                        }
                    }
                } else if ("CD".equals(documentType)) {
                    try (PreparedStatement pstmtCD = conn.prepareStatement(
                            "SELECT * FROM CD WHERE documentID = ?")) {

                        pstmtCD.setInt(1, documentID);
                        ResultSet rsCD = pstmtCD.executeQuery();

                        if (rsCD.next()) {
                            CD cd = new CD(
                                    documentID,
                                    rs.getString("titre"),
                                    rs.getInt("nombreExemplaires"),
                                    rsCD.getString("artiste"),
                                    rsCD.getInt("duree"),
                                    rsCD.getString("genre"));

                            return cd;
                        }
                    }
                } else if ("DVD".equals(documentType)) {
                    try (PreparedStatement pstmtDVD = conn.prepareStatement(
                            "SELECT * FROM DVD WHERE documentID = ?")) {

                        pstmtDVD.setInt(1, documentID);
                        ResultSet rsDVD = pstmtDVD.executeQuery();

                        if (rsDVD.next()) {
                            DVD dvd = new DVD(
                                    documentID,
                                    rs.getString("titre"),
                                    rs.getInt("nombreExemplaires"),
                                    rsDVD.getString("director"),
                                    rsDVD.getInt("duree"),
                                    rsDVD.getString("rating"));

                            return dvd;
                        }
                    }
                }
            }

            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Document> findAll() {
        List<Document> documents = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Document")) {

            while (rs.next()) {
                int documentID = rs.getInt("documentID");
                String documentType = rs.getString("documentType");

                Document document = findById(documentID);
                if (document != null) {
                    documents.add(document);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return documents;
    }

    @Override
    public boolean update(Document document) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(
                     "UPDATE Document SET titre = ?, nombreExemplaires = ? WHERE documentID = ?")) {

            pstmt.setString(1, document.getTitre());
            pstmt.setInt(2, document.getNombreExemplaires());
            pstmt.setInt(3, document.getDocumentID());

            pstmt.executeUpdate();

            if (document instanceof Livre) {
                Livre livre = (Livre) document;
                try (PreparedStatement pstmtLivre = conn.prepareStatement(
                        "UPDATE Livre SET ISBN = ?, auteur = ?, editeur = ?, nombrePages = ? WHERE documentID = ?")) {

                    pstmtLivre.setString(1, livre.getISBN());
                    pstmtLivre.setString(2, livre.getAuteur());
                    pstmtLivre.setString(3, livre.getEditeur());
                    pstmtLivre.setInt(4, livre.getNombrePages());
                    pstmtLivre.setInt(5, document.getDocumentID());
                    pstmtLivre.executeUpdate();
                }
            } else if (document instanceof CD) {
                CD cd = (CD) document;
                try (PreparedStatement pstmtCD = conn.prepareStatement(
                        "UPDATE CD SET artiste = ?, duree = ?, genre = ? WHERE documentID = ?")) {

                    pstmtCD.setString(1, cd.getArtiste());
                    pstmtCD.setInt(2, cd.getDuree());
                    pstmtCD.setString(3, cd.getGenre());
                    pstmtCD.setInt(4, document.getDocumentID());
                    pstmtCD.executeUpdate();
                }
            } else if (document instanceof DVD) {
                DVD dvd = (DVD) document;
                try (PreparedStatement pstmtDVD = conn.prepareStatement(
                        "UPDATE DVD SET director = ?, duree = ?, rating = ? WHERE documentID = ?")) {

                    pstmtDVD.setString(1, dvd.getDirector());
                    pstmtDVD.setInt(2, dvd.getDuree());
                    pstmtDVD.setString(3, dvd.getRating());
                    pstmtDVD.setInt(4, document.getDocumentID());
                    pstmtDVD.executeUpdate();
                }
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Document> findByTitle(String title) {
        List<Document> documents = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT * FROM Document WHERE LOWER(titre) LIKE ?")) {

            pstmt.setString(1, "%" + title.toLowerCase() + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                documents.add(findById(rs.getInt("documentID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return documents;
    }

    @Override
    public List<Document> findByAuthor(String author) {
        List<Document> documents = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT d.* FROM Document d JOIN Livre l ON d.documentID = l.documentID WHERE LOWER(l.auteur) LIKE ?")) {

            pstmt.setString(1, "%" + author.toLowerCase() + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                documents.add(findById(rs.getInt("documentID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return documents;
    }

    @Override
    public List<Document> findByPublisher(String publisher) {
        List<Document> documents = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT d.* FROM Document d JOIN Livre l ON d.documentID = l.documentID WHERE LOWER(l.editeur) LIKE ?")) {

            pstmt.setString(1, "%" + publisher.toLowerCase() + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                documents.add(findById(rs.getInt("documentID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return documents;
    }

    @Override
    public List<Document> findByType(String type) {
        List<Document> documents = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT * FROM Document WHERE LOWER(documentType) = ?")) {

            pstmt.setString(1, type.toLowerCase());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                documents.add(findById(rs.getInt("documentID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return documents;
    }
}