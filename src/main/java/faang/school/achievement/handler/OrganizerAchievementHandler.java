package faang.school.achievement.handler;

import java.beans.EventHandler;

public class OrganizerAchievementHandler extends EventHandler {

    /**
     * Creates a new {@code EventHandler} object;
     * you generally use one of the {@code create} methods
     * instead of invoking this constructor directly.  Refer to
     * {@link EventHandler#create(Class, Object, String, String)
     * the general version of create} for a complete description of
     * the {@code eventPropertyName} and {@code listenerMethodName}
     * parameter.
     *
     * @param target             the object that will perform the action
     * @param action             the name of a (possibly qualified) property or method on
     *                           the target
     * @param eventPropertyName  the (possibly qualified) name of a readable property of the incoming event
     * @param listenerMethodName the name of the method in the listener interface that should trigger the action
     * @throws NullPointerException if {@code target} is null
     * @throws NullPointerException if {@code action} is null
     * @see EventHandler
     * @see #create(Class, Object, String, String, String)
     * @see #getTarget
     * @see #getAction
     * @see #getEventPropertyName
     * @see #getListenerMethodName
     */
    public OrganizerAchievementHandler(Object target, String action, String eventPropertyName, String listenerMethodName) {
        super(target, action, eventPropertyName, listenerMethodName);
    }
}
