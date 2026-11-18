package fr.exemple.alienplayer.mixin;

import fr.exemple.alienplayer.AlienMeshBuilder;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

/**
 * Armures alien : même répartition par pièce que HumanoidModel.createArmorMeshSet.
 * - casque    : crâne alien seul, sans le visage
 * - plastron  : corps + bras
 * - jambières : jambes + bassin (corps), déformation interne
 * - bottes    : jambes
 */
@Mixin(HumanoidModel.class)
public abstract class ArmorMeshMixin {

    @Inject(method = "createArmorMeshSet(Lnet/minecraft/client/model/geom/builders/CubeDeformation;Lnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/renderer/entity/ArmorModelSet;",
            at = @At("HEAD"), cancellable = true)
    private static void alienplayer$alienArmor(CubeDeformation innerDeformation,
                                               CubeDeformation outerDeformation,
                                               CallbackInfoReturnable<ArmorModelSet<MeshDefinition>> cir) {
        MeshDefinition head = AlienMeshBuilder.build(outerDeformation, false);
        head.getRoot().retainPartsAndChildren(Set.of("head"));

        MeshDefinition chest = AlienMeshBuilder.build(outerDeformation);
        chest.getRoot().retainExactParts(Set.of("body", "left_arm", "right_arm"));

        MeshDefinition legs = AlienMeshBuilder.build(innerDeformation);
        legs.getRoot().retainExactParts(Set.of("left_leg", "right_leg", "body"));

        MeshDefinition feet = AlienMeshBuilder.build(outerDeformation);
        feet.getRoot().retainExactParts(Set.of("left_leg", "right_leg"));

        cir.setReturnValue(new ArmorModelSet<>(head, chest, legs, feet));
    }
}
