package android.view;

import android.content.Context;

/* loaded from: classes.dex */
public class UnisocViewRootImplFactory extends ViewRootImplFactory {
    public ViewRootImpl makeViewRootImpl(Context context, Display display) {
        UnisocViewRootImpl unisocViewRootImpl = new UnisocViewRootImpl(context, display);
        return unisocViewRootImpl;
    }
}