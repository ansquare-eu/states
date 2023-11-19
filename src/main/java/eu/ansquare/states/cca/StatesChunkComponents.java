package eu.ansquare.states.cca;

import dev.onyxstudios.cca.api.v3.chunk.ChunkComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.chunk.ChunkComponentInitializer;
import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import eu.ansquare.states.States;
import net.minecraft.util.Identifier;

public class StatesChunkComponents implements ChunkComponentInitializer {
	public static final ComponentKey<ClaimedChunkComponent> CLAIMED_CHUNK_COMPONENT =
			ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(States.MODID, "claimedchunk"), ClaimedChunkComponent.class);

	@Override
	public void registerChunkComponentFactories(ChunkComponentFactoryRegistry registry) {
		registry.register(CLAIMED_CHUNK_COMPONENT, world -> new ClaimedChunkComponent());
	}
}
