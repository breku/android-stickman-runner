package com.thinkfaster.manager;

import android.graphics.Color;
import android.util.Log;
import com.thinkfaster.util.ContextConstants;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Breku
 * Date: 21.09.13
 */
public class ResourcesManager {

    private static final ResourcesManager INSTANCE = new ResourcesManager();
    private static final String TAG = "ResourcesManager";
    private BaseGameActivity activity;
    private Engine engine;
    private Camera camera;
    private VertexBufferObjectManager vertexBufferObjectManager;
    private BitmapTextureAtlas splashTextureAtlas, menuFontTextureAtlas, gameFontTextureAtlas, greenFontTextureAtlas,
            chalkFontTextureAtlas, loadingTextureAtlas;
    private BuildableBitmapTextureAtlas menuTextureAtlas, aboutTextureAtlas, endGameTextureAtlas,
            recordTextureAtlas, gameTypeTextureAtlas, gameTextureAtlas;
    // Game
    private List<ITiledTextureRegion> animalTiledTextureRegionList;
    private List<BuildableBitmapTextureAtlas> gameTextureAtlasList;
    private TextureRegion questionMarkGameTextureRegion;
    // Splash
    private ITextureRegion splashTextureRegion;
    // Menu
    private ITextureRegion menuBackgroundTextureRegion, playButtonTextureRegion, hexagonTextureRegion, highscoresButtonTextureRegion;
    private ITiledTextureRegion soundButtonsTiledTextureRegion;
    // Help
    private ITextureRegion aboutBackgroundTextureRegion;
    // EndGame
    private ITextureRegion endGameBackgroundTextureRegion;
    // HighScore
    private ITextureRegion recordBackgroundTextureRegion, buttonHighScoreTextureRegion,
            highscoreSmallGameTypeTextureRegion, highscoreMediumGameTypeTextureRegion, highscoreBigGameTypeTextureRegion;
    ;
    //Loading
    private ITextureRegion loadingTextureRegion;
    // Game Type
    private ITextureRegion backgroundGameTypeTextureRegion, smallGameTypeTextureRegion, mediumGameTypeTextureRegion, bigGameTypeTextureRegion;

    private List<Sound> animalSoundList = new ArrayList<>();

    private Sound startGameSound;
    private Font whiteFont, blackFont, greenFont, chalkFont;

    public static void prepareManager(Engine engine, BaseGameActivity activity, Camera camera, VertexBufferObjectManager vertexBufferObjectManager) {
        getInstance().engine = engine;
        getInstance().activity = activity;
        getInstance().camera = camera;
        getInstance().vertexBufferObjectManager = vertexBufferObjectManager;
    }

    public static ResourcesManager getInstance() {
        return INSTANCE;
    }

    public List<Sound> getAnimalSoundList() {
        return animalSoundList;
    }

    public TextureRegion getQuestionMarkGameTextureRegion() {
        return questionMarkGameTextureRegion;
    }

    public ITextureRegion getHexagonTextureRegion() {
        return hexagonTextureRegion;
    }

    public void loadAboutResources() {
        loadAboutGraphics();
    }

    public void loadMainMenuResources() {
        loadMainMenuGraphics();
        loadMainMenuSounds();
        loadWhiteFont();
        loadBlackFont();
        loadGreenFont();
        loadChalkFont();
    }

    public void loadGameResources() {
        loadGameGraphics();
        loadEndGameResources();
    }

    public void loadEndGameResources() {
        loadEndGameGraphics();
    }

    public Engine getEngine() {
        return engine;
    }

    public void loadRecordResources() {
        loadHighScoreGraphics();
    }

    public void loadGameTypeResources() {
        loadGameTypeGraphics();
    }

