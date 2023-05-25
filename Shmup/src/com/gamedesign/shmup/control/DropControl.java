package com.gamedesign.shmup.control;

import com.almasb.fxgl.ecs.Control;
import com.almasb.fxgl.ecs.Entity;
import com.almasb.fxgl.entity.GameEntity;

public class DropControl extends Control
{
    private GameEntity drop;

    @Override
    public void onAdded(Entity entity)
    {
        drop = (GameEntity) entity;
    }

    @Override
    public void onUpdate(Entity entity, double v)
    {
        drop.getPositionComponent().translateY(3);
    }
}
