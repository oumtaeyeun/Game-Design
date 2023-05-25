package com.gamedesign.shmup.collision;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.ecs.Entity;
import com.almasb.fxgl.entity.component.PositionComponent;
import com.almasb.fxgl.physics.CollisionHandler;
import com.gamedesign.shmup.ShmupApp;
import com.gamedesign.shmup.ShmupType;
import com.gamedesign.shmup.component.DamageComponent;
import com.gamedesign.shmup.component.HealthComponent;
import com.gamedesign.shmup.control.BossControl;

public class P_bulletEnemyHandler extends CollisionHandler
{

    public P_bulletEnemyHandler()
    {
        super(ShmupType.P_BULLET, ShmupType.ENEMY);
    }

    @Override
    protected void onCollision(Entity pBullet, Entity enemy)
    {
        int dropChance = (int) (Math.random() * 20);

        if(enemy.hasComponent(HealthComponent.class))
        {
            HealthComponent healthComponent = enemy.getComponent(HealthComponent.class);
            DamageComponent damageComponent = pBullet.getComponent(DamageComponent.class);
            healthComponent.setValue(healthComponent.getValue() - damageComponent.getValue());
            pBullet.removeFromWorld();
            if (healthComponent.getValue() <= 0)
            {
                double scoreScale = Math.pow(1.3, FXGL.getApp().getGameState(). getInt("level") - 1);
                if(enemy.hasControl(BossControl.class))
                    FXGL.getApp().getGameState().increment("score", (int) (10000 * scoreScale));
                else
                    FXGL.getApp().getGameState().increment("score", (int) (1000 * scoreScale));
                if(dropChance == 0) // 5% spawn chance
                {
                    FXGL.getApp().getGameWorld().spawn("Bomb Drop", enemy.getComponent(PositionComponent.class).getValue().subtract(-14, -5));
                }
                else if(dropChance < 5) // 20%
                {
                    FXGL.getApp().getGameWorld().spawn("Upgrade Drop", enemy.getComponent(PositionComponent.class).getValue().subtract(-14, -5));
                }
                if (enemy.hasControl(BossControl.class))
                {
                    enemy.removeFromWorld();
                    ((ShmupApp) FXGL.getApp()).cleanupLevel();
                }
                else
                    enemy.removeFromWorld();

            }
        }
    }
}
