package mobile.android.prototype.ui.beacon;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;

public class BeaconViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    @Mock
    private Context mMockContext;
    @Mock
    private ContentResolver mMockContentResolver;

    @Before
    public void setUp() {

        Mockito.mockStatic(Settings.class, Answers.RETURNS_DEEP_STUBS);
        Mockito.mockStatic(Settings.Secure.class, Answers.RETURNS_DEEP_STUBS);
        mMockContext = Mockito.mock(Context.class);
        mMockContentResolver = Mockito.mock(ContentResolver.class);
        Mockito.when(mMockContext.getContentResolver()).thenReturn(mMockContentResolver);

    }

    @Test
    public void getTextReturnsDefault() {
        // arrange
        Context c = Mockito.mock(Context.class);
        Mockito.when(Settings.Secure.getString(c.getContentResolver(), Settings.Secure.ANDROID_ID)).thenReturn("aad8c2762f373551");
        BeaconViewModel vm = new BeaconViewModel(Mockito.mock(Application.class));
        String expectedUUID = "010d2108-0462-4f97-aad8-c2762f373551";
        String expectedString = "Not transmitting\nId: " + expectedUUID;

        // act
        String actual = vm.getText().getValue();

        // assert
        Assert.assertEquals(expectedString, actual);
    }

}