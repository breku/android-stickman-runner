package com.thinkfaster.model.shape;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by brekol on 22.09.15.
 */
public class Player extends AnimatedSprite {

    private static final String TAG = "Player";
    private Body body;

    public Player(float pX, float pY, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, PhysicsWorld physicsWorld) {
        super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
        this.body = createBody(physicsWorld);
        physicsWorld.registerPhysicsConnector(new PhysicsConnector(this, body));
    }

    public Body createBody(PhysicsWorld physicsWorld) {
        final FixtureDef objectFixtureDef = PhysicsFactory.createFixtureDef(1, 0.5f, 0.5f);
        final Body result = PhysicsFactory.createBoxBody(physicsWorld, this, BodyDef.BodyType.DynamicBody, objectFixtureDef);
        result.setUserData(TAG);
        return result;
    }

    public void startRunning() {
        body.setLinearVelocity(1, 0);
        animate(50);
    }
}
