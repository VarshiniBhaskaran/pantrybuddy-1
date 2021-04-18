package com.pantrybuddy.server;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.pantrybuddy.activity.IWebService;

import org.json.JSONException;
import org.json.JSONObject;

public class Server {

    private static final String SERVER_NAME = "3.19.241.211";
    private static final String SERVER_PORT = "8080";
    public static final String URL_SIGNUP = "http://" + SERVER_NAME + ":" + SERVER_PORT + "/api/user/create?";
    public static final String URL_LOGIN = "http://" + SERVER_NAME + ":" + SERVER_PORT + "/api/user/login?";
    public static final String URL_GEN_OTP ="http://" + SERVER_NAME + ":" + SERVER_PORT + "/api/user/otp?";
    public static final String URL_VER_OTP ="http://" + SERVER_NAME + ":" + SERVER_PORT + "/api/user/otp?";
    public static final String URL_RES_PWD ="http://" + SERVER_NAME + ":" + SERVER_PORT + "/api/user/password?";

    private static final String ERROR_TAG = "Web Service Error";
    private static final String INFO_TAG = "Web Service INFO";
    private JsonObjectRequest jsonObjectRequest;
    private RequestQueue requestQueue;

    Context context;

    public Server(Context context) {
        this.context = context;
    }

    //http://3.89.143.237:8080/api/user/create?firstName=Varshini&lastName=Bhaskaran&emailId=bhaskarv@uci.edu&phoneNumber=+919710439153&password=varshini
    public void signUp(String email, String mobile, String firstName, String lastName, String password) {

        requestQueue = Volley.newRequestQueue(context);
        String FinalURL = URL_SIGNUP + "firstName=" + firstName + "&lastName=" + lastName + "&emailId=" + email + "&phoneNumber=" + mobile + "&password=" + password;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, FinalURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    ((IWebService) context).processResponse(response);
                    Log.d(INFO_TAG, "Webservice called :" + FinalURL + " : with :" + " firstname:" + firstName + " lastName:" + lastName + " email:" + email + " phoneNumber:" + mobile + " password:" + password);

                } catch (JSONException e) {
                    Log.d(ERROR_TAG, "Cannot sign up user.Error: " + e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(ERROR_TAG, "Cannot sign up user.Error: " + error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void loginEmailPwd(String email, String password) {
        requestQueue = Volley.newRequestQueue(context);
        String FinalURL = URL_LOGIN + "&emailId=" + email + "&password=" + password;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, FinalURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    ((IWebService) context).processResponse(response);
                    Log.d(INFO_TAG, "Webservice called :" + FinalURL + " : with :" + " email:" + email + " password:" + password);
                } catch (JSONException e) {
                    Log.d(ERROR_TAG, "Cannot log in user.Error: " + e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(ERROR_TAG, "Cannot log in user.Error: " + error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void generateOtp(String emailFP) {
        requestQueue = Volley.newRequestQueue(context);
        String FinalURL = URL_GEN_OTP + "&emailId=" + emailFP;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FinalURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    response.put("Type","Generate");
                    ((IWebService) context).processResponse(response);
                    Log.d(INFO_TAG, "Webservice called :" + FinalURL + " : with :" + " email:" + emailFP );
                } catch (JSONException e) {
                    Log.d(ERROR_TAG, "Cannot gen OTP.Error: " + e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(ERROR_TAG, "Cannot gen OTP.Error: " + error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);

    }

    public void verifyOtp(String otp,String emailFP) {
        requestQueue = Volley.newRequestQueue(context);
        String FinalURL = URL_VER_OTP + "&otp=" + otp+"&emailId="+emailFP;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, FinalURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    response.put("Type","Verify");
                    ((IWebService) context).processResponse(response);
                    Log.d(INFO_TAG, "Webservice called :" + FinalURL + " : with :" + " email:" + emailFP );
                } catch (JSONException e) {
                    Log.d(ERROR_TAG, "Cannot verify OTP.Error: " + e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(ERROR_TAG, "Cannot verify OTP.Error: " + error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void resetPassword(String emailFP, String newPwd) {
        requestQueue = Volley.newRequestQueue(context);
        String FinalURL = URL_RES_PWD + "&emailId=" + emailFP+"&password="+newPwd;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, FinalURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    response.put("Type","Reset");
                    ((IWebService) context).processResponse(response);
                    Log.d(INFO_TAG, "Webservice called :" + FinalURL + " : with :" + " email:" + emailFP );
                } catch (JSONException e) {
                    Log.d(ERROR_TAG, "Cannot reset password.Error: " + e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(ERROR_TAG, "Cannot reset password.Error: " + error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
