package uit.iot.test_app_smarthome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import uit.iot.test_app_smarthome.BottomNavigation.MainNavigationActivity;
import uit.iot.test_app_smarthome.Database.User;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogin;
    private EditText userName, passWord;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private DatabaseReference dbRef;

    @Override
    protected void onPostResume() {
        Log.i("login", "post resume");
        btnLogin.setEnabled(true);
        super.onPostResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        FirebaseSetup();
        btnLogin.setOnClickListener(this);

    }

    private void FirebaseSetup() {
        mAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference("users");
    }

    private void initView() {
        btnLogin = findViewById(R.id.button_login);
        userName = findViewById(R.id.user_name);
        passWord = findViewById(R.id.password);
        progressBar = findViewById(R.id.progress3);
    }

    @Override
    public void onClick(View view) {
        String gmail = userName.getText().toString().trim();
        String pass = passWord.getText().toString().trim();

        if (gmail.isEmpty()){
            userName.setError("yêu cầu nhập gmail");
            userName.requestFocus();
        }
        if (pass.isEmpty()){
            passWord.setError("yêu cầu nhập mật khẩu");
            passWord.requestFocus();
        }
        progressBar.setVisibility(View.VISIBLE);
        login(gmail, pass);

    }

    private void login(String gmail, String pass) {

        mAuth.signInWithEmailAndPassword(gmail, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
//                            User user = new User("duy", gmail, pass);
                            String [] name = gmail.split("@");
                            User.getInstance().setUser(name[0], name[0], pass);
                            InsertUser(User.getInstance());
                            Intent intent = new Intent(Login.this, MainNavigationActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            btnLogin.setEnabled(false);
                        }
                        else {
                            Toast.makeText(Login.this, "đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    private void InsertUser(User user) {

        dbRef.child(user.getUsername()).setValue(user);
    }
}