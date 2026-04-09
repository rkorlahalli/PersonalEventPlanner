package com.example.personaleventplanner.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.personaleventplanner.R;
import com.example.personaleventplanner.databinding.FragmentEventListBinding;
import com.example.personaleventplanner.viewmodel.EventViewModel;

public class EventListFragment extends Fragment {

    private FragmentEventListBinding binding;
    private EventViewModel eventViewModel;
    private EventAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEventListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        eventViewModel = new ViewModelProvider(requireActivity()).get(EventViewModel.class);

        adapter = new EventAdapter(event -> {
            Bundle bundle = new Bundle();
            bundle.putInt("eventId", event.getId());
            NavHostFragment.findNavController(EventListFragment.this)
                    .navigate(R.id.action_eventListFragment_to_editEventFragment, bundle);
        });

        binding.recyclerViewEvents.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewEvents.setAdapter(adapter);

        eventViewModel.getAllEvents().observe(getViewLifecycleOwner(), events -> {
            adapter.setEvents(events);
            binding.textEmpty.setVisibility(events == null || events.isEmpty() ? View.VISIBLE : View.GONE);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
