package rs.raf.scheduler.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import java.util.List;

import rs.raf.scheduler.R;
import rs.raf.scheduler.applications.App;
import rs.raf.scheduler.fragments.TaskPreviewFragment;
import rs.raf.scheduler.models.Model;
import rs.raf.scheduler.models.Task;

public class PreviewActivity extends AppCompatActivity {

    public static final String EXTRA_DATE = "EXTRA_DATE";
    public static final String EXTRA_POS = "EXTRA_POS";

    private TextView titleTextView;
    private ViewPager previewViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE); // Hide title
        setContentView(R.layout.activity_preview);
        init();
    }

    private void init() {
        initView();
        initListeners();
    }

    private void initView() {
        previewViewPager = findViewById(R.id.previewViewPager);
        titleTextView = findViewById(R.id.titleTextView);

        // Get date
        String date = getIntent().getStringExtra(EXTRA_DATE);
        titleTextView.setText(date);

        List<Model> tasks = App.getTaskRepository().getAll(date);

        previewViewPager.setAdapter(
                new FragmentPagerAdapter(getSupportFragmentManager()) {

                    @Override
                    public int getCount() {
                        return tasks.size();
                    }

                    @NonNull
                    @Override
                    public Fragment getItem(int position) {
                        return new TaskPreviewFragment((Task) tasks.get(position));
                    }
                }
        );

        int pos = getIntent().getIntExtra(EXTRA_POS, 0);
        previewViewPager.setCurrentItem(pos);
    }

    private void initListeners() {

    }
}