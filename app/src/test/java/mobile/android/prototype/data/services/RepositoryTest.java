package mobile.android.prototype.data.services;

import org.junit.Test;
import org.mockito.Answers;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import mobile.android.prototype.data.models.CustomerEntity;
import mobile.android.prototype.data.models.TagEntity;
import mobile.android.prototype.data.services.api.ApiProvider;
import mobile.android.prototype.data.services.api.ApiProviderImpl;

public class RepositoryTest {
    /*
        Singleton part of the repository is not being tested
     */

    @Test
    public void requestCustomerCallsAPIWithCorrectParameters() {
        // arrange
        ApiProvider api = Mockito.mock(ApiProviderImpl.class, Answers.RETURNS_DEEP_STUBS);
        UUID uuid = UUID.randomUUID();
        Repository repository = new Repository(api, uuid, null);

        // act
        repository.requestCustomer();

        // assert
        Mockito.verify(api).getCustomer(uuid.toString());
    }

    @Test
    public void requestProfileProductsCallsApi() {
        // arrange
        ApiProvider api = Mockito.mock(ApiProviderImpl.class, Answers.RETURNS_DEEP_STUBS);
        UUID uuid = UUID.randomUUID();
        CustomerEntity customer = Mockito.mock(CustomerEntity.class);
        Mockito.when(customer.getId()).thenReturn(1L);
        Repository repository = new Repository(api, uuid, customer);

        // act
        repository.requestProfileProducts();

        // assert
        Mockito.verify(api).getProfileProducts(1L, 10);
    }


    @Test
    public void addTagsToCustomerCallsApi() {
        // arrange
        ApiProvider api = Mockito.mock(ApiProviderImpl.class, Answers.RETURNS_DEEP_STUBS);
        UUID uuid = UUID.randomUUID();
        CustomerEntity customer = Mockito.mock(CustomerEntity.class);
        Mockito.when(customer.getId()).thenReturn(1L);
        List<TagEntity> tags = new ArrayList<>();
        TagEntity e1 = new TagEntity();
        e1.setTag("TestTag1");
        tags.add(e1);
        TagEntity e2 = new TagEntity();
        e2.setTag("TestTag2");
        tags.add(e2);
        Repository repository = new Repository(api, uuid, customer);

        // act
        repository.addTagsToCustomer(tags);

        // assert
        Mockito.verify(api).addTagsToCustomer(1L, tags);
    }



}