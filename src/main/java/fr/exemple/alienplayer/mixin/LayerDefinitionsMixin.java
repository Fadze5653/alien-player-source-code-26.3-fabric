package fr.exemple.alienplayer.mixin;

import fr.exemple.alienplayer.AlienMeshBuilder;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;

/**
 * Remplace UNIQUEMENT le modèle principal des joueurs (normal et slim) par la forme alien.
 * Les couches cape, oreilles, armures et têtes gardent leur structure vanilla,
 * sinon elles ne trouvent plus leurs parties (ex. "cape").
 */
@Mixin(LayerDefinitions.class)
public abstract class LayerDefinitionsMixin {

    @Inject(method = "createRoots", at = @At("RETURN"), cancellable = true)
    private static void alienplayer$replacePlayerLayers(
            CallbackInfoReturnable<Map<ModelLayerLocation, LayerDefinition>> cir) {

        Map<ModelLayerLocation, LayerDefinition> original = cir.getReturnValue();
        Map<ModelLayerLocation, LayerDefinition> roots = new HashMap<>(original);

        LayerDefinition alien = LayerDefinition.create(
                AlienMeshBuilder.build(CubeDeformation.NONE), 64, 64);

        for (ModelLayerLocation key : original.keySet()) {
            String name = key.toString();
            if (name.equals("minecraft:player#main") || name.equals("minecraft:player_slim#main")) {
                roots.put(key, alien);
                System.out.println("[AlienPlayer] remplacée : " + name);
            }
        }

        cir.setReturnValue(roots);
    }
}
