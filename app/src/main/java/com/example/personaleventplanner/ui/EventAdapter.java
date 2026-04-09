package com.example.personaleventplanner.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personaleventplanner.data.EventEntity;
import com.example.personaleventplanner.databinding.ItemEventBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private final List<EventEntity> eventList = new ArrayList<>();
    private final OnEventClickListener listener;

    public interface OnEventClickListener {
        void onEventClick(EventEntity event);
    }

    public EventAdapter(OnEventClickListener listener) {
        this.listener = listener;
    }

    public void setEvents(List<EventEntity> events) {
        eventList.clear();
        eventList.addAll(events);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemEventBinding binding = ItemEventBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new EventViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        holder.bind(eventList.get(position));
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    class EventViewHolder extends RecyclerView.ViewHolder {

        private final ItemEventBinding binding;

        EventViewHolder(ItemEventBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(EventEntity event) {
            binding.textTitle.setText(event.getTitle());
            binding.textCategory.setText("Category: " + event.getCategory());
            binding.textLocation.setText("Location: " + event.getLocation());

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault());
            binding.textDateTime.setText("Date: " + sdf.format(new Date(event.getDateTimeMillis())));

            binding.getRoot().setOnClickListener(v -> listener.onEventClick(event));
        }
    }
}
