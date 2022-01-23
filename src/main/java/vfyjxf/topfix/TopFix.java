package vfyjxf.topfix;

import net.minecraftforge.fml.common.Mod;

import java.util.logging.Logger;

@Mod(
        modid = TopFix.MOD_ID,
        name = TopFix.MOD_NAME,
        version = TopFix.VERSION,
        dependencies = "required-after:theoneprobe"
)
public class TopFix {

    public static final String MOD_ID = "topfix";
    public static final String MOD_NAME = "Top Fix";
    public static final String VERSION = "1.0-SNAPSHOT";
    public static final Logger logger = Logger.getLogger("topfix");

}
