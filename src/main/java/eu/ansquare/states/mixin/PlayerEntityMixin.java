package eu.ansquare.states.mixin;

import com.mojang.datafixers.util.Either;
import eu.ansquare.states.States;
import eu.ansquare.states.api.StatePermission;
import eu.ansquare.states.cca.StatesEntityComponents;
import eu.ansquare.states.item.StatesItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.OptionalInt;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}
  /*  @Inject(method = "isBlockBreakingRestricted", at = @At("HEAD"), cancellable = true)
	public void onIsBlockBreakingRestricted(World world, BlockPos pos, GameMode gameMode, CallbackInfoReturnable<Boolean> cir) {
		if(!StatePermission.permissionAt(world.getChunk(pos), (PlayerEntity) (Object) this).maybuild){
			cir.setReturnValue(true);
		}
	}*/


	@Inject(method = "interact", at = @At("HEAD"), cancellable = true)
	public void onInteract(Entity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir){

		if(entity instanceof ServerPlayerEntity && !getWorld().isClient()){
			if(getStackInHand(hand).isOf(StatesItems.NOTEPAD)){
				NbtCompound nbt = getStackInHand(hand).getOrCreateNbt();
				NbtList element = nbt.getList("players", 11);
				element.add(NbtHelper.fromUuid(entity.getUuid()));
				nbt.put("players", element);
			}
		}
	}
}
