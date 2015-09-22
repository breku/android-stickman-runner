package com.thinkfaster.handler;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.text.Text;

/**
 * Created by brekol on 19.09.15.
 */
public class TimeUpdateHandler implements IUpdateHandler {

    private final Text timerText;

    private int firstRunCounter = 0;
    private long startTime;

    public TimeUpdateHandler(Text timerText) {
        this.timerText = timerText;
    }

    @Override
    public void onUpdate(float pSecondsElapsed) {
        if (firstRunCounter++ == 1) {
            startTime = System.currentTimeMillis();
        }
        updateTime();
    }

    @Override
    public void reset() {

    }

    private void updateTime() {
        long elapsedTime = (System.currentTimeMillis() - startTime) / 10;
        timerText.setText(elapsedTime / 100 + "." + (elapsedTime % 100) / 10);
    }
}
