package asyetuntitled.datagenerator;

import asyetuntitled.AsYetUntitled;
import asyetuntitled.common.item.ItemsRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class LanguageProviderGenerator extends LanguageProvider {

    public LanguageProviderGenerator(DataGenerator gen, String locale) {
        super(gen, AsYetUntitled.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        add("itemGroup." + AsYetUntitled.MODID, AsYetUntitled.NAME);
        add(ItemsRegistry.BLANK_SLOT.get(), "Blank Slot");
        add(ItemsRegistry.BACKPACK_BASE.get(), "Backpack Nether");
    }
}
