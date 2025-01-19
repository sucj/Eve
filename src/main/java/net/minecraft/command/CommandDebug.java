package net.minecraft.command;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandDebug extends CommandBase {
    private static final Logger logger = LogManager.getLogger();
    private long profileStartTime;
    private int profileStartTick;

    public String getCommandName() {
        return "debug";
    }

    public int getRequiredPermissionLevel() {
        return 3;
    }

    public String getCommandUsage(ICommandSender sender) {
        return "commands.debug.usage";
    }

    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 1) {
            throw new WrongUsageException("commands.debug.usage");
        } else {
            if (args[0].equals("start")) {
                if (args.length != 1) {
                    throw new WrongUsageException("commands.debug.usage");
                }

                notifyOperators(sender, this, "commands.debug.start");
                this.profileStartTime = MinecraftServer.getCurrentTimeMillis();
                this.profileStartTick = MinecraftServer.getServer().getTickCounter();
            } else {
                if (!args[0].equals("stop")) {
                    throw new WrongUsageException("commands.debug.usage");
                }

                if (args.length != 1) {
                    throw new WrongUsageException("commands.debug.usage");
                }

                long i = MinecraftServer.getCurrentTimeMillis();
                int j = MinecraftServer.getServer().getTickCounter();
                long k = i - this.profileStartTime;
                int l = j - this.profileStartTick;
                this.saveProfileResults(k, l);
                notifyOperators(sender, this, "commands.debug.stop", k / 1000.0F, l);
            }
        }
    }

    private void saveProfileResults(long timeSpan, int tickSpan) {
        File file1 = new File(MinecraftServer.getServer().getFile("debug"), "profile-results-" + (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()) + ".txt");
        file1.getParentFile().mkdirs();

        try {
            FileWriter filewriter = new FileWriter(file1);
            filewriter.write(this.getProfileResults(timeSpan, tickSpan));
            filewriter.close();
        } catch (Throwable throwable) {
            logger.error("Could not save profiler results to {}", file1, throwable);
        }
    }

    private String getProfileResults(long timeSpan, int tickSpan) {
        return "---- Minecraft Profiler Results ----\n" +
                "// " +
                getWittyComment() +
                "\n\n" +
                "Time span: " + timeSpan + " ms\n" +
                "Tick span: " + tickSpan + " ticks\n" +
                "// This is approximately " + String.format("%.2f", tickSpan / (timeSpan / 1000.0F)) + " ticks per second. It should be " + 20 + " ticks per second\n\n";
    }

    private static String getWittyComment() {
        String[] astring = new String[]{"Shiny numbers!", "Am I not running fast enough? :(", "I'm working as hard as I can!", "Will I ever be good enough for you? :(", "Speedy. Zoooooom!", "Hello world", "40% better than a crash report.", "Now with extra numbers", "Now with less numbers", "Now with the same numbers", "You should add flames to things, it makes them go faster!", "Do you feel the need for... optimization?", "*cracks redstone whip*", "Maybe if you treated it better then it'll have more motivation to work faster! Poor server."};

        try {
            return astring[(int) (System.nanoTime() % astring.length)];
        } catch (Throwable var2) {
            return "Witty comment unavailable :(";
        }
    }

    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, "start", "stop") : null;
    }
}
