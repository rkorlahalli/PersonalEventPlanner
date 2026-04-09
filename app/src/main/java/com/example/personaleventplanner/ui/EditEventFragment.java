package com.example.personaleventplanner.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.personaleventplanner.data.EventEntity;
import com.example.personaleventplanner.databinding.FragmentEditEventBinding;
import com.example.personaleventplanner.viewmodel.EventViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditEventFragment extends Fragment {

    private FragmentEditEventBinding binding;
    private EventViewModel eventViewModel;
    private Calendar selectedDateTime;
    private EventEntity currentEvent;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditEventBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        eventViewModel = new ViewModelProvider(requireActivity()).get(EventViewModel.class);
        selectedDateTime = Calendar.getInstance();

        String[] categories = {"Work", "Social", "Travel", "Study", "Other"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                categories
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerCategoryEdit.setAdapter(spinnerAdapter);

        int eventId = getArguments() != null ? getArguments().getInt("eventId", -1) : -1;

        if (eventId != -1) {
            eventViewModel.getEventById(eventId, event -> {
                if (event != null && getActivity() != null) {
                    requireActivity().runOnUiThread(() -> {
                        currentEvent = event;
                        populateFields(event);
                    });
                }
            });
        }

        binding.buttonPickDateEdit.setOnClickListener(v -> showDatePicker());
        binding.buttonPickTimeEdit.setOnClickListener(v -> showTimePicker());
        binding.buttonUpdateEvent.setOnClickListener(v -> updateEvent());
        binding.buttonDeleteEvent.setOnClickListener(v -> deleteEvent());
    }

    private void populateFields(EventEntity event) {
        binding.editTextTitleEdit.setText(event.getTitle());
        binding.editTextLocationEdit.setText(event.getLocation());

        String[] categories = {"Work", "Social", "Travel", "Study", "Other"};
        for (int i = 0; i < categories.length; i++) {
            if (categories[i].equals(event.getCategory())) {
                binding.spinnerCategoryEdit.setSelection(i);
                break;
            }
        }

        selectedDateTime.setTimeInMillis(event.getDateTimeMillis());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());

        binding.textSelectedDateEdit.setText(dateFormat.format(selectedDateTime.getTime()));
        binding.textSelectedTimeEdit.setText(timeFormat.format(selectedDateTime.getTime()));
    }

    private void showDatePicker() {
        DatePickerDialog dialog = new DatePickerDialog(
                requireContext(),
                (view, year, month, dayOfMonth) -> {
                    selectedDateTime.set(Calendar.YEAR, year);
                    selectedDateTime.set(Calendar.MONTH, month);
                    selectedDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    binding.textSelectedDateEdit.setText(sdf.format(selectedDateTime.getTime()));
                },
                selectedDateTime.get(Calendar.YEAR),
                selectedDateTime.get(Calendar.MONTH),
                selectedDateTime.get(Calendar.DAY_OF_MONTH)
        );
        dialog.show();
    }

    private void showTimePicker() {
        TimePickerDialog dialog = new TimePickerDialog(
                requireContext(),
                (view, hourOfDay, minute) -> {
                    selectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    selectedDateTime.set(Calendar.MINUTE, minute);
                    selectedDateTime.set(Calendar.SECOND, 0);
                    selectedDateTime.set(Calendar.MILLISECOND, 0);

                    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
                    binding.textSelectedTimeEdit.setText(sdf.format(selectedDateTime.getTime()));
                },
                selectedDateTime.get(Calendar.HOUR_OF_DAY),
                selectedDateTime.get(Calendar.MINUTE),
                false
        );
        dialog.show();
    }

    private void updateEvent() {
        if (currentEvent == null) return;

        String title = binding.editTextTitleEdit.getText().toString().trim();
        String category = binding.spinnerCategoryEdit.getSelectedItem().toString();
        String location = binding.editTextLocationEdit.getText().toString().trim();

        if (TextUtils.isEmpty(title)) {
            Snackbar.make(binding.getRoot(), "Title is required", Snackbar.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(binding.textSelectedDateEdit.getText().toString()) ||
                TextUtils.isEmpty(binding.textSelectedTimeEdit.getText().toString())) {
            Snackbar.make(binding.getRoot(), "Date and time are required", Snackbar.LENGTH_SHORT).show();
            return;
        }

        currentEvent.setTitle(title);
        currentEvent.setCategory(category);
        currentEvent.setLocation(location);
        currentEvent.setDateTimeMillis(selectedDateTime.getTimeInMillis());

        eventViewModel.update(currentEvent);

        Snackbar.make(binding.getRoot(), "Event updated", Snackbar.LENGTH_SHORT).show();
        NavHostFragment.findNavController(this).navigateUp();
    }

    private void deleteEvent() {
        if (currentEvent == null) return;

        eventViewModel.delete(currentEvent);
        Snackbar.make(binding.getRoot(), "Event deleted", Snackbar.LENGTH_SHORT).show();
        NavHostFragment.findNavController(this).navigateUp();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
