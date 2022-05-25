package mobile.android.prototype.data.services;

import android.content.Context;

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


    private void requestCustomer() {
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
        System.out.println("Requesting products");
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
        System.out.println("adding tags to customer;" + customerEntity.getId() + ", " + Arrays.toString(tags.toArray()));

        api.addTagsToCustomer(customerEntity.getId(), tags).enqueue(new Callback<CustomerEntity>() {
            @Override
            public void onResponse(Call<CustomerEntity> call, Response<CustomerEntity> response) {
                CustomerEntity customer = response.body();
                if (customer != null) {
                    customerEntity = customer;
                }
                System.out.println(response.body());
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
