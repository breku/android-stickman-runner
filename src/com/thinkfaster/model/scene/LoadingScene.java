package com.thinkfaster.model.scene;

import android.util.Log;
import com.thinkfaster.manager.ResourcesManager;
import com.thinkfaster.util.ContextConstants;
import com.thinkfaster.util.SceneType;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Breku
 * Date: 21.09.13
 */
public class LoadingScene extends BaseScene {

    private static final String TAG = "LoadingScene";
    private float runningTime;

    @Override
    public void createScene(Object... objects) {
        clear();
        createBackground();
        createLoadingText();
        createLoadingTextDots();
    }

    @Override
    public void onBackKeyPressed() {
        return;
    }

    @Override
    public SceneType getSceneType() {
        return SceneType.LOADING;
    }

    @Override
    public void disposeScene() {
    }

    private void createLoadingTextDots() {
        runningTime = 0;

        final List<Text> dotList = new ArrayList<>();

        dotList.add(createDotText(85));
        dotList.add(createDotText(93));
        dotList.add(createDotText(101));

        registerUpdateHandler(new IUpdateHandler() {
            private int arrayIndex = 0;

            @Override
            public void onUpdate(float pSecondsElapsed) {
                runningTime += pSecondsElapsed;
                if (runningTime > 0.3f) {
                    for (int i = 0; i < dotList.size(); i++) {
                        dotList.get(i).setVisible(i <= arrayIndex % 3);
                    }
                    arrayIndex++;
                    runningTime = 0;
                }
            }

            @Override
            public void reset() {

            }
        });

        for (Text text : dotList) {
            attachChild(text);
        }
    }

    private Text createDotText(final int xOffset) {
        return new Text(ContextConstants.SCREEN_WIDTH / 2 + xOffset, ContextConstants.SCREEN_HEIGHT / 2, ResourcesManager.getInstance().getChalkFont(),
                ".", 3, new TextOptions(HorizontalAlign.LEFT), vertexBufferObjectManager);
    }

    private void createLoadingText() {
        attachChild(new Text(ContextConstants.SCREEN_WIDTH / 2, ContextConstants.SCREEN_HEIGHT / 2, ResourcesManager.getInstance().getChalkFont(),
                "loading", vertexBufferObjectManager));
    }

    private void clear() {
        clearEntityModifiers();
        clearTouchAreas();
        clearUpdateHandlers();
        clearChildScene();
    }

    private void createBackground() {
        setBackground(new Background(Color.WHITE));
    }
}
