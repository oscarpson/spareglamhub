package stylist.com.glamhub;

/**
 * Created by OSCAR on 3/12/2017.
 */

public class Fuelgetters {
    String pStation,petrolPrice,diselPrice,paraffinPrice,flat,flong;

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getFlong() {
        return flong;
    }

    public void setFlong(String flong) {
        this.flong = flong;
    }

    public Fuelgetters()
    {

    }
    public Fuelgetters(String station,String petrol,String disel,String paraffin,String flat,String flong)
    {
        this.pStation=station;
        this.petrolPrice=petrol;
        this.diselPrice=disel;
        this.paraffinPrice=paraffin;
        this.flat=flat;
        this.flong=flong;
    }


    public String getpStation() {
        return pStation;
    }

    public void setpStation(String pStation) {
        this.pStation = pStation;
    }

    public String getPetrolPrice() {
        return petrolPrice;
    }

    public void setPetrolPrice(String petrolPrice) {
        this.petrolPrice = petrolPrice;
    }

    public String getDiselPrice() {
        return diselPrice;
    }

    public void setDiselPrice(String diselPrice) {
        this.diselPrice = diselPrice;
    }

    public String getParaffinPrice() {
        return paraffinPrice;
    }

    public void setParaffinPrice(String paraffinPrice) {
        this.paraffinPrice = paraffinPrice;
    }
}
