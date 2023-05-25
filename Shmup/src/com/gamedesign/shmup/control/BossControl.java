package com.gamedesign.shmup.control;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.ecs.Control;
import com.almasb.fxgl.ecs.Entity;
import com.almasb.fxgl.entity.GameEntity;
import com.almasb.fxgl.time.LocalTimer;

import static com.gamedesign.shmup.Config.*;

public class BossControl extends Control
{
    private GameEntity boss;
    private LocalTimer localTimer;

    @Override
    public void onAdded(Entity entity)
    {
        boss = (GameEntity) entity;

        localTimer = FXGL.newLocalTimer();
    }

    @Override
    public void onUpdate(Entity entity, double v)
    {
        if(localTimer.elapsed(Boss.FIRE_RATE))
        {
            FXGL.getApp().getGameWorld().spawn("BOSS_BULLET", boss.getCenter().subtract(boss.getWidth() / 10, -boss.getHeight() / 10));
            FXGL.getApp().getGameWorld().spawn("BOSS_BULLET", boss.getCenter().subtract(-boss.getWidth() / 10, -boss.getHeight() / 10));
            localTimer.capture();
        }
    }
}








