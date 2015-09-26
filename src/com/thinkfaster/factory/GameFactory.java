package com.thinkfaster.factory;

import com.thinkfaster.manager.resources.GameResourcesManager;
import com.thinkfaster.model.shape.GameButton;
import com.thinkfaster.model.shape.Player;
import org.andengine.extension.physics.box2d.PhysicsWorld;

/**
 * Created by brekol on 23.09.15.
 */
public class GameFactory {

    private final GameResourcesManager resourcesManager;
    private final PhysicsWorld physicsWorld;

    public GameFactory(GameResourcesManager resourcesManager, PhysicsWorld physicsWorld) {
        this.resourcesManager = resourcesManager;
        this.physicsWorld = physicsWorld;
    }

    public Player createPlayer() {
        final Player player = new Player(400, 200, resourcesManager.getPlayerTiledTextureRegion(), resourcesManager.getVertexBufferObjectManager(), physicsWorld);
        return player;
    }

    public GameButton createGameButton(final int x, final int y) {
        final GameButton gameButton = new GameButton(x, y, resourcesManager.getButtonTextureRegion(), resourcesManager.getVertexBufferObjectManager());
        gameButton.setScale(4.0f);
        return gameButton;
    }
}
