package com.istv.progcomp.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
public class ReservationEntity implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    private int price;
    @Temporal(TemporalType.DATE)
    private Date createdAt;
    private Date dateDebut;
    private Date dateFin;
    private Boolean active;
    private Boolean validated;
    private int nbrOccupant;
    @ManyToOne
    @JoinColumn(name = "bailleur_id")
    private UserEntity baileur;
    @ManyToOne
    @JoinColumn(name = "locataire_id")
    private UserEntity locataire;
    @ManyToOne
    @JoinColumn(name = "logementEntity_id")
    private LogementEntity logement;

    public long getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean isValidated() {
        return validated;
    }

    public Boolean getValidated(){return validated;}

    public void setValidated(Boolean validated) {
        this.validated = validated;
    }

    public int getNbrOccupant() {
        return nbrOccupant;
    }

    public void setNbrOccupant(int nbrOccupant) {
        this.nbrOccupant = nbrOccupant;
    }

    public UserEntity getBaileur() {
        return baileur;
    }

    public void setBaileur(UserEntity baileur) {
        this.baileur = baileur;
    }

    public UserEntity getLocataire() {
        return locataire;
    }

    public void setLocataire(UserEntity locataire) {
        this.locataire = locataire;
    }

    public LogementEntity getLogement() {
        return logement;
    }

    public void setLogement(LogementEntity logement) {
        this.logement = logement;
    }
}
