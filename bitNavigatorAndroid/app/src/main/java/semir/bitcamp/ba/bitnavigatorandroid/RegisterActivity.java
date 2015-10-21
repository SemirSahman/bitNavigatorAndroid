package semir.bitcamp.ba.bitnavigatorandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Semir on 17.10.2015.
 */

public class RegisterActivity extends Activity {

    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText password;
    private EditText confirmPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set View to register.xml
        setContentView(R.layout.register);

        firstName = (EditText) findViewById(R.id.firstname);
        lastName = (EditText) findViewById(R.id.lastname);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirmpassword);

        TextView loginScreen = (TextView) findViewById(R.id.link_to_login);
        TextView homeScreen = (TextView) findViewById(R.id.link_to_homepage);

        findViewById(R.id.btnRegister).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                final String emailString = email.getText().toString();
                if (!isValidEmail(emailString)) {
                    email.setError("Invalid Email");
                }

                final String pass = password.getText().toString();
                if (!isValidPassword(pass)) {
                    password.setError("Invalid Password");
                }

                final String fName = firstName.getText().toString();
                if(!isValidName(fName)){
                    firstName.setError("First name can only contain letters");
                }

                final String lName = lastName.getText().toString();
                if(!isValidName(lName)){
                    lastName.setError("Last name can only contain letters");
                }

                if(!(password.getText().toString().equals(confirmPassword.getText().toString()))){
                    confirmPassword.setError("Password does not match");
                }
            }
        });

        // Listening to Login Screen link
        loginScreen.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // Closing registration screen
                // Switching to Login Screen/closing register screen
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);

                finish();
            }
        });

        // Listening to Map Screen link
        homeScreen.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // Closing registration screen
                // Switching to Login Screen/closing register screen
                Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(i);

                finish();
            }
        });
    }

    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidName(String name){
        String NAME_PATTERN = "[a-zA-Z]+";

        Pattern pattern = Pattern.compile(NAME_PATTERN);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    // validating password with retype password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 7) {
            return true;
        }
        return false;
    }

}