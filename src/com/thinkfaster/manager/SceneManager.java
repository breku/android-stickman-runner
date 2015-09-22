package com.thinkfaster.manager;

import android.util.Log;
import com.purplebrain.adbuddiz.sdk.AdBuddiz;
import com.thinkfaster.model.scene.BaseScene;
import com.thinkfaster.model.scene.LoadingScene;
import com.thinkfaster.model.scene.SplashScene;
import com.thinkfaster.model.scene.game.EndGameScene;
import com.thinkfaster.model.scene.game.GameScene;
import com.thinkfaster.model.scene.game.GameTypeScene;
import com.thinkfaster.model.scene.menu.AboutScene;
import com.thinkfaster.model.scene.menu.HighScoreScene;
import com.thinkfaster.model.scene.menu.MainMenuScene;
import com.thinkfaster.util.AppRater;
import com.thinkfaster.util.ContextConstants;
import com.thinkfaster.util.SceneType;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.ui.IGameInterface;
import org.andengine.ui.activity.BaseGameActivity;

import java.util.Random;

/**
 * User: Breku
 * Date: 21.09.13
 */
public class SceneManager {

    private static final SceneManager INSTANCE = new SceneManager();
    private static final String TAG = "SceneManager";
    private BaseGameActivity activity;
    private Random random = new Random();
    private SceneType currentSceneType = SceneType.SPLASH;
    private BaseScene gameScene, menuScene, loadingScene, splashScene, currentScene,
            aboutScene, endGameScene, recordScene, gameTypeScene;
    private boolean musicLoaded;

    public static void prepareManager(BaseGameActivity activity) {
        getInstance().activity = activity;
    }

    public static SceneManager getInstance() {
        return INSTANCE;
    }

    public void createSplashScene(IGameInterface.OnCreateSceneCallback onCreateSceneCallback) {
        ResourcesManager.getInstance().loadSplashScreen();
        splashScene = new SplashScene();
        currentScene = splashScene;
        onCreateSceneCallback.onCreateSceneFinished(splashScene);
    }

    public void createMainMenuScene() {
        ResourcesManager.getInstance().loadMainMenuResources();
        ResourcesManager.getInstance().loadGameTypeResources();
        ResourcesManager.getInstance().loadLoadingResources();
        menuScene = new MainMenuScene();
        loadingScene = new LoadingScene();
        setScene(menuScene);
        disposeSplashScene();
        showAppRating();
    }

    public void setScene(BaseScene scene) {
        ResourcesManager.getInstance().getEngine().setScene(scene);
        currentScene = scene;
        currentSceneType = scene.getSceneType();
    }

    public void loadMenuScene(BaseScene scene) {
        Log.i(TAG, ">> Loading menu scene");
        setScene(loadingScene);
        scene.disposeScene();
        ResourcesManager.getInstance().getEngine().registerUpdateHandler(new TimerHandler(ContextConstants.LOADING_SCENE_TIME, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                ResourcesManager.getInstance().getEngine().unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadMenuTextures();
                ResourcesManager.getInstance().loadGameTypeResources();
                setScene(menuScene);
                Log.i(TAG, "<< Loading menu scene finished");
            }
        }));
    }

    public void loadGameTypeScene() {
        Log.i(TAG, ">> Loading game type scene");
        gameTypeScene = new GameTypeScene();
        setScene(gameTypeScene);
        ResourcesManager.getInstance().unloadMenuTextures();
        showAd();
        Log.i(TAG, "<< Loading game type scene finished");
    }

    public void loadGameScene() {
        Log.i(TAG, ">> Loading game scene");
        setScene(loadingScene);
        ResourcesManager.getInstance().unloadGameTypeTextures();
        ResourcesManager.getInstance().getEngine().registerUpdateHandler(new TimerHandler(ContextConstants.LOADING_SCENE_TIME, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                ResourcesManager.getInstance().getEngine().unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadGameResources();
                gameScene = new GameScene();
                setScene(gameScene);
                Log.i(TAG, "<< Loading game scene finished");
            }
        }));
    }

    public void loadAboutScene() {
        Log.i(TAG, ">> Loading about scene");
        setScene(loadingScene);
        ResourcesManager.getInstance().unloadMenuTextures();
        ResourcesManager.getInstance().getEngine().registerUpdateHandler(new TimerHandler(ContextConstants.LOADING_SCENE_TIME / 2, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                ResourcesManager.getInstance().getEngine().unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadAboutResources();
                aboutScene = new AboutScene();
                setScene(aboutScene);
                Log.i(TAG, "<< Loading about scene finished");
            }
        }));
    }

    public void loadHighScoreSceneFrom(SceneType sceneType) {
        Log.i(TAG, ">> Loading highscore scene from=" + sceneType.name());
        switch (sceneType) {
            case MENU:
                setScene(loadingScene);
                ResourcesManager.getInstance().unloadMenuTextures();
                ResourcesManager.getInstance().getEngine().registerUpdateHandler(new TimerHandler(ContextConstants.LOADING_SCENE_TIME / 2, new ITimerCallback() {
                    @Override
                    public void onTimePassed(TimerHandler pTimerHandler) {
                        ResourcesManager.getInstance().getEngine().unregisterUpdateHandler(pTimerHandler);
                        ResourcesManager.getInstance().loadRecordResources();
                        recordScene = new HighScoreScene();
                        setScene(recordScene);
                    }
                }));
                Log.i(TAG, "<< Loading highscore scene finished");
                break;
            case SINGLE_PLAYER_GAME:
                setScene(loadingScene);
                gameScene.disposeScene();
                ResourcesManager.getInstance().unloadGameTextures();
                ResourcesManager.getInstance().getEngine().registerUpdateHandler(new TimerHandler(ContextConstants.LOADING_SCENE_TIME / 4, new ITimerCallback() {
                    @Override
                    public void onTimePassed(TimerHandler pTimerHandler) {
                        ResourcesManager.getInstance().getEngine().unregisterUpdateHandler(pTimerHandler);
                        ResourcesManager.getInstance().loadRecordResources();
                        recordScene = new HighScoreScene();
                        setScene(recordScene);
                    }
                }));
                Log.i(TAG, "<< Loading highscore scene finished");
                break;
            case ENDGAME:
                ResourcesManager.getInstance().loadRecordResources();
                recordScene = new HighScoreScene();
                setScene(recordScene);
                Log.i(TAG, "<< Loading highscore scene finished");
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }

    public void loadEndGameScene(double score) {
        Log.i(TAG, ">> Loading endgame scene");
        endGameScene = new EndGameScene(score);
        setScene(endGameScene);
        gameScene.disposeScene();
        ResourcesManager.getInstance().unloadGameTextures();
        Log.i(TAG, "<< Loading engame scene finished");
    }

    public BaseScene getCurrentScene() {
        return currentScene;
    }

    public void setMusicLoaded(boolean musicLoaded) {
        this.musicLoaded = musicLoaded;
    }

    private void showAd() {
        AdBuddiz.showAd(activity);
    }

    private void disposeSplashScene() {
        splashScene.disposeScene();
        splashScene = null;
    }

    private void showAppRating() {
        final BaseGameActivity activity = ResourcesManager.getInstance().getActivity();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AppRater.app_launched(activity);
            }
        });
    }
}
