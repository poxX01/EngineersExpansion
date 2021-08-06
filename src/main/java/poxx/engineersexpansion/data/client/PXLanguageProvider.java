package poxx.engineersexpansion.data.client;

import net.minecraft.data.DataGenerator;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fml.RegistryObject;
import poxx.engineersexpansion.EngineersExpansion;
import poxx.engineersexpansion.common.PXContent;

final class PXLanguageProvider extends LanguageProvider {
    PXLanguageProvider(DataGenerator dataGenerator){
        super(dataGenerator, EngineersExpansion.MODID, "en_us");
    }

    public void addTranslations(){
        PXContent.PXBlocks.BLOCK_REGISTER.getEntries().forEach(element -> add(element.get(), translateToDislpayName(element.getId().getPath())));
        for (RegistryObject<? extends Item> element : PXContent.PXItems.ITEM_REGISTER.getEntries()){
            Item elementItem = element.get();
            if (!(elementItem instanceof BlockItem)) {
                add(element.get(), translateToDislpayName(element.getId().getPath()));
            }
        }

        add("itemGroup.engineersexpansion", "Engineer's Expansion");
    }

    private String translateToDislpayName(String idName){
        String[] currentName = idName.split("_");
        for (int index = 0; index < currentName.length; index++){
            String currentString = currentName[index];
            currentName[index] = Character.toUpperCase(currentString.charAt(0)) + currentString.substring(1);
        }
        return String.join(" ", currentName);
    }
}
