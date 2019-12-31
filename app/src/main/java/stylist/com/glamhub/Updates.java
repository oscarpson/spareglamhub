package stylist.com.glamhub;


public class Updates {
    private String Partname,orderId,justId;

    private String Price;
    private String vehiclemodel;
    private String spareId;
    private String viewers;
    private String photostatus;

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    private String ordertime;
    String image;

    public Updates(String spareId, String image, String partname, String Price, String vehiclemodel) {
        this.Partname = partname;
        this.Price = Price;
        this.vehiclemodel = vehiclemodel;
        this.spareId = spareId;
        this.image = image;
    }

    public Updates(String partname, String price, String spareId, String vehiclemodel) {
        Partname = partname;
        Price = price;
        this.spareId = spareId;
        this.vehiclemodel = vehiclemodel;
    }

    public Updates(String partname,String vehiclemodel, String spareId, String viewers, String orderId, String ordertime,String photostatus) {
        Partname = partname;
        this.orderId = orderId;
        this.ordertime=ordertime;
        this.vehiclemodel = vehiclemodel;
        this.spareId = spareId;
        this.viewers = viewers;
        this.photostatus=photostatus;
    }

    public String getPartname() {
        return Partname;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getJustId() {
        return justId;
    }

    public String getPhotostatus() {
        return photostatus;
    }

    public void setPhotostatus(String photostatus) {
        this.photostatus = photostatus;
    }

    public void setJustId(String justId) {
        this.justId = justId;
    }

    public String getViewers() {
        return viewers;
    }

    public void setViewers(String viewers) {
        this.viewers = viewers;
    }

    public void setPartname(String partname) {
        Partname = partname;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getVehiclemodel() {
        return vehiclemodel;
    }

    public void setVehiclemodel(String vehiclemodel) {
        this.vehiclemodel = vehiclemodel;
    }

    public String getSpareId() {
        return spareId;
    }

    public void setSpareId(String spareId) {
        this.spareId = spareId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Updates() {
    }


    // private int newsId;




}