    public void loadSplashScreen() {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        splashTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
        splashTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, activity, "splash.jpg", 0, 0);
        splashTextureAtlas.load();
    }

    public void loadMenuTextures() {
        menuTextureAtlas.load();
    }

    public void unloadSplashScreen() {
        splashTextureAtlas.unload();
        splashTextureRegion = null;
    }

    public void unloadAboutTextures() {
        aboutTextureAtlas.unload();
    }

    public void unloadEndGameTextures() {
        endGameTextureAtlas.unload();
    }

    public void unloadRecordsTextures() {
        recordTextureAtlas.unload();
    }

    public void unloadGameTypeTextures() {
        gameTypeTextureAtlas.unload();
    }

    public void unloadGameTextures() {
        gameTextureAtlas.unload();
        for (BuildableBitmapTextureAtlas atlas : gameTextureAtlasList) {
            atlas.unload();
        }
    }

    public List<ITiledTextureRegion> getAnimalTiledTextureRegionList() {
        return animalTiledTextureRegionList;
    }

    public void unloadMenuTextures() {
        menuTextureAtlas.unload();
    }

    public BaseGameActivity getActivity() {
        return activity;
    }

    public Camera getCamera() {
        return camera;
    }

    public VertexBufferObjectManager getVertexBufferObjectManager() {
        return vertexBufferObjectManager;
    }

    public ITextureRegion getSplashTextureRegion() {
        return splashTextureRegion;
    }

    public ITextureRegion getMenuBackgroundTextureRegion() {
        return menuBackgroundTextureRegion;
    }

    public ITextureRegion getAboutBackgroundTextureRegion() {
        return aboutBackgroundTextureRegion;
    }

    public Font getWhiteFont() {
        return whiteFont;
    }

    public Font getBlackFont() {
        return blackFont;
    }

    public ITextureRegion getHighscoreBigGameTypeTextureRegion() {
        return highscoreBigGameTypeTextureRegion;
    }

    public ITextureRegion getHighscoreMediumGameTypeTextureRegion() {
        return highscoreMediumGameTypeTextureRegion;
    }

    public ITextureRegion getHighscoreSmallGameTypeTextureRegion() {
        return highscoreSmallGameTypeTextureRegion;
    }

    public Font getGreenFont() {
        return greenFont;
    }

    public Font getChalkFont() {
        return chalkFont;
    }

    public ITextureRegion getEndGameBackgroundTextureRegion() {
        return endGameBackgroundTextureRegion;
    }

    public ITextureRegion getRecordBackgroundTextureRegion() {
        return recordBackgroundTextureRegion;
    }

    public ITextureRegion getButtonHighScoreTextureRegion() {
        return buttonHighScoreTextureRegion;
    }

    public ITextureRegion getBackgroundGameTypeTextureRegion() {
        return backgroundGameTypeTextureRegion;
    }

    public ITextureRegion getSmallGameTypeTextureRegion() {
        return smallGameTypeTextureRegion;
    }

    public ITextureRegion getMediumGameTypeTextureRegion() {
        return mediumGameTypeTextureRegion;
    }

    public ITextureRegion getBigGameTypeTextureRegion() {
        return bigGameTypeTextureRegion;
    }

    public ITextureRegion getLoadingTextureRegion() {
        return loadingTextureRegion;
    }

    public ITiledTextureRegion getSoundButtonsTiledTextureRegion() {
        return soundButtonsTiledTextureRegion;
    }

    public void loadLoadingResources() {
        if (loadingTextureAtlas != null) {
            loadingTextureAtlas.load();
            return;
        }

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/loading/");
        loadingTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
        loadingTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(loadingTextureAtlas, activity, "background.png", 0, 0);
        loadingTextureAtlas.load();
    }

    public ITiledTextureRegion getAnimalTiledTexture(int animalId) {
        return animalTiledTextureRegionList.get(animalId);
    }

    public ITextureRegion getPlayButtonTextureRegion() {
        return playButtonTextureRegion;
    }

    public ITextureRegion getHighscoresButtonTextureRegion() {
        return highscoresButtonTextureRegion;
    }

    public Sound getStartGameSound() {
        return startGameSound;
    }

    private void loadAboutGraphics() {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/about/");
        aboutTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);

        aboutBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(aboutTextureAtlas, activity, "background.jpg");

        try {
            aboutTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
            aboutTextureAtlas.load();
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            Log.e(TAG, "Error during building atlasses");
        }
    }

    private void loadMainMenuGraphics() {

        if (menuTextureAtlas != null) {
            menuTextureAtlas.load();
            return;
        }

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
        menuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.DEFAULT);

        menuBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "background.jpg");
        buttonHighScoreTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "menu_high.png");
        playButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "playButton.png");
        hexagonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "hexagon.png");
        highscoresButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "highscoresButton.png");
        soundButtonsTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(menuTextureAtlas, activity, "soundButtons.png", 2, 1);

        try {
            menuTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
            menuTextureAtlas.load();
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            Log.e(TAG, "Error during building atlasses");
        }
    }

    private void loadWhiteFont() {
        if (menuFontTextureAtlas != null) {
            return;
        }
        FontFactory.setAssetBasePath("font/");
        menuFontTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 512, 512, TextureOptions.BILINEAR);
        whiteFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), menuFontTextureAtlas, activity.getAssets(), "ChalkPaint.ttf", 50, true, Color.WHITE, 2, Color.WHITE);
        menuFontTextureAtlas.load();
        whiteFont.load();
    }

    private void loadBlackFont() {
        if (gameFontTextureAtlas != null) {
            return;
        }
        FontFactory.setAssetBasePath("font/");
        gameFontTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 512, 512, TextureOptions.BILINEAR);
        blackFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), gameFontTextureAtlas, activity.getAssets(), "font1.ttf", 30, true, Color.BLACK, 1, Color.BLACK);
        gameFontTextureAtlas.load();
        blackFont.load();
    }

    private void loadGreenFont() {
        if (greenFontTextureAtlas != null) {
            return;
        }
        FontFactory.setAssetBasePath("font/");
        greenFontTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 512, 512, TextureOptions.BILINEAR);
        greenFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), greenFontTextureAtlas, activity.getAssets(), "ChalkPaint.ttf", 50, true, Color.GREEN, 2, Color.GREEN);
        greenFontTextureAtlas.load();
        greenFont.load();
    }

    private void loadChalkFont() {
        if (chalkFontTextureAtlas != null) {
            return;
        }
        FontFactory.setAssetBasePath("font/");
        chalkFontTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 512, 512, TextureOptions.BILINEAR);
        chalkFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), chalkFontTextureAtlas, activity.getAssets(), "ChalkPaint.ttf", 50, true, Color.BLACK, 2, Color.BLACK);
        chalkFontTextureAtlas.load();
        chalkFont.load();
    }

    private void loadGameGraphics() {

        if (gameTextureAtlas != null) {
            gameTextureAtlas.load();
            for (BuildableBitmapTextureAtlas atlas : gameTextureAtlasList) {
                atlas.load();
            }
            return;
        }

        gameTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.DEFAULT);
        gameTextureAtlasList = new ArrayList<>();
        for (int i = 0; i < ContextConstants.NUMBER_OF_ANIMALS; i++) {
            gameTextureAtlasList.add(new BuildableBitmapTextureAtlas(activity.getTextureManager(), 512, 512, TextureOptions.DEFAULT));
        }

        loadOterGameTextures();
        loadAnimalsTextures();
        loadGameAtlases();
    }

    private void loadOterGameTextures() {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game/");

        questionMarkGameTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "questionItem.png");
    }

    private void loadAnimalsTextures() {
        animalTiledTextureRegionList = new ArrayList<>();
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game/animals/");

        for (int i = 0; i < ContextConstants.NUMBER_OF_ANIMALS; i++) {
            animalTiledTextureRegionList.add(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlasList.get(i), activity, i + ".jpg", 2, 2));
        }
    }

    private void loadGameAtlases() {
        try {
            for (int i = 0; i < ContextConstants.NUMBER_OF_ANIMALS; i++) {
                gameTextureAtlasList.get(i).build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
                gameTextureAtlasList.get(i).load();
            }
            gameTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
            gameTextureAtlas.load();
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            Log.e(TAG, "Error during building atlasses");
        }
    }

    private void loadMainMenuSounds() {
        if (startGameSound != null) {
            return;
        }

        SoundFactory.setAssetBasePath("mfx/animals/");

        try {
            startGameSound = SoundFactory.createSoundFromAsset(getEngine().getSoundManager(), activity, "go.ogg");
        } catch (IOException e) {
            Log.i(TAG, "Error during loading sounds");
        }
    }

    private void loadEndGameGraphics() {
        if (endGameTextureAtlas != null) {
            endGameTextureAtlas.load();
        }

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/endgame/");
        endGameTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 512, TextureOptions.BILINEAR);

        endGameBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(endGameTextureAtlas, activity, "background.png");

        try {
            endGameTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
            endGameTextureAtlas.load();
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            Log.e(TAG, "Error during building atlasses");
        }
    }

    private void loadHighScoreGraphics() {
        if (recordTextureAtlas != null) {
            recordTextureAtlas.load();
        }

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/highscore/");

        recordTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.DEFAULT);
        recordBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(recordTextureAtlas, activity, "background.png");
        highscoreSmallGameTypeTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(recordTextureAtlas, activity, "4x4.png");
        highscoreMediumGameTypeTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(recordTextureAtlas, activity, "4x5.png");
        highscoreBigGameTypeTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(recordTextureAtlas, activity, "4x6.png");

        try {
            recordTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
            recordTextureAtlas.load();
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            Log.e(TAG, "Error during building atlasses");
        }
    }

    private void loadGameTypeGraphics() {
        if (gameTypeTextureAtlas != null) {
            gameTypeTextureAtlas.load();
        }

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/gametype/");
        gameTypeTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.DEFAULT);

        backgroundGameTypeTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTypeTextureAtlas, activity, "background.jpg");
        smallGameTypeTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTypeTextureAtlas, activity, "4x4.png");
        mediumGameTypeTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTypeTextureAtlas, activity, "4x5.png");
        bigGameTypeTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTypeTextureAtlas, activity, "4x6.png");

        try {
            gameTypeTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
            gameTypeTextureAtlas.load();
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            Log.e(TAG, "Error during building atlasses");
        }
    }
}
