package com.reactnativemeet;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.net.URL;

public class RNMeetConferenceOptions implements Parcelable {
    /**
     * Server where the conference should take place.
     */
    final static String serverAddress ="https://meet.webshrine.in/";
    private URL serverURL;
    private String room;
    /**
     * Conference subject.
     */
    private String subject;
    /**
     * JWT token used for authentication.
     */
    private String token;

    private Bundle colorScheme;

    private Bundle featureFlags;
    private Boolean audioMuted;
    private Boolean audioOnly;
    private Boolean videoMuted;

    private RNMeetUserInfo userInfo;

    public static class Builder {
        private URL serverURL;
        private String room;
        private String subject;
        private String token;

        private Bundle colorScheme;
        private Bundle featureFlags;

        private Boolean audioMuted;
        private Boolean audioOnly;
        private Boolean videoMuted;

        private RNMeetUserInfo userInfo;

        public Builder() {
            featureFlags = new Bundle();

        }

        /**\
         * Sets the server URL.
         * @param url - {@link URL} of the server where the conference should take place.
         * @return - The {@link Builder} object itself so the method calls can be chained.
         */
        public Builder setServerURL(URL url) {
            //this.serverURL = url;

            return this;
        }

        /**
         * Sets the room where the conference will take place.
         * @param room - Name of the room.
         * @return - The {@link Builder} object itself so the method calls can be chained.
         */
        public Builder setRoom(String room) {
            this.room = room;

            return this;
        }

        /**
         * Sets the conference subject.
         * @param subject - Subject for the conference.
         * @return - The {@link Builder} object itself so the method calls can be chained.
         */
        public Builder setSubject(String subject) {
            this.subject = subject;

            return this;
        }

        /**
         * Sets the JWT token to be used for authentication when joining a conference.
         * @param token - The JWT token to be used for authentication.
         * @return - The {@link Builder} object itself so the method calls can be chained.
         */
        public Builder setToken(String token) {
            this.token = token;

            return this;
        }

        public Builder setColorScheme(Bundle colorScheme) {
            this.colorScheme = colorScheme;

            return this;
        }

        /**
         * Indicates the conference will be joined with the microphone muted.
         * @param muted - Muted indication.
         * @return - The {@link Builder} object itself so the method calls can be chained.
         */
        public Builder setAudioMuted(boolean muted) {
            this.audioMuted = muted;

            return this;
        }

        /**
         * Indicates the conference will be joined in audio-only mode. In this mode no video is
         * sent or received.
         * @param audioOnly - Audio-mode indicator.
         * @return - The {@link Builder} object itself so the method calls can be chained.
         */
        public Builder setAudioOnly(boolean audioOnly) {
            this.audioOnly = audioOnly;

            return this;
        }
        /**
         * Indicates the conference will be joined with the camera muted.
         * @param videoMuted - Muted indication.
         * @return - The {@link Builder} object itself so the method calls can be chained.
         */
        public Builder setVideoMuted(boolean videoMuted) {
            this.videoMuted = videoMuted;

            return this;
        }

        /**
         * Sets the welcome page enabled / disabled. The welcome page lists recent meetings and
         * calendar appointments and it's meant to be used by standalone applications. Defaults to
         * false.
         * @param enabled - Whether the welcome page should be enabled or not.
         * @return - The {@link Builder} object itself so the method calls can be chained.
         */
        public Builder setWelcomePageEnabled(boolean enabled) {
            this.featureFlags.putBoolean("welcomepage.enabled", enabled);

            return this;
        }

        public Builder setFeatureFlag(String flag, boolean value) {
            this.featureFlags.putBoolean(flag, value);

            return this;
        }

        public Builder setFeatureFlag(String flag, String value) {
            this.featureFlags.putString(flag, value);

            return this;
        }

        public Builder setFeatureFlag(String flag, int value) {
            this.featureFlags.putInt(flag, value);
            return this;
        }

        public Builder setUserInfo(RNMeetUserInfo userInfo) {
            this.userInfo = userInfo;

            return this;
        }


        public RNMeetConferenceOptions build() {
            RNMeetConferenceOptions options = new RNMeetConferenceOptions();

            try{
                options.serverURL = new URL(serverAddress);
            }catch(Exception ex){

            }
            options.room = this.room;
            options.subject = this.subject;
            options.token = this.token;
            options.colorScheme = this.colorScheme;
            options.featureFlags = this.featureFlags;
            options.audioMuted = this.audioMuted;
            options.audioOnly = this.audioOnly;
            options.videoMuted = this.videoMuted;
            options.userInfo = this.userInfo;

            return options;
        }
    }

    private RNMeetConferenceOptions() {
    }

    private RNMeetConferenceOptions(Parcel in) {
        room = in.readString();
        subject = in.readString();
        token = in.readString();
        colorScheme = in.readBundle();
        featureFlags = in.readBundle();
        userInfo = new RNMeetUserInfo(in.readBundle());
        byte tmpAudioMuted = in.readByte();
        audioMuted = tmpAudioMuted == 0 ? null : tmpAudioMuted == 1;
        byte tmpAudioOnly = in.readByte();
        audioOnly = tmpAudioOnly == 0 ? null : tmpAudioOnly == 1;
        byte tmpVideoMuted = in.readByte();
        videoMuted = tmpVideoMuted == 0 ? null : tmpVideoMuted == 1;
    }

    Bundle asProps() {
        Bundle props = new Bundle();

        // Android always has the PiP flag set by default.
        if (!featureFlags.containsKey("pip.enabled")) {
            featureFlags.putBoolean("pip.enabled", true);
        }

        props.putBundle("flags", featureFlags);

        if (colorScheme != null) {
            props.putBundle("colorScheme", colorScheme);
        }

        Bundle config = new Bundle();

        if (audioMuted != null) {
            config.putBoolean("startWithAudioMuted", audioMuted);
        }
        if (audioOnly != null) {
            config.putBoolean("startAudioOnly", audioOnly);
        }
        if (videoMuted != null) {
            config.putBoolean("startWithVideoMuted", videoMuted);
        }
        if (subject != null) {
            config.putString("subject", subject);
        }

        Bundle urlProps = new Bundle();

        // The room is fully qualified
        if (room != null && room.contains("://")) {
            urlProps.putString("url", room);
        } else {
            if (serverURL != null) {
                urlProps.putString("serverURL", serverURL.toString());
            }
            if (room != null) {
                urlProps.putString("room", room);
            }
        }

        if (token != null) {
            urlProps.putString("jwt", token);
        }

        if (token == null && userInfo != null) {
            props.putBundle("userInfo", userInfo.asBundle());
        }

        urlProps.putBundle("config", config);
        props.putBundle("url", urlProps);

        return props;
    }

    // Parcelable interface
    //

    public static final Creator<RNMeetConferenceOptions> CREATOR = new Creator<RNMeetConferenceOptions>() {
        @Override
        public RNMeetConferenceOptions createFromParcel(Parcel in) {
            return new RNMeetConferenceOptions(in);
        }

        @Override
        public RNMeetConferenceOptions[] newArray(int size) {
            return new RNMeetConferenceOptions[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(room);
        dest.writeString(subject);
        dest.writeString(token);
        dest.writeBundle(colorScheme);
        dest.writeBundle(featureFlags);
        dest.writeBundle(userInfo != null ? userInfo.asBundle() : new Bundle());
        dest.writeByte((byte) (audioMuted == null ? 0 : audioMuted ? 1 : 2));
        dest.writeByte((byte) (audioOnly == null ? 0 : audioOnly ? 1 : 2));
        dest.writeByte((byte) (videoMuted == null ? 0 : videoMuted ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
