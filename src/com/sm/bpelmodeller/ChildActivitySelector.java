package com.sm.bpelmodeller;

import org.oasis_open.docs.wsbpel._2_0.process.executable.TActivity;

import java.lang.reflect.InvocationTargetException;

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
        return selectChildPrivate(parentBpelObject, false);
    }

    public SelectedChild selectChildSafe(Object parentBpelObject) {
        return selectChildPrivate(parentBpelObject, true);
    }

    private SelectedChild selectChildPrivate(Object parentBpelObject, boolean nullable) {
        if (parentBpelObject == null && nullable) {
            return new NullChild();
        }
        Class parentClass = parentBpelObject.getClass();
        for (BpelActivityType type : BpelActivityType.values()) {
            try {
                Object result = parentClass.getMethod(type.getGetter()).invoke(parentBpelObject);
                if (result != null) {
                    return new SelectedChild(type.getClazz().cast(result), type);
                }
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                // ignore
            }
        }

        if (nullable) {
            return new NullChild();
        }
        throw new IllegalArgumentException("object doesn't have any child");
    }

    public static class NullChild extends SelectedChild{
        public NullChild() {
            super(null, null);
        }
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
