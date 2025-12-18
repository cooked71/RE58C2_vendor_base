package android.view;

import android.icu.text.SimpleDateFormat;
import android.os.SystemProperties;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import java.util.Date;
import java.util.HashMap;

/* loaded from: classes.dex */
public class UnisocViewDebugConfigImpl extends ViewDebugConfig {
    private static int DBG_TIMEOUT_VALUE = 400;
    private static final String INPUT_DISPATCH_STATE_FINISHED = "0: Finish handle input event";
    private static final String INPUT_DISPATCH_STATE_STARTED = "1: Start event from input";
    private static final int INPUT_TIMEOUT = 6000;
    private static final String PROPERTY_DEBUG_FOCUS = "persist.debug.view_focus";
    private static final String PROPERTY_DEBUG_INPUT = "persist.debug.dispatch_input";
    private static final String PROPERTY_DEBUG_LIFECYCLE = "persist.debug.lifecycle";
    private static final String PROPERTY_DEBUG_REQUESTLAYOUT = "persist.debug.requestlayout";
    private static final String PROPERTY_DEBUG_SCHEDULETRAVERSALS = "persist.debug.scheduletraversals";
    private static final String PROPERTY_DEBUG_SURFACE = "persist.debug.surface";
    private static final String PROPERTY_DEBUG_SURFACEVIEW = "persist.debug.surfaceview";
    private static final String PROPERTY_DEBUG_VIEWROOT_DRAW = "persist.debug.viewroot_draw";
    private static final String PROPERTY_DEBUG_VIEWROOT_LAYOUT = "persist.debug.viewroot_layout";
    private static final String PROPERTY_DEBUG_VIEW_DRAW = "persist.debug.view_draw";
    private static final String PROPERTY_DEBUG_VIEW_DRAW_FRAME = "persist.debug.view_draw_frame";
    private static final String PROPERTY_DEBUG_VIEW_LAYOUT = "persist.debug.view_layout";
    private static final String PROPERTY_DISABLE_HWACC = "persist.disable.hwacc";
    private static final String PROPERTY_SYSTRACE_DRAW = "persist.systrace.view_draw";
    private static final String PROPERTY_SYSTRACE_LAYOUT = "persist.systrace.view_layout";
    private static final String PROPERTY_SYSTRACE_MEASURE = "persist.systrace.view_measure";
    private static final String VIEWGROUP_LOG_TAG = "ViewGroup";
    private static final String VIEW_LOG_TAG = "View";
    private long mKeyEventStartTime;
    private long mMotionEventStartTime;
    private HashMap<Object, Long> mInputStageRecored = new HashMap<>();
    private String mKeyEventStageStatus = INPUT_DISPATCH_STATE_FINISHED;
    private String mMotionEventStageStatus = INPUT_DISPATCH_STATE_FINISHED;

    void debugKeyDispatch(View v, KeyEvent event) {
        if (event.getAction() == 0) {
            Log.i(VIEW_LOG_TAG, "Key down dispatch to " + v + ", event = " + event);
        } else if (event.getAction() == 1) {
            Log.i(VIEW_LOG_TAG, "Key up dispatch to " + v + ", event = " + event);
        }
    }

    void debugEventHandled(View v, InputEvent event, String handler) {
        Log.i(VIEW_LOG_TAG, "Event handle in " + v + ", event = " + event + ", handler = " + handler);
    }

    void debugTouchDispatched(View v, MotionEvent event) {
        if (event.getAction() == 0) {
            Log.i(VIEW_LOG_TAG, "Touch down dispatch to " + v + ", event x = " + event.getX() + ",y = " + event.getY());
        } else if (event.getAction() == 1) {
            Log.i(VIEW_LOG_TAG, "Touch up dispatch to " + v + ", event x = " + event.getX() + ",y = " + event.getY());
        } else {
            Log.d(VIEW_LOG_TAG, "(View)dispatchTouchEvent: event action = " + MotionEvent.actionToString(event.getAction()) + ",x = " + event.getX() + ",y = " + event.getY() + ",this = " + v);
        }
    }

