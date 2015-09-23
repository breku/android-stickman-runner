package com.thinkfaster.model.scene;

import com.thinkfaster.manager.IResourcesManager;
import com.thinkfaster.manager.SceneManager;
import com.thinkfaster.manager.resources.HighscoresResourcesManager;
import com.thinkfaster.util.ContextConstants;
import com.thinkfaster.util.SceneType;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

/**
 * User: Breku
 * Date: 06.10.13
 */
public class HighScoreScene extends BaseScene implements IOnSceneTouchListener {

    private HighscoresResourcesManager resourcesManager;

    public HighScoreScene(IResourcesManager resourcesManager) {
        super(resourcesManager);
        this.resourcesManager = (HighscoresResourcesManager) resourcesManager;
    }

    @Override
    public void createScene() {
        createBackground();
        setOnSceneTouchListener(this);
    }

    @Override
    public void onBackKeyPressed() {
        SceneManager.getInstance().loadMenuScene(this);
    }

    @Override
    public SceneType getSceneType() {
        return SceneType.RECORDS;
    }

    @Override
    public void disposeScene() {
        resourcesManager.unloadResources();
    }

    @Override
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
        if (pSceneTouchEvent.isActionUp()) {
            SceneManager.getInstance().loadMenuScene(this);
        }
        return false;
    }

    private void createBackground() {
        attachChild(new Sprite(ContextConstants.SCREEN_WIDTH / 2, ContextConstants.SCREEN_HEIGHT / 2,
                resourcesManager.getRecordBackgroundTextureRegion(), vertexBufferObjectManager));
    }
}
