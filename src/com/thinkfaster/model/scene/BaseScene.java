package com.thinkfaster.model.scene;

import android.app.Activity;
import com.thinkfaster.manager.IResourcesManager;
import com.thinkfaster.util.SceneType;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * User: Breku
 * Date: 21.09.13
 */
public abstract class BaseScene extends Scene {

    protected Activity activity;
    protected VertexBufferObjectManager vertexBufferObjectManager;
    protected Camera camera;

    public BaseScene(IResourcesManager resourcesManager) {
        this.activity = resourcesManager.getActivity();
        this.vertexBufferObjectManager = resourcesManager.getVertexBufferObjectManager();
        this.camera = resourcesManager.getCamera();
    }

    public abstract void createScene();

    public abstract void onBackKeyPressed();

    public abstract SceneType getSceneType();

    public abstract void disposeScene();
}
