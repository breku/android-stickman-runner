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
public class MainMenuResourcesManager extends AbstractResourcesManager {

    private static final String TAG = "MainMenuResourcesManager";
    private BuildableBitmapTextureAtlas menuTextureAtlas;

    private TextureRegion menuBackgroundTextureRegion;
    private TextureRegion playButtonTextureRegion;
    private TextureRegion highscoresButtonTextureRegion;

    public MainMenuResourcesManager(BaseGameActivity activity, Camera camera, Engine engine) {
        super(activity, camera, engine);
    }

    public TextureRegion getHighscoresButtonTextureRegion() {
        return highscoresButtonTextureRegion;
    }

    public TextureRegion getMenuBackgroundTextureRegion() {
        return menuBackgroundTextureRegion;
    }

    public TextureRegion getPlayButtonTextureRegion() {
        return playButtonTextureRegion;
    }

    @Override
    public void loadResources() {
        loadMainMenuGraphics();
    }

    @Override
    public void unloadResources() {
        menuTextureAtlas.unload();
    }

    private void loadMainMenuGraphics() {

        if (menuTextureAtlas != null) {
            menuTextureAtlas.load();
            return;
        }

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
        menuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.DEFAULT);

        menuBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "background.jpg");
        playButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "playButton.png");
        highscoresButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "highscoresButton.png");

        try {
            menuTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
            menuTextureAtlas.load();
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            Log.e(TAG, "Error during building atlasses");
        }
    }
}
