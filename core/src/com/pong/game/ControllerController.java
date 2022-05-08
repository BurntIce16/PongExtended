package com.pong.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;

public class ControllerController implements ControllerListener {
    private static final String TAG = "controller";


    public ControllerController(){
        for (Controller controller : Controllers.getControllers()) {
            Gdx.app.log(TAG, controller.getName());
            System.out.println(controller.getName());
        }

    }


    @Override
    public void connected(Controller controller) {

    }

    @Override
    public void disconnected(Controller controller) {

    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        System.out.println(controller.getName() + "-" + controller.getName() + " " + buttonCode);
        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {

        return false;
    }
}
