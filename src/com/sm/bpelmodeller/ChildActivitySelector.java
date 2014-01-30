package com.sm.bpelmodeller;

import org.oasis_open.docs.wsbpel._2_0.process.executable.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * User: mikola
 * Date: 23.11.12
 * Time: 23:54
 */
public class ChildActivitySelector {
    private ChildActivitySelector(){}
    public static ChildActivitySelector getOne(){
        return new ChildActivitySelector();
    }


    public SelectedChild selectChild(Object parentBpelObject) {
        Class parentClass = parentBpelObject.getClass();
        try {
            for (BpelActivityType type : BpelActivityType.values()) {
                Object result = parentClass.getMethod(type.getGetter()).invoke(parentBpelObject);
                if (result != null) {
                    return new SelectedChild(type.getClazz().cast(result), type);
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("object doesn't have any child");
    }

    public static class SelectedChild {
        private final TActivity child;
        private final BpelActivityType type;

        public SelectedChild(TActivity child, BpelActivityType type) {
            this.child = child;
            this.type = type;
        }

        public TActivity getActivity() {
            return child;
        }

        public BpelActivityType getType() {
            return type;
        }
    }
}
