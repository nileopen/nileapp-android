package com.nalib.fwk.camera;

/**
 * Created by taotao on 16/7/14.
 */
public class NaCameraEngine implements ICameraEngine{
    private ICameraEvent mEvent;
    @Override
    public void setCameraEvent(ICameraEvent event) {
        this.mEvent = event;
    }
}
