package stylist.com.glamhub.myorders;

/**
 * Created by OSCAR on 7/13/2017.
 */

public class BestPrice
    {
        String username,price,manufacturer,orderId,spareId,mechId,time,status,sparename;

        public BestPrice(String username, String price, String manufacturer, String orderId, String spareId, String mechId,String time) {
            this.username = username;
            this.price = price;
            this.manufacturer = manufacturer;
            this.orderId = orderId;
            this.spareId = spareId;
            this.mechId = mechId;
            this.time=time;
        }

        public BestPrice(String username, String price, String manufacturer, String time, String status, String sparename) {
            this.username = username;
            this.price = price;
            this.manufacturer = manufacturer;
            this.time = time;
            this.status = status;
            this.sparename = sparename;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getSparename() {
            return sparename;
        }

        public void setSparename(String sparename) {
            this.sparename = sparename;
        }

        public String getUsername() {
            return username;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getManufacturer() {
            return manufacturer;
        }

        public void setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getSpareId() {
            return spareId;
        }

        public void setSpareId(String spareId) {
            this.spareId = spareId;
        }

        public String getMechId() {
            return mechId;
        }

        public void setMechId(String mechId) {
            this.mechId = mechId;
        }

        public BestPrice() {
        }
    }

