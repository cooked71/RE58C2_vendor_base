package android.view;

import android.app.ActivityManager;
import android.content.Context;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.util.Slog;

/* loaded from: classes.dex */
public class UnisocViewRootImpl extends ViewRootImpl {
    static final int HEIGHT_TO_SKIP = 20;
    private static final String PROPERTY_DEBUG_EVENT_DROP = "debug.enable.event.drop";
    private static final String TAG = "UnisocViewRootImpl";
    boolean mIsSupportDynamicNavbar;
    boolean mSwipeFromBottom;
    private boolean sDebugEventDrop;

    public UnisocViewRootImpl(Context context, Display display) {
        super(context, display);
        this.mIsSupportDynamicNavbar = false;
        this.mSwipeFromBottom = false;
        this.sDebugEventDrop = false;
        this.mDisplayHeight = this.mDisplay.getMode().getPhysicalHeight();
        this.mIsSupportDynamicNavbar = isSupportDynamicNavbar();
        this.sDebugEventDrop = SystemProperties.getBoolean(PROPERTY_DEBUG_EVENT_DROP, false);
    }

    boolean dropIfNeeded(InputEvent event) {
        boolean isDrop = false;
        if (this.mIsSupportDynamicNavbar && (event instanceof MotionEvent)) {
            MotionEvent e = (MotionEvent) event;
            switch (e.getAction()) {
                case 0:
                    this.mSwipeFromBottom = false;
                    boolean canNavMove = false;
                    try {
                        canNavMove = WindowManagerGlobal.getWindowManagerService().canNavigationBarMove();
                    } catch (Exception er) {
                        Slog.e(TAG, "throw exception, " + er);
                    }
                    int orientation = this.mDisplay.getOrientation();
                    boolean keyguardShowing = false;
                    boolean hasNavigationBar = false;
                    if (shouldDropPoint(orientation, canNavMove, e)) {
                        try {
                            keyguardShowing = WindowManagerGlobal.getWindowManagerService().isKeyguardShowingAndNotOccluded();
                            hasNavigationBar = WindowManagerGlobal.getWindowManagerService().isNavigationBarShowing();
                        } catch (Exception er2) {
                            Slog.e(TAG, "throw exception, " + er2);
                        }
                        if (!keyguardShowing && !hasNavigationBar) {
                            this.mSwipeFromBottom = true;
                            isDrop = true;
                            break;
                        }
                    }
                    break;
                case 1:
                    if (this.mSwipeFromBottom) {
                        this.mSwipeFromBottom = false;
                        isDrop = true;
                        break;
                    }
                    break;
                default:
                    if (this.mSwipeFromBottom) {
                        isDrop = true;
                        break;
                    }
                    break;
            }
        }
        if (this.sDebugEventDrop) {
            Slog.d(TAG, "dropIfNeeded isDrop = " + isDrop);
        }
        return isDrop;
    }

    boolean shouldDropPoint(int orientation, boolean canNavMove, MotionEvent e) {
        boolean tmp1 = canNavMove && ((orientation % 2 == 0 && e.getRawY() > ((float) (this.mDisplayHeight + (-20)))) || ((orientation % 4 == 1 && e.getRawX() > ((float) (this.mDisplayHeight + (-20)))) || (orientation % 4 == 3 && e.getRawX() < 20.0f)));
        boolean tmp2 = !canNavMove && e.getRawY() > ((float) (this.mDisplayHeight + (-20)));
        return tmp1 || tmp2;
    }

    boolean isSupportDynamicNavbar() {
        boolean hasNavbar = false;
        try {
            hasNavbar = WindowManagerGlobal.getWindowManagerService().hasNavigationBar(this.mContext.getDisplayId());
        } catch (RemoteException ex) {
            Slog.d(TAG, "RemoteException: " + ex);
        }
        boolean supportDaynamicNavbar = !ActivityManager.isLowRamDeviceStatic();
        return hasNavbar && supportDaynamicNavbar;
    }

    void removeAccessibilityListener() {
        this.mAccessibilityInteractionConnectionManager.ensureNoConnection();
        this.mAccessibilityManager.removeAccessibilityStateChangeListener(this.mAccessibilityInteractionConnectionManager);
        this.mAccessibilityManager.removeHighTextContrastStateChangeListener(this.mHighContrastTextManager);
        setAccessibilityFocus(null, null);
    }
}