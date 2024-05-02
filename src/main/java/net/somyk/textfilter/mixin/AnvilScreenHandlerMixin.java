package net.somyk.textfilter.mixin;

import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import static net.somyk.textfilter.TextFilter.MOD_ID;
import static net.somyk.textfilter.util.CheckText.deleteSymbols;
import static net.somyk.textfilter.util.ModConfig.ANVIL_BP;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {

    public AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(type, syncId, playerInventory, context);
    }

    @ModifyArg(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/text/Text;literal(Ljava/lang/String;)Lnet/minecraft/text/MutableText;"))
    private String listen(String newItemName){
        if (Permissions.check(player, String.join(".", MOD_ID, ANVIL_BP))) return newItemName;
        else return deleteSymbols(newItemName);
    }
}
