package net.somyk.textfilter.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.block.entity.SignText;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.filter.FilteredMessage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

import static net.somyk.textfilter.TextFilter.MOD_ID;
import static net.somyk.textfilter.util.CheckText.deleteSymbols;
import static net.somyk.textfilter.util.ModConfig.SIGN_BP;

@Mixin(SignBlockEntity.class)
public class SignBlockEntityMixin {

	@Unique
	private PlayerEntity player;

	@Inject(method = "getTextWithMessages", at = @At("HEAD"))
	private void getPlayer(PlayerEntity player, List<FilteredMessage> messages, SignText text, CallbackInfoReturnable<SignText> cir){
		this.player = player;
	}

	@ModifyReturnValue(method = "getTextWithMessages", at = @At("RETURN"))
	private SignText listen(SignText original) {
		if (Permissions.check(player, String.join(".", MOD_ID, SIGN_BP))) return original;
		else return deleteSymbols(original);
	}
}