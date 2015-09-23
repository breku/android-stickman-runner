package com.thinkfaster.manager.resources;

import android.util.Log;
import com.thinkfaster.manager.AbstractResourcesManager;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

/**
 * Created by brekol on 22.09.15.
 */
public class HighscoresResourcesManager extends AbstractResourcesManager {

    private static final String TAG = "HighscoresResourceManager";
    private BuildableBitmapTextureAtlas recordTextureAtlas;
    private TextureRegion recordBackgroundTextureRegion;

    public HighscoresResourcesManager(BaseGameActivity activity, Camera camera, Engine engine) {
        super(activity, camera, engine);
    }

    @Override
    public void loadResources() {
        loadGraphics();
    }

    @Override
    public void unloadResources() {
        recordTextureAtlas.unload();
    }

    public TextureRegion getRecordBackgroundTextureRegion() {
        return recordBackgroundTextureRegion;
    }

    private void loadGraphics() {
        if (recordTextureAtlas != null) {
            recordTextureAtlas.load();
        }

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/highscore/");

        recordTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.DEFAULT);
        recordBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(recordTextureAtlas, activity, "background.png");

        try {
            recordTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
            recordTextureAtlas.load();
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            Log.e(TAG, "Error during building atlasses");
        }
    }
}
