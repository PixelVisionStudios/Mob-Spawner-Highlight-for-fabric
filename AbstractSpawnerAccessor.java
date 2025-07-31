package net.fabricmc.example.mixin;

import net.minecraft.world.MobSpawnerLogic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MobSpawnerLogic.class)
public interface AbstractSpawnerAccessor {
    @Accessor("spawnDelay")
    int getSpawnDelay();
}
