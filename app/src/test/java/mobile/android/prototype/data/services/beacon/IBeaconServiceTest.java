package mobile.android.prototype.data.services.beacon;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.BeaconTransmitter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class IBeaconServiceTest {

    @Mock
    private Context mMockContext;
    @Mock
    private ContentResolver mMockContentResolver;



    @Before
    public void setUp() {

        mockedSettings = Mockito.mockStatic(Settings.class, Answers.RETURNS_DEEP_STUBS);
        mockedSecure = Mockito.mockStatic(Settings.Secure.class, Answers.RETURNS_DEEP_STUBS);
        mMockContext = Mockito.mock(Context.class);
        mMockContentResolver = Mockito.mock(ContentResolver.class);
        Mockito.when(mMockContext.getContentResolver()).thenReturn(mMockContentResolver);

    }

    private static MockedStatic<Settings> mockedSettings;
    private static MockedStatic<Settings.Secure> mockedSecure;
    @After
    public void tearDown() {
        mockedSettings.close();
        mockedSecure.close();
    }

    @Test
    public void enableTransmittingMustCallBeaconTransmitter() {
        // arrange
        Context c = Mockito.mock(Context.class);
        Mockito.when(Settings.Secure.getString(c.getContentResolver(), Settings.Secure.ANDROID_ID)).thenReturn("aad8c2762f373551");
        String expectedUUID = "010d2108-0462-4f97-aad8-c2762f373551";
        BeaconTransmitter mockTransmitter = Mockito.mock(BeaconTransmitter.class, Answers.RETURNS_DEEP_STUBS);
        BeaconService beaconService = new BeaconService(
                c,
                Mockito.mock(BeaconParser.class),
                expectedUUID,
                0,
                0,
                Mockito.mock(Beacon.class),
                mockTransmitter);

        // act
        beaconService.enableTransmitting();

        // assert
        Mockito.verify(mockTransmitter, Mockito.atMostOnce()).setAdvertiseMode(2);
        Mockito.verify(mockTransmitter, Mockito.atMostOnce()).setAdvertiseTxPowerLevel(2);
        Mockito.verify(mockTransmitter, Mockito.atMostOnce()).startAdvertising();
    }


    @Test
    public void disableTransmittingMustCallBeaconTransmitter() {
        // arrange
        Context c = Mockito.mock(Context.class);
        Mockito.when(Settings.Secure.getString(c.getContentResolver(), Settings.Secure.ANDROID_ID)).thenReturn("aad8c2762f373551");
        String expectedUUID = "010d2108-0462-4f97-aad8-c2762f373551";
        BeaconTransmitter mockTransmitter = Mockito.mock(BeaconTransmitter.class, Answers.RETURNS_DEEP_STUBS);
        BeaconService beaconService = new BeaconService(
                c,
                Mockito.mock(BeaconParser.class),
                expectedUUID,
                0,
                0,
                Mockito.mock(Beacon.class),
                mockTransmitter);

        // act
        beaconService.enableTransmitting();

        // assert
        Mockito.verify(mockTransmitter, Mockito.atMostOnce()).stopAdvertising();
    }


}