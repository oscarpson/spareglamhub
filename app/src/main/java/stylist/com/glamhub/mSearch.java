package stylist.com.glamhub;

import android.graphics.drawable.Drawable;

/**
 * Created by OSCAR on 3/14/2017.
 */

public class mSearch {

    String mSearchtype,mSearchmoel,mSearchdetails,imgmSearch,partName;

    public mSearch(String  imgsearch, String model, String type, String details)
    {
        this.imgmSearch=imgsearch;
        this.mSearchdetails=details;
        this.mSearchmoel=model;
        this.mSearchtype=type;

    }

    public String getImgmSearch() {
        return imgmSearch;
    }

    public void setImgmSearch(String imgmSearch) {
        this.imgmSearch = imgmSearch;
    }

    public String getmSearchtype() {
        return mSearchtype;
    }

    public void setmSearchtype(String mSearchtype) {
        this.mSearchtype = mSearchtype;
    }

    public String getmSearchmoel() {
        return mSearchmoel;
    }

    public void setmSearchmoel(String mSearchmoel) {
        this.mSearchmoel = mSearchmoel;
    }

    public String getmSearchdetails() {
        return mSearchdetails;
    }

    public void setmSearchdetails(String mSearchdetails) {
        this.mSearchdetails = mSearchdetails;
    }
}
