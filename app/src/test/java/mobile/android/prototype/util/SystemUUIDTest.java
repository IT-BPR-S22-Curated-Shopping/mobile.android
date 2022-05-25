package mobile.android.prototype.util;


import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.UUID;


public class SystemUUIDTest {

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
    public void checkIfReturnsCorrectUUID() {
        // arrange
        Context c = Mockito.mock(Context.class);
        Mockito.when(Settings.Secure.getString(c.getContentResolver(), Settings.Secure.ANDROID_ID)).thenReturn("aad8c2762f373551");
        String expected = "010d2108-0462-4f97-aad8-c2762f373551";
        // act
        UUID uuid = SystemUUID.getDeviceUUID(c);

        // assert
        Assert.assertNotNull(uuid);
        Assert.assertEquals(expected, uuid.toString());
    }

}