package com.thinkfaster.manager;

import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;

/**
 * Created by brekol on 22.09.15.
 */
public interface IResourcesManager {

    void loadResources();

    void unloadResources();

    VertexBufferObjectManager getVertexBufferObjectManager();

    BaseGameActivity getActivity();

    Camera getCamera();

    Font getFont(int color);
}
