package com.sineva;

import com.roslibrary.ros.entity.PublishEvent;

/**
 * Created by Eligah on 2017/9/4.
 */

public interface EventInterface {
    public abstract void onEvent(String data);
    public abstract void onEvent(PublishEvent data);
}
