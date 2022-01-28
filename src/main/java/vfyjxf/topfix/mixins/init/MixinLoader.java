package vfyjxf.topfix.mixins.init;

import org.spongepowered.asm.mixin.Mixins;

@zone.rong.mixinbooter.MixinLoader
public class MixinLoader {

    public MixinLoader(){
        Mixins.addConfiguration("mixins.topfix.json");
    }

}
