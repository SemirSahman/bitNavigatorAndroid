package semir.bitcamp.ba.bitnavigatorandroid.models;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by semir.sahman on 22.10.15..
 */
public class User {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Date dateAdded;

    public User(String firstName, String lastName, String email, String password){
        id = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        dateAdded = Calendar.getInstance().getTime();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
