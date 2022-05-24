package mobile.android.prototype.data.services.beacon;

import android.app.Application;
import android.bluetooth.le.AdvertiseSettings;
import android.content.Context;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.BeaconTransmitter;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.Region;

import java.util.Arrays;

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

    private final Context context;
    private final BeaconParser beaconParser;
    private final String selectedUUID;
    private final int minor;
    private final int major;
    private BeaconTransmitter beaconTransmitter;


    public BeaconService(Application context, int minor, int major) {
        this.context = context;
        selectedUUID = SystemUUID.getDeviceUUID(context).toString();
        beaconParser = new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"); // type of beacon
        this.minor = minor;
        this.major = major;

    }

    @Override
    public void enableTransmitting() {

        Beacon beacon = new Beacon.Builder()
                .setId1(selectedUUID) // UUID
                .setId2("" + major) // Major
                .setId3("" + minor) // Minor
                .setManufacturer(0x004c)
                .setTxPower(TX_POWER) // power dB
                .setDataFields(Arrays.asList(new Long[]{0l})) // Remove this for beacon layouts without d:
                .build();

        if (beaconParser != null) {
            beaconTransmitter = new BeaconTransmitter(context, beaconParser);
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
