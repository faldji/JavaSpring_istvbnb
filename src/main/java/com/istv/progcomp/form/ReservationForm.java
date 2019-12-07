package com.istv.progcomp.form;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

public class ReservationForm {
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateDebut;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateFin;
    @NotNull
    private int price;
    @Positive
    @NotNull
    private int nbrOccupant;

    public Date getDateDebut() {
        return dateDebut;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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

    public int getNbrOccupant() {
        return nbrOccupant;
    }

    public void setNbrOccupant(int nbrOccupant) {
        this.nbrOccupant = nbrOccupant;
    }
}
