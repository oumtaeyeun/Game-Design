package com.gamedesign.shmup;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.GameEntity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.io.FS;
import com.almasb.fxgl.settings.GameSettings;
import com.almasb.fxgl.ui.UI;
import com.gamedesign.shmup.collision.*;
import com.gamedesign.shmup.component.UpgradeComponent;
import com.gamedesign.shmup.control.*;
import com.gamedesign.shmup.state.PauseState;
import javafx.util.Duration;

import java.util.*;

import static com.gamedesign.shmup.Config.*;

public class ShmupApp extends GameApplication
{
    private List<EnemyType> enemies;
    private SaveData saveData = null;
    private boolean boss;

    public GameEntity player()
    {
        return (GameEntity) getGameWorld().getSingleton(ShmupType.PLAYER);
    }

    public PlayerControl playerControl()
    {
        return getGameWorld()
                .getSingleton(ShmupType.PLAYER)
                .getControl(PlayerControl.class);
    }

    @Override
    protected void initSettings(GameSettings gameSettings)
    {
        gameSettings.setTitle("Shmup");
        gameSettings.setVersion("0.1");
        gameSettings.setWidth(App.WIDTH);
        gameSettings.setHeight(App.HEIGHT);
        gameSettings.setApplicationMode(ApplicationMode.DEVELOPER);
        gameSettings.setIntroEnabled(false);
        gameSettings.setProfilingEnabled(false);
        gameSettings.setCloseConfirmation(false);
        gameSettings.setMenuEnabled(false);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars)
    {
        vars.put("lives", 3);
        vars.put("score", 0);
        vars.put("bombs", 1);
        vars.put("level", 1);
        vars.put("upgrade", 1);
    }

    @Override
    protected void initInput()
    {
        getInput().addAction(new UserAction("Move Up")
        {
            @Override
            protected void onAction()
            {
                playerControl().moveUp();
            }
        }, Player.UP);

        getInput().addAction(new UserAction("Move Left")
        {
            @Override
            protected void onAction()
            {
                playerControl().moveLeft();
            }
        }, Player.LEFT);

        getInput().addAction(new UserAction("Move Down")
        {
            @Override
            protected void onAction()
            {
                playerControl().moveDown();
            }
        }, Player.DOWN);

        getInput().addAction(new UserAction("Move Right")
        {
            @Override
            protected void onAction()
            {
                playerControl().moveRight();
            }
        }, Player.RIGHT);

        getInput().addAction(new UserAction("Paws") {
            @Override
            protected void onAction()
            {
                getStateMachine().pushState(new PauseState());
            }
        }, Pause.PAUSE);

        getInput().addAction(new UserAction("Shoot Bullet")
        {
            @Override
            protected void onActionBegin()
            {
                GameEntity player = (GameEntity) FXGL.getApp()
                        .getGameWorld()
                        .getSingleton(ShmupType.PLAYER);
                if(getGameState().getInt("upgrade") == 1)
                    getGameWorld().spawn("P_BULLET", player.getPosition().getX() + 14,  player.getPosition().getY() - 5);
                else if(getGameState().getInt("upgrade") == 2)
                {
                    getGameWorld().spawn("P_BULLET", player.getPosition().getX() + 7, player.getPosition().getY() - 5);
                    getGameWorld().spawn("P_BULLET", player.getPosition().getX() + 21, player.getPosition().getY() - 5);
                }
                else
                {
                    getGameWorld().spawn("P_BULLET", player.getPosition().getX() + 14, player.getPosition().getY() - 4);
                    getGameWorld().spawn("P_BULLET", player.getPosition().getX(), player.getPosition().getY() - 21);
                    getGameWorld().spawn("P_BULLET", player.getPosition().getX() + 28, player.getPosition().getY() - 21);
                }
            }
        }, Player.SHOOT);

        getInput().addAction(new UserAction("Use a bomb")
        {
            @Override
            protected void onActionBegin()
            {
                if(getGameState().getInt("bombs") > 0)
                {
                    getGameScene().getViewport().shake(20);
                    getGameWorld().getEntitiesByType(ShmupType.ENEMY)
                            .forEach(e -> getGameState().increment("score", 1000));
                    getGameWorld().getEntitiesByType(ShmupType.ENEMY)
                            .forEach(e -> e.removeFromWorld());
                    getGameState().increment("bombs", -1);

                }
            }
        }, Player.BOMB);

    }

    @Override
    protected void initGame()
    {
        getGameWorld().spawn(ShmupType.BACKGROUND.name());
        initLevel();

        FS.<SaveData>readDataTask(FILE)
                .onSuccess(data -> saveData = data)
                .onFailure(ignore -> {})
                .execute();

        if(saveData == null)
            saveData = new SaveData("Human", 0);
    }

