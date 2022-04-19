package asyetuntitled.datagenerator;

import asyetuntitled.AsYetUntitled;
import asyetuntitled.common.block.BlocksRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockStatesGenerator extends BlockStateProvider {

    public BlockStatesGenerator(DataGenerator gen, ExistingFileHelper helper) {
        super(gen, AsYetUntitled.MODID, helper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(BlocksRegistry.TOUCHSTONE.get());
    }
}