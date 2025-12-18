package android.os;

import android.content.Context;
import android.content.pm.UserInfo;
import android.util.Log;

/* loaded from: classes.dex */
public class UnisocUserManager {
    private static final String TAG = "UnisocUserManager";

    public boolean isCloneProfile(Context context, UserHandle userHandle) {
        if (context == null) {
            Log.e(TAG, "Context can not be null.");
            return false;
        }
        if (userHandle == null) {
            Log.e(TAG, "UserHandle can not be null.");
            return false;
        }
        int userId = userHandle.getIdentifier();
        if (userId < 0) {
            Log.w(TAG, "User [" + userId + "] is not a profile.");
            return false;
        }
        UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
        UserInfo userInfo = userManager.getUserInfo(userId);
        if (userInfo == null) {
            Log.e(TAG, "User [" + userId + "] does not exist.");
            return false;
        }
        return userInfo.isCloneProfile();
    }
}