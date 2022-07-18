package atomic.mcbomber.commands;

import atomic.mcbomber.Addon;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import meteordevelopment.meteorclient.systems.commands.Command;
import meteordevelopment.meteorclient.utils.player.ChatUtils;
import net.minecraft.command.CommandSource;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class bind extends Command {
    public bind() {
        super("mcbind", "attempts to bind");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(context -> {
            ChatUtils.info(Addon.broadcast("TEST"));
            return SINGLE_SUCCESS;
        });
    }
}
