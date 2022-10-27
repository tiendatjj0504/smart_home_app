package uit.iot.test_app_smarthome;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import uit.iot.test_app_smarthome.BottomNavigation.FragmentBedRoom;
import uit.iot.test_app_smarthome.BottomNavigation.FragmentHomePage;
import uit.iot.test_app_smarthome.BottomNavigation.FragmentKitchenRoom;
import uit.iot.test_app_smarthome.BottomNavigation.FragmentMainDoor;
import uit.iot.test_app_smarthome.BottomNavigation.FragmentSetting;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 1:
                return new FragmentMainDoor();
            case 2:
                return new FragmentKitchenRoom();
            case 3:
                return new FragmentBedRoom();
            case 4:
                return new FragmentSetting();
            default:
                return new FragmentHomePage();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
