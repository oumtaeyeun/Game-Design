package com.gamedesign.shmup.control;

import com.almasb.fxgl.ecs.Control;
import com.almasb.fxgl.ecs.Entity;
import com.almasb.fxgl.entity.GameEntity;

import static com.gamedesign.shmup.Config.*;

public class C2_bulletControl extends Control
{
    private GameEntity bullet;
    private static double levelScale = 1;

    @Override
    public void onAdded(Entity entity)
    {
        bullet = (GameEntity) entity;
    }

    @Override
    public void onUpdate(Entity entity, double v)
    {
        bullet.getPositionComponent().translateY(C2bullet.SPEED * levelScale);
    }

    public static double getLevelScale() {
        return levelScale;
    }

    public static void setLevelScale(double levelScale) {
        C2_bulletControl.levelScale = levelScale;
    }
}
