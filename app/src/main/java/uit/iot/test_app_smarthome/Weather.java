package uit.iot.test_app_smarthome;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public final class Weather {
    private final String logcat = "weather";
    private final String url = "https://api.openweathermap.org/data/2.5/weather";
    private final String appid = "e53301e27efa0b66d05045d91b2742d3";
    DecimalFormat df = new DecimalFormat("#.##");
    private Context context;
    private double temp;
    private String description;

    public Weather(Context context) {
        this.context = context;
    }

    public double getTemp(){
        getWeatherDetails();
        Log.i(logcat, String.valueOf(temp));
        return temp;
    }

    public String getDescription() {
        getWeatherDetails();
        return description;
    }

    private void getWeatherDetails() {
        String tempUrl = "";
        String city = "Ho Chi Minh";
        String country = "VN";
        final double[] outputTemp = {0.0};
        final String[] outputDecription = {""};

        tempUrl = url + "?q=" + city + "," + country + "&appid=" + appid;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String output = "";
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                    JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                    outputDecription[0] = jsonObjectWeather.getString("description");
                    JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                    outputTemp[0] = jsonObjectMain.getDouble("temp") - 273.15;

                    output += "Current weather of " + city + " (" + country + ")"
                                + "\n Temp: " + df.format(temp) + " °C"
                                + "\n Description: " + description;

                 } catch (JSONException e) {
                        e.printStackTrace();
                 }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}

// https://github.com/sandipapps/Weather-Update
