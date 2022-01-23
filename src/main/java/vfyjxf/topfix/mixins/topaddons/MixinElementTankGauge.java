package vfyjxf.topfix.mixins.topaddons;

import io.github.drmanganese.topaddons.elements.ElementTankGauge;
import io.github.drmanganese.topaddons.styles.ProgressStyleTank;
import mcjty.theoneprobe.apiimpl.client.ElementProgressRender;
import mcjty.theoneprobe.apiimpl.client.ElementTextRender;
import mcjty.theoneprobe.rendering.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import static io.github.drmanganese.topaddons.elements.ElementRenderHelper.drawSmallText;
import static mcjty.theoneprobe.api.IProbeInfo.ENDLOC;
import static mcjty.theoneprobe.api.IProbeInfo.STARTLOC;

@Mixin(value = ElementTankGauge.class, remap = false)
public class MixinElementTankGauge {

    @Shadow
    @Final
    private String fluidName;

    @Shadow @Final private int capacity;

    @Shadow @Final private int amount;

    @Shadow @Final private int color1;

    @Shadow @Final private int color2;

    @Shadow @Final private boolean sneaking;

    @Shadow @Final private String tankName;

    @Shadow @Final private String suffix;

    private static String stylifyString(String text) {
        while (text.contains(STARTLOC) && text.contains(ENDLOC)) {
            int start = text.indexOf(STARTLOC);
            int end = text.indexOf(ENDLOC);
            if (start < end) {
                // Translation is needed
                String left = text.substring(0, start);
                String middle = text.substring(start + 2, end);
                middle = I18n.format(middle).trim();
                String right = text.substring(end + 2);
                System.out.println(text);
                text = left + middle + right;
            } else {
                break;
            }
        }
        return text;
    }

    /**
     * @author vfyjxf
     * @reason
     */
    @Overwrite
    public void render(int x, int y) {
        if (capacity > 0) {
            ElementProgressRender.render(new ProgressStyleTank().filledColor(color1).alternateFilledColor(color2), amount, capacity, x, y, 100, sneaking ? 12 : 8);
        } else {
            ElementProgressRender.render(new ProgressStyleTank(), amount, capacity, x, y, 100, sneaking ? 12 : 8);
        }

        if (sneaking) {
            for (int i = 1; i < 10; i++) {
                RenderHelper.drawVerticalLine(x + i * 10, y + 1, y + (i == 5 ? 11 : 6), 0xff767676);
            }

            ElementTextRender.render((capacity > 0) ? amount + "/" + capacity + " " + suffix : I18n.format("topaddons:tank_empty"), x + 3, y + 2);
            drawSmallText(x + 99 - Minecraft.getMinecraft().fontRenderer.getStringWidth(stylifyString(fluidName)) / 2, y + 13, stylifyString(fluidName), color1);
        }

        drawSmallText(sneaking ? x + 1 :  x +2, sneaking ? y + 13 : y + 2, tankName, 0xffffffff);
        RenderHelper.drawVerticalLine(x + 99, y, y + (sneaking ? 12 : 8), 0xff969696);
    }

}
