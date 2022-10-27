package uit.iot.test_app_smarthome.BottomNavigation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import uit.iot.test_app_smarthome.Database.Bedroom;
import uit.iot.test_app_smarthome.Database.Kitchen;
import uit.iot.test_app_smarthome.R;

@SuppressLint("UseSwitchCompatOrMaterialCode")
public class FragmentBedRoom extends Fragment implements CompoundButton.OnCheckedChangeListener {
    private  View view;
    private Switch switchWindow, switchLight;
    private boolean window, light;
    private DatabaseReference dbRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bed_room, container, false);
        initView();
        switchWindow.setOnCheckedChangeListener(this);
        switchLight.setOnCheckedChangeListener(this);
        getData();
        return view;
    }

    private void getData() {
        dbRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                DataSnapshot year = null;

                for (DataSnapshot d : snapshot.getChildren()){
                    year = d;
                }


                if(year == null){
                    Toast.makeText(requireContext(), "year null", Toast.LENGTH_SHORT).show();
                    return;
                }

                DataSnapshot month = null;

                for (DataSnapshot d : year.getChildren()){
                    month = d;
                }

                if(month == null){
                    Toast.makeText(requireContext(), "month null", Toast.LENGTH_SHORT).show();
                    return;
                }

                DataSnapshot day = null;

                for (DataSnapshot d : month.getChildren()){
                    day = d;
                }

                if(day == null){
                    Toast.makeText(requireContext(), "day null", Toast.LENGTH_SHORT).show();
                    return;
                }

                DataSnapshot time = null;

                for (DataSnapshot d : day.getChildren()){
                    time = d;
                }

                if(time == null){
                    Toast.makeText(requireContext(), "day null", Toast.LENGTH_SHORT).show();
                    return;
                }

                Bedroom bedroom = time.getValue(Bedroom.class);

                if(bedroom == null){
                    Toast.makeText(requireContext(), "bedroom null", Toast.LENGTH_SHORT).show();
                    return;
                }
                switchWindow.setChecked(bedroom.isWindowState());
                switchLight.setChecked(bedroom.isLightState());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initView() {
        switchWindow = view.findViewById(R.id.bed_window_state);
        switchLight = view.findViewById(R.id.bed_light_state);
        dbRef = FirebaseDatabase.getInstance().getReference("bedroom");
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean state) {
        switch(compoundButton.getId()){
            case R.id.bed_window_state:
                window = state;
                dbRef.child(getDateTime()).setValue(new Bedroom(state, light));
                break;
            default:
                light = state;
                dbRef.child(getDateTime()).setValue(new Bedroom(window, state));
        }
    }
    @SuppressLint("SimpleDateFormat")
    private static String getDateTime(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd/ HH:mm:ss");
        Date now = Calendar.getInstance().getTime();
        return dateFormat.format(now);
    }

}
