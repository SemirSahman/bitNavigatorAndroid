package semir.bitcamp.ba.bitnavigatorandroid.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;
import android.os.Looper;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import semir.bitcamp.ba.bitnavigatorandroid.R;
import semir.bitcamp.ba.bitnavigatorandroid.service.ServiceRequest;


public class LoginActivity extends Activity {

    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private Button mLoginButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setting default screen to login.xml
        setContentView(R.layout.activity_login);

        mEmailEditText = (EditText) findViewById(R.id.login_email);
        mPasswordEditText = (EditText) findViewById(R.id.login_password);
        mLoginButton = (Button) findViewById(R.id.btnLogin);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mEmailEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();

                JSONObject json = new JSONObject();
                try {
                    json.put("email", email);
                    json.put("password", password);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String url = getString(R.string.service_sign_in);

                ServiceRequest.post(url, json.toString(), loginVerification());

            }
        });




        TextView registerScreen = (TextView) findViewById(R.id.link_to_register);
        TextView homeScreen = (TextView) findViewById(R.id.link_to_homepage);

        // Listening to register new account link
        registerScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Switching to Register screen
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });

        // Listening to Map Screen link
        homeScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // Closing registration screen
                // Switching to Login Screen/closing register screen
                Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(i);

                finish();
            }
        });
    }

    private Callback loginVerification() {
        return new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                //makeToast(R.string.toast_try_again);
                Log.d("dibag", "hdashgdkjsa87998987");
            }

            @Override
            public void onResponse(Response response) throws IOException {

                try {
                    String responseJSON= response.body().string();
                    JSONObject json = new JSONObject(responseJSON);
                    Integer id = json.getInt("id");
                    Log.d("dibag", "tuj sam /*/*/------------*//*/*/*/*/-----------/*/*/*/");


                    gotToMap();


                } catch (JSONException e) {
                    makeToast("Email and/or password incorrect");
                    e.printStackTrace();
                }
            }
        };
    }

    public void gotToMap() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    private void makeToast(final String message){
        new Handler(Looper.getMainLooper())
                .post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this,
                                message,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
