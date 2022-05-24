package mobile.android.prototype.ui.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.daprlabs.cardstack.SwipeDeck;

import java.util.List;

import mobile.android.prototype.R;
import mobile.android.prototype.data.models.ProductEntity;
import mobile.android.prototype.data.services.api.ApiProvider;
import mobile.android.prototype.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private DeckAdapter adapter;
    private FragmentProfileBinding binding;
    private SwipeDeck cardStack;
    private ProgressBar loadingBar;
    private ProfileViewModel vm;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        vm.getList().observeForever(this::cardListChange);
        loadingBar = binding.progressBarCyclic;


        {
            cardStack = binding.swipeDeck;
            adapter = new DeckAdapter(vm.getList().getValue(), getContext());
            cardStack.setAdapter(adapter);
            cardStack.setEventCallback(swipeEventCallback());
            cardStack.setLeftImage(R.id.left_image);
            cardStack.setRightImage(R.id.right_image);
        }


        return root;
    }

    private void cardListChange(List<CardItemModel> cardItemModels) {
        adapter.addItems(cardItemModels);
        loadingBar.setVisibility(View.GONE);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private SwipeDeck.SwipeEventCallback swipeEventCallback() {
        return new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {
                // on card swipe left we are displaying a toast message.
                System.out.println("left");
            }

            @Override
            public void cardSwipedRight(int position) {
                // on card swiped to right we are displaying a toast message.
                System.out.println("right");
                CardItemModel product = adapter.getItem(position);
                vm.likeProduct(product);
            }

            @Override
            public void cardsDepleted() {
                // this method is called when no card is present
                loadingBar.setVisibility(View.VISIBLE);
                vm.requestProfileProducts();

            }

            @Override
            public void cardActionDown() {
            }

            @Override
            public void cardActionUp() {
            }
        };
    }

}