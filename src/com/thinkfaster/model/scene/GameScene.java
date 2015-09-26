package com.thinkfaster.model.scene;

import android.hardware.SensorManager;
import android.util.Log;
import com.badlogic.gdx.math.Vector2;
import com.thinkfaster.factory.GameFactory;
import com.thinkfaster.handler.TimeUpdateHandler;
import com.thinkfaster.manager.IResourcesManager;
import com.thinkfaster.manager.SceneManager;
import com.thinkfaster.manager.resources.GameResourcesManager;
import com.thinkfaster.model.shape.GameButton;
import com.thinkfaster.model.shape.Player;
import com.thinkfaster.util.ContextConstants;
import com.thinkfaster.util.SceneType;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.extension.debugdraw.DebugRenderer;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * User: Breku
 * Date: 21.09.13
 */
public class GameScene extends BaseScene {

    private static final String TAG = "GameScene";

    private HUD gameHUD;
    private Text timerText;
    private Text frameRateText;
    private GameResourcesManager resourcesManager;
    private GameFactory gameFactory;
    private Player player;
    private GameButton buttonPlus;
    private GameButton buttonMinus;
    private int frameRate = 50;
    private DebugRenderer debugRenderer;

    private PhysicsWorld physicsWorld;

    public GameScene(IResourcesManager resourcesManager) {
        super(resourcesManager);
        this.resourcesManager = (GameResourcesManager) resourcesManager;
    }

    @Override
    public void createScene() {
        clear();
        init();
        createBackground();
        createPlayer();
        createButtons();
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
        resourcesManager.unloadResources();
    }

    private void createButtons() {
        buttonMinus = gameFactory.createGameButton(100, 100);
        buttonPlus = gameFactory.createGameButton(700, 100);
        registerTouchArea(buttonPlus);
        registerTouchArea(buttonMinus);
        attachChild(buttonPlus);
        attachChild(buttonMinus);
    }

    private void createPlayer() {
        player = gameFactory.createPlayer();
        attachChild(player);
    }

    private void clear() {
        clearChildScene();
        clearUpdateHandlers();
        clearTouchAreas();
        clearEntityModifiers();
    }

    private void registerUpdateHandlers() {
        final List<IUpdateHandler> updateHandlerList = new LinkedList<>();
        updateHandlerList.add(new TimeUpdateHandler(timerText));
        updateHandlerList.add(physicsWorld);

        for (IUpdateHandler iUpdateHandler : updateHandlerList) {
            registerUpdateHandler(iUpdateHandler);
        }

        Log.i(TAG, ">> Registered handlers: " + updateHandlerList);
    }

    private void init() {
        physicsWorld = new PhysicsWorld(new Vector2(0, SensorManager.GRAVITY_EARTH), false);
        gameFactory = new GameFactory(resourcesManager, physicsWorld);
        debugRenderer = new DebugRenderer(physicsWorld, resourcesManager.getVertexBufferObjectManager());
        attachChild(debugRenderer);
    }

    private void createBackground() {

        setBackground(new Background(Color.WHITE));
    }

    private void createHUD() {
        gameHUD = new HUD();

        Text timeText = new Text(380, 440, resourcesManager.getFont(android.graphics.Color.BLACK), "Time: ", new TextOptions(HorizontalAlign.LEFT), vertexBufferObjectManager);
        timerText = new Text(490, 440, resourcesManager.getFont(android.graphics.Color.BLACK), "0123456789", 30, new TextOptions(HorizontalAlign.CENTER), vertexBufferObjectManager);
        frameRateText = new Text(100, 440, resourcesManager.getFont(android.graphics.Color.BLACK), "0123456789", 30, new TextOptions(HorizontalAlign.CENTER), vertexBufferObjectManager);
        timerText.setText("00.00");
        frameRateText.setText(String.valueOf(frameRate));

        gameHUD.attachChild(timeText);
        gameHUD.attachChild(timerText);
        gameHUD.attachChild(frameRateText);

        camera.setHUD(gameHUD);
    }

    private void handleButtons() {

        if (buttonMinus.isClicked()) {
            buttonMinus.setClicked(false);
            frameRate -= 5;
            player.animate(frameRate);
            frameRateText.setText(String.valueOf(frameRate));
        }

        if (buttonPlus.isClicked()) {
            buttonMinus.setClicked(false);
            player.startRunning();
        }
    }

    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {
        super.onManagedUpdate(pSecondsElapsed);

        handleButtons();

        sortChildren();
    }
}
