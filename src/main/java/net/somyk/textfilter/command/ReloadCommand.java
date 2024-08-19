package net.somyk.textfilter.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.somyk.textfilter.util.CheckText;

import static net.somyk.textfilter.TextFilter.MOD_ID;

public class ReloadCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        LiteralCommandNode<ServerCommandSource> root = CommandManager
                .literal(MOD_ID)
                .requires(source -> source.hasPermissionLevel(4) || Permissions.check(source, MOD_ID + ".reload"))
                .build();

        LiteralCommandNode<ServerCommandSource> reloadNode = CommandManager
                .literal("reload")
                .executes(ReloadCommand::executeReload)
                .build();

        dispatcher.getRoot().addChild(root);
        root.addChild(reloadNode);
    }

    private static int executeReload(CommandContext<ServerCommandSource> context) {
        CheckText.reloadPatterns();
        context.getSource().sendFeedback(() -> Text.literal("TextFilter successfully reloaded").formatted(Formatting.GREEN), true);
        return 1;
    }
}