    void debugOnDrawDone(View v, long start) {
        if (ViewDebugConfig.DEBUG_DRAW) {
            long nowTime = System.currentTimeMillis();
            if (nowTime - start > DBG_TIMEOUT_VALUE) {
                Log.d(VIEW_LOG_TAG, "[ANR Warning]onDraw time too long, this =" + v + "time =" + (nowTime - start) + " ms");
            }
            Log.d(VIEW_LOG_TAG, "onDraw done, this =" + v + "time =" + (nowTime - start) + " ms");
        }
    }

    long debugOnMeasureStart(View v, int widthMeasureSpec, int heightMeasureSpec, int oldWidthMeasureSpec, int oldHeightMeasureSpec) {
        if (ViewDebugConfig.DEBUG_LAYOUT) {
            Log.d(VIEW_LOG_TAG, "view measure start, this = " + v + ", widthMeasureSpec = " + View.MeasureSpec.toString(widthMeasureSpec) + ", heightMeasureSpec = " + View.MeasureSpec.toString(heightMeasureSpec) + ", mOldWidthMeasureSpec = " + View.MeasureSpec.toString(oldWidthMeasureSpec) + ", mOldHeightMeasureSpec = " + View.MeasureSpec.toString(oldHeightMeasureSpec) + getViewLayoutProperties(v));
        }
        return System.currentTimeMillis();
    }

    private String getViewLayoutProperties(View v) {
        StringBuilder out = new StringBuilder(128);
        out.append(", Padding = {" + v.getPaddingLeft() + ", " + v.getPaddingTop() + ", " + v.getPaddingRight() + ", " + v.getPaddingBottom() + "}");
        if (v.getLayoutParams() == null) {
            out.append(", BAD! no layout params");
        } else {
            out.append(", " + v.getLayoutParams().debug(""));
        }
        return out.toString();
    }

    void debugOnMeasureEnd(View v, long logTime) {
        if (ViewDebugConfig.DEBUG_LAYOUT) {
            long nowTime = System.currentTimeMillis();
            if (nowTime - logTime > DBG_TIMEOUT_VALUE) {
                Log.d(VIEW_LOG_TAG, "[ANR Warning]onMeasure time too long, this =" + v + "time =" + (nowTime - logTime) + " ms");
            }
            Log.d(VIEW_LOG_TAG, "view onMeasure end (measure cache), this =" + v + ", mMeasuredWidth = " + v.getMeasuredWidth() + ", mMeasuredHeight = " + v.getMeasuredHeight() + ", time =" + (nowTime - logTime) + " ms");
        }
    }

    public void debugOnLayoutEnd(View v, long logTime) {
        if (ViewDebugConfig.DEBUG_LAYOUT) {
            long nowTime = System.currentTimeMillis();
            if (nowTime - logTime > DBG_TIMEOUT_VALUE) {
                Log.d(VIEW_LOG_TAG, "[ANR Warning]onLayout time too long, this =" + v + "time =" + (nowTime - logTime) + " ms");
            }
            Log.d(VIEW_LOG_TAG, "view layout end, this =" + v + ", time =" + (nowTime - logTime) + " ms");
        }
    }

    private int getCurrentLevel(View view) {
        int level = 0;
        ViewParent parent = view.getParent();
        while (parent != null && (parent instanceof View)) {
            level++;
            View v = (View) parent;
            parent = v.getParent();
        }
        return level;
    }

    void debugViewRemoved(View child, ViewGroup parent, Thread rootThread) {
        if (ViewDebugConfig.DEBUG_LIFECYCLE) {
            if (parent.getViewRootImpl() != null && rootThread != Thread.currentThread()) {
                Log.e(VIEWGROUP_LOG_TAG, "[Warning] remove view from parent not in UIThread: parent = " + parent + " view == " + child);
            }
            Log.e(VIEWGROUP_LOG_TAG, "will remove view from parent " + parent + " view == " + child, new Throwable());
        }
    }

