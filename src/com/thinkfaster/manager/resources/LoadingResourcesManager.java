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
public class LoadingResourcesManager extends AbstractResourcesManager {

    private BitmapTextureAtlas loadingTextureAtlas;
    private TextureRegion loadingTextureRegion;

    public LoadingResourcesManager(BaseGameActivity activity, Camera camera, Engine engine) {
        super(activity, camera, engine);
    }

    @Override
    public void loadResources() {
        loadGraphics();
        loadFonts();
    }

    @Override
    public void unloadResources() {
        loadingTextureAtlas.unload();
    }

    public TextureRegion getLoadingTextureRegion() {
        return loadingTextureRegion;
    }

    public void loadGraphics() {
        if (loadingTextureAtlas != null) {
            loadingTextureAtlas.load();
            return;
        }

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/loading/");
        loadingTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
        loadingTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(loadingTextureAtlas, activity, "background.png", 0, 0);
        loadingTextureAtlas.load();
    }
}
