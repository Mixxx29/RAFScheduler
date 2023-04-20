package rs.raf.scheduler.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import rs.raf.scheduler.R;
import rs.raf.scheduler.applications.App;
import rs.raf.scheduler.models.Task;

public class EditTaskActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE = "EXTRA_TITLE";

    private EditText titleEditText;
    private EditText timeStartEditText;
    private EditText timeEndEditText;
    private Spinner prioritiesSpinner;
    private EditText descriptionEditText;

    private Button editButton;
    private Button cancelButton;

    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE); // Hide title
        setContentView(R.layout.activity_edit_task);

        init();
    }

    private void init() {
        initView();
        initListeners();
    }

    private void initView() {
        titleEditText = findViewById(R.id.titleEditText);
        timeStartEditText = findViewById(R.id.timeStartEditText);
        timeEndEditText = findViewById(R.id.timeEndEditText);
        prioritiesSpinner = findViewById(R.id.priorities_spinner);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        editButton = findViewById(R.id.editButton);
        cancelButton = findViewById(R.id.cancelButton);

        Task.Priority[] priorities = Task.Priority.values();
        String[] options = new String[priorities.length];
        for (int i = 0; i < priorities.length; i++) {
            options[i] = priorities[i].toString();
        }

        Spinner spinner = findViewById(R.id.priorities_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Get task
        task = (Task) App.getTaskRepository().get(getIntent().getStringExtra(EXTRA_TITLE));

        titleEditText.setText(task.getTitle());
        timeStartEditText.setText(task.getStartTime());
        timeEndEditText.setText(task.getEndTime());
        prioritiesSpinner.setSelection(task.getPriority().ordinal());
        descriptionEditText.setText(task.getDescription());
    }

    private void initListeners() {
        editButton.setOnClickListener(view -> {
            if (titleEditText.getText().toString().equals("")) return;
            if (timeStartEditText.getText().toString().equals("")) return;
            if (timeEndEditText.getText().toString().equals("")) return;
            if (descriptionEditText.getText().toString().equals("")) return;

            //task.setTitle(titleEditText.getText().toString());
            task.setStartTime(timeStartEditText.getText().toString());
            task.setEndTime(timeEndEditText.getText().toString());
            task.setPriority(Task.Priority.values()[prioritiesSpinner.getSelectedItemPosition()]);
            task.setDescription(descriptionEditText.getText().toString());

            App.getTaskRepository().save(task);

            finish();
        });

        cancelButton.setOnClickListener(view -> {
            finish();
        });
    }
}