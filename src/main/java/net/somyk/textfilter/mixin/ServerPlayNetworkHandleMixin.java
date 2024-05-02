package net.somyk.textfilter.mixin;

import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.List;

import static net.somyk.textfilter.TextFilter.MOD_ID;
import static net.somyk.textfilter.util.CheckText.deleteSymbols;
import static net.somyk.textfilter.util.ModConfig.BOOK_BP;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandleMixin {

    @Shadow public ServerPlayerEntity player;

    @ModifyArg(method = "onBookUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;filterTexts(Ljava/util/List;)Ljava/util/concurrent/CompletableFuture;"))
    private List<String> listen(List<String> texts){
        if (Permissions.check(player, String.join(".", MOD_ID, BOOK_BP))) return texts;
        else return deleteSymbols(texts);
    }
}
