package com.PinkCats.worldprotect.mixin;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.ticks.LevelTickAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

@Mixin(value = Level.class,remap = false)
public class BlockBreakMixin implements LevelAccessor {

    @Shadow
    private long subTickCount;

    @Shadow
    public long nextSubTickCount() {
        return this.subTickCount++;
    }



    @Inject(method = "removeBlock",at=@At("HEAD" ))
    public void removeBlock(BlockPos BlockPos, boolean p_46624_, CallbackInfoReturnable<Boolean> cir) {
        BlockState blockstate = this.getBlockState(BlockPos);
        //System.out.println("removeBlock");
        //System.out.println(BlockPos + "  " + blockstate);
    }

    @Inject(method = "destroyBlock",at=@At("HEAD" ))
    public void BreakEvent(BlockPos BlockPos, boolean p_46627_, Entity entity, int p_46629_, CallbackInfoReturnable<Boolean> cir){
        BlockState blockstate = this.getBlockState(BlockPos);

        //System.out.println(BlockPos + "  " + blockstate);
        //System.out.println(p_46627_);
        //System.out.println(entity);
        //System.out.println(p_46629_);


    }


    @Override
    public LevelTickAccess<Block> getBlockTicks() {
        return null;
    }

    @Override
    public LevelTickAccess<Fluid> getFluidTicks() {
        return null;
    }

    @Shadow
    public LevelData getLevelData() {
        return null;
    }

    @Shadow
    public DifficultyInstance getCurrentDifficultyAt(BlockPos p_46800_) {
        return null;
    }

    @Shadow
    public @Nullable MinecraftServer getServer() {
        return null;
    }

    @Override
    public ChunkSource getChunkSource() {
        return null;
    }

    @Shadow
    public RandomSource getRandom() {
        return null;
    }

    @Shadow
    public void playSound(@Nullable Player p_46775_, BlockPos p_46776_, SoundEvent p_46777_, SoundSource p_46778_, float p_46779_, float p_46780_) {

    }

    @Shadow
    public void addParticle(ParticleOptions p_46783_, double p_46784_, double p_46785_, double p_46786_, double p_46787_, double p_46788_, double p_46789_) {

    }

    @Override
    public void levelEvent(@Nullable Player p_46771_, int p_46772_, BlockPos p_46773_, int p_46774_) {

    }

    @Override
    public void gameEvent(GameEvent p_220404_, Vec3 p_220405_, GameEvent.Context p_220406_) {

    }

    @Override
    public float getShade(Direction p_45522_, boolean p_45523_) {
        return 0;
    }

    @Shadow
    public LevelLightEngine getLightEngine() {
        return null;
    }

    @Shadow
    public WorldBorder getWorldBorder() {
        return null;
    }

    @Shadow
    public @Nullable BlockEntity getBlockEntity(BlockPos p_45570_) {
        return null;
    }

    @Shadow
    public BlockState getBlockState(BlockPos p_45571_) {
        return null;
    }

    @Shadow
    public FluidState getFluidState(BlockPos p_45569_) {
        return null;
    }

    @Shadow
    public List<Entity> getEntities(@Nullable Entity p_45936_, AABB p_45937_, Predicate<? super Entity> p_45938_) {
        return List.of();
    }

    @Shadow
    public <T extends Entity> List<T> getEntities(EntityTypeTest<Entity, T> p_151464_, AABB p_151465_, Predicate<? super T> p_151466_) {
        return List.of();
    }

    @Override
    public List<? extends Player> players() {
        return List.of();
    }

    @Shadow
    public @Nullable ChunkAccess getChunk(int p_46823_, int p_46824_, ChunkStatus p_46825_, boolean p_46826_) {
        return null;
    }

    @Shadow
    public int getHeight(Heightmap.Types p_46827_, int p_46828_, int p_46829_) {
        return 0;
    }

    @Shadow
    public int getSkyDarken() {
        return 0;
    }

    @Shadow
    public BiomeManager getBiomeManager() {
        return null;
    }

    @Override
    public Holder<Biome> getUncachedNoiseBiome(int p_204159_, int p_204160_, int p_204161_) {
        return null;
    }

    @Shadow
    public boolean isClientSide() {
        return false;
    }

    @Shadow
    public int getSeaLevel() {
        return 0;
    }

    @Shadow
    public DimensionType dimensionType() {
        return null;
    }

    @Shadow
    public RegistryAccess registryAccess() {
        return null;
    }

    @Override
    public FeatureFlagSet enabledFeatures() {
        return null;
    }

    @Shadow
    public boolean isStateAtPosition(BlockPos p_46938_, Predicate<BlockState> p_46939_) {
        return false;
    }

    @Shadow
    public boolean isFluidAtPosition(BlockPos p_151584_, Predicate<FluidState> p_151585_) {
        return false;
    }

    @Shadow
    public boolean setBlock(BlockPos p_46947_, BlockState p_46948_, int p_46949_, int p_46950_) {
        return false;
    }

    @Shadow
    public boolean removeBlock(BlockPos p_46951_, boolean p_46952_) {
        return false;
    }

    @Shadow
    public boolean destroyBlock(BlockPos p_46957_, boolean p_46958_, @Nullable Entity p_46959_, int p_46960_) {
        return false;
    }
}
