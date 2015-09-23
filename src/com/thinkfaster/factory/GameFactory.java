package com.thinkfaster.factory;

import com.thinkfaster.manager.resources.GameResourcesManager;
import com.thinkfaster.model.shape.GameButton;
import com.thinkfaster.model.shape.Player;

/**
 * Created by brekol on 23.09.15.
 */
public class GameFactory {

    private final GameResourcesManager resourcesManager;

    public GameFactory(GameResourcesManager resourcesManager) {
        this.resourcesManager = resourcesManager;
    }

    public Player createPlayer() {
        final Player player = new Player(400, 200, resourcesManager.getPlayerTiledTextureRegion(), resourcesManager.getVertexBufferObjectManager());
        return player;
    }

    public GameButton createGameButton(final int x, final int y) {
        final GameButton gameButton = new GameButton(x, y, resourcesManager.getButtonTextureRegion(), resourcesManager.getVertexBufferObjectManager());
        gameButton.setScale(4.0f);
        return gameButton;
    }
}
