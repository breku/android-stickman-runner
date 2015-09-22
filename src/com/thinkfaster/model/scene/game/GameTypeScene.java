package com.thinkfaster.model.scene.game;

import com.thinkfaster.manager.ResourcesManager;
import com.thinkfaster.manager.SceneManager;
import com.thinkfaster.model.scene.BaseScene;
import com.thinkfaster.util.ContextConstants;
import com.thinkfaster.util.SceneType;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;

/**
 * User: Breku
 * Date: 08.10.13
 */
public class GameTypeScene extends BaseScene implements MenuScene.IOnMenuItemClickListener {

    private MenuScene menuScene;

    public GameTypeScene() {
        super();
    }

    @Override
    public void createScene(Object... objects) {
        createBackground();
        createButtons();
    }

    @Override
    public void onBackKeyPressed() {
        SceneManager.getInstance().loadMenuScene(this);
    }

    @Override
    public SceneType getSceneType() {
        return SceneType.GAMETYPE;
    }

    @Override
    public void disposeScene() {
        ResourcesManager.getInstance().unloadGameTypeTextures();
    }

    @Override
    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
        SceneManager.getInstance().loadGameScene();
        return true;
    }

    private void createBackground() {
        attachChild(new Sprite(ContextConstants.SCREEN_WIDTH / 2, ContextConstants.SCREEN_HEIGHT / 2, resourcesManager.getBackgroundGameTypeTextureRegion(), vertexBufferObjectManager));
    }

    private void createButtons() {
        menuScene = new MenuScene(camera);
        menuScene.setPosition(0, 0);
        menuScene.setBackgroundEnabled(false);
        menuScene.buildAnimations();

        final IMenuItem smallSizeGame = new ScaleMenuItemDecorator(new SpriteMenuItem(1, ResourcesManager.getInstance().getSmallGameTypeTextureRegion(), vertexBufferObjectManager), 1.2f, 1);
        final IMenuItem mediumSizeGame = new ScaleMenuItemDecorator(new SpriteMenuItem(2, ResourcesManager.getInstance().getMediumGameTypeTextureRegion(), vertexBufferObjectManager), 1.2f, 1);
        final IMenuItem bigSizeGame = new ScaleMenuItemDecorator(new SpriteMenuItem(3, ResourcesManager.getInstance().getBigGameTypeTextureRegion(), vertexBufferObjectManager), 1.2f, 1);

        menuScene.addMenuItem(smallSizeGame);
        menuScene.addMenuItem(mediumSizeGame);
        menuScene.addMenuItem(bigSizeGame);

        menuScene.buildAnimations();
        menuScene.setBackgroundEnabled(false);

        smallSizeGame.setPosition(250, 260);
        mediumSizeGame.setPosition(400, 260);
        bigSizeGame.setPosition(550, 260);

        menuScene.setOnMenuItemClickListener(this);

        setChildScene(menuScene);
    }
}
