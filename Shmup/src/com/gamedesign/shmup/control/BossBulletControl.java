package com.gamedesign.shmup.control;

import com.almasb.fxgl.ecs.Control;
import com.almasb.fxgl.ecs.Entity;
import com.almasb.fxgl.entity.GameEntity;
import javafx.geometry.Point2D;

import com.gamedesign.shmup.Config.*;

public class BossBulletControl extends Control
{
    private GameEntity bullet;
    private Point2D destination;
    private static double levelScale = 1;

    @Override
    public void onAdded(Entity entity)
    {
        bullet = (GameEntity) entity;
        destination = new Point2D(randomCoordinate(), randomCoordinate());
    }

    private double randomCoordinate()
    {
        return (Math.random() < 0.5 ? 1 : -1) *
                (Math.random() * Integer.MAX_VALUE);
    }

    @Override
    public void onUpdate(Entity entity, double v)
    {
        bullet.translateTowards(destination, Boss.BULLET_SPEED * levelScale);
    }

    public static double getLevelScale()
    {
        return levelScale;
    }

    public static void setLevelScale(double scale) {
        levelScale = scale;
    }
}
