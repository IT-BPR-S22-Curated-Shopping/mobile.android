package mobile.android.prototype.data.services.api;

import java.util.List;

import mobile.android.prototype.data.models.CustomerEntity;
import mobile.android.prototype.data.models.ProductEntity;
import mobile.android.prototype.data.models.TagEntity;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiProvider {

    @GET("customer/uuid/{uuid}")
    Call<CustomerEntity> getCustomer(@Path("uuid") String uuid);

    @PUT("customer/{customerId}")
    Call<CustomerEntity> addTagsToCustomer(@Path("customerId") Long id, @Body List<TagEntity> tags);

    @GET("customer/profileproducts/{customerId}")
    Call<List<ProductEntity>> getProfileProducts(@Path("customerId") Long id, @Query("size") int size);

}