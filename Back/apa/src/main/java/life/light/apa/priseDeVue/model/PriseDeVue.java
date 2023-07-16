package life.light.apa.priseDeVue.model;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "prise_de_vue")
public class PriseDeVue {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    private String nom;
    private Date date;
    private String position;
    @ManyToOne
    @JoinColumn(name = "statut_id")
    private StatutPriseDeVue statutPriseDeVue;
    private String remarque;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public StatutPriseDeVue getStatutPriseDeVue() {
        return statutPriseDeVue;
    }

    public void setStatutPriseDeVue(StatutPriseDeVue statutPriseDeVue) {
        this.statutPriseDeVue = statutPriseDeVue;
    }

    public String getRemarque() {
        return remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }
}
