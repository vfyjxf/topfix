package vfyjxf.topfix.mixins.topaddons;

import io.github.drmanganese.topaddons.addons.AddonForge;
import io.github.drmanganese.topaddons.reference.Colors;
import io.github.drmanganese.topaddons.reference.Names;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import static mcjty.theoneprobe.api.IProbeInfo.STARTLOC;
import static mcjty.theoneprobe.api.IProbeInfo.ENDLOC;

/**
 * @author vfyjxf
 */
@Mixin(value = AddonForge.class, remap = false)
public abstract class MixinAddonForge {

    @Shadow protected abstract boolean showTOPAddonsTank(EntityPlayer player);

    /**
     * @author vfyjxf
     * @reason
     */
    @Overwrite
    public static IProbeInfo addTankElement(IProbeInfo probeInfo, Class<? extends TileEntity> clazz, FluidTank fluidTank, int i, ProbeMode mode, EntityPlayer player) {
        String tankName = "Tank";
        if (Names.tankNamesMap.containsKey(clazz)) {
            tankName = Names.tankNamesMap.get(clazz)[i];
        }

        if (fluidTank.getFluid() != null) {
            return AddonForge.addTankElement(probeInfo, tankName, STARTLOC + fluidTank.getFluid().getUnlocalizedName() + ENDLOC, fluidTank.getFluidAmount(), fluidTank.getCapacity(), "mB", Colors.getHashFromFluid(fluidTank.getFluid()), mode, player);
        } else {
            return AddonForge.addTankElement(probeInfo, tankName, "", 0, 0, "", 0xff777777, mode, player);
        }
    }
    /**
     * @author vfyjxf
     * @reason
     */
    @Overwrite
    public static IProbeInfo addTankElement(IProbeInfo probeInfo, String name, FluidTankInfo tankInfo, ProbeMode mode, EntityPlayer player) {
        String suffix = "mB";
        if (name.equals("Blood Altar")) suffix = "LP";

        if (tankInfo.fluid != null) {
            return AddonForge.addTankElement(probeInfo, name, STARTLOC + tankInfo.fluid.getUnlocalizedName() + ENDLOC, tankInfo.fluid.amount, tankInfo.capacity, suffix, Colors.getHashFromFluid(tankInfo.fluid), mode, player);
        } else {
            return AddonForge.addTankElement(probeInfo, name, "", 0, 0, "", 0xff777777, mode, player);
        }
    }
    /**
     * @author vfyjxf
     * @reason
     */
    @Overwrite
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        if (!showTOPAddonsTank(player))
            return;

        /* Disable for enderio, endertanks */
        String modid = ForgeRegistries.BLOCKS.getKey(blockState.getBlock()).getNamespace();
        if (modid.equals("enderio") || modid.equals("endertanks"))
            return;

        TileEntity tile = world.getTileEntity(data.getPos());

        if (tile != null && tile.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null)) {
            IFluidHandler capability = tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
            IFluidTankProperties[] tanks;
            try {
                tanks = capability.getTankProperties();
            } catch (NullPointerException ignored) {
                return;
            }

            for (int i = 0; i < tanks.length; i++) {
                IFluidTankProperties tank = tanks[i];
                String tankName = "Tank";
                if (Names.tankNamesMap.containsKey(tile.getClass())) {
                    tankName = Names.tankNamesMap.get(tile.getClass())[i];
                }

                if (tank.getContents() != null) {
                    AddonForge.addTankElement(probeInfo, tankName, STARTLOC + tank.getContents().getFluid().getUnlocalizedName(tank.getContents()) + ENDLOC, tank.getContents().amount, tank.getCapacity(), "mB", Colors.getHashFromFluid(tank), mode, player);
                } else {
                    AddonForge.addTankElement(probeInfo, tankName, "", 0, 0, "", 0xff777777, mode, player);
                }
            }

        }
    }
}
