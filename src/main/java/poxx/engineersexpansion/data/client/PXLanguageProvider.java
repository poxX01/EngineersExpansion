package poxx.engineersexpansion.data.client;

import net.minecraft.data.DataGenerator;
import net.minecraft.item.BlockItem;
import net.minecraftforge.common.data.LanguageProvider;
import poxx.engineersexpansion.EngineersExpansion;
import poxx.engineersexpansion.common.PXContent;

public final class PXLanguageProvider extends LanguageProvider {
    PXLanguageProvider(DataGenerator dataGenerator){
        super(dataGenerator, EngineersExpansion.MODID, "en_us");
    }

    public static final String infoEnergyStored = stringWithModID("info", "energyStored");

    public void addTranslations(){
        PXContent.PXBlocks.BLOCK_REGISTER.getEntries().forEach(element -> add(element.get(), translateToDislpayName(element.getId().getPath())));
        PXContent.PXItems.ITEM_REGISTER.getEntries().stream()
                .filter(itemRegistryObject -> !(itemRegistryObject.get() instanceof BlockItem))
                .forEach(element -> add(element.get(), translateToDislpayName(element.getId().getPath())));

        add(stringWithModID("itemGroup"), "Engineer's Expansion");
        add(infoEnergyStored, "Energy stored: %1$dRF/%2$dRF");
    }
    private static String translateToDislpayName(String idName){
        String[] currentName = idName.split("_");
        for (int index = 0; index < currentName.length; index++){
            String currentString = currentName[index];
            currentName[index] = Character.toUpperCase(currentString.charAt(0)) + currentString.substring(1);
        }
        return String.join(" ", currentName);
    }
    private static String stringWithModID(String prefix, String suffix){
        return prefix + "."+EngineersExpansion.MODID+"."+ suffix;
    }
    private static String stringWithModID(String prefix){
        return prefix + "."+EngineersExpansion.MODID;
    }
}
