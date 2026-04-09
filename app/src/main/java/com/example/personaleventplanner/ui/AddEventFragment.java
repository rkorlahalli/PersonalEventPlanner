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

import com.example.personaleventplanner.data.EventEntity;
import com.example.personaleventplanner.databinding.FragmentAddEventBinding;
import com.example.personaleventplanner.viewmodel.EventViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddEventFragment extends Fragment {

    private FragmentAddEventBinding binding;
    private EventViewModel eventViewModel;
    private Calendar selectedDateTime;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddEventBinding.inflate(inflater, container, false);
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
        binding.spinnerCategory.setAdapter(spinnerAdapter);

        binding.buttonPickDate.setOnClickListener(v -> showDatePicker());
        binding.buttonPickTime.setOnClickListener(v -> showTimePicker());
        binding.buttonSaveEvent.setOnClickListener(v -> saveEvent());
    }

    private void showDatePicker() {
        Calendar now = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(
                requireContext(),
                (view, year, month, dayOfMonth) -> {
                    selectedDateTime.set(Calendar.YEAR, year);
                    selectedDateTime.set(Calendar.MONTH, month);
                    selectedDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    binding.textSelectedDate.setText(sdf.format(selectedDateTime.getTime()));
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );

        dialog.show();
    }

    private void showTimePicker() {
        Calendar now = Calendar.getInstance();

        TimePickerDialog dialog = new TimePickerDialog(
                requireContext(),
                (view, hourOfDay, minute) -> {
                    selectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    selectedDateTime.set(Calendar.MINUTE, minute);
                    selectedDateTime.set(Calendar.SECOND, 0);
                    selectedDateTime.set(Calendar.MILLISECOND, 0);

                    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
                    binding.textSelectedTime.setText(sdf.format(selectedDateTime.getTime()));
                },
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
        );

        dialog.show();
    }

    private void saveEvent() {
        String title = binding.editTextTitle.getText().toString().trim();
        String category = binding.spinnerCategory.getSelectedItem().toString();
        String location = binding.editTextLocation.getText().toString().trim();

        boolean dateChosen = !TextUtils.isEmpty(binding.textSelectedDate.getText().toString());
        boolean timeChosen = !TextUtils.isEmpty(binding.textSelectedTime.getText().toString());

        if (TextUtils.isEmpty(title)) {
            Snackbar.make(binding.getRoot(), "Title is required", Snackbar.LENGTH_SHORT).show();
            return;
        }

        if (!dateChosen || !timeChosen) {
            Snackbar.make(binding.getRoot(), "Date and time are required", Snackbar.LENGTH_SHORT).show();
            return;
        }

        if (selectedDateTime.getTimeInMillis() < System.currentTimeMillis()) {
            Snackbar.make(binding.getRoot(), "Please choose a future date and time", Snackbar.LENGTH_SHORT).show();
            return;
        }

        EventEntity event = new EventEntity(title, category, location, selectedDateTime.getTimeInMillis());
        eventViewModel.insert(event);

        Snackbar.make(binding.getRoot(), "Event saved", Snackbar.LENGTH_SHORT).show();

        binding.editTextTitle.setText("");
        binding.editTextLocation.setText("");
        binding.spinnerCategory.setSelection(0);
        binding.textSelectedDate.setText("");
        binding.textSelectedTime.setText("");
        selectedDateTime = Calendar.getInstance();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
