package com.jkgames.game.models;

import com.badlogic.androidgames.framework.DynamicGameObject;
import com.badlogic.androidgames.framework.math.Vector2;

public class WorldBob extends DynamicGameObject {

    public WorldBob(float x, float y, float width, float height) {
        super(x, y, width, height);
        velocity.x = 5;
        velocity.y = 5;
    }

    public static final int BOB_DIRECTION_RIGHT = 1;
    public static final int BOB_DIRECTION_LEFT = -1;

    public int xDirection;
    public int yDirection;
    public Vector2 destination;

    public void update(float deltaTime) {
        position.add(velocity.x * xDirection * deltaTime, velocity.y * yDirection * deltaTime);

        if(velocity.x > 0)
            if(position.x > destination.x)
                position.x = destination.x;
        else if(velocity.x < 0)
            if(position.x < destination.x)
                position.x = destination.x;

        if(velocity.y > 0)
            if(position.y > destination.y)
                position.y = destination.y;
        else if(velocity.y < 0)
            if(position.y < destination.y)
                position.y = destination.y;

    }

    public void setDestination(Vector2 destination)
    {
        this.destination = destination;
        Vector2 dir = destination.sub(position);

        if(dir.x > 0)
            xDirection = BOB_DIRECTION_RIGHT;
        else
            xDirection = BOB_DIRECTION_LEFT;

        if(dir.y > 0)
            yDirection = 1;
        else
            yDirection = -1;
    }
}
