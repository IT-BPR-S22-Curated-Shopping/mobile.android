package mobile.android.prototype;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.le.AdvertiseSettings;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

//import mobile.android.prototype.ui.profile.ProfileActivity;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.BeaconTransmitter;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.Region;

import java.util.Arrays;

/*
    https://altbeacon.github.io/android-beacon-library/samples-java.html

    Reference code can be found here:
    https://altbeacon.github.io/android-beacon-library/beacon-transmitter.html
 */

public class BeaconActivity extends AppCompatActivity implements MonitorNotifier {

    private static final String LOG_TAG = "BLE DEVICE";
    private static final String PROFILE_1 = "Modern profile";
    private static final String PROFILE_2 = "Classic profile";
    private static final String PROFILE_3 = "Retro profile";
    private static final String STOP_TRANSMITTING = "Stop Transmitting";

    private static final int TX_POWER = AdvertiseSettings.ADVERTISE_TX_POWER_MEDIUM;
    private static final int MODE = AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY;

    public static String UUID_PROFILE1 = "010d2108-0462-4f97-bab8-000000000001";
    public static String UUID_PROFILE2 = "010d2108-0462-4f97-bab8-000000000002";
    public static String UUID_PROFILE3 = "010d2108-0462-4f97-bab8-000000000003";

    private TextView textViewUUID;
    private TextView textViewMajor;
    private TextView textViewMinor;
    private TextView textViewIdentity;
    private TextView textViewStatus;
    private Button buttonProfil1;
    private Button buttonProfil2;
    private Button buttonProfil3;
    private SeekBar seekBarTx;
    private int seekbarValue = -59;
    private TextView textViewSeekBar;
    private ImageView imageView;

    BeaconTransmitter beaconTransmitter;
    private boolean isTransmitting = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon);

        textViewUUID = findViewById(R.id.textViewUUID);
        textViewMajor = findViewById(R.id.textViewMajor);
        textViewMinor = findViewById(R.id.textViewMinor);
        textViewIdentity = findViewById(R.id.textViewIdentity);
        textViewStatus = findViewById(R.id.textViewStatus);
        buttonProfil1 = findViewById(R.id.buttonTransmit);
        buttonProfil1.setText(PROFILE_1);
        buttonProfil2 = findViewById(R.id.buttonTransmit2);
        buttonProfil2.setText(PROFILE_2);
        buttonProfil3 = findViewById(R.id.buttonTransmit3);
        buttonProfil3.setText(PROFILE_3);

        seekBarTx = findViewById(R.id.seekBarTx);
        seekBarTx.setMin(-100);
        seekBarTx.setMax(0);
        seekBarTx.setProgress(seekbarValue);
        seekBarTx.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekbarValue = seekBar.getProgress();
                textViewSeekBar.setText(String.format("%s", i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        textViewSeekBar = findViewById(R.id.textViewSelectedTX);

        imageView = findViewById(R.id.imageView);
        imageView.setVisibility(View.INVISIBLE);
        String imageUrl = "https://images.squarespace-cdn.com/content/v1/5f6a3b396b1bc12ab9192246/1612621587515-BP8Q50NXPW0A3FMY3PP7/animation+company.gif";

        Glide
                .with(getApplicationContext())
                .load(imageUrl)
                .override(300, Target.SIZE_ORIGINAL)
                .into(imageView);


        textViewUUID.setText("NOT SET");

        buttonProfil1.setOnClickListener(v -> {

            buttonClick(1);
        });
        buttonProfil2.setOnClickListener(v -> buttonClick(2));
        buttonProfil3.setOnClickListener(v -> buttonClick(3));

        enableForegroundService();
    }

    private void enableForegroundService() {
        BeaconManager beaconManager = BeaconManager.getInstanceForApplication(getApplicationContext());

        // The following code block sets up the foreground service

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "My Notification Channel ID");
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setContentTitle("Broadcasting beacon");
        Intent intent = new Intent(getApplicationContext(), getClass());
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
        );
        builder.setContentIntent(pendingIntent);
        NotificationChannel channel = new NotificationChannel("My Notification Channel ID",
                "My Notification Name", NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription("My Notification Channel Description");
        NotificationManager notificationManager = (NotificationManager) getSystemService(
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

    public void buttonClick(int i) {
        if (!isTransmitting) {
            seekBarTx.setEnabled(false);
            if (i == 1) {
                selectedUUID = UUID_PROFILE1;
                buttonProfil1.setText(STOP_TRANSMITTING);
                buttonProfil2.setVisibility(View.INVISIBLE);
                buttonProfil3.setVisibility(View.INVISIBLE);
            } else if (i == 2) {
                selectedUUID = UUID_PROFILE2;
                buttonProfil2.setText(STOP_TRANSMITTING);
                buttonProfil1.setVisibility(View.INVISIBLE);
                buttonProfil3.setVisibility(View.INVISIBLE);
            } else {
                selectedUUID = UUID_PROFILE3;
                buttonProfil3.setText(STOP_TRANSMITTING);
                buttonProfil2.setVisibility(View.INVISIBLE);
                buttonProfil1.setVisibility(View.INVISIBLE);
            }

            startAdvertising();
            imageView.setVisibility(View.VISIBLE);
        } else {
            buttonProfil1.setText(PROFILE_1);
            buttonProfil1.setVisibility(View.VISIBLE);
            buttonProfil2.setText(PROFILE_2);
            buttonProfil2.setVisibility(View.VISIBLE);
            buttonProfil3.setText(PROFILE_3);
            buttonProfil3.setVisibility(View.VISIBLE);

            stopAdvertising();
            imageView.setVisibility(View.INVISIBLE);
            seekBarTx.setEnabled(true);
        }

        isTransmitting = !isTransmitting;
    }

    private String selectedUUID;

    private void startAdvertising() {

        String majorString = String.valueOf(1);
        String minorString = String.valueOf(1);
        Beacon beacon = new Beacon.Builder()
                .setId1(selectedUUID) // UUID
                .setId2(majorString) // Major
                .setId3(minorString) // Minor
                .setManufacturer(0x004c)
                .setTxPower(seekbarValue) // power dB
                .setDataFields(Arrays.asList(new Long[]{0l})) // Remove this for beacon layouts without d:
                .build();


        BeaconParser beaconParser = new BeaconParser()
                .setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24");

        if (beaconParser != null) {

            beaconTransmitter = new BeaconTransmitter(getApplicationContext(), beaconParser);

            beaconTransmitter.setAdvertiseMode(MODE);
            beaconTransmitter.setAdvertiseTxPowerLevel(TX_POWER);

            textViewMajor.setText(majorString);
            textViewMinor.setText(minorString);
            textViewUUID.setText(selectedUUID);

            beaconTransmitter.startAdvertising(beacon);
            textViewIdentity.setText(beacon.getBluetoothAddress());

            textViewStatus.setText("Transmitting");
        }


    }

    private void stopAdvertising() {
        beaconTransmitter.stopAdvertising();
        textViewStatus.setText("Not Transmitting");
    }


    @Override
    public void didEnterRegion(Region region) {
        // not used
    }

    @Override
    public void didExitRegion(Region region) {
        // not used
    }

    @Override
    public void didDetermineStateForRegion(int state, Region region) {
        // not used
    }
}