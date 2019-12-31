package stylist.com.glamhub;

/**
 * Created by admin on 10/19/2016.
 */

public class User
{
    String phoneNumber;
    String password;
    String login_status;
    String idNumber;
    String dateOfBirth;
    String role;
    String bioData;
    String token;


    // Empty constructor
    public User()
    {

    }
    // constructor
    public User(String phoneNumber,String password,String login_status,String idNumber,String role,String bioData,String token,String dateOfBirth)
    {
        this.phoneNumber=phoneNumber;
        this.password=password;
        this.login_status=login_status;
       this.idNumber=idNumber;
        this.role=role;
        this.bioData=bioData;
        this.dateOfBirth=dateOfBirth;
        this.token=token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin_status() {
        return login_status;
    }

    public void setLogin_status(String login_status) {
        this.login_status = login_status;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getBioData() {
        return bioData;
    }

    public void setBioData(String bioData) {
        this.bioData = bioData;
    }
}
