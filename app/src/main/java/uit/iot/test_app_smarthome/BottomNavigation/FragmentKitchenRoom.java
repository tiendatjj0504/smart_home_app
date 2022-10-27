package uit.iot.test_app_smarthome.BottomNavigation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
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

import uit.iot.test_app_smarthome.Database.Kitchen;
import uit.iot.test_app_smarthome.Database.MainDoor;
import uit.iot.test_app_smarthome.R;

@SuppressLint("UseSwitchCompatOrMaterialCode")
public class FragmentKitchenRoom extends Fragment implements CompoundButton.OnCheckedChangeListener {
    private  View view;
    private Switch doorState;
    private TextView gas, temp, humi, warning;
    private Kitchen kitchen;
    private DatabaseReference dbRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_kitchen_room, container, false);
        initView();
        doorState.setOnCheckedChangeListener(this);
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

                Kitchen kitchen = time.getValue(Kitchen.class);

                if(kitchen == null){
                    Toast.makeText(requireContext(), "kitchen null", Toast.LENGTH_SHORT).show();
                    return;
                }

                doorState.setChecked(kitchen.isDoorState());
                gas.setText(kitchen.getGas() + "%");
                temp.setText(kitchen.getTemp() + "%");
                humi.setText(kitchen.getHumi() + "%");
                warning.setText(kitchen.getWarningState());
                if (kitchen.getWarningState().equals("an toan")){
                    warning.setTextColor(getResources().getColor(R.color.green));
                }
                else {
                    warning.setTextColor(getResources().getColor(R.color.red));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void initView() {
        doorState = view.findViewById(R.id.kitchen_door_state);
        gas = view.findViewById(R.id.kitchen_gas);
        temp = view.findViewById(R.id.kitchen_temp);
        humi = view.findViewById(R.id.kitchen_humi);
        warning = view.findViewById(R.id.kitchen_warning);
        dbRef = FirebaseDatabase.getInstance().getReference("kitchen");
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean state) {
        dbRef.child(getDateTime()).setValue(new Kitchen(state, "50", "50", "50", "an toan"));
    }

    @SuppressLint("SimpleDateFormat")
    private static String getDateTime(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd/ HH:mm:ss");
        Date now = Calendar.getInstance().getTime();
        return dateFormat.format(now);
    }
}
