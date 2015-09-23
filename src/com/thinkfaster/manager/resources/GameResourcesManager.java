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
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

/**
 * Created by brekol on 22.09.15.
 */
public class GameResourcesManager extends AbstractResourcesManager {

    private static final String TAG = "GameResourceManager";

    private BuildableBitmapTextureAtlas gameTextureAtlas;
    private ITiledTextureRegion playerTiledTextureRegion;
    private TextureRegion buttonTextureRegion;

    public GameResourcesManager(BaseGameActivity activity, Camera camera, Engine engine) {
        super(activity, camera, engine);
    }

    public TextureRegion getButtonTextureRegion() {
        return buttonTextureRegion;
    }

    @Override
    public void loadResources() {
        loadGameGraphics();
        loadFonts();
    }

    @Override
    public void unloadResources() {
        gameTextureAtlas.unload();
    }

    public ITiledTextureRegion getPlayerTiledTextureRegion() {
        return playerTiledTextureRegion;
    }

    private void loadGameGraphics() {

        if (gameTextureAtlas != null) {
            gameTextureAtlas.load();
            return;
        }
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game/");

        gameTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 2048, 2048, TextureOptions.DEFAULT);

        playerTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "runnerspritesheet.png", 9, 1);
        buttonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "goldStar.png");
        loadGameAtlases();
    }

    private void loadGameAtlases() {
        try {
            gameTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
            gameTextureAtlas.load();
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            Log.e(TAG, "Error during building atlasses");
        }
    }
}
