package mobile.android.prototype.util;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;

import java.util.UUID;

public class SystemUUID {

    public static UUID getDeviceUUID(Context context) {
        return UUID.fromString("010d2108-4062-4f97-" + id(context.getContentResolver())[0] + "-" + id(context.getContentResolver())[1]);
    }

    @SuppressLint("HardwareIds")
    private static String[] id(ContentResolver context) {
        String deviceId = Settings.Secure.getString(context,
                Settings.Secure.ANDROID_ID);
        return new String[]{deviceId.substring(0, 4), deviceId.substring(4)};
    }
}
