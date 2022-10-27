package uit.iot.test_app_smarthome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    private Animation topAnimation, bottomAnimation;
    private ImageView logo;
    private TextView name_group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        initView();
        generateAnimation();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, Login.class));
                finish();
            }
        }, 3000);
    }

    private void initView() {
        logo = findViewById(R.id.icon_home);
        name_group = findViewById(R.id.group_name);
    }

    private void generateAnimation() {
        topAnimation = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.bottom_animation);
        logo.setAnimation(topAnimation);
        name_group.setAnimation(bottomAnimation);
    }
}