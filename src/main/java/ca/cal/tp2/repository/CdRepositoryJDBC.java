package ca.cal.tp2.repository;

import ca.cal.tp2.model.Cd;
import ca.cal.tp2.model.Emprunt;
import ca.cal.tp2.model.Emprunteur;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class CdRepositoryJDBC extends ParentRepository implements InterfaceRepository<Cd> {

    @Override
    public void save(Cd cd) {
        String sql = "INSERT INTO cd (id, titre, anneePublication, nombreExemplaire, artiste, duree, genre) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, cd.getId());
            ps.setString(2, cd.getTitre());
            ps.setDate(3, java.sql.Date.valueOf(cd.getAnneePublication()));
            ps.setInt(4, cd.getNombreExemplaire());
            ps.setString(5, cd.getArtiste());
            ps.setInt(6, cd.getDuree());
            ps.setString(7, cd.getGenre());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Cd get(Long id) {
        String sql = "SELECT * FROM cd WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            var result = ps.executeQuery();
            if (result.next()) {
                return new Cd(
                        result.getLong("id"),
                        result.getString("titre"),
                        result.getDate("anneePublication").toLocalDate(),
                        result.getInt("nombreExemplaire"),
                        result.getString("artiste"),
                        result.getInt("duree"),
                        result.getString("genre"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Cd> get(String titreSubString, LocalDate annePublication) {
        return List.of();
    }

    @Override
    public List<Cd> get(String titreSubString) {
        return List.of();
    }

    @Override
    public List<Cd> get(LocalDate annePublication) {
        return List.of();
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM cd WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Cd> get(Emprunteur emprunteur) {
        return List.of();
    }

    @Override
    public List<Cd> get(Emprunt emprunt) {
        return List.of();
    }
}