package ca.cal.tp2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String email;
    private String numTelephone;
    public Utilisateur(String nom, String email, String numTelephone){
        this.nom = nom;
        this.email = email;
        this.numTelephone = numTelephone;
    }
}