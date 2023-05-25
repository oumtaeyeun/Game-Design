package com.gamedesign.shmup.control;

import com.almasb.fxgl.ecs.Control;
import com.almasb.fxgl.ecs.Entity;
import com.almasb.fxgl.entity.GameEntity;

import com.gamedesign.shmup.Config.*;

public class P_bulletControl extends Control
{
    private GameEntity pBullet;

    @Override
    public void onAdded(Entity entity)
    {
        pBullet = (GameEntity) entity;
    }

    @Override
    public void onUpdate(Entity entity, double v)
    {
        pBullet.getPositionComponent().translateY(-P_bullet.SPEED); // translates upward
    }
}
