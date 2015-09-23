package com.thinkfaster.manager;

import android.util.Log;
import com.purplebrain.adbuddiz.sdk.AdBuddiz;
import com.thinkfaster.manager.resources.MainMenuResourcesManager;
import com.thinkfaster.model.scene.*;
import com.thinkfaster.util.AppRater;
import com.thinkfaster.util.ContextConstants;
import com.thinkfaster.util.SceneType;
import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.ui.IGameInterface;
import org.andengine.ui.activity.BaseGameActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Breku
 * Date: 21.09.13
 */
public class SceneManager {

    private static final SceneManager INSTANCE = new SceneManager();
    private static final String TAG = "SceneManager";
    private final Map<SceneType, IResourcesManager> resourceManagerMap = new HashMap<>();
    private BaseGameActivity activity;
    private BaseScene gameScene, menuScene, loadingScene, splashScene, currentScene,
            recordScene;
    private Engine engine;

    public static void prepareManager(final BaseGameActivity activity, Engine engine) {
        INSTANCE.activity = activity;
        INSTANCE.engine = engine;
    }

    public static void addResourceManager(final SceneType sceneType, final IResourcesManager resourceManager) {
        INSTANCE.resourceManagerMap.put(sceneType, resourceManager);
    }

    public static SceneManager getInstance() {
        return INSTANCE;
    }

    public void createSplashScene(IGameInterface.OnCreateSceneCallback onCreateSceneCallback) {
        resourceManagerMap.get(SceneType.SPLASH).loadResources();
        splashScene = new SplashScene(resourceManagerMap.get(SceneType.SPLASH));
        splashScene.createScene();
        currentScene = splashScene;
        onCreateSceneCallback.onCreateSceneFinished(splashScene);
    }

    public void createMainMenuScene() {
        final MainMenuResourcesManager menuResourcesManager = (MainMenuResourcesManager)resourceManagerMap.get(SceneType.MENU);
        final IResourcesManager loadingResourcesManger = resourceManagerMap.get(SceneType.LOADING);

        menuResourcesManager.loadResources();
        menuResourcesManager.loadFonts();
        loadingResourcesManger.loadResources();

        menuScene = new MainMenuScene(menuResourcesManager);
        menuScene.createScene();
        loadingScene = new LoadingScene(loadingResourcesManger);
        loadingScene.createScene();
        setScene(menuScene);
        disposeSplashScene();
        showAppRating();
    }

    public void setScene(BaseScene scene) {
        engine.setScene(scene);
        currentScene = scene;
    }

    public void loadMenuScene(BaseScene scene) {
        Log.i(TAG, ">> Loading menu scene");
        setScene(loadingScene);
        scene.disposeScene();
        engine.registerUpdateHandler(new TimerHandler(ContextConstants.LOADING_SCENE_TIME, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                engine.unregisterUpdateHandler(pTimerHandler);
                resourceManagerMap.get(SceneType.MENU).loadResources();
                setScene(menuScene);
                Log.i(TAG, "<< Loading menu scene finished");
            }
        }));
    }

    public void loadGameScene() {
        Log.i(TAG, ">> Loading game scene");
        setScene(loadingScene);
        engine.registerUpdateHandler(new TimerHandler(ContextConstants.LOADING_SCENE_TIME, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                engine.unregisterUpdateHandler(pTimerHandler);
                resourceManagerMap.get(SceneType.SINGLE_PLAYER_GAME).loadResources();
                gameScene = new GameScene(resourceManagerMap.get(SceneType.SINGLE_PLAYER_GAME));
                gameScene.createScene();
                setScene(gameScene);
                Log.i(TAG, "<< Loading game scene finished");
            }
        }));
    }

    public void loadHighScoreSceneFrom() {
        Log.i(TAG, ">> Loading highscore scene ");
        setScene(loadingScene);
        engine.registerUpdateHandler(new TimerHandler(ContextConstants.LOADING_SCENE_TIME / 2, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                engine.unregisterUpdateHandler(pTimerHandler);
                resourceManagerMap.get(SceneType.RECORDS).loadResources();
                recordScene = new HighScoreScene(resourceManagerMap.get(SceneType.RECORDS));
                recordScene.createScene();
                setScene(recordScene);
            }
        }));
        Log.i(TAG, "<< Loading highscore scene finished");
    }

    public BaseScene getCurrentScene() {
        return currentScene;
    }


    private void showAd() {
        AdBuddiz.showAd(activity);
    }

    private void disposeSplashScene() {
        splashScene.disposeScene();
        splashScene = null;
    }

    private void showAppRating() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AppRater.app_launched(activity);
            }
        });
    }
}
