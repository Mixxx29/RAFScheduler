package rs.raf.scheduler.recycler.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import rs.raf.scheduler.R;
import rs.raf.scheduler.activities.EditTaskActivity;
import rs.raf.scheduler.activities.PreviewActivity;
import rs.raf.scheduler.applications.App;
import rs.raf.scheduler.models.Model;
import rs.raf.scheduler.models.Task;
import rs.raf.scheduler.viewmodels.DayViewModel;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private List<Model> tasksData;
    private Context context;

    private DayViewModel model;

    public TaskAdapter(List<Model> tasksData, Context context) {
        this.tasksData = tasksData;
        this.context = context;
        this.model = new ViewModelProvider((ViewModelStoreOwner) context).get(DayViewModel.class);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_task, parent, false);
        view.setBackground(ContextCompat.getDrawable(parent.getContext(), R.drawable.shadow));
        view.setElevation(4);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = (Task) tasksData.get(position); // Get task object

        // Color icon based on priority
        switch (task.getPriority()) {
            case LOW: holder.iconImageView.setBackgroundColor(Color.parseColor("#41FF7E")); break;
            case MID: holder.iconImageView.setBackgroundColor(Color.parseColor("#FFD641")); break;
            case HIGH: holder.iconImageView.setBackgroundColor(Color.parseColor("#FF4141")); break;
        }

        // Set task title
        holder.titleTextView.setText(task.getTitle());

        // Set time
        holder.timeTextView.setText(task.getStartTime() + " - " + task.getEndTime());

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), PreviewActivity.class);
            intent.putExtra(PreviewActivity.EXTRA_DATE, task.getDate());
            intent.putExtra(PreviewActivity.EXTRA_POS, position);
            view.getContext().startActivity(intent);
        });

        holder.editImageView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), EditTaskActivity.class);
            intent.putExtra(EditTaskActivity.EXTRA_TITLE, task.getTitle());
            view.getContext().startActivity(intent);
        });

        // Set delete button
        holder.deleteImageView.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
            builder.setMessage("Are you sure you want to delete " + holder.titleTextView.getText().toString() + " ?")
                    .setCancelable(true)
                    .setPositiveButton("Delete", (dialog, id) -> {
                        int pos = holder.getBindingAdapterPosition();
                        App.getTaskRepository().delete(task);
                        App.getTaskRepository().save(task);
                        tasksData.remove(pos);
                        notifyItemRemoved(pos);
                        notifyItemChanged(pos, tasksData.size());
                    }).setNegativeButton("Cancel", (dialog, id) -> {
                        // cancel delete
                        dialog.cancel();
                    });
            AlertDialog alert = builder.create();
            alert.show();
        });
    }

    @Override
    public int getItemCount() {
        return tasksData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iconImageView;
        private TextView titleTextView;

        private TextView timeTextView;

        private ImageView deleteImageView;
        private ImageView editImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            iconImageView = itemView.findViewById(R.id.iconImageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);

            deleteImageView = itemView.findViewById(R.id.deleteImageView);
            editImageView = itemView.findViewById(R.id.editImageView);
        }
    }
}
