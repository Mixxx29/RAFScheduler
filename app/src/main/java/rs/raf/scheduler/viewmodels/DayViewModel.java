package rs.raf.scheduler.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

public class DayViewModel extends AndroidViewModel {

    private MutableLiveData<String> date = new MutableLiveData<>();

    public DayViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date.setValue(date);
    }
}
