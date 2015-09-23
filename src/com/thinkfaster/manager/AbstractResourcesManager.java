package com.thinkfaster.manager;

import android.graphics.Color;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by brekol on 22.09.15.
 */
public abstract class AbstractResourcesManager implements IResourcesManager {

    protected final BaseGameActivity activity;
    protected final Camera camera;
    protected final Engine engine;
    protected final VertexBufferObjectManager vertexBufferObjectManager;
    private Map<Integer, Font> fontMap;

    protected AbstractResourcesManager(BaseGameActivity activity, Camera camera, Engine engine) {
        this.activity = activity;
        this.camera = camera;
        this.engine = engine;
        this.vertexBufferObjectManager = activity.getVertexBufferObjectManager();
    }

    public void loadFonts() {
        if (fontMap != null) {
            return;
        }
        fontMap = new HashMap<>();

        fontMap.put(Color.WHITE, loadFont("ChalkPaint.ttf", Color.WHITE));
        fontMap.put(Color.BLACK, loadFont("ChalkPaint.ttf", Color.BLACK));
        fontMap.put(Color.GREEN, loadFont("ChalkPaint.ttf", Color.GREEN));
        fontMap.put(Color.RED, loadFont("font1.ttf", Color.RED));
    }

    public Font getFont(int color) {
        return fontMap.get(color);
    }

    @Override
    public VertexBufferObjectManager getVertexBufferObjectManager() {
        return vertexBufferObjectManager;
    }

    @Override
    public BaseGameActivity getActivity() {
        return activity;
    }

    @Override
    public Camera getCamera() {
        return camera;
    }

    private Font loadFont(String fontName, int color) {
        FontFactory.setAssetBasePath("font/");

        final BitmapTextureAtlas textureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 512, 512, TextureOptions.BILINEAR);
        final Font font = FontFactory.createStrokeFromAsset(activity.getFontManager(), textureAtlas, activity.getAssets(), fontName, 50, true, color, 2, color);
        textureAtlas.load();
        font.load();
        return font;
    }
}
