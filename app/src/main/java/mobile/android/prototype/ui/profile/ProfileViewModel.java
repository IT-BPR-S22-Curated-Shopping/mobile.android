package mobile.android.prototype.ui.profile;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import mobile.android.prototype.R;
import mobile.android.prototype.data.models.CustomerEntity;
import mobile.android.prototype.data.models.ProductEntity;
import mobile.android.prototype.data.models.TagEntity;
import mobile.android.prototype.data.services.Repository;
import mobile.android.prototype.data.services.api.ApiProvider;
import mobile.android.prototype.data.services.api.ApiProviderImpl;
import mobile.android.prototype.data.services.api.ServiceGenerator;
import mobile.android.prototype.util.SystemUUID;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileViewModel extends AndroidViewModel {

    private final MutableLiveData<List<CardItemModel>> list;
    private final MutableLiveData<String> mHeader;

    public ProfileViewModel(Application app) {
        super(app);
        list = new MutableLiveData<>();
        mHeader = new MutableLiveData<>();
        mHeader.setValue(app.getResources().getString(R.string.swipe_products));
        try {
            Repository.getInstance().getProfileProducts().observeForever(this::profileProductsChanged);
            Repository.getInstance().requestProfileProducts();
        } catch (Exception e) {
        }

    }

    private void profileProductsChanged(List<ProductEntity> productEntities) {
        List<CardItemModel> cards = new ArrayList<>();
        for (ProductEntity product : productEntities) {
            if (product.getImage() != null && !product.getImage().isEmpty())
                cards.add(new CardItemModel(product.getName(), product.getTags(), product.getImage()));
        }
        list.postValue(cards);
    }

    public LiveData<List<CardItemModel>> getList() {
        return list;
    }


    public void likeProduct(CardItemModel product) {
        List<TagEntity> tags = product.getTags();
        if (tags != null && tags.size() > 0)
            Repository.getInstance().addTagsToCustomer(tags);
    }

    public void swipeDone() {
        mHeader.setValue(getApplication().getResources().getString(R.string.profile_swipe_done));
    }

    public LiveData<String> getHeader() {
        return mHeader;
    }
}