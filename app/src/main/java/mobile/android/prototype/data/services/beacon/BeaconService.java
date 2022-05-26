package mobile.android.prototype.data.services.beacon;

import android.app.Application;
import android.bluetooth.le.AdvertiseSettings;
import android.content.Context;

import androidx.annotation.VisibleForTesting;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.BeaconTransmitter;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.Region;

import java.util.Arrays;

import mobile.android.prototype.data.services.Repository;
import mobile.android.prototype.data.services.api.ApiProviderImpl;
import mobile.android.prototype.util.SystemUUID;

/*
    https://altbeacon.github.io/android-beacon-library/samples-java.html

    Reference code can be found here:
    https://altbeacon.github.io/android-beacon-library/beacon-transmitter.html
 */

public class BeaconService implements MonitorNotifier, IBeaconService {

    private static final int TX_POWER_MODE = AdvertiseSettings.ADVERTISE_TX_POWER_MEDIUM;
    private static final int TX_POWER = -59;
    private static final int TX_LATENCY_MODE = AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY;

    private final Beacon beacon;
    private final BeaconParser beaconParser;
    private final BeaconTransmitter beaconTransmitter;
    private final String selectedUUID;

    @VisibleForTesting
    public BeaconService(Context context, BeaconParser beaconParser, String selectedUUID, int minor, int major, Beacon beacon, BeaconTransmitter beaconTransmitter) {
        this.beaconParser = beaconParser;
        this.selectedUUID = selectedUUID;
        this.beacon = beacon;
        this.beaconTransmitter = beaconTransmitter;
    }

    public BeaconService(Application context, int minor, int major) {
        selectedUUID = SystemUUID.getDeviceUUID(context).toString();
        beaconParser = new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"); // type of beacon
        beacon = new Beacon.Builder()
                .setId1(selectedUUID) // UUID
                .setId2("" + major) // Major
                .setId3("" + minor) // Minor
                .setManufacturer(0x004c)
                .setTxPower(TX_POWER) // power dB
                .setDataFields(Arrays.asList(new Long[]{0l})) // Remove this for beacon layouts without d:
                .build();
        beaconTransmitter = new BeaconTransmitter(context, beaconParser);
    }

    @Override
    public void enableTransmitting() {
        if (beaconParser != null) {

            beaconTransmitter.setAdvertiseMode(TX_LATENCY_MODE);
            beaconTransmitter.setAdvertiseTxPowerLevel(TX_POWER_MODE);
            beaconTransmitter.startAdvertising(beacon);
        }
    }

    @Override
    public void stopTransmitting() {
        if (beaconTransmitter != null)
            beaconTransmitter.stopAdvertising();
    }

    @Override
    public void didEnterRegion(Region region) {
        // not implemented
    }

    @Override
    public void didExitRegion(Region region) {
        // not implemented
    }

    @Override
    public void didDetermineStateForRegion(int state, Region region) {
        // not implemented
    }
}
