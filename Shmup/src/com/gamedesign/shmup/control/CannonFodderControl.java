package com.gamedesign.shmup.control;

import com.almasb.fxgl.ecs.Control;
import com.almasb.fxgl.ecs.Entity;
import com.almasb.fxgl.entity.GameEntity;

import static com.gamedesign.shmup.Config.*;

public class CannonFodderControl extends Control
{
    private GameEntity cannonFodder;
    private static double levelScale;

    @Override
    public void onAdded(Entity entity)
    {
        cannonFodder = (GameEntity) entity;
    }

    @Override
    public void onUpdate(Entity entity, double v)
    {
        cannonFodder.translateY(CannonFodder.SPEED);
    }

    public static double getLevelScale() {
        return levelScale;
    }

    public static void setLevelScale(double levelScale) {
        CannonFodderControl.levelScale = levelScale;
    }
}
