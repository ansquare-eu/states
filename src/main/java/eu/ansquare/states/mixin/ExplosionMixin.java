package eu.ansquare.states.mixin;

import eu.ansquare.states.States;
import eu.ansquare.states.api.StatePermission;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Explosion.class)
public abstract class ExplosionMixin {
	@Shadow
	@Final
	private ObjectArrayList<BlockPos> affectedBlocks;

	@Shadow
	@Nullable
	public abstract LivingEntity getCausingEntity();

	@Shadow
	@Final
	private World world;

	@Inject(method = "affectWorld", at = @At("HEAD"), cancellable = true)
	public void onAffectWorld(boolean particles, CallbackInfo ci) {
		if (this.getCausingEntity() instanceof PlayerEntity player) {
			this.affectedBlocks.forEach(blockPos -> {
				if (!StatePermission.permissionAt(blockPos, world, player).maybuild){
					affectedBlocks.clear();
				}
			});
		}
	}
}
