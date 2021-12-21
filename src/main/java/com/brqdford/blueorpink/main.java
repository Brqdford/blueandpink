package com.brqdford.blueorpink;

import com.google.inject.Inject;
import io.github.nucleuspowered.nucleus.api.NucleusAPI;
import io.github.nucleuspowered.nucleus.api.module.nickname.NucleusNicknameService;
import io.github.nucleuspowered.nucleus.api.module.nickname.exception.NicknameException;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.tab.TabList;
import org.spongepowered.api.entity.living.player.tab.TabListEntry;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.scoreboard.Scoreboard;
import org.spongepowered.api.scoreboard.Team;
import org.spongepowered.api.service.permission.SubjectData;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.Tristate;

import java.time.Instant;
import java.util.HashMap;
import java.util.Optional;
import java.util.Random;

@Plugin(id = "blueorpink", name = "blueorpink", version = "1.0", description = "blueorpink", authors = "Brqdford")
public class main {


    @Inject
    Game game;
    @Inject
    Logger logger;
    @Inject
    private PluginContainer container;

    private static main instance;

    public static HashMap<String, Instant> cooldown = new HashMap<>();

    @Listener
    public void onPreInit(GameInitializationEvent e) {
        instance = this;
    }
    Scoreboard scoreboard;

    @Listener
    public void onGameInit(GameInitializationEvent e) {
        logger.info(container.getName() +
                " running (version "
                + container.getVersion().orElse("UNSTABLE")
                + ")");


        CommandSpec commandman = CommandSpec.builder()
                .executor((CommandSource src, CommandContext args) -> {
                    Player player = (Player) src;
                    if (cooldown.containsKey(player.getName()) && Instant.now().minusSeconds(((Instant) cooldown.get(player.getName())).getEpochSecond()).getEpochSecond() < 60L) {
                        player.sendMessage((Text) Text.of("§cYou must wait " + (60L - Instant.now().minusSeconds(((Instant) cooldown.get(player.getName())).getEpochSecond()).getEpochSecond()) + " seconds to use this command."));
                        return CommandResult.success();
                    } else {
                    cooldown.put(player.getName(), Instant.now());
                        try {
                            NucleusAPI.getNicknameService().get().setNickname(player, (Text.of(TextColors.AQUA, player.getName())));
                        } catch (NicknameException nicknameException) {
                            nicknameException.printStackTrace();
                        }
                        src.getSubjectData().setPermission(SubjectData.GLOBAL_CONTEXT, "tabmanager.group.blue", Tristate.TRUE);
                    src.getSubjectData().setPermission(SubjectData.GLOBAL_CONTEXT, "tabmanager.group.pink", Tristate.UNDEFINED);
                    src.sendMessage(Text.of("§bYou have changed your name color to blue. You will have to reset your role if you had one."));
                    scoreboard = player.getScoreboard();
                    Random random = new Random();
                    int nnumber = random.nextInt(999999999);
                    Team teams = Team.builder().name("t" + nnumber).prefix(Text.of("§b")).allowFriendlyFire(true).canSeeFriendlyInvisibles(false).color(TextColors.AQUA).build();
                    teams.addMember(player.getTeamRepresentation());
                    scoreboard.registerTeam(teams);
                        Sponge.getServer().getOnlinePlayers().stream().forEach(p -> {
                            TabList tabList = p.getTabList();
                            Optional<TabListEntry> opEntry = tabList.getEntry(player.getUniqueId());
                            if(!opEntry.isPresent()){
                                return;
                            }
                            opEntry.get().setDisplayName(Text.of(TextColors.AQUA, player.getName()));
                        });
                    return CommandResult.success();
                }
                })
                .build();
        Sponge.getCommandManager().register(container, commandman, "blue");
        CommandSpec commandman2 = CommandSpec.builder()
                .executor((CommandSource src, CommandContext args) -> {
                    Player player = (Player) src;
                    if (cooldown.containsKey(player.getName()) && Instant.now().minusSeconds(((Instant) cooldown.get(player.getName())).getEpochSecond()).getEpochSecond() < 60L) {
                        player.sendMessage((Text) Text.of("§cYou must wait " + (60L - Instant.now().minusSeconds(((Instant) cooldown.get(player.getName())).getEpochSecond()).getEpochSecond()) + " seconds to use this command."));
                        return CommandResult.success();
                    } else {
                        cooldown.put(player.getName(), Instant.now());
                        try {
                            NucleusAPI.getNicknameService().get().setNickname(player, (Text.of(TextColors.LIGHT_PURPLE, player.getName())));
                        } catch (NicknameException nicknameException) {
                            nicknameException.printStackTrace();
                        }
                        src.getSubjectData().setPermission(SubjectData.GLOBAL_CONTEXT, "tabmanager.group.blue", Tristate.UNDEFINED);
                        src.getSubjectData().setPermission(SubjectData.GLOBAL_CONTEXT, "tabmanager.group.pink", Tristate.TRUE);
                        src.sendMessage(Text.of("§dYou have changed your name color to pink. You will have to reset your role if you had one."));
                        scoreboard = player.getScoreboard();
                        Random random = new Random();
                        int nnumber = random.nextInt(999999999);
                        Team teams = Team.builder().name("t" + nnumber).prefix(Text.of("§d")).allowFriendlyFire(true).canSeeFriendlyInvisibles(false).color(TextColors.LIGHT_PURPLE).build();
                        teams.addMember(player.getTeamRepresentation());
                        scoreboard.registerTeam(teams);
                        Sponge.getServer().getOnlinePlayers().stream().forEach(p -> {
                            TabList tabList = p.getTabList();
                            Optional<TabListEntry> opEntry = tabList.getEntry(player.getUniqueId());
                            if(!opEntry.isPresent()){
                                return;
                            }
                            opEntry.get().setDisplayName(Text.of(TextColors.LIGHT_PURPLE, player.getName()));
                        });
                        return CommandResult.success();
                    }
                })
                .build();
        Sponge.getCommandManager().register(container, commandman2, "pink");
        CommandSpec commandman3 = CommandSpec.builder()
                .executor((CommandSource src, CommandContext args) -> {
                    Player player = (Player) src;
                    if (cooldown.containsKey(player.getName()) && Instant.now().minusSeconds(((Instant) cooldown.get(player.getName())).getEpochSecond()).getEpochSecond() < 60L) {
                        player.sendMessage((Text) Text.of("§cYou must wait " + (60L - Instant.now().minusSeconds(((Instant) cooldown.get(player.getName())).getEpochSecond()).getEpochSecond()) + " seconds to use this command."));
                        return CommandResult.success();
                    }else {
                        cooldown.put(player.getName(), Instant.now());
                        try {
                            NucleusAPI.getNicknameService().get().setNickname(player, (Text.of(TextColors.WHITE, player.getName())));
                        } catch (NicknameException nicknameException) {
                            nicknameException.printStackTrace();
                        }
                        src.getSubjectData().setPermission(SubjectData.GLOBAL_CONTEXT, "tabmanager.group.blue", Tristate.UNDEFINED);
                        src.getSubjectData().setPermission(SubjectData.GLOBAL_CONTEXT, "tabmanager.group.pink", Tristate.UNDEFINED);
                        src.sendMessage(Text.of("§fYou have cleared your name color. You will have to reset your role if you had one."));
                        scoreboard = player.getScoreboard();
                        Random random = new Random();
                        int nnumber = random.nextInt(999999999);
                        Team teams = Team.builder().name("t" + nnumber).prefix(Text.of("§f")).allowFriendlyFire(true).canSeeFriendlyInvisibles(false).build();
                        teams.addMember(player.getTeamRepresentation());
                        scoreboard.registerTeam(teams);
                        Sponge.getServer().getOnlinePlayers().stream().forEach(p -> {
                            TabList tabList = p.getTabList();
                            Optional<TabListEntry> opEntry = tabList.getEntry(player.getUniqueId());
                            if(!opEntry.isPresent()){
                                return;
                            }
                            opEntry.get().setDisplayName(Text.of(TextColors.WHITE, player.getName()));
                        });
                    }

                    return CommandResult.success();
                })
                .build();
        Sponge.getCommandManager().register(container, commandman3, "nameclear");




    }

}