    void debugViewGroupChildMeasure(View child, View parent, ViewGroup.MarginLayoutParams lp, int widthUsed, int heightUsed) {
        int level = getCurrentLevel(parent);
        Log.d(VIEWGROUP_LOG_TAG, "[ViewGroup][measureChildWithMargins] +" + level + " , child = " + child + ", child margin (L,R,T,B) = " + lp.leftMargin + "," + lp.rightMargin + "," + lp.topMargin + "," + lp.bottomMargin + ", widthUsed = " + widthUsed + ", heightUsed = " + heightUsed + ", parent padding (L,R,T,B) = " + parent.getPaddingLeft() + "," + parent.getPaddingRight() + "," + parent.getPaddingTop() + "," + parent.getPaddingBottom() + ", this = " + this);
    }

    void debugViewGroupChildMeasure(View child, View parent, ViewGroup.LayoutParams lp, int widthUsed, int heightUsed) {
        int level = getCurrentLevel(parent);
        Log.d(VIEWGROUP_LOG_TAG, "[ViewGroup][measureChildWithMargins] +" + level + " , child = " + child + ", child params (width, height) = " + lp.width + "," + lp.height + ", widthUsed = " + widthUsed + ", heightUsed = " + heightUsed + ", parent padding (L,R,T,B) = " + parent.getPaddingLeft() + "," + parent.getPaddingRight() + "," + parent.getPaddingTop() + "," + parent.getPaddingBottom() + ", this = " + this);
    }

    void debugViewRootConstruct(String logTag, Object context, Object thread, Object chorgrapher, Object traversal, ViewRootImpl root) {
        checkViewRootLogProperty();
        checkViewLogProperty();
        checkSurfaceLogProperty();
    }

    static void checkViewRootLogProperty() {
        ViewDebugConfig.DEBUG_REQUESTLAYOUT = SystemProperties.getBoolean(PROPERTY_DEBUG_REQUESTLAYOUT, false);
        ViewDebugConfig.DEBUG_SCHEDULETRAVERSALS = SystemProperties.getBoolean(PROPERTY_DEBUG_SCHEDULETRAVERSALS, false);
        ViewDebugConfig.DEBUG_DISABLE_HWACC = SystemProperties.getBoolean(PROPERTY_DISABLE_HWACC, false);
        ViewDebugConfig.DEBUG_VIEWROOT_LAYOUT = SystemProperties.getBoolean(PROPERTY_DEBUG_VIEWROOT_LAYOUT, false);
        ViewDebugConfig.DEBUG_VIEWROOT_DRAW = SystemProperties.getBoolean(PROPERTY_DEBUG_VIEWROOT_DRAW, false);
        ViewDebugConfig.DEBUG_LIFECYCLE = SystemProperties.getBoolean(PROPERTY_DEBUG_LIFECYCLE, false);
    }

    static void checkViewLogProperty() {
        ViewDebugConfig.DEBUG_INPUT = SystemProperties.getBoolean(PROPERTY_DEBUG_INPUT, false);
        ViewDebugConfig.DEBUG_FOCUS = SystemProperties.getBoolean(PROPERTY_DEBUG_FOCUS, false);
        ViewDebugConfig.DEBUG_LAYOUT = SystemProperties.getBoolean(PROPERTY_DEBUG_VIEW_LAYOUT, false);
        ViewDebugConfig.DEBUG_DRAW = SystemProperties.getBoolean(PROPERTY_DEBUG_VIEW_DRAW, false);
        ViewDebugConfig.DEBUG_FRAME = SystemProperties.getBoolean(PROPERTY_DEBUG_VIEW_DRAW_FRAME, true);
        ViewDebugConfig.DEBUG_SYSTRACE_MEASURE = SystemProperties.getBoolean(PROPERTY_SYSTRACE_MEASURE, false);
        ViewDebugConfig.DEBUG_SYSTRACE_LAYOUT = SystemProperties.getBoolean(PROPERTY_SYSTRACE_LAYOUT, false);
        ViewDebugConfig.DEBUG_SYSTRACE_DRAW = SystemProperties.getBoolean(PROPERTY_SYSTRACE_DRAW, false);
    }

