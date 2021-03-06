package com.reactnativemeet;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RNMeetPackage implements ReactPackage, IRNMeetViewReference {

    private RNMeetView mMeetView = null;

    public void setMeetView(RNMeetView MeetView) {
        mMeetView = MeetView;
    }

    public RNMeetView getMeetView() {
        return mMeetView;
    }

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();
        modules.add(new RNMeetModule(reactContext, this));
        return modules;
    }

    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Arrays.<ViewManager>asList(
                new RNMeetViewManager(reactContext, this)
        );
    }
}