    private void initLevel()
    {
        getGameWorld().spawn("Player", App.WIDTH / 2, App.HEIGHT / 2);
        nextLevel();
    }

    private void nextLevel()
    {
        cleanupLevel();
        getGameState().increment("level", 1);
        double enemyCount = App.MINEMIES +
                            App.MINEMIES_SCALE_FACTOR *
                                    getGameState().getInt("level");

        boss = false;
        Map<EnemyType, Integer> counts = new HashMap<>();
        counts.put(EnemyType.CANNON_FODDER, (int) (CannonFodder.SPAWN_PERCENT * enemyCount));
        counts.put(EnemyType.CANNON_FODDER2, (int) (CannonFodder2.SPAWN_PERCENT * enemyCount));
        CannonFodder2Control.setLevelScale(CannonFodder2Control.getLevelScale() + LEVEL_SCALE);
        CannonFodderControl.setLevelScale(CannonFodderControl.getLevelScale() + LEVEL_SCALE);
        C2_bulletControl.setLevelScale(C2_bulletControl.getLevelScale() + LEVEL_SCALE);
        BossBulletControl.setLevelScale(BossBulletControl.getLevelScale() + LEVEL_SCALE);

        for(Map.Entry<EnemyType, Integer> entry : counts.entrySet())
        {
            int n = entry.getValue();
            while(n > 0)
            {
                enemies.add(entry.getKey());
                n--;
            }
        }

        Collections.shuffle(enemies);
        spawnEnemy();
        double enemySpawnInterval = Math.pow(0.8,getGameState().getInt("level") - 1);
        double scoreInterval = Math.pow(0.9, getGameState().getInt("level") - 1);
        getMasterTimer().runAtInterval(this::spawnEnemy, Duration.millis(2800 * enemySpawnInterval));
        getMasterTimer().runAtInterval(() -> getGameState()
                                                .increment("score", 1),
                                                Duration.millis(100 * scoreInterval));
    }

    private void spawnEnemy()
    {
        if(enemies.size() > 0)
        {
            double x = Math.random() * (App.WIDTH - 40);
            getGameWorld().spawn(enemies.remove(0).name(), x, 0);
        }
    }

    public void cleanupLevel()
    {
        getGameState().setValue("upgrade", 1);
        getMasterTimer().clear();
        getGameWorld().getEntitiesByType(ShmupType.ENEMY)
                .forEach(e -> e.removeFromWorld());
        enemies = new LinkedList<>();
    }

    @Override
    protected void initUI()
    {
        GameController uiController = new GameController(getGameScene());

        UI ui = getAssetLoader().loadUI("main.fxml", uiController);

        uiController.getLabelScore()
                .textProperty()
                .bind(
                        getGameState()
                        .intProperty("score")
                        .asString("Score: %d")
                      );

        uiController.getLabelBombs()
                .textProperty()
                .bind(
                        getGameState()
                                .intProperty("bombs")
                                .asString("Bombs: %d")
                );

        uiController.getLabelLives()
                .textProperty()
                .bind(
                        getGameState()
                                .intProperty("lives")
                                .asString("Lives: %d")
                );

        getGameScene().addUI(ui);
    }

    @Override
    protected void initPhysics()
    {
        getPhysicsWorld().addCollisionHandler(new PlayerEnemyHandler());
        getPhysicsWorld().addCollisionHandler(new PlayerDropHandler());
        getPhysicsWorld().addCollisionHandler(new P_bulletEnemyHandler());
    }

    @Override
    protected void onUpdate(double tpf)
    {
        if(enemies.isEmpty() && !boss)
        {
            getGameWorld().spawn("BOSS", App.WIDTH / 2 - (234 / 2), 0);
            boss = true;
        }
        super.onUpdate(tpf);
        if(enemies.isEmpty() && getGameWorld().getEntitiesByType(ShmupType.ENEMY).isEmpty())
        {
            getDisplay().showConfirmationBox("READY FOR THE NEXT LEVEL?", yes -> {
                if(yes)
                    nextLevel();
                else
                    exit();
            });
        }
    }

    @Override
    protected void preInit()
    {
        getAudioPlayer().loopBGM(BGM);
    }

    public void gameOver()
    {
        getDisplay().showConfirmationBox("GAME OVER!\n Play Again?", yes -> {
            if(yes)
                startNewGame();
            else
                exit();
        });

        int score = getGameState().getInt("score");
        if(score > saveData.getHighScore())
            getDisplay().showInputBox("HIGH SCORE!\n Please enter your name: ", playerName -> {
                FS.writeDataTask(new SaveData(playerName, score), FILE).execute();
            });
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
