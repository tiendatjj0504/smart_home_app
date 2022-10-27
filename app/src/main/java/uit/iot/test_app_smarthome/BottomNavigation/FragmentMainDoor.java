package uit.iot.test_app_smarthome.BottomNavigation;

import static uit.iot.test_app_smarthome.R.color.red;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import uit.iot.test_app_smarthome.Database.MainDoor;
import uit.iot.test_app_smarthome.R;


public class FragmentMainDoor extends Fragment implements CompoundButton.OnCheckedChangeListener {

    private  View view;
    private Switch doorSwitch;
    private TextView tvLightState, tvWarning;
    private DatabaseReference dbRef;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_door, container, false);
        initView();
        doorSwitch.setOnCheckedChangeListener(this);
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

                MainDoor maindoor = time.getValue(MainDoor.class);

                if(maindoor == null){
                    Toast.makeText(requireContext(), "maindoor null", Toast.LENGTH_SHORT).show();
                    return;
                }


                doorSwitch.setChecked(maindoor.isDoorState());
                tvLightState.setText(maindoor.getLightState() + "%");
                tvWarning.setText(maindoor.getWarningState());
                if (maindoor.getWarningState() == "an toan"){
                    tvWarning.setTextColor(getResources().getColor(R.color.green));
                }
                else {
                    tvWarning.setTextColor(getResources().getColor(R.color.red));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initView() {
        doorSwitch = view.findViewById(R.id.door_state);
        tvLightState = view.findViewById(R.id.main_door_light_state);
        tvWarning = view.findViewById(R.id.main_door_warning);
        dbRef = FirebaseDatabase.getInstance().getReference("maindoor");
    };

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean state) {
        dbRef.child(getDateTime()).setValue(new MainDoor(state, "50", "an toan"));
    }
    // Get datetime
    @SuppressLint("SimpleDateFormat")
    private static String getDateTime(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd/ HH:mm:ss");
        Date now = Calendar.getInstance().getTime();
        return dateFormat.format(now);
    }
}