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

public class AddTaskActivity extends AppCompatActivity {

    public static final String EXTRA_DATE = "EXTRA_DATE";

    private EditText titleEditText;
    private EditText timeStartEditText;
    private EditText timeEndEditText;
    private Spinner prioritiesSpinner;
    private EditText descriptionEditText;

    private Button addButton;
    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE); // Hide title
        setContentView(R.layout.activity_add_task);

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
        addButton = findViewById(R.id.editButton);
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

    }

    private void initListeners() {
        addButton.setOnClickListener(view -> {
            if (titleEditText.getText().toString().equals("")) return;
            if (timeStartEditText.getText().toString().equals("")) return;
            if (timeEndEditText.getText().toString().equals("")) return;
            if (descriptionEditText.getText().toString().equals("")) return;

            // Get date
            String date = getIntent().getStringExtra(EXTRA_DATE);

            // Create ne task
            Task task = new Task(
                    date,
                    titleEditText.getText().toString(),
                    timeStartEditText.getText().toString(),
                    timeEndEditText.getText().toString(),
                    Task.Priority.values()[prioritiesSpinner.getSelectedItemPosition()],
                    descriptionEditText.getText().toString()
            );
            App.getTaskRepository().create(task);
            task.save();

            finish();
        });

        cancelButton.setOnClickListener(view -> {
            finish();
        });
    }
}