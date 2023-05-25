package com.gamedesign.shmup.collision;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.ecs.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.gamedesign.shmup.ShmupType;
import com.gamedesign.shmup.component.BombComponent;
import com.gamedesign.shmup.component.UpgradeComponent;

public class PlayerDropHandler extends CollisionHandler
{
    public PlayerDropHandler()
    {
        super(ShmupType.PLAYER, ShmupType.DROP);
    }

    @Override
    protected void onCollision(Entity player, Entity drop)
    {
        FXGL.getApp().getGameState().increment("score", 30);
        if(drop.hasComponent(BombComponent.class))
        {
            FXGL.getApp().getGameState()
                    .increment("bombs", drop.getComponent(BombComponent.class).getValue());
            drop.removeFromWorld();
        }
        else if(drop.hasComponent(UpgradeComponent.class))
        {
            FXGL.getApp().getGameState()
                    .increment("upgrade", 1);
            drop.removeFromWorld();
        }
    }
}
