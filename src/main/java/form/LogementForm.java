package form;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;

import java.util.Date;
public class LogementForm {
    @NotNull
    private String address;
    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date houseYear;
    @NotNull
    @PositiveOrZero
    private double surface;
    @NotNull
    @NumberFormat
    @Positive
    private int price;
    @NotNull
    @NumberFormat
    @Min(value = 1)
    private int nbrLoc;

    @Size(max = 125)
    private String description;
    private MultipartFile img;
    @NotNull
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public MultipartFile getImg() {
        return img;
    }

    public void setImg(MultipartFile img) {
        this.img = img;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getHouseYear() {
        return houseYear;
    }

    public void setHouseYear(Date houseYear) {
        this.houseYear = houseYear;
    }

    public double getSurface() {
        return surface;
    }

    public void setSurface(double surface) {
        this.surface = surface;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
