package com.thinkfaster.model.scene.game;

import android.util.Log;
import com.thinkfaster.handler.TimeUpdateHandler;
import com.thinkfaster.manager.ResourcesManager;
import com.thinkfaster.manager.SceneManager;
import com.thinkfaster.util.ContextConstants;
import com.thinkfaster.util.SceneType;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * User: Breku
 * Date: 21.09.13
 */
public class GameScene extends AbstractGameScene {

    private static final String TAG = "GameScene";

    private HUD gameHUD;
    private Text timerText;

    /**
     * @param objects objects[0] - levelDifficulty
     *                objects[1] - mathParameter
     *                objects[2] - multiplayer
     */
    public GameScene(Object... objects) {
        super(objects);
    }

    @Override
    public void initializeServices() {

    }

    @Override
    public void createScene(Object... objects) {
        clear();
        init(objects);
        createBackground();
        createHUD();
        registerUpdateHandlers();
    }

    @Override
    public void onBackKeyPressed() {
        SceneManager.getInstance().loadMenuScene(this);
    }

    @Override
    public SceneType getSceneType() {
        return SceneType.SINGLE_PLAYER_GAME;
    }

    @Override
    public void disposeScene() {
        gameHUD.clearChildScene();
        camera.setHUD(null);
        camera.setCenter(ContextConstants.SCREEN_WIDTH / 2, ContextConstants.SCREEN_HEIGHT / 2);
        camera.setChaseEntity(null);
        ResourcesManager.getInstance().unloadGameTextures();
    }

    private void clear() {
        clearChildScene();
        clearUpdateHandlers();
        clearTouchAreas();
    }

    private void registerUpdateHandlers() {
        final List<IUpdateHandler> updateHandlerList = new LinkedList<>();
        updateHandlerList.add(new TimeUpdateHandler(timerText));

        for (IUpdateHandler iUpdateHandler : updateHandlerList) {
            registerUpdateHandler(iUpdateHandler);
        }

        Log.i(TAG, ">> Registered handlers: " + updateHandlerList);
    }

    private void init(Object... objects) {

    }

    private void createBackground() {

        setBackground(new Background(Color.WHITE));
    }

    private void createHUD() {
        gameHUD = new HUD();

        Text timeText = new Text(380, 440, resourcesManager.getBlackFont(), "Time: ", new TextOptions(HorizontalAlign.LEFT), vertexBufferObjectManager);
        timerText = new Text(490, 440, resourcesManager.getBlackFont(), "0123456789", 30, new TextOptions(HorizontalAlign.CENTER), vertexBufferObjectManager);
        timerText.setText("00.00");

        gameHUD.attachChild(timeText);
        gameHUD.attachChild(timerText);

        camera.setHUD(gameHUD);
    }

    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {
        super.onManagedUpdate(pSecondsElapsed);

        sortChildren();
    }
}
