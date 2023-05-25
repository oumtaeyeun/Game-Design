package com.gamedesign.shmup;

import javafx.scene.input.KeyCode;
import javafx.util.Duration;

public class Config
{
    public static final String FILE = "./highscore.dat";

    public static final class App
    {
        public static final int WIDTH = 800;
        public static final int HEIGHT = 600;
        public static final int MINEMIES = 15;
        public static final double MINEMIES_SCALE_FACTOR = 1.2;
    }

    public static final class Pause
    {
        public static final KeyCode PAUSE = KeyCode.SPACE;
    }

    public static final class Player
    {
        public static final int HEALTH = 100;
        public static final double SPEED = 5.0;
        public static final String PLAYER_TEXTURE = "plane0t.png";

        public static final Duration INVINCIBILITY_DURATION = Duration.millis(1500);

        public static final KeyCode UP = KeyCode.W;
        public static final KeyCode LEFT = KeyCode.A;
        public static final KeyCode DOWN = KeyCode.S;
        public static final KeyCode RIGHT = KeyCode.D;
        public static final KeyCode SHOOT = KeyCode.H;
        public static final KeyCode BOMB = KeyCode.J;
    }

    public static final class P_bullet
    {
        public static final double SPEED = 8.0;
        public static final int DAMAGE = 20;
        public static final String P_BULLET_TEXTURE = "bullet.png";
    }

    public static final class Boss
    {
        public static final String BOSS_TEXTURE = "Boss.png";
        public static final int HEALTH = 1000;
        public static final double BULLET_SPEED = 5.0;
        public static final String B_BULLET_TEXTURE = "bbullet.png";
        public static final Duration FIRE_RATE = Duration.millis(150);
    }

    //kamikaze
    public static final class CannonFodder
    {
        public static final String CANNON_FODDER_TEXTURE = "cfodder.png";
        public static final double SPEED = 7.5;
        public static final int HEALTH = 20;
        public static final double SPAWN_PERCENT = 0.7;
    }

    public static final class CannonFodder2
    {
        public static final String CANNON_FODDER2_TEXTURE = "cfodder2.png";
        public static final double SPEED = 3.0;
        public static final int HEALTH = 40;
        public static final double SPAWN_PERCENT = 0.3;
        public static final double RANGE = App.WIDTH / 4;
        public static final Duration FIRE_RATE = Duration.millis(1400);
    }

    public static final class C2bullet
    {
        public static final String C2_BULLET_TEXTURE = "cbullet.png";
        public static final double SPEED = 7.0;
    }

    public static final class Drop
    {
        public static final String DROP_TEXTURE = "drop.png";
        public static final String UPGRADE_TEXTURE = "upgrade.png";
    }
    public static final String BACKGROUND = "spacebackground.gif";
    public static final String BGM = "Shump.mp3";
    public static final double LEVEL_SCALE = 0.2;
}







