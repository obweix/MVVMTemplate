package com.example.mvvmapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mvvmapplication.ui.dashboard.DashboardFragment;
import com.example.mvvmapplication.ui.home.HomeFragment;
import com.example.mvvmapplication.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.mvvmapplication.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    NavController mNavController;

    private long mLastBackKeyPressTime = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     binding = ActivityMainBinding.inflate(getLayoutInflater());
     setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);

        mNavController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);

        mNavController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if(destination.getId() != R.id.navigation_home
                        && destination.getId() != R.id.navigation_dashboard
                        && destination.getId() != R.id.navigation_notifications
                ){
                    navView.setVisibility(View.INVISIBLE);
                }else{
                    navView.setVisibility(View.VISIBLE);
                }
            }
        });


        NavigationUI.setupWithNavController(binding.navView, mNavController);

    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK &&  mNavController.getCurrentDestination().getId() == R.id.navigation_home){
            if(-1 == mLastBackKeyPressTime || System.currentTimeMillis() - mLastBackKeyPressTime  > 2000){
                mLastBackKeyPressTime = System.currentTimeMillis();
                Toast.makeText(this,"再按一次退出程序",Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return super.onKeyUp(keyCode, event);
    }
}