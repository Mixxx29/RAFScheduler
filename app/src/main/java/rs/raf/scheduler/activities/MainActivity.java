package rs.raf.scheduler.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Window;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import rs.raf.scheduler.R;
import rs.raf.scheduler.viewpager.PagerAdapter;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_USERNAME = "EXTRA_USERNAME";

    public static final String EXTRA_DATE = "EXTRA_DATE";

    private ViewPager viewPager;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE); // Hide title
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        initView();
        initListeners();
    }

    @SuppressLint("NonConstantResourceId")
    private void initView() {
        // Initialize view pager
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));

        // Initialize navigation
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.dailyPlanNavigation:
                    viewPager.setCurrentItem(PagerAdapter.DAILY_PLAN_FRAGMENT, false);
                    break;

                case R.id.calendarNavigation:
                    viewPager.setCurrentItem(PagerAdapter.CALENDAR_FRAGMENT, false);
                    break;

                case R.id.accountNavigation:
                    viewPager.setCurrentItem(PagerAdapter.ACCOUNT_FRAGMENT, false);
                    break;
            }
            return true;
        });
        bottomNavigationView.setSelectedItemId(R.id.dailyPlanNavigation);
    }

    private void initListeners() {

    }

    public void setDailyPlan() {
        bottomNavigationView.setSelectedItemId(R.id.dailyPlanNavigation);
    }
}