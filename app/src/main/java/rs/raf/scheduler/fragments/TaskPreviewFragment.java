package rs.raf.scheduler.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import rs.raf.scheduler.R;
import rs.raf.scheduler.activities.EditTaskActivity;
import rs.raf.scheduler.models.Task;

public class TaskPreviewFragment extends Fragment {

    private TextView titleTextView;
    private TextView timeTextView;
    private TextView priorityTextView;
    private TextView descriptionTextView;

    private Button editButton;
    private Button cancelButton;

    private Task task;

    public TaskPreviewFragment(Task task) {
        super(R.layout.fragment_task_preview);
        this.task = task;
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
        titleTextView = view.findViewById(R.id.titleTextView);
        titleTextView.setText(task.getTitle());

        timeTextView = view.findViewById(R.id.timeTextView);
        timeTextView.setText(task.getStartTime() + " - " + task.getEndTime());

        priorityTextView = view.findViewById(R.id.priorityTextView);
        priorityTextView.setText("Priority: " + task.getPriority().toString());

        descriptionTextView = view.findViewById(R.id.descriptionTextView);
        descriptionTextView.setText(task.getDescription());

        editButton = view.findViewById(R.id.editButton);
        cancelButton = view.findViewById(R.id.cancelButton);

    }

    private void initListeners() {
        editButton.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), EditTaskActivity.class);
            intent.putExtra(EditTaskActivity.EXTRA_TITLE, task.getTitle());
            view.getContext().startActivity(intent);
        });

        cancelButton.setOnClickListener(view -> {
            getActivity().finish();
        });
    }
}
