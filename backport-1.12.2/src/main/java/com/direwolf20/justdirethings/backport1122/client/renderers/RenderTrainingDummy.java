package com.direwolf20.justdirethings.backport1122.client.renderers;

import com.direwolf20.justdirethings.backport1122.content.entity.EntityTrainingDummy;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderTrainingDummy extends RenderLiving<EntityTrainingDummy> {
    private static final ResourceLocation ZOMBIE_TEXTURE =
            new ResourceLocation("minecraft", "textures/entity/zombie/zombie.png");

    public static final IRenderFactory<EntityTrainingDummy> FACTORY = new IRenderFactory<EntityTrainingDummy>() {
        @Override
        public RenderTrainingDummy createRenderFor(RenderManager manager) {
            return new RenderTrainingDummy(manager);
        }
    };

    public RenderTrainingDummy(RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelZombie(), 0.5F);
        this.addLayer(new LayerBipedArmor(this));
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityTrainingDummy entity) {
        return ZOMBIE_TEXTURE;
    }
}
