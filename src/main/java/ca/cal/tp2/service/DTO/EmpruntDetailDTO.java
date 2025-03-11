package ca.cal.tp2.service.DTO;

import ca.cal.tp2.model.Document;
import ca.cal.tp2.model.Emprunt;
import java.time.LocalDate;

public record EmpruntDetailDTO(LocalDate dateRetourPrevue, LocalDate dateRetourActuelle, String status, Emprunt emprunt, Document document) {}
