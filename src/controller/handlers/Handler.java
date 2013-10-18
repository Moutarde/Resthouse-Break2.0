package controller.handlers;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import controller.actions.IAction;


/**
 * @author Nicolas Kniebihler
 *
 */
public abstract class Handler implements Observer {

    @Override
    public void update(Observable obs, Object action) {
        if (action instanceof List) {
            for (Object o : (List<?>)action) {
                assert o instanceof IAction : "Trying to update Handler with an action which is not a IAction";

                ((IAction)o).execute(obs, this);
            }
        }
        else {
            assert action instanceof IAction : "Trying to update Handler with an action which is not a IAction";

            ((IAction)action).execute(obs, this);
        }
    }

}
