package com.gamedesign.shmup;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.scene.GameScene;
import com.almasb.fxgl.ui.UIController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import static com.gamedesign.shmup.Config.Player;

public class GameController implements UIController
{
    @FXML private Label labelScore, labelBombs, labelLives;

    private GameScene gameScene;

    public GameController(GameScene gameScene)
    {
        this.gameScene = gameScene;
    }

    @Override
    public void init()
    {
        labelScore.setFont(FXGL.getUIFactory().newFont(18));
        labelBombs.setFont(FXGL.getUIFactory().newFont(18));
        labelLives.setFont(FXGL.getUIFactory().newFont(18));
    }

    public Label getLabelScore()
    {
        return labelScore;
    }

    public Label getLabelBombs()
    {
        return labelBombs;
    }

    public Label getLabelLives()
    {
        return labelLives;
    }
}
