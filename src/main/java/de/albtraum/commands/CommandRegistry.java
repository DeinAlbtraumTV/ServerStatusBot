package de.albtraum.commands;

import java.util.ArrayList;

public final class CommandRegistry {
        public static final ArrayList<CommandBase> commands = new ArrayList<>();

        public static void registerCommand(final CommandBase cmd) {
            if (!commands.contains(cmd))
                commands.add(cmd);
        }

        public static void registerCommands(final CommandBase... cmds) {
            for (final CommandBase cmd : cmds) {
                registerCommand(cmd);
            }
        }
    }
