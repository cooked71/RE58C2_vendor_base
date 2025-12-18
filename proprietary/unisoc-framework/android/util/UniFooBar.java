package android.util;

/* loaded from: classes.dex */
public class UniFooBar extends FooBar {
    public void foo() {
        Log.d("UniFooBar", "Override foo()");
    }

    public int bar() {
        Log.d("UniFooBar", "Override bar()");
        return -1;
    }
}