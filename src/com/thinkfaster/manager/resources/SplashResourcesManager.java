package com.thinkfaster.manager.resources;

import com.thinkfaster.manager.AbstractResourcesManager;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

/**
 * Created by brekol on 22.09.15.
 */
public class SplashResourcesManager extends AbstractResourcesManager {

    private BitmapTextureAtlas splashTextureAtlas;
    private TextureRegion splashTextureRegion;

    public SplashResourcesManager(BaseGameActivity activity, Camera camera, Engine engine) {
        super(activity, camera, engine);
    }

    @Override
    public void loadResources() {
        loadSplashScreen();
    }

    @Override
    public void unloadResources() {
        splashTextureAtlas.unload();
    }

    public TextureRegion getSplashTextureRegion() {
        return splashTextureRegion;
    }

    public void loadSplashScreen() {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        splashTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
        splashTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, activity, "splash.jpg", 0, 0);
        splashTextureAtlas.load();
    }
}
