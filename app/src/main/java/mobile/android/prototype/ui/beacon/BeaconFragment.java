package mobile.android.prototype.ui.beacon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import mobile.android.prototype.databinding.FragmentBeaconBinding;

public class BeaconFragment extends Fragment {

    private FragmentBeaconBinding binding;

    private TextView textView;
    private Button mainThreadButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        BeaconViewModel vm =
                new ViewModelProvider(this).get(BeaconViewModel.class);

        binding = FragmentBeaconBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        textView = binding.textBeacon;
        mainThreadButton = binding.transmitButton;

        vm.getText().observe(getViewLifecycleOwner(), textView::setText);
        mainThreadButton.setOnClickListener(view -> vm.changeTransmittingStatus());


        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}