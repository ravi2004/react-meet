package com.reactnativemeet;

import android.app.Activity;
import android.view.View;

import android.util.Log;
import java.net.URL;
import java.net.MalformedURLException;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.bridge.ReadableMap;

import com.facebook.react.ReactActivity;

@ReactModule(name = RNMeetModule.MODULE_NAME)
public class RNMeetModule extends ReactContextBaseJavaModule {
    public static final String MODULE_NAME = "RNMeetModule";
    private IRNMeetViewReference mMeetViewReference;

    public RNMeetModule(ReactApplicationContext reactContext, IRNMeetViewReference MeetViewReference) {
        super(reactContext);
        mMeetViewReference = MeetViewReference;
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @ReactMethod
    public void initialize() {
        Log.d("Meet", "Initialize is deprecated in v2");
    }

    @ReactMethod
    public void hide(){
       // Activity reactActivity = getCurrentActivity();
        if (mMeetViewReference.getMeetView() != null) {
            View decorView = mMeetViewReference.getMeetView(); //reactActivity.getWindow().getDecorView();
            // Hide both the navigation bar and the status bar.
            // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
            // a general rule, you should design your app to hide the status bar whenever you
            // hide the navigation bar.

        int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        | View.SYSTEM_UI_FLAG_FULLSCREEN
        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(flags);
        }
    }


    @ReactMethod
    public void call(ReadableMap options) {
        
        UiThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mMeetViewReference.getMeetView() != null) {
                    RNMeetUserInfo _userInfo = new RNMeetUserInfo();
                    String roomName=null, subject=null, token=null;
                    Boolean  
                                audioMuted = true, 
                                videoMuted = true,
                                audioOnly = false,
                                welcomePage=false,    
                                pipEnabled = false,
                                inviteEnabled=false,
                                fullScreen=true;
                    if(options != null){

                        if (options.hasKey("displayName")) {
                            _userInfo.setDisplayName(options.getString("displayName"));
                          }
                          if (options.hasKey("email")) {
                            _userInfo.setEmail(options.getString("email"));
                          }
                          if (options.hasKey("avatar")) {
                            String avatarURL = options.getString("avatar");
                            try {
                                _userInfo.setAvatar(new URL(avatarURL));
                            } catch (MalformedURLException e) {
                            }
                          }


                        if(options.hasKey("roomName")){
                            roomName=options.getString("roomName");
                        }


                        if(options.hasKey("audioMuted")){
                            audioMuted=options.getBoolean("audioMuted");
                        }

                        if(options.hasKey("videoMuted")){
                            videoMuted=options.getBoolean("videoMuted");    
                        }

                        if(options.hasKey("audioOnly")){
                            audioOnly=options.getBoolean("audioOnly");    
                        }

                        if(options.hasKey("subject")){
                            subject=options.getString("subject");
                        }

                        if(options.hasKey("pipEnabled")){
                            pipEnabled=options.getBoolean("pipEnabled");

                        }

                        if(options.hasKey("token")){
                            token=options.getString("token");
                        }

                        if(options.hasKey("welcomePage")){
                            welcomePage=options.getBoolean("welcomePage");
                        }

                        if(options.hasKey("inviteEnabled")){
                            inviteEnabled=options.getBoolean("inviteEnabled");
                        }

                        if(options.hasKey("fullScreen")){
                            fullScreen=options.getBoolean("fullScreen");
                        }
                    }
                    

                    RNMeetConferenceOptions meeting_options = new RNMeetConferenceOptions.Builder()
                            .setSubject(subject)
                            .setToken(token)
                            .setAudioMuted(audioMuted)                            
                            .setVideoMuted(videoMuted)
                            .setAudioOnly(audioOnly)
                            .setWelcomePageEnabled(welcomePage)
                            .setFeatureFlag("pip.enabled", pipEnabled)
                            .setFeatureFlag("fullscreen.enabled", fullScreen)
                            .setFeatureFlag("invite.enabled", inviteEnabled)
                            .setRoom(roomName)
                            .setUserInfo(_userInfo)
                            .build();
                
                    mMeetViewReference.getMeetView().join(meeting_options);
                }
            }
        });
    }

    @ReactMethod
    public void audioCall(String url, ReadableMap userInfo) {
        UiThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mMeetViewReference.getMeetView() != null) {
                    RNMeetUserInfo _userInfo = new RNMeetUserInfo();
                    if (userInfo != null) {
                        if (userInfo.hasKey("displayName")) {
                            _userInfo.setDisplayName(userInfo.getString("displayName"));
                          }
                          if (userInfo.hasKey("email")) {
                            _userInfo.setEmail(userInfo.getString("email"));
                          }
                          if (userInfo.hasKey("avatar")) {
                            String avatarURL = userInfo.getString("avatar");
                            try {
                                _userInfo.setAvatar(new URL(avatarURL));
                            } catch (MalformedURLException e) {
                            }
                          }
                    }
                    RNMeetConferenceOptions options = new RNMeetConferenceOptions.Builder()
                            .setRoom(url)
                            .setAudioOnly(true)
                            .setUserInfo(_userInfo)
                            .build();
                    mMeetViewReference.getMeetView().join(options);
                }
            }
        });
    }

    @ReactMethod
    public void endCall() {
        UiThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mMeetViewReference.getMeetView() != null) {
                    mMeetViewReference.getMeetView().leave();
                }
            }
        });
    }
}
