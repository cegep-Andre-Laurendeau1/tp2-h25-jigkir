package ca.cal.tp2.repository;

import ca.cal.tp2.model.*;

import java.time.LocalDate;
import java.util.List;

public class DocumentRepositoryJDBC implements InterfaceRepository<Document>  {
    private final CdRepositoryJDBC cdRepositoryJDBC;
    private final DvdRepositoryJDBC dvdRepositoryJDBC;
    private final LivreRepositoryJDBC livreRepositoryJDBC;

    public DocumentRepositoryJDBC(CdRepositoryJDBC cdRepositoryJDBC, DvdRepositoryJDBC dvdRepositoryJDBC, LivreRepositoryJDBC livreRepositoryJDBC) {
        this.cdRepositoryJDBC = cdRepositoryJDBC;
        this.dvdRepositoryJDBC = dvdRepositoryJDBC;
        this.livreRepositoryJDBC = livreRepositoryJDBC;
    }

    @Override
    public void save(Document document) {
        if (document instanceof Cd) {
            cdRepositoryJDBC.save((Cd) document);
        } else if (document instanceof Dvd) {
            dvdRepositoryJDBC.save((Dvd) document);
        } else if (document instanceof Livre) {
            livreRepositoryJDBC.save((Livre) document);
        }
    }

    @Override
    public Document get(Long id) {
        Document document = cdRepositoryJDBC.get(id);
        if (document == null) {
            document = dvdRepositoryJDBC.get(id);
        }
        if (document == null) {
            document = livreRepositoryJDBC.get(id);
        }
        return document;
    }

    @Override
    public List<Document> get(String titreSubString, LocalDate annePublication) {
        return List.of();
    }

    @Override
    public List<Document> get(String titreSubString) {
        // Implement as needed
        return null;
    }

    @Override
    public List<Document> get(LocalDate anneePublication) {
        // Implement as needed
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Document> get(Emprunteur emprunteur) {
        return List.of();
    }

    @Override
    public List<Document> get(Emprunt emprunt) {
        return List.of();
    }
}

