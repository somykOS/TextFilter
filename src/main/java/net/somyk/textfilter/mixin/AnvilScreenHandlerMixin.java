//    This Source Code Form is subject to the terms of the Mozilla Public
//    License, v. 2.0. If a copy of the MPL was not distributed with this
//    file, You can obtain one at https://mozilla.org/MPL/2.0/.

package net.somyk.textfilter.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.ForgingSlotsManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import static net.somyk.textfilter.TextFilter.MOD_ID;
import static net.somyk.textfilter.util.CheckText.deleteSymbols;
import static net.somyk.textfilter.util.ModConfig.ANVIL_BP;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {

    @Shadow @Nullable private String newItemName;

    public AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context, ForgingSlotsManager forgingSlotsManager) {
        super(type, syncId, playerInventory, context, forgingSlotsManager);
    }

    @WrapOperation(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/StringHelper;isBlank(Ljava/lang/String;)Z"))
    private boolean listen(String string, Operation<Boolean> original){
        if (player instanceof ServerPlayerEntity serverPlayerEntity &&
                Permissions.check(serverPlayerEntity, String.join(".", MOD_ID, ANVIL_BP))) return original.call(string);
        else {
            this.newItemName = deleteSymbols(string);
            return original.call(newItemName);
        }
    }
}
