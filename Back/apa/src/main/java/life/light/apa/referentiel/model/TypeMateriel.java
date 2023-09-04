package life.light.apa.referentiel.model;


import jakarta.persistence.*;

@Entity
@Table(name = "type_materiel")
public class TypeMateriel {

    public static final long ID_PRISE_DE_VUE = 1L;
    public static final long DEVELOPPEMENT = 2L;
    public static final long TIRAGE = 3L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}


