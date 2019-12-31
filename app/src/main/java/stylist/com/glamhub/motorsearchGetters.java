package stylist.com.glamhub;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by OSCAR on 3/14/2017.
 */

public class motorsearchGetters
{

   String mSearchtype;
    String mSearchmoel;
    String mSearchdetails;

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    String imgmSearch;

    public motorsearchGetters(String mSearchtype, String mSearchmoel, String mSearchdetails, String imgmSearch, String partName) {
        this.mSearchtype = mSearchtype;
        this.mSearchmoel = mSearchmoel;
        this.mSearchdetails = mSearchdetails;
        this.imgmSearch = imgmSearch;
        this.partName = partName;
    }

    String partName;

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

    public motorsearchGetters(String imgsearch, String model, String type, String details)
    {
        this.imgmSearch=imgsearch;
        this.mSearchdetails=details;
        this.mSearchmoel=model;
        this.mSearchtype=type;

    }
}