    static void checkSurfaceLogProperty() {
        ViewDebugConfig.DEBUG_SURFACE = SystemProperties.getBoolean(PROPERTY_DEBUG_SURFACE, false);
        boolean z = SystemProperties.getBoolean(PROPERTY_DEBUG_SURFACEVIEW, false);
        SurfaceView.DEBUG_POSITION = z;
        SurfaceView.DEBUG = z;
    }

    void debugInputStageDeliverd(Object stage, long time) {
        if (ViewDebugConfig.DEBUG_INPUT) {
            this.mInputStageRecored.put(stage, Long.valueOf(time));
        }
    }

    private void clearInputStageInfo() {
        this.mInputStageRecored.clear();
    }

    private void dumpInputStageInfo(String logTag, SimpleDateFormat sdf) {
        if (!this.mInputStageRecored.isEmpty()) {
            for (Object obj : this.mInputStageRecored.keySet()) {
                long dt = this.mInputStageRecored.get(obj).longValue();
                Date deliveredTime = new Date(dt);
                if (dt != 0) {
                    Log.v(logTag, "Input event delivered to " + obj + " at " + sdf.format(deliveredTime));
                }
            }
        }
    }

    void debugInputEventStart(InputEvent event) {
        if (ViewDebugConfig.DEBUG_INPUT) {
            if (event instanceof KeyEvent) {
                this.mKeyEventStartTime = System.currentTimeMillis();
                this.mKeyEventStageStatus = INPUT_DISPATCH_STATE_STARTED;
            } else {
                this.mMotionEventStartTime = System.currentTimeMillis();
                this.mMotionEventStageStatus = INPUT_DISPATCH_STATE_STARTED;
            }
        }
    }

    void debugInputEventFinished(String logTag, boolean handled, InputEvent event, ViewRootImpl root) {
        String stage;
        long inputElapseTime;
        if (ViewDebugConfig.DEBUG_INPUT) {
            long currentTime = System.currentTimeMillis();
            if (event instanceof KeyEvent) {
                stage = this.mKeyEventStageStatus;
                this.mKeyEventStageStatus = INPUT_DISPATCH_STATE_FINISHED;
                inputElapseTime = currentTime - this.mKeyEventStartTime;
            } else {
                stage = this.mMotionEventStageStatus;
                this.mMotionEventStageStatus = INPUT_DISPATCH_STATE_FINISHED;
                inputElapseTime = currentTime - this.mMotionEventStartTime;
            }
            if (inputElapseTime >= 6000) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                Date enqueueTime = new Date(currentTime - inputElapseTime);
                Log.v(logTag, "[ANR Warning]Input routeing takes more than 6000ms since " + simpleDateFormat.format(enqueueTime) + ", this = " + this);
                dumpInputStageInfo(logTag, simpleDateFormat);
            }
            clearInputStageInfo();
            if (event instanceof MotionEvent) {
                Log.v(logTag, "finishInputEvent: handled = " + handled + ",event action = " + MotionEvent.actionToString(((MotionEvent) event).getAction()) + ",x = " + ((MotionEvent) event).getX() + ",y = " + ((MotionEvent) event).getY() + ", stage = " + stage);
            } else {
                Log.v(logTag, "finishInputEvent: handled = " + handled + ",event = " + event + ", stage = " + stage);
            }
        }
    }

    void debugInputDispatchState(InputEvent event, String state) {
        if (ViewDebugConfig.DEBUG_INPUT) {
            if (event instanceof KeyEvent) {
                setKeyDispatchState(state);
            } else {
                setMotionDispatchState(state);
            }
        }
    }

    private void setKeyDispatchState(String state) {
        this.mKeyEventStageStatus = state;
    }

    private void setMotionDispatchState(String state) {
        this.mMotionEventStageStatus = state;
    }
}