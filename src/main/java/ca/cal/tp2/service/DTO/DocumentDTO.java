package ca.cal.tp2.service.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class DocumentDTO {
    private String titre;
    private LocalDate anneePublication;

    @Override
    public String toString() {
        return "DocumentDTO{" +
                ", titre='" + titre + '\'' +
                ", anneePublication=" + anneePublication +
                '}';
    }
}