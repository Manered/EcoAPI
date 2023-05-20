package dev.manere.economy.commands;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to provide information about a command.
 * It can be applied to a class to specify the command's name, permission, and execution restrictions.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandInfo {

    /**
     * Specifies the name of the command.
     * @return the name of the command
     */
    String name();

    /**
     * Specifies the permission required to execute the command.
     * @return the permission required to execute the command
     */
    String permission() default "";

    /**
     * Specifies whether only players can execute the command.
     * @return true if only players can execute the command, false otherwise
     */
    boolean onlyPlayersCanExecute();
}

