package com.istv.progcomp.entity;

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
    private Boolean isActive;
    private Boolean isValidated;
    private int nbrOccupant;
    @ManyToOne
    @JoinColumn(name = "bailleur_id")
    private UserEntity baileur;
    @ManyToOne
    @JoinColumn(name = "locataire_id")
    private UserEntity locataire;
    @ManyToOne
    @JoinColumn(name = "logementEntity_id")
    private LogementEntity logementEntity;

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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getValidated() {
        return isValidated;
    }

    public void setValidated(Boolean validated) {
        isValidated = validated;
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
        return logementEntity;
    }

    public void setLogement(LogementEntity logement) {
        this.logementEntity = logement;
    }
}
