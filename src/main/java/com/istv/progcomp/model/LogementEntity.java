package com.istv.progcomp.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Entity
public class LogementEntity implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    private String reference;
    private int type;
    private String address;
    private Date houseYear;
    private double surface;
    private int price;
    private int nbrLoc;
    private String description;
    private String img;
    private boolean enabled;
    @ManyToOne
    @JoinColumn
    private UserEntity bailleur;
    @OneToMany(targetEntity = ReservationEntity.class,mappedBy = "logement",cascade = CascadeType.ALL)
    private Collection<ReservationEntity> reservations;

    public long getId() {
        return id;
    }

    public String getReference() {
        return reference;
    }

    public double getSurface() {
        return surface;
    }

    public void setSurface(double surface) {
        this.surface = surface;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getHouseYear() {
        return houseYear;
    }

    public void setHouseYear(Date houseYear) {
        this.houseYear = houseYear;
    }

    public Collection<ReservationEntity> getReservations() {
        return reservations;
    }
    public Collection<ReservationEntity> getActiveReservations() {
        if (reservations.isEmpty())
            return reservations;
        return reservations.stream().filter(ReservationEntity::isActive).collect(Collectors.toCollection(ArrayList::new));
    }
    public Collection<ReservationEntity> getValidReservations() {
        if (reservations.isEmpty())
            return reservations;
        return reservations.stream().filter(j -> j.isActive() && j.isValidated()).collect(Collectors.toCollection(ArrayList::new));
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setReservations(Collection<ReservationEntity> reservations) {
        this.reservations = reservations;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getNbrLoc() {
        return nbrLoc;
    }

    public void setNbrLoc(int nbrLoc) {
        this.nbrLoc = nbrLoc;
    }

    public UserEntity getBailleur() {
        return bailleur;
    }

    public void setBailleur(UserEntity bailleur) {
        this.bailleur = bailleur;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
