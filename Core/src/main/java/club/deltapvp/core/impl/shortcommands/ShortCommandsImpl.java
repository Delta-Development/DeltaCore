package club.deltapvp.core.impl.shortcommands;

import club.deltapvp.deltacore.api.commands.Command;
import club.deltapvp.deltacore.api.commands.SubCommand;
import club.deltapvp.deltacore.api.commands.shortcommands.ShortCommands;

import java.util.*;

public class ShortCommandsImpl extends ShortCommands {

    private final HashMap<Command, ArrayList<String>> commandShortCommands = new HashMap<>();
    private final HashMap<SubCommand, ArrayList<String>> subCommandShortCommands = new HashMap<>();

    public ShortCommandsImpl() {
        setInstance(this);
    }

    @Override
    public void addShortCommand(Command command, String[] commands) {
        ArrayList<String> strings = commandShortCommands.getOrDefault(command, new ArrayList<>());
        Arrays.stream(commands).filter(s -> !strings.contains(s)).forEach(strings::add);

        boolean b = commandShortCommands.containsKey(command);
        if (b)
            commandShortCommands.replace(command, strings);
        else
            commandShortCommands.put(command, strings);
    }

    @Override
    public void addShortSubCommand(SubCommand command, String[] commands) {
        ArrayList<String> strings = subCommandShortCommands.getOrDefault(command, new ArrayList<>());
        Arrays.stream(commands).filter(s -> !strings.contains(s)).forEach(strings::add);

        boolean b = subCommandShortCommands.containsKey(command);
        if (b)
            subCommandShortCommands.replace(command, strings);
        else
            subCommandShortCommands.put(command, strings);
    }

    @Override
    public Optional<Command> getCommand(String cmd) {
        Optional<Map.Entry<Command, ArrayList<String>>> entry = commandShortCommands.entrySet()
                .stream()
                .filter(commandArrayListEntry -> commandArrayListEntry.getValue().stream().anyMatch(s -> s.equalsIgnoreCase(cmd)))
                .findFirst();
        return entry.map(Map.Entry::getKey);
    }

    @Override
    public Optional<SubCommand> getSubCommand(String cmd) {
        Optional<Map.Entry<SubCommand, ArrayList<String>>> entry = subCommandShortCommands.entrySet()
                .stream()
                .filter(subCommandArrayListEntry -> subCommandArrayListEntry.getValue().stream().anyMatch(s -> s.equalsIgnoreCase(cmd)))
                .findFirst();

        return entry.map(Map.Entry::getKey);
    }
}
