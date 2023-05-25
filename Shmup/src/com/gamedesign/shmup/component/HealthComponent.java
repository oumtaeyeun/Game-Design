package com.gamedesign.shmup.component;

import com.almasb.fxgl.ecs.component.IntegerComponent;
import javafx.beans.property.SimpleIntegerProperty;

public class HealthComponent extends IntegerComponent
{
    private SimpleIntegerProperty max;

    public HealthComponent(int max)
    {
        this(max, max);
    }

    public HealthComponent(int initialValue, int max)
    {
        super(initialValue);
        this.max = new SimpleIntegerProperty(max);
    }

    public int getMax()
    {
        return max.get();
    }

    public SimpleIntegerProperty maxProperty()
    {
        return max;
    }

    public void setMax(int max)
    {
        this.max.set(max);
    }

    public boolean isMax()
    {
        return getMax() == getValue();
    }
}



