package rs.raf.scheduler.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import rs.raf.scheduler.R;
import rs.raf.scheduler.activities.AddTaskActivity;
import rs.raf.scheduler.activities.MainActivity;
import rs.raf.scheduler.applications.App;
import rs.raf.scheduler.models.Model;
import rs.raf.scheduler.models.Task;
import rs.raf.scheduler.recycler.adapter.TaskAdapter;
import rs.raf.scheduler.viewmodels.DayViewModel;
import timber.log.Timber;

public class DailyPlanFragment extends Fragment {

    private TextView dateTextView;
    private RecyclerView recyclerView;

    private FloatingActionButton fab;

    private String date;

    public DailyPlanFragment() {
        super(R.layout.fragment_daily_plan);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        setListAdapter();
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
        // Set date and year
        Calendar calendar = Calendar.getInstance();
        String monthName = new DateFormatSymbols().getMonths()[calendar.get(Calendar.MONTH)];
        int year = calendar.get(Calendar.YEAR);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        dateTextView = view.findViewById(R.id.dateTextView);
        dateTextView.setText(day + " " + monthName + " " + year);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        DayViewModel viewModel = new ViewModelProvider(requireActivity()).get(DayViewModel.class);
        viewModel.getDate().observe(getViewLifecycleOwner(), date -> {
            this.date = date;
            setListAdapter();
        });

        date = getActivity().getIntent().getStringExtra(MainActivity.EXTRA_DATE);
        setListAdapter();

        fab = view.findViewById(R.id.fab);
    }

    private void setListAdapter() {
        List<Model> data = App.getTaskRepository().getAll(date);
        TaskAdapter taskAdapter = new TaskAdapter(data, getActivity());
        recyclerView.setAdapter(taskAdapter);
        dateTextView.setText(date);
    }

    private void initListeners() {
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), AddTaskActivity.class);
            intent.putExtra(AddTaskActivity.EXTRA_DATE, date);
            startActivity(intent);
        });
    }
}