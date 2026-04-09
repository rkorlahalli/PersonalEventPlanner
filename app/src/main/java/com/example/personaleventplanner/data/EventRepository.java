package com.example.personaleventplanner.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EventRepository {

    private final EventDao eventDao;
    private final LiveData<List<EventEntity>> allEvents;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public EventRepository(Application application) {
        EventDatabase db = EventDatabase.getDatabase(application);
        eventDao = db.eventDao();
        allEvents = eventDao.getAllEvents();
    }

    public LiveData<List<EventEntity>> getAllEvents() {
        return allEvents;
    }

    public void insert(EventEntity event) {
        executorService.execute(() -> eventDao.insert(event));
    }

    public void update(EventEntity event) {
        executorService.execute(() -> eventDao.update(event));
    }

    public void delete(EventEntity event) {
        executorService.execute(() -> eventDao.delete(event));
    }

    public interface EventCallback {
        void onEventLoaded(EventEntity event);
    }

    public void getEventById(int id, EventCallback callback) {
        executorService.execute(() -> {
            EventEntity event = eventDao.getEventById(id);
            callback.onEventLoaded(event);
        });
    }
}
