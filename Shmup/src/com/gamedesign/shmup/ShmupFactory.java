package com.gamedesign.shmup;

import com.almasb.fxgl.annotation.SetEntityFactory;
import com.almasb.fxgl.annotation.SpawnSymbol;
import com.almasb.fxgl.annotation.Spawns;
import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.ecs.Component;
import com.almasb.fxgl.ecs.Control;
import com.almasb.fxgl.ecs.Entity;
import com.almasb.fxgl.ecs.component.IrremovableComponent;
import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.entity.component.CollidableComponent;
import com.almasb.fxgl.entity.control.KeepOnScreenControl;
import com.gamedesign.shmup.component.*;
import com.gamedesign.shmup.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


import static com.gamedesign.shmup.Config.*;

@SetEntityFactory
public class ShmupFactory implements EntityFactory
{
    @Spawns("Player")
    public GameEntity newPlayer(SpawnData spawnData)
    {
        return Entities.builder()
                .from(spawnData)
                .type(ShmupType.PLAYER)
                .viewFromNodeWithBBox(FXGL.getAssetLoader().loadTexture(Player.PLAYER_TEXTURE).toAnimatedTexture(3, Duration.seconds(0.3)))
                .with(new PlayerControl())
                .with(new KeepOnScreenControl(true, true))
                .with(new CollidableComponent(true))
                .with(new HealthComponent(Player.HEALTH))
                .with(new InvincibleComponent(false))
                .build();
    }

    private GameEntity newEnemy(SpawnData spawnData)
    {
        return Entities.builder()
                .from(spawnData)
                .type(ShmupType.ENEMY)
                .viewFromNodeWithBBox(spawnData.get("Hitbox"))
                //.with((Control) spawnData.get("Control"))
                .with((Control[]) spawnData.get("Controls"))
                //.with((Component) spawnData.get("Damage"))
                .with((Component[]) spawnData.get("Components"))
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("BOSS")
    public GameEntity newBoss(SpawnData spawnData)
    {
        return newEnemy(spawnData
                            .put("Hitbox", FXGL.getAssetLoader().loadTexture(Boss.BOSS_TEXTURE))
                            //.put("Control", new BossControl())
                            .put("Controls", new Control[]{new BossControl()})
                            //.put("Damage", new DamageComponent(Bouncer.DAMAGE))
                            .put("Components", new Component[]{new HealthComponent(Boss.HEALTH)})
                        );
    }

    @Spawns("BOSS_BULLET")
    public Entity newbBullet(SpawnData spawnData)
    {
        return Entities.builder()
                .from(spawnData)
                .type(EnemyType.BOSS_BULLET)
                .type(ShmupType.ENEMY)
                .viewFromNodeWithBBox(FXGL.getAssetLoader().loadTexture(Boss.B_BULLET_TEXTURE))
                .with(new CollidableComponent(true))
                .with(new BossBulletControl())
                .build();
    }

    @Spawns("CANNON_FODDER")
    public GameEntity newcFodder(SpawnData spawnData)
    {
        return newEnemy(spawnData
                .put("Hitbox", FXGL.getAssetLoader().loadTexture(CannonFodder.CANNON_FODDER_TEXTURE))
                .put("Controls", new Control[]{new CannonFodderControl()})
                .put("Components", new Component[]{new HealthComponent(CannonFodder.HEALTH)})
        );
    }

    @Spawns("CANNON_FODDER2")
    public GameEntity newcFodder2(SpawnData spawnData)
    {
        return newEnemy(spawnData
                            .put("Hitbox", FXGL.getAssetLoader().loadTexture(CannonFodder2.CANNON_FODDER2_TEXTURE))
                            .put("Controls", new Control[]{new CannonFodder2Control()})
                            .put("Components", new Component[]{new HealthComponent(CannonFodder2.HEALTH)})
                       );
    }

    @Spawns("C2_BULLET")
    public Entity newc2Bullet(SpawnData spawnData)
    {
        return Entities.builder()
                .from(spawnData)
                .type(EnemyType.C2_BULLET)
                .type(ShmupType.ENEMY)
                .viewFromNodeWithBBox(FXGL.getAssetLoader().loadTexture(C2bullet.C2_BULLET_TEXTURE))
                .with(new CollidableComponent(true))
                .with(new C2_bulletControl())
                .build();
    }

    @Spawns("DROP")
    @SpawnSymbol('D')
    public Entity Drop(SpawnData spawnData, String texture, Component... components)
    {
        return Entities.builder()
                .at(spawnData.getX(), spawnData.getY())
                .type(ShmupType.DROP)
                .viewFromNodeWithBBox(FXGL.getAssetLoader().loadTexture(texture))
                .with(new DropControl())
                .with(new CollidableComponent(true))
                .with(components)
                .build();
    }

    @Spawns("Bomb Drop")
    public Entity newBombDrop(SpawnData spawnData)
    {
        return Drop(spawnData,
                Drop.DROP_TEXTURE,
                new BombComponent(1));
    }

    @Spawns("Upgrade Drop")
    public Entity newUpgradeDrop(SpawnData spawnData)
    {
        return Drop(spawnData,
                Drop.UPGRADE_TEXTURE,
                new UpgradeComponent(1));
    }

    @Spawns("BACKGROUND")
    public Entity newBackground(SpawnData spawnData)
    {
        return Entities.builder()
                .from(spawnData)
                .type(ShmupType.BACKGROUND)
                .viewFromNode(FXGL.getAssetLoader().loadTexture(BACKGROUND))
                .renderLayer(RenderLayer.BACKGROUND)
                .with(new IrremovableComponent())
                .build();
    }

    @Spawns("P_BULLET")
    public Entity newpBullet(SpawnData spawnData)
    {
        return Entities.builder()
                .from(spawnData)
                .type(ShmupType.P_BULLET)
                .viewFromNodeWithBBox(FXGL.getAssetLoader().loadTexture(P_bullet.P_BULLET_TEXTURE))
                .with(new CollidableComponent(true))
                .with(new P_bulletControl())
                .with(new DamageComponent(P_bullet.DAMAGE))
                .build();
    }
}










