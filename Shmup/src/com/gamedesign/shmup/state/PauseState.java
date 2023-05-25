package com.gamedesign.shmup.state;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.app.SubState;
import com.almasb.fxgl.input.UserAction;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.gamedesign.shmup.Config.*;

public class PauseState extends SubState
{
    public PauseState()
    {
        initMasker();
        initInput();
    }

    private void initMasker()
    {
        Rectangle masker = new Rectangle(App.WIDTH, App.HEIGHT, new Color(1, 1, 1, 0.50));
        getChildren().add(masker);
    }

    private void initInput()
    {
        getInput().addAction(new UserAction("Unpause")
        {
            @Override
            protected void onAction()
            {
                FXGL.getApp().getStateMachine().popState();
            }
        }, Pause.PAUSE);
    }
}
