package com.sm.bpelmodeller;

import com.sm.logging.LogService;
import com.sm.model.Action;
import com.sm.model.ActionContainer;
import org.oasis_open.docs.wsbpel._2_0.process.executable.*;

/**
 * User: mikola
 * Date: 19.11.12
 * Time: 22:40
 */
public enum BpelActivityType {
    ASSIGN          ("getAssign",       TAssign.class),
    EMPTY           ("getEmpty",        TEmpty.class),
    EXIT            ("getExit",         TExit.class),
    FLOW            ("getFlow",         TFlow.class),
    FOREACH         ("getForEach",      TForEach.class),
    IF              ("getIf",           TIf.class),
    INVOKE          ("getInvoke",       TInvoke.class),
    PICK            ("getPick",         TPick.class),
    RECEIVE         ("getReceive",      TReceive.class),
    REPEAT_UNTIL    ("getRepeatUntil",  TRepeatUntil.class),
    REPLY           ("getReply",        TReply.class),
    RETHROW         ("getRethrow",      TRethrow.class),
    SCOPE           ("getScope",        TScope.class),
    SEQUENCE        ("getSequence",     TSequence.class),
    THROW           ("getThrow",        TThrow.class),
    VALIDATE        ("getValidate",     TValidate.class),
    WAIT            ("getWait",         TWait.class),
    WHILE           ("getWhile",        TWhile.class);
//        childGetters.put("getExtensionActivity", TExtensionActivity.class),

    private final String getter;
    private final Class<? extends TActivity> clazz;
    private final ActivityProcessorFactory.ActivityProcessor processor;

    private BpelActivityType(String getter, Class<? extends TActivity> clazz) {
        this.getter = getter;
        this.clazz = clazz;
        this.processor = ActivityProcessorFactory.getActivityProcessorFor(clazz);
    }

    public static BpelActivityType valueOf(TActivity a) {
        for (BpelActivityType type : values()) {
            if (type.clazz.isAssignableFrom(a.getClass())) {
                return type;
            }
        }
        throw new IllegalArgumentException("cannot find value of " + a.getClass().getSimpleName());
    }

    public String getGetter() {
        return getter;
    }

    public Class<? extends TActivity> getClazz() {
        return clazz;
    }

    public Action process(TActivity act) {
        if (act != null) {
            String n = act.getName();
            LogService.get().debug("Process "+act.getClass().getSimpleName()+" conversion... " + (n!=null?n : ""));
            return processor.processActivity(act);
        }
        //return null;
        throw new IllegalArgumentException("cannot process null activity");
    }
}
