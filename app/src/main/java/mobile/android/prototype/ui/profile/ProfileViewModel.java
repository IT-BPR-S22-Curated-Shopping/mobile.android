package mobile.android.prototype.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProfileViewModel extends ViewModel {

    private final LiveData<List<CardItemModel>> courseModalArrayList;


    public ProfileViewModel() {
        MutableLiveData<List<CardItemModel>> data = new MutableLiveData<>();

        List<CardItemModel> list = new ArrayList<>();

        list.add(new CardItemModel("Lamp1", null, "https://www.ikea.com/dk/da/images/products/ikea-ps-2014-loftlampe-hvid-kobberfarvet__0880877_pe613967_s5.jpg"));
        list.add(new CardItemModel("Lamp2", null, "https://www.ikea.com/dk/da/images/products/tokabo-bordlampe-glas-gron__0879821_pe724779_s5.jpg"));
        list.add(new CardItemModel("Lamp3", null, "https://www.ikea.com/dk/da/images/products/bunkeflo-loftlampe-hvid-birk__1008760_pe827315_s5.jpg"));
        list.add(new CardItemModel("Lamp4", null, "https://www.ikea.com/dk/da/images/products/grindfallet-loftlampe-sort__1008889_pe827368_s5.jpg"));
        list.add(new CardItemModel("Lamp5", null,"https://www.ikea.com/dk/da/images/products/arstid-bordlampe-messing-hvid__0880725_pe617347_s5.jpg"));

        data.setValue(list);
        courseModalArrayList = data;
    }

    public LiveData<List<CardItemModel>> getList() {
        return courseModalArrayList;
    }
}