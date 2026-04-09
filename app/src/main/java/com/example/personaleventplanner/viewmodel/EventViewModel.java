package com.example.personaleventplanner.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.personaleventplanner.data.EventEntity;
import com.example.personaleventplanner.data.EventRepository;

import java.util.List;

public class EventViewModel extends AndroidViewModel {

    private final EventRepository repository;
    private final LiveData<List<EventEntity>> allEvents;

    public EventViewModel(@NonNull Application application) {
        super(application);
        repository = new EventRepository(application);
        allEvents = repository.getAllEvents();
    }

    public LiveData<List<EventEntity>> getAllEvents() {
        return allEvents;
    }

    public void insert(EventEntity event) {
        repository.insert(event);
    }

    public void update(EventEntity event) {
        repository.update(event);
    }

    public void delete(EventEntity event) {
        repository.delete(event);
    }

    public void getEventById(int id, EventRepository.EventCallback callback) {
        repository.getEventById(id, callback);
    }
}
