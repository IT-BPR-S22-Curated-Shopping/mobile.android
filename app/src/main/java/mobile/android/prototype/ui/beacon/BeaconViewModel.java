package mobile.android.prototype.ui.beacon;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import mobile.android.prototype.data.services.beacon.BeaconService;
import mobile.android.prototype.data.services.beacon.IBeaconService;
import mobile.android.prototype.util.SystemUUID;

public class BeaconViewModel extends AndroidViewModel {

    private final MutableLiveData<String> mText;
    private final Context context;

    private IBeaconService beaconService;

    private boolean isTransmitting = false;
    private boolean isForegroundServiceEnabled = false;

    public BeaconViewModel(Application app) {
        super(app);
        context = app;
        mText = new MutableLiveData<>();

        beaconService = new BeaconService(app, 0, 0);

        String android_id = SystemUUID.getDeviceUUID(app).toString();
        mText.setValue("Not transmitting\nId: "+ android_id);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void setmText(String string) {
        mText.setValue(string);
    }

    public void changeTransmittingStatus() {
        if (!isTransmitting) {
            beaconService.enableTransmitting();
            mText.setValue("Transmitting\nId: " + SystemUUID.getDeviceUUID(context));
        } else {
            beaconService.stopTransmitting();
            mText.setValue("Not transmitting.");
        }
        isTransmitting = !isTransmitting;
    }

    public void changeTransmittingForegroundStatus() {
        if (!isForegroundServiceEnabled) {
            beaconService.enableForegroundService();
            isForegroundServiceEnabled = true;
        } else {
            beaconService.disableForegroundService();
            isForegroundServiceEnabled = false;
        }
    }
}