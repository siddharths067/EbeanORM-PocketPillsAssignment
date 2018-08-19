package models.db;

import java.util.*;
import io.ebean.annotation.Index;
import io.ebean.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import java.io.Serializable;


@Entity
@Table(name = "patient_address")
public class PatientAddress extends BaseModel implements Serializable {

    @Column(name = "patient_id")
    private long patientId;

    @Column(name = "nickname")
    @NotNull(message = "Name cannot be null")
    @Size(max = 255, min = 1, message = "Name must be between 1 and 255 characters long")
    private String nickname;

    @Column(name = "province")
    @NotNull(message = "Province is required")
    @Size(min = 1, max = 87)
    private String province;

    @Column(name = "postal_code")
    @NotNull(message = "Postal Code  is required")
    @Size(min = 6, max = 40, message = "Incorrect Province length")
    private String postalCode;

    @Column(name = "street_address")
    @NotNull(message = "Street Address is required")
    @Size(min = 1, max = 255, message = "Street address length is incorrect")
    private String streetAddress;

    @Column(name = "city")
    @NotNull(message = "City is required")
    @Size(min = 1, max = 58, message = "City name length is incorrect")
    private String city;

    //Storing only the TLDs
    @Column(name = "country")
    @Size(min = 2, max = 4)
    private String country = "ca";

    /******************************* Getters and Setters ******************************/
    public void setPatientId(long patientId){
        this.patientId = patientId;
    }

    public long getPatientId(){
        return this.patientId;
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public String getNickname(){
        return this.nickname;
    }

    public void setProvice(String province){
        this.province = province;
    }

    public String getProvince(){
        return this.province;
    }

    public void setPostalCode(String postalCode){
        this.postalCode = postalCode;
    }

    public String getPostalCode(){
        return this.postalCode;
    }

    public void setStreetAddress(String streetAddress){
        this.streetAddress = streetAddress;
    }

    public String getStreetAddress(){
        return this.streetAddress;
    }

    public void setCountry(String country){
        this.country = country;
    }

    public String getCountry(){
        return this.country;
    }

    public void setCity(String city){
        this.city = city;
    }

    public String getCity(){
        return this.city;
    }

    public PatientAddress(long patientId, String nickname, String province, String postalCode, String streetAddress, String city, String country){
        this.enabled = true;
        this.patientId = patientId;
        this.nickname = nickname;
        this.province = province;
        this.postalCode = postalCode;
        this.streetAddress = streetAddress;
        this.city = city;
        this.country = country;

    }
    /******************************* End Of Getters and Setters ***********************/


    public static Finder<Long, PatientAddress> find  = new Finder<Long, PatientAddress>( PatientAddress.class);

    public static List<PatientAddress> FindAllById(Long patientId){

        return find.nativeSql("select * from patient_address where patient_id="+patientId).findList();
    }

}
