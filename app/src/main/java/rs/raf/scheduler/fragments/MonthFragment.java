package rs.raf.scheduler.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import java.text.DateFormatSymbols;
import java.util.List;

import rs.raf.scheduler.R;
import rs.raf.scheduler.activities.MainActivity;
import rs.raf.scheduler.applications.App;
import rs.raf.scheduler.models.Model;
import rs.raf.scheduler.models.Task;
import rs.raf.scheduler.viewmodels.DayViewModel;
import timber.log.Timber;

public class MonthFragment extends Fragment {

    private TextView dateTextView;

    private GridLayout calendarGridLayout;

    private int year;
    private int month;

    public MonthFragment(int year, int month) {
        super(R.layout.fragment_month);
        this.year = year;
        this.month = month;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view) {
        initView(view);
        generateDays();
        initListeners();
    }

    private void initView(View view) {
        dateTextView = view.findViewById(R.id.dateTextView);
        calendarGridLayout = view.findViewById(R.id.calendarGridLayout);
        // Set the number of rows and columns in the grid
        calendarGridLayout.setColumnCount(7);
        calendarGridLayout.setRowCount(7);
    }

    @SuppressLint("ResourceAsColor")
    private void generateDays() {
        // Loop through each day of the week and add a column header
        String[] dayNames = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        for (int day = 0; day < 7; day++) {
            TextView columnHeader = new TextView(getActivity());
            columnHeader.setText(dayNames[day]);
            columnHeader.setTextSize(16);
            columnHeader.setTextColor(Color.BLACK);
            columnHeader.setGravity(Gravity.CENTER);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.rowSpec = GridLayout.spec(0, 0.0f);
            params.columnSpec = GridLayout.spec(day, 1f);
            calendarGridLayout.addView(columnHeader, params);
        }

        // Set date text
        String monthName[] = new DateFormatSymbols().getMonths();
        dateTextView.setText(monthName[month] + " " + year);

        // Create calendar instance
        Calendar calendar = Calendar.getInstance();

        // Get maximum days in previous month
        calendar.set(year, month - 1, 1);
        int maxDaysPrev = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        // Get maximum days in current month
        calendar.set(year, month, 1);
        int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        // Which day is start day
        int startDay = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (startDay == 0) startDay = 7;

        // Loop through each day in the month
        for (int cell = 1; cell <= 7 * 6; cell++) {
            //if (cell >= startDay + maxDays && cell == 7 * 5 + 1) break;

            int day;
            if (cell < startDay) day = maxDaysPrev - startDay + cell + 1;
            else if (cell - startDay < maxDays) day = cell - startDay + 1;
            else day = cell - startDay - maxDays + 1;

            Button button = createDateButton(day);

            if (cell < startDay || cell - startDay >= maxDays) {
                button.setEnabled(false);
                button.setBackgroundColor(Color.LTGRAY);
            }

            // Increment the calendar to the next day
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    private Button createDateButton(int day) {
        // Get task
        String date = day + "-" + (month + 1) + "-" + year;
        List<Model> tasks = App.getTaskRepository().getAll(date);

        Task.Priority priority = null;
        for (Model model : tasks) {
            Task task = (Task) model;
            if (priority == null) {
                priority = task.getPriority();
                continue;
            }

            if (task.getPriority().ordinal() > priority.ordinal()) {
                priority = task.getPriority();
            }
        }

        int c = Color.WHITE;
        if (priority != null) {
            switch (priority) {
                case LOW:
                    c = Color.parseColor("#41FF7E");
                    break;

                case MID:
                    c = Color.parseColor("#FFD641");
                    break;

                case HIGH:
                    c = Color.parseColor("#FF4141");
                    break;
            }
        }

        // Create a new button for the day
        Button button = new Button(getActivity());
        button.setBackgroundColor(c);
        button.setTextSize(20);
        button.setText(String.valueOf(day));

        // Add the button to the grid layout
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = 0;
        params.height = GridLayout.LayoutParams.WRAP_CONTENT;
        params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        calendarGridLayout.addView(button, params);

        // Add a click listener to the button
        button.setOnClickListener(view -> {
            DayViewModel viewModel = new ViewModelProvider(requireActivity()).get(DayViewModel.class);
            viewModel.setDate(date);
            ((MainActivity) getActivity()).setDailyPlan();
        });

        return button;
    }

    private void initListeners() {

    }
}
