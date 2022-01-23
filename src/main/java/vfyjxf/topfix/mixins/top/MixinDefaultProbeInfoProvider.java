package vfyjxf.topfix.mixins.top;

import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.TextStyleClass;
import mcjty.theoneprobe.apiimpl.ProbeConfig;
import mcjty.theoneprobe.apiimpl.elements.ElementProgress;
import mcjty.theoneprobe.apiimpl.providers.DefaultProbeInfoProvider;
import mcjty.theoneprobe.config.Config;
import net.minecraftforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;


@Mixin(value = DefaultProbeInfoProvider.class, remap = false)
public class MixinDefaultProbeInfoProvider {


    /**
     * @reason
     * @author vfyjxf
     */
    @Overwrite
    private void addFluidInfo(IProbeInfo probeInfo, ProbeConfig config, FluidStack fluidStack, int maxContents) {
        int contents = fluidStack == null ? 0 : fluidStack.amount;
        if (fluidStack != null) {
            probeInfo.text(TextStyleClass.NAME + "Liquid: " + " {*" + fluidStack.getUnlocalizedName() +"*}");
        }
        if (config.getTankMode() == 1) {
            probeInfo.progress(contents, maxContents,
                    probeInfo.defaultProgressStyle()
                            .suffix("mB")
                            .filledColor(Config.tankbarFilledColor)
                            .alternateFilledColor(Config.tankbarAlternateFilledColor)
                            .borderColor(Config.tankbarBorderColor)
                            .numberFormat(Config.tankFormat));
        } else {
            probeInfo.text(TextStyleClass.PROGRESS + ElementProgress.format(contents, Config.tankFormat, "mB"));
        }
    }



}
