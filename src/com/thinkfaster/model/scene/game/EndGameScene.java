package com.thinkfaster.model.scene.game;

import com.thinkfaster.manager.ResourcesManager;
import com.thinkfaster.manager.SceneManager;
import com.thinkfaster.model.scene.BaseScene;
import com.thinkfaster.util.ContextConstants;
import com.thinkfaster.util.SceneType;
import org.andengine.entity.primitive.Line;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.color.Color;

/**
 * User: Breku
 * Date: 04.10.13
 */
public class EndGameScene extends BaseScene implements IOnSceneTouchListener {

    /**
     * @param objects objects[0] - Integer score
     */
    public EndGameScene(Object... objects) {
        super(objects);
    }

    @Override
    public void createScene(Object... objects) {
        createBackground((double) objects[0]);
        setOnSceneTouchListener(this);
    }

    @Override
    public void onBackKeyPressed() {
        SceneManager.getInstance().loadMenuScene(this);
    }

    @Override
    public SceneType getSceneType() {
        return SceneType.ENDGAME;
    }

    @Override
    public void disposeScene() {
        ResourcesManager.getInstance().unloadEndGameTextures();
    }

    @Override
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
        if (pSceneTouchEvent.isActionUp()) {
            SceneManager.getInstance().loadMenuScene(this);
        }
        return false;
    }

    private void createBackground(double score) {
        attachChild(createBackground());
        attachChild(createScoreText(score));
        attachChild(createLine());
    }

    private Text createScoreText(double score) {
        return new Text(380, 240, ResourcesManager.getInstance().getBlackFont(),
                getScoreText(score), vertexBufferObjectManager);
    }

    private Sprite createBackground() {
        return new Sprite(ContextConstants.SCREEN_WIDTH / 2, ContextConstants.SCREEN_HEIGHT / 2,
                ResourcesManager.getInstance().getEndGameBackgroundTextureRegion(), vertexBufferObjectManager);
    }

    private Line createLine() {
        final Line line = new Line(280, 220, 510, 220, vertexBufferObjectManager);
        line.setColor(Color.BLACK);
        return line;
    }

    private String getScoreText(double score) {
        return String.format("%s", score);
    }
}
