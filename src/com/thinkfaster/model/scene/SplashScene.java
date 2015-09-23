package com.thinkfaster.model.scene;

import com.thinkfaster.manager.IResourcesManager;
import com.thinkfaster.manager.resources.SplashResourcesManager;
import com.thinkfaster.util.SceneType;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

/**
 * User: Breku
 * Date: 21.09.13
 */
public class SplashScene extends BaseScene {

    private Sprite sprite;
    private SplashResourcesManager resourceManager;

    public SplashScene(IResourcesManager resourceManager) {
        super(resourceManager);
        this.resourceManager = (SplashResourcesManager) resourceManager;
    }

    @Override
    public void createScene() {
        sprite = new Sprite(0, 0, resourceManager.getSplashTextureRegion(), vertexBufferObjectManager) {
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
        };
        sprite.setPosition(400, 240);
        attachChild(sprite);
    }

    @Override
    public void onBackKeyPressed() {
        return;
    }

    @Override
    public SceneType getSceneType() {
        return SceneType.SPLASH;
    }

    @Override
    public void disposeScene() {
        resourceManager.unloadResources();
        sprite.detachSelf();
        sprite.dispose();
        this.detachSelf();
        this.dispose();
    }
}
