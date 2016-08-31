package com.nile.webrtc.demo;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.webrtc.AudioSource;
import org.webrtc.AudioTrack;
import org.webrtc.CameraEnumerationAndroid;
import org.webrtc.DataChannel;
import org.webrtc.IceCandidate;
import org.webrtc.MediaConstraints;
import org.webrtc.MediaStream;
import org.webrtc.PeerConnection;
import org.webrtc.PeerConnectionFactory;
import org.webrtc.RendererCommon;
import org.webrtc.VideoCapturer;
import org.webrtc.VideoCapturerAndroid;
import org.webrtc.VideoRenderer;
import org.webrtc.VideoRendererGui;
import org.webrtc.VideoSource;
import org.webrtc.VideoTrack;

import java.util.ArrayList;

public class WebRtcActivity extends AppCompatActivity {
    private final static String Tag = "WebRtcActivity";
    private static final String VIDEO_TRACK_ID = "video-1";
    private static final String AUDIO_TRACK_ID = "audio-1";
    private static final String LOCAL_MEDIA_STREAM_ID = "stream-1";
    PeerConnectionFactory peerConnectionFactory = null;
    VideoCapturer capturer = null;
    VideoSource videoSource = null;
    VideoTrack localVideoTrack = null;
    VideoRenderer renderer = null;
    AudioSource audioSource = null;
    AudioTrack localAudioTrack = null;
    PeerConnection peerConnection = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroy();
    }

    private void init() {
        destroy();
        // First, we initiate the PeerConnectionFactory with
        // our application context and some options.
        boolean isInit = PeerConnectionFactory.initializeAndroidGlobals(this.getApplication().getApplicationContext(), true, true, false);
        Log.e(Tag, "isInit=" + isInit);
        peerConnectionFactory = new PeerConnectionFactory();
        // Returns the front face device name
        String name = CameraEnumerationAndroid.getNameOfFrontFacingDevice();

        if (name == null) {
            // Returns the back facing device name
            CameraEnumerationAndroid.getNameOfBackFacingDevice();
        }

        // Creates a VideoCapturerAndroid instance for the device name
        capturer = VideoCapturerAndroid.create(name);

        MediaConstraints videoConstraints = new MediaConstraints();

        // First we create a VideoSource
        videoSource = peerConnectionFactory.createVideoSource(capturer, videoConstraints);

        // Once we have that, we can create our VideoTrack
        // Note that VIDEO_TRACK_ID can be any string that uniquely
        // identifies that video track in your application
        localVideoTrack = peerConnectionFactory.createVideoTrack(VIDEO_TRACK_ID, videoSource);

        MediaConstraints audioConstraints = new MediaConstraints();
        // First we create an AudioSource
        audioSource = peerConnectionFactory.createAudioSource(audioConstraints);

        // Once we have that, we can create our AudioTrack
        // Note that AUDIO_TRACK_ID can be any string that uniquely
        // identifies that audio track in your application
        localAudioTrack = peerConnectionFactory.createAudioTrack(AUDIO_TRACK_ID, audioSource);

        // To create our VideoRenderer, we can use the
        // included VideoRendererGui for simplicity
        // First we need to set the GLSurfaceView that it should render to
        GLSurfaceView videoView = (GLSurfaceView) findViewById(R.id.glview_call);

        // Then we set that view, and pass a Runnable
        // to run once the surface is ready
        VideoRendererGui.setView(videoView, new Runnable() {
            @Override
            public void run() {
                Log.e(Tag, "setView run");
            }
        });

        // Now that VideoRendererGui is ready, we can get our VideoRenderer
        try {
            renderer = VideoRendererGui.createGui(0, 0, 100, 100, RendererCommon.ScalingType.SCALE_ASPECT_FILL, false);

            // And finally, with our VideoRenderer ready, we
            // can add our renderer to the VideoTrack.
            localVideoTrack.addRenderer(renderer);

//            VideoRenderer renderer2 = VideoRendererGui.createGui(20, 20, 50, 50, RendererCommon.ScalingType.SCALE_ASPECT_FILL, false);
//            localVideoTrack.addRenderer(renderer);
//            localVideoTrack.addRenderer(renderer2);
//            VideoRenderer renderer3 = VideoRendererGui.createGui(80, 80, 20, 20, RendererCommon.ScalingType.SCALE_ASPECT_FILL, false);
//            localVideoTrack.addRenderer(renderer3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // We start out with an empty MediaStream object,
        // created with help from our PeerConnectionFactory
        // Note that LOCAL_MEDIA_STREAM_ID can be any string
        MediaStream mediaStream = peerConnectionFactory.createLocalMediaStream(LOCAL_MEDIA_STREAM_ID);

        // Now we can add our tracks.
        mediaStream.addTrack(localVideoTrack);
        mediaStream.addTrack(localAudioTrack);

        ArrayList<PeerConnection.IceServer> list = new ArrayList();
        list.add(new PeerConnection.IceServer("https://192.168.11.23:8080"));

        MediaConstraints constraints = new MediaConstraints();
        constraints.mandatory.add(new MediaConstraints.KeyValuePair("OfferToReceiveAudio", "true"));
        constraints.mandatory.add(new MediaConstraints.KeyValuePair("OfferToReceiveVideo", "true"));
        constraints.optional.add(new MediaConstraints.KeyValuePair("internalSctpDataChannels", "true"));
        constraints.optional.add(new MediaConstraints.KeyValuePair("DtlsSrtpKeyAgreement", "true"));
        peerConnection = peerConnectionFactory.createPeerConnection(list, constraints, new PeerConnection.Observer() {
            @Override
            public void onSignalingChange(PeerConnection.SignalingState signalingState) {
                Log.e(Tag, "onSignalingChange SignalingState=" + signalingState);
            }

            @Override
            public void onIceConnectionChange(PeerConnection.IceConnectionState iceConnectionState) {
                Log.e(Tag, "onIceConnectionChange iceConnectionState=" + iceConnectionState);
            }

            @Override
            public void onIceConnectionReceivingChange(boolean b) {
                Log.e(Tag, "onIceConnectionReceivingChange change=" + b);
            }

            @Override
            public void onIceGatheringChange(PeerConnection.IceGatheringState iceGatheringState) {
                Log.e(Tag, "onIceGatheringChange iceGatheringState=" + iceGatheringState);
            }

            @Override
            public void onIceCandidate(IceCandidate iceCandidate) {
                Log.e(Tag, "onIceCandidate iceCandidate=" + iceCandidate);
            }

            @Override
            public void onAddStream(MediaStream mediaStream) {
                Log.e(Tag, "onAddStream");
            }

            @Override
            public void onRemoveStream(MediaStream mediaStream) {
                Log.e(Tag, "onRemoveStream");
            }

            @Override
            public void onDataChannel(DataChannel dataChannel) {
                Log.e(Tag, "onDataChannel");
            }

            @Override
            public void onRenegotiationNeeded() {
                Log.e(Tag, "onRenegotiationNeeded");
            }
        });

        if (peerConnection != null) {
            peerConnection.addStream(mediaStream);
        }
    }

    private void destroy() {

        if (peerConnectionFactory != null) {
            peerConnectionFactory.dispose();
            peerConnectionFactory = null;
        }

        if (capturer != null) {
            capturer.dispose();
            capturer = null;
        }
        if (videoSource != null) {
            videoSource.stop();
            videoSource.dispose();
            videoSource = null;
        }

        if (localVideoTrack != null) {
            if (renderer != null) {
                localVideoTrack.removeRenderer(renderer);
            }

            localVideoTrack.dispose();
            localVideoTrack = null;
        }

        if (audioSource != null) {
            audioSource.dispose();
            audioSource = null;
        }

        if (localAudioTrack != null) {
            localAudioTrack.dispose();
            localAudioTrack = null;
        }
    }
}
