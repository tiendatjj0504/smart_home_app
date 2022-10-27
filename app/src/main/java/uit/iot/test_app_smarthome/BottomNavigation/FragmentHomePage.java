package uit.iot.test_app_smarthome.BottomNavigation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import uit.iot.test_app_smarthome.R;
import uit.iot.test_app_smarthome.Weather;

public class FragmentHomePage extends Fragment {
    private  View view;
    private TextView date, time, temp;
    // Neu muon hien thi 28.27 thi format #.##
    DecimalFormat df = new DecimalFormat("#");
    Thread thread = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_page, container, false);
        initView();
        getWeatherDetails();
        return view;
    }

    @Override
    public void onResume() {
        Log.i("homepage", "resume");
        if (thread == null) {
            thread = new Thread(){
                @Override
                public void run() {
                    try{
                        while (!isInterrupted()){
                            Thread.sleep(1000);
                            requireActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String[] dateTime = getDateTime().split(" ");
                                    date.setText(dateTime[0]);
                                    time.setText(dateTime[1]);
                                }
                            });
                        }
                    }catch(InterruptedException ignored){}
                }
            };
            thread.start();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.i("homepage", "Pause");
        interruptThread();
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i("homepage", "stop");
        interruptThread();
        super.onStop();
    }

    private void interruptThread(){
        if (thread != null){
            Thread moribund = thread;
            thread = null;
            moribund.interrupt();
        }
    }

    private void initView() {
        date = view.findViewById(R.id.home_date);
        time = view.findViewById(R.id.home_time);
        temp = view.findViewById(R.id.home_temp);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getDateTime(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date now = Calendar.getInstance().getTime();
        return dateFormat.format(now);
    }

    private void getWeatherDetails() {
        String tempUrl = "";
        String city = "Ho Chi Minh";
        String country = "VN";

        String url = "https://api.openweathermap.org/data/2.5/weather";
        String appid = "e53301e27efa0b66d05045d91b2742d3";

        tempUrl = url + "?q=" + city + "," + country + "&appid=" + appid;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                    JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                    String Description = jsonObjectWeather.getString("description");
                    JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                    double Temp = jsonObjectMain.getDouble("temp") - 273.15;
                    temp.setText(df.format(Temp) + "°C");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}
