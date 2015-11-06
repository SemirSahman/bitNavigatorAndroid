package ba.bitcamp.bitNavigator.controllers;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Handler;
import android.os.Looper;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ba.bitcamp.bitNavigator.bitnavigator.R;
import ba.bitcamp.bitNavigator.models.User;
import ba.bitcamp.bitNavigator.service.Navbar;
import ba.bitcamp.bitNavigator.service.ServiceRequest;

/**
 * Created by hajrudin.sehic on 17/10/15.
 */
public class LoginActivity extends Navbar {

    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private Button mLoginButton;
    private Button mLinkToRegister;

    private Dialog progressDialog;

    private User user;

    private SharedPreferences sharedpreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressDialog = new Dialog(LoginActivity.this);
        sharedpreferences = getSharedPreferences("SESSION", Context.MODE_PRIVATE);
        if(sharedpreferences.contains("email")){
            Intent i = new Intent(getApplicationContext(),
                    ProfileActivity.class);
            startActivity(i);
            finish();
        }

        mEmailEditText = (EditText) findViewById(R.id.login_email);
        mPasswordEditText = (EditText) findViewById(R.id.login_password);
        mLoginButton = (Button) findViewById(R.id.login);
        mLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);

        mLinkToRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setTitle("Loading...");
                progressDialog.show();
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

          navbarButtons();

    }


    private Callback loginVerification() {
        return new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                progressDialog.cancel();
                e.getMessage();
                makeToast("Email and/or password incorrect");
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    String responseJSON= response.body().string();
                    JSONObject userObj = new JSONObject(responseJSON.toString());
                    Integer id = userObj.getInt("id");
                    String name = userObj.getString("firstName");
                    String surname = userObj.getString("lastName");
                    String email = userObj.getString("email");
                    String password = userObj.getString("password");
                    String avatar = userObj.getString("avatar");
                    user = new User(id, name, surname, email, password, avatar);

                    makeToast("Wellcome " + user.getFirstName());

                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putInt("id", id);
                    editor.putString("name", name);
                    editor.putString("surname", surname);
                    editor.putString("email", email);
                    editor.putString("avatar", avatar);
                    editor.commit();

                    goToProfile();

                } catch (JSONException e) {
                    e.getMessage();
                    progressDialog.cancel();
                    makeToast("Email and/or password incorrect");
                }
            }
        };
    }

    public void goToProfile() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    private void makeToast(final String message){
        new Handler(Looper.getMainLooper())
                .post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ba.bitcamp.bitNavigator.controllers.LoginActivity.this,
                                message,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
