package rs.raf.scheduler.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import rs.raf.scheduler.R;
import timber.log.Timber;

public class CalendarFragment extends Fragment {

    private ViewPager calendarViewPager;

    private List<Fragment> months;

    private int pos = 0;

    public CalendarFragment() {
        super(R.layout.fragment_calendar);
        months = new ArrayList<>(); // Create empty months array
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        setCalendarAdapter();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view) {
        initView(view);
        initListeners();
    }

    private void initView(View view) {
        calendarViewPager = view.findViewById(R.id.calendarViewPager);
        setCalendarAdapter();
    }

    private void setCalendarAdapter() {
        CustomAdapter adapter = new CustomAdapter(getChildFragmentManager());
        calendarViewPager.setAdapter(adapter);
        calendarViewPager.addOnPageChangeListener(adapter);
        calendarViewPager.setCurrentItem(pos);
    }

    private void initListeners() {

    }

    private class CustomAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {

        public CustomAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            // Get the current month and year
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, position);
            return new MonthFragment(
                    calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH));
        }

        @Override
        public int getCount() {
            return 12;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            pos = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
