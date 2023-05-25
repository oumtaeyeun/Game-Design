package com.gamedesign.shmup.collision;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.ecs.Entity;
import com.almasb.fxgl.entity.GameEntity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.gamedesign.shmup.ShmupApp;
import com.gamedesign.shmup.ShmupType;
import com.gamedesign.shmup.component.InvincibleComponent;
import com.gamedesign.shmup.control.PlayerControl;

import static com.gamedesign.shmup.Config.*;

public class PlayerEnemyHandler extends CollisionHandler
{
    private ShmupApp app;

    public PlayerEnemyHandler()
    {
        super(ShmupType.PLAYER, ShmupType.ENEMY);
        app = (ShmupApp) FXGL.getApp();
    }

    @Override
    protected void onCollision(Entity player, Entity enemy)
    {
        if(player.getComponent(InvincibleComponent.class).getValue())
            return;
        player.removeFromWorld();

        FXGL.getApp().getGameState().increment("lives", -1);

        int lives = FXGL.getApp()
                .getGameState()
                .getInt("lives");
        if(lives > 0)
        {
            FXGL.getApp()
                    .getGameWorld()
                    .spawn("Player", App.WIDTH / 2, App.HEIGHT / 2);
            player = (GameEntity) FXGL.getApp()
                    .getGameWorld()
                    .getSingleton(ShmupType.PLAYER);
            PlayerControl playerControl = player.getControl(PlayerControl.class);
            playerControl.enableInvincibility();
            app.getMasterTimer().runOnceAfter(playerControl::disableInvincibility,
                    Player.INVINCIBILITY_DURATION);
        }
        else
            ((ShmupApp) FXGL.getApp()).gameOver();

    }
}








