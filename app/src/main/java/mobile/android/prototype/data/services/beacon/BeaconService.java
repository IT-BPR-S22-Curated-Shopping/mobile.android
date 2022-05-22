package mobile.android.prototype.data.services.beacon;

import android.app.ActivityManager;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.le.AdvertiseSettings;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.BeaconTransmitter;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.Region;

import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import mobile.android.prototype.R;
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
    private BeaconManager beaconManager;
    private BeaconParser beaconParser;
    private final String selectedUUID;
    private final int minor;
    private final int major;
    private BeaconTransmitter beaconTransmitter;


    public BeaconService(Application context, int minor, int major) {
        this.context = context;
        selectedUUID = SystemUUID.getDeviceUUID(context).toString();
        beaconManager = BeaconManager.getInstanceForApplication(context.getApplicationContext());
        beaconParser = new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"); // type of beacon
        this.minor = minor;
        this.major = major;

    }

    @Override
    public void enableForegroundService() {
                // The following code block sets up the foreground service
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "My Notification Channel ID");
        builder.setSmallIcon(R.drawable.ic_outline_fiber_smart_record_24);
        builder.setContentTitle("Broadcasting beacon");
        Intent intent = new Intent(context, getClass());
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
        );
        builder.setContentIntent(pendingIntent);
        NotificationChannel channel = new NotificationChannel("My Notification Channel ID",
                "My Notification Name", NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription("My Notification Channel Description");
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
        builder.setChannelId(channel.getId());
        beaconManager.enableForegroundServiceScanning(builder.build(), 456);
        beaconManager.setEnableScheduledScanJobs(false);

        // The following code block effectively disables beacon scanning in the foreground service
        // to save battery.  Do not include this code block if you want to detect beacons
        beaconManager.getBeaconParsers().clear(); // clearning all beacon parsers ensures nothing matches
        beaconManager.setBackgroundBetweenScanPeriod(Long.MAX_VALUE);
        beaconManager.setBackgroundScanPeriod(0);
        beaconManager.setForegroundBetweenScanPeriod(Long.MAX_VALUE);
        beaconManager.setForegroundScanPeriod(0);


        // The following code block activates the foreground service by starting background scanning
        Region region = new Region("dummy-region", Identifier.parse("FFFFFFFF-FFFF-FFFF-FFFF-FFFFFFFFFFFF"), null, null);
        beaconManager.startMonitoring(region);
        beaconManager.addMonitorNotifier(this);
    }

    @Override
    public void disableForegroundService() {
        beaconManager.disableForegroundServiceScanning();
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
