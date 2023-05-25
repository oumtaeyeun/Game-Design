package com.gamedesign.shmup.control;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.ecs.Control;
import com.almasb.fxgl.ecs.Entity;
import com.almasb.fxgl.entity.GameEntity;
import com.almasb.fxgl.time.LocalTimer;

import static com.gamedesign.shmup.Config.*;

public class CannonFodder2Control extends Control
{
    private GameEntity cannonFodder2;
    private double startX;
    private int xDirection = 1;
    private LocalTimer localTimer;
    private static double levelScale = 1;

    @Override
    public void onAdded(Entity entity)
    {
        cannonFodder2 = (GameEntity) entity;
        startX = cannonFodder2.getCenter().getX();
        localTimer = FXGL.newLocalTimer();
    }

    @Override
    public void onUpdate(Entity entity, double v)
    {
        if(cannonFodder2.getX() <= 0 || cannonFodder2.getX() <= startX - CannonFodder2.RANGE)
            xDirection = 1;
        else if(cannonFodder2.getRightX() >= App.WIDTH || cannonFodder2.getRightX() >= startX + CannonFodder2.RANGE)
            xDirection = -1;

        cannonFodder2.getPositionComponent().translate(xDirection * CannonFodder2.SPEED, CannonFodder2.SPEED * levelScale / 10);
        if(localTimer.elapsed(CannonFodder2.FIRE_RATE))
        {
            FXGL.getApp().getGameWorld().spawn("C2_BULLET", cannonFodder2.getPosition().getX() + 14, cannonFodder2.getPosition().getY() + 5);
            localTimer.capture();
        }
    }

    public static void setLevelScale(double scale)
    {
        levelScale = scale;
    }

    public static double getLevelScale()
    {
        return levelScale;
    }
}