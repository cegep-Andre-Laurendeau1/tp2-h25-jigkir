package ca.cal.tp2.service;

import ca.cal.tp2.model.Document;
import ca.cal.tp2.repository.DocumentRepository;

import java.util.List;

public class DocumentService {
    private DocumentRepository documentRepository;

    public DocumentService() {
        this.documentRepository = new DocumentRepository();
    }

    public boolean addDocument(Document document) {
        return documentRepository.save(document);
    }

    public Document findById(int documentId) {
        return documentRepository.findById(documentId);
    }

    public List<Document> searchByTitle(String title) {
        return documentRepository.findByTitle(title);
    }

    public List<Document> searchByAuthor(String author) {
        return documentRepository.findByAuthor(author);
    }

    public List<Document> searchByPublisher(String publisher) {
        return documentRepository.findByPublisher(publisher);
    }

    public List<Document> searchByType(String type) {
        return documentRepository.findByType(type);
    }

    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }
}