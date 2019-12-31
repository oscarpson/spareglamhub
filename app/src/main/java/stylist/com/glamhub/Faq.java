package stylist.com.glamhub;

/**
 * Created by admin on 10/26/2016.
 */

public class Faq
{ String header,content;
    public Faq(String header,String content)
    {
        this.header=header;
        this.content=content;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
