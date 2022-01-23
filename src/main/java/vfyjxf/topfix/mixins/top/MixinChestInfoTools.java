package vfyjxf.topfix.mixins.top;

import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.apiimpl.providers.ChestInfoTools;
import mcjty.theoneprobe.config.Config;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.apiimpl.styles.ItemStyle;
import mcjty.theoneprobe.apiimpl.styles.LayoutStyle;

import java.util.List;



@Mixin(value = ChestInfoTools.class,remap = false)
public class MixinChestInfoTools {

    /**
     * @author vfyjxf
     * @reason
     */
    @Overwrite
    private static void showChestContents(IProbeInfo probeInfo, World world, BlockPos pos, List<ItemStack> stacks, boolean detailed) {

        IProbeInfo vertical = null;
        IProbeInfo horizontal = null;

        int rows = 0;
        int idx = 0;

        vertical = probeInfo.vertical(probeInfo.defaultLayoutStyle().borderColor(Config.chestContentsBorderColor).spacing(0));

        if (detailed) {
            for (ItemStack stackInSlot : stacks) {
                horizontal = vertical.horizontal(new LayoutStyle().spacing(10).alignment(ElementAlignment.ALIGN_CENTER));
                horizontal.item(stackInSlot, new ItemStyle().width(16).height(16))
                        .itemLabel(stackInSlot);
            }
        } else {
            for (ItemStack stackInSlot : stacks) {
                if (idx % 10 == 0) {
                    horizontal = vertical.horizontal(new LayoutStyle().spacing(0));
                    rows++;
                    if (rows > 4) {
                        break;
                    }
                }
                horizontal.item(stackInSlot);
                idx++;
            }
        }
    }
}
