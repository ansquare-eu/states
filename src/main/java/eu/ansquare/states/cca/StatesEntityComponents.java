package eu.ansquare.states.cca;

import dev.onyxstudios.cca.api.v3.chunk.ChunkComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.chunk.ChunkComponentInitializer;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import eu.ansquare.states.States;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class StatesEntityComponents implements EntityComponentInitializer {
	public static final ComponentKey<CitizenComponent> CITIZEN_COMPONENT =
			ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(States.MODID, "citizen"), CitizenComponent.class);

	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.registerFor(LivingEntity.class, CITIZEN_COMPONENT, world -> new CitizenComponent());
	}
}
