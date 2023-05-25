package com.gamedesign.shmup.control;

import com.almasb.fxgl.ecs.Control;
import com.almasb.fxgl.ecs.Entity;
import com.almasb.fxgl.entity.GameEntity;
import com.gamedesign.shmup.component.InvincibleComponent;


import static com.gamedesign.shmup.Config.*;

public class PlayerControl extends Control
{
    private GameEntity player;



    @Override
    public void onAdded(Entity entity)
    {
        player = (GameEntity) entity;
    }

    @Override
    public void onUpdate(Entity entity, double v)
    {

    }

    public void moveUp()
    {
        move(0, -Player.SPEED);
    }

    public void moveLeft()
    {
        move(-Player.SPEED, 0);
    }

    public void moveDown()
    {
        move(0, Player.SPEED);
    }

    public void moveRight()
    {
        move(Player.SPEED, 0);
    }

    private void move(double dx, double dy)
    {
        player.getPositionComponent().translate(dx, dy);
    }

    public void enableInvincibility()
    {
        player.getComponent(InvincibleComponent.class).setValue(true);
    }

    public void disableInvincibility()
    {
        player.getComponent(InvincibleComponent.class).setValue(false);
    }
}






