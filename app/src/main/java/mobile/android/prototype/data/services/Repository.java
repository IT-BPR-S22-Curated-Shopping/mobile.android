package mobile.android.prototype.data.services;

import android.content.Context;

import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import mobile.android.prototype.data.models.CustomerEntity;
import mobile.android.prototype.data.models.ProductEntity;
import mobile.android.prototype.data.models.TagEntity;
import mobile.android.prototype.data.services.api.ApiProvider;
import mobile.android.prototype.data.services.api.ApiProviderImpl;
import mobile.android.prototype.util.SystemUUID;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {


    private static Repository INSTANCE;
    private final ApiProvider api;
    private final MutableLiveData<List<ProductEntity>> profileProducts;

    private UUID deviceId;
    private CustomerEntity customerEntity;

    @VisibleForTesting
    public Repository(ApiProvider api, UUID uuid, CustomerEntity customerEntity) {
        this.api = api;
        this.deviceId = uuid;
        this.customerEntity = customerEntity;
        profileProducts = new MutableLiveData<>();
    }

    private Repository() {
        api = new ApiProviderImpl();
        profileProducts = new MutableLiveData<>();
    }

    public static Repository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Repository();
        }
        return INSTANCE;
    }

    public void init(Context context) {
        deviceId = SystemUUID.getDeviceUUID(context);
        requestCustomer();
    }


    public void requestCustomer() {
        api.getCustomer(deviceId.toString()).enqueue(new Callback<CustomerEntity>() {
            @Override
            public void onResponse(Call<CustomerEntity> call, Response<CustomerEntity> response) {
                CustomerEntity customer = response.body();
                if (customer != null) {
                    customerEntity = customer;
                }
            }

            @Override
            public void onFailure(Call<CustomerEntity> call, Throwable t) {
            }
        });
    }

    public void requestProfileProducts() {
        api.getProfileProducts(customerEntity.getId(), 10).enqueue(new Callback<List<ProductEntity>>() {
            @Override
            public void onResponse(Call<List<ProductEntity>> call, Response<List<ProductEntity>> response) {
                List<ProductEntity> products = response.body();
                if (products != null) {
                    profileProducts.setValue(products);
                }
            }

            @Override
            public void onFailure(Call<List<ProductEntity>> call, Throwable t) {

            }
        });
    }


    public void addTagsToCustomer(List<TagEntity> tags) {
        api.addTagsToCustomer(customerEntity.getId(), tags).enqueue(new Callback<CustomerEntity>() {
            @Override
            public void onResponse(Call<CustomerEntity> call, Response<CustomerEntity> response) {
                CustomerEntity customer = response.body();
                if (customer != null) {
                    customerEntity = customer;
                }
            }

            @Override
            public void onFailure(Call<CustomerEntity> call, Throwable t) {
                System.out.println("failed to update customer with error: " + t.getMessage());
            }
        });
    }

    public LiveData<List<ProductEntity>> getProfileProducts() {
        return profileProducts;
    }

}
