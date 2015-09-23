package com.thinkfaster.model.scene;

import com.thinkfaster.manager.IResourcesManager;
import com.thinkfaster.manager.SceneManager;
import com.thinkfaster.manager.resources.MainMenuResourcesManager;
import com.thinkfaster.util.ContextConstants;
import com.thinkfaster.util.SceneType;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.adt.color.Color;

/**
 * User: Breku
 * Date: 21.09.13
 */
public class MainMenuScene extends BaseScene implements MenuScene.IOnMenuItemClickListener {
    private final int NEW_GAME = 0;
    private final int ABOUT = 2;
    private final int EXIT = 3;
    private final int RECORDS = 4;

    private MenuScene menuScene;
    private MainMenuResourcesManager resourcesManager;

    public MainMenuScene(IResourcesManager resourcesManager) {
        super(resourcesManager);
        this.resourcesManager = (MainMenuResourcesManager) resourcesManager;
    }

    @Override
    public void createScene() {
        clear();
        createBackground();
        createButtons();
    }

    @Override
    public void onBackKeyPressed() {
        System.exit(0);
    }

    @Override
    public SceneType getSceneType() {
        return SceneType.MENU;
    }

    @Override
    public void disposeScene() {

    }

    @Override
    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
        switch (pMenuItem.getID()) {
            case NEW_GAME:
                SceneManager.getInstance().loadGameScene();
                break;
            case EXIT:
                System.exit(0);
            case RECORDS:
                SceneManager.getInstance().loadHighScoreSceneFrom();
                break;
            default:
                return false;
        }
        return false;
    }

    private void clear() {
        clearChildScene();
        clearEntityModifiers();
        clearTouchAreas();
        clearUpdateHandlers();
    }

    private void createBackground() {
        attachChild(new Sprite(ContextConstants.SCREEN_WIDTH / 2, ContextConstants.SCREEN_HEIGHT / 2, resourcesManager.getMenuBackgroundTextureRegion(), vertexBufferObjectManager));
    }

    private void createButtons() {
        menuScene = new MenuScene(camera);
        menuScene.setPosition(0, 0);

        final IMenuItem newGameItem = new ScaleMenuItemDecorator(new SpriteMenuItem(NEW_GAME, resourcesManager.getPlayButtonTextureRegion(), vertexBufferObjectManager), 1.2f, 1);
        final IMenuItem recordsItem = new ScaleMenuItemDecorator(new SpriteMenuItem(RECORDS, resourcesManager.getHighscoresButtonTextureRegion(), vertexBufferObjectManager), 1.2f, 1);
        recordsItem.setColor(new Color(0.51f, 0.51f, 0.51f));

        menuScene.addMenuItem(newGameItem);
        menuScene.addMenuItem(recordsItem);

        menuScene.buildAnimations();
        menuScene.setBackgroundEnabled(false);

        newGameItem.setPosition(400, 280);
        recordsItem.setPosition(220, 270);

        menuScene.setOnMenuItemClickListener(this);

        setChildScene(menuScene);
    }
}
