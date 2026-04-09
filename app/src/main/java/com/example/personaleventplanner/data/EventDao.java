package com.example.personaleventplanner.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EventDao {

    @Insert
    void insert(EventEntity event);

    @Update
    void update(EventEntity event);

    @Delete
    void delete(EventEntity event);

    @Query("SELECT * FROM events ORDER BY dateTimeMillis ASC")
    LiveData<List<EventEntity>> getAllEvents();

    @Query("SELECT * FROM events WHERE id = :eventId LIMIT 1")
    EventEntity getEventById(int eventId);
}
