package mobile.android.prototype.data.services.api;

import java.util.List;

import mobile.android.prototype.data.models.CustomerEntity;
import mobile.android.prototype.data.models.ProductEntity;
import mobile.android.prototype.data.models.TagEntity;
import retrofit2.Call;

public class ApiProviderImpl implements ApiProvider {

    public ApiProviderImpl() {
    }

    @Override
    public Call<CustomerEntity> getCustomer(String uuid) {
        return ServiceGenerator.getApi().getCustomer(uuid);
    }

    @Override
    public Call<CustomerEntity> addTagsToCustomer(Long id, List<TagEntity> tags) {
        return ServiceGenerator.getApi().addTagsToCustomer(id, tags);
    }

    @Override
    public Call<List<ProductEntity>> getProfileProducts(Long id, int size) {
        return ServiceGenerator.getApi().getProfileProducts(id, size);
    }
}
