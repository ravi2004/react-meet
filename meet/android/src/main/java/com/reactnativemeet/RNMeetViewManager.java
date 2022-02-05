package com.reactnativemeet;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.facebook.react.module.annotations.ReactModule;

import org.jitsi.meet.sdk.JitsiMeetViewListener;

import java.util.Map;

import static java.security.AccessController.getContext;

@ReactModule(name = RNMeetViewManager.REACT_CLASS)
public class RNMeetViewManager extends SimpleViewManager<RNMeetView> implements JitsiMeetViewListener {
    public static final String REACT_CLASS = "RNMeetView";
    private IRNMeetViewReference mMeetViewReference;
    private ReactApplicationContext mReactContext;

    public RNMeetViewManager(ReactApplicationContext reactContext, IRNMeetViewReference MeetViewReference) {
        mMeetViewReference = MeetViewReference;
        mReactContext = reactContext;
    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    public RNMeetView createViewInstance(ThemedReactContext context) {
        if (mMeetViewReference.getMeetView() == null) {
            RNMeetView view = new RNMeetView(context.getCurrentActivity());
            view.setListener(this);
            mMeetViewReference.setMeetView(view);
        }
        return mMeetViewReference.getMeetView();
    }

    public void onConferenceJoined(Map<String, Object> data) {
        WritableMap event = Arguments.createMap();
        event.putString("url", (String) data.get("url"));
        mReactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
                mMeetViewReference.getMeetView().getId(),
                "conferenceJoined",
                event);
    }

    public void onConferenceTerminated(Map<String, Object> data) {
        WritableMap event = Arguments.createMap();
        event.putString("url", (String) data.get("url"));
        event.putString("error", (String) data.get("error"));
        mReactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
                mMeetViewReference.getMeetView().getId(),
                "conferenceTerminated",
                event);
    }

    public void onConferenceWillJoin(Map<String, Object> data) {
        WritableMap event = Arguments.createMap();
        event.putString("url", (String) data.get("url"));
        mReactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
                mMeetViewReference.getMeetView().getId(),
                "conferenceWillJoin",
                event);
    }

    public Map getExportedCustomBubblingEventTypeConstants() {
        return MapBuilder.builder()
                .put("conferenceJoined", MapBuilder.of("phasedRegistrationNames", MapBuilder.of("bubbled", "onConferenceJoined")))
                .put("conferenceTerminated", MapBuilder.of("phasedRegistrationNames", MapBuilder.of("bubbled", "onConferenceTerminated")))
                .put("conferenceWillJoin", MapBuilder.of("phasedRegistrationNames", MapBuilder.of("bubbled", "onConferenceWillJoin")))
                .build();
    }
}