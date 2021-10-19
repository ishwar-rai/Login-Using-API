package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {
    Button button;
    TextView textView;
    String uemail, upassword;
    EditText email, password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //finding the view by ids
        button = findViewById(R.id.button);
        email = findViewById(R.id.emailid);
        password = findViewById(R.id.passwordid);
        textView = findViewById(R.id.textView);

        //login button click listener call when user will click login button
        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                uemail = email.getText().toString();          //getting the user email
                upassword = email.getText().toString();       //user password

                //Field validation: email or password shouldn't be empty
                if(email.getText().toString().isEmpty() || password.getText().toString().isEmpty())
                {
                    textView.setText("Please Fill the required fields");
                }
                else{
                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());  //Creating Queue for sending request
                        final String url = "https://reqres.in/api/login";                      //assigning api url to a string variable
                        JSONObject params = new JSONObject();                                  //Creatng JSON object

                        //Handling the JSONException as JSON might throw exception
                        try
                        {
                            params.put("email",uemail);
                            params.put("password",upassword);
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }

                        // Request a JSONObject response from the provided URL.
                        JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>()
                        {
                            @Override
                            public void onResponse(JSONObject response)
                            {
                                try
                                {
                                    String token = response.getString("token");                       //token in reponse after succesful login
                                    startActivity(new Intent(getApplicationContext(), HomeActivity.class)   //sending email and password to HomeActivity via Implicit Intent
                                            .putExtra("email", uemail)
                                            .putExtra("token", token));
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error)
                            {
                                textView.setText("Error: "+error);
                            }
                        }){

                        };
                        queue.add(request);  //adding the request to queue
                }
            }
        });
    }
}