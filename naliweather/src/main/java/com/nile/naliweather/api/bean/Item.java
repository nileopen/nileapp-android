package com.nile.naliweather.api.bean;

/**
 * @actor:taotao
 * @DATE: 16/9/2
 */
public abstract class Item {
    public final static byte ITEM_1 = 1;
    public final static byte ITEM_2 = 2;
    public final static byte[] TYPES = {
            ITEM_1, ITEM_2
    };
    public final static int TYPE_COUNT = TYPES.length;
    private byte type;

    public Item(byte type) {
        this.type = type;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }
}
