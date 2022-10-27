package uit.iot.test_app_smarthome.BottomNavigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import uit.iot.test_app_smarthome.R;

public class FragmentSetting extends Fragment implements View.OnClickListener {
    private View view;
    private EditText name, password;
    private Button edit;
    private ImageView logout;
    private FirebaseAuth mAuth;
    private DatabaseReference dbRef;
    private MainNavigationActivity mainNavigationActivity;
    private boolean isUpdate = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting, container, false);
        initView();
        FirebaseSetup();
        logout.setOnClickListener(this);
        edit.setOnClickListener(this);

        return view;
    }

    private void initView() {
        name = view.findViewById(R.id.edit_name);
        password = view.findViewById(R.id.edit_password);
        edit = view.findViewById(R.id.button_edit);
        logout = view.findViewById(R.id.logout);
        mainNavigationActivity = (MainNavigationActivity) requireActivity();
    }

    private void FirebaseSetup() {
        mAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference("users");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.logout:
                logOut();
                break;
            default:
                editProfile();
        }
    }

    private void editProfile() {
        if (isUpdate){
            name.setEnabled(false);
            password.setEnabled(false);
            edit.setText("chỉnh sửa");
            isUpdate = false;
        }
        else{
            name.setEnabled(true);
            password.setEnabled(true);
            edit.setText("Cập nhật");
            isUpdate = true;
        }

    }

    private void logOut() {
        mAuth.signOut();
        mainNavigationActivity.finish();
    }

}
