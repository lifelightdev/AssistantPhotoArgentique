package life.light.apa.referentiel.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sous_type_materiel")
public class SousTypeMateriel {

    public static final long ID_APPAREIL_PHOTO_ARGENTIQUE = 1;
    public static final long ID_CHASSIS_PRISE_DE_VUE = 3;
    public static final long ID_PIED = 6;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "type_id")
    private TypeMateriel type;
    private String nom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeMateriel getType() {
        return type;
    }

    public void setType(TypeMateriel type) {
        this.type = type;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
