package mobile.android.prototype.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;

import java.util.UUID;

public class SystemUUID {

    public static UUID getDeviceUUID(Context context) {
        return UUID.fromString("010d2108-0462-4f97-" + id(context)[0] + "-" + id(context)[1]);
    }

    @SuppressLint("HardwareIds")
    private static String[] id(Context context) {
        String deviceId = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return new String[]{deviceId.substring(0, 4), deviceId.substring(4)};
    }
}
