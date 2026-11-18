package fr.exemple.alienplayer;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

/**
 * Géométrie alien du joueur. Minecraft ne gère que des boîtes :
 * la tête est donc faite de plusieurs blocs (crâne bombé + visage plus étroit).
 *
 * Les noms des parties suivent PlayerModel.createMesh, donc les armures
 * et les objets en main restent accrochés.
 */
public final class AlienMeshBuilder {

    private AlienMeshBuilder() {}

    public static MeshDefinition build(CubeDeformation d) {
        return build(d, true);
    }

    /**
     * @param withFace false pour la géométrie du casque : seul le crâne est gardé.
     */
    public static MeshDefinition build(CubeDeformation d, boolean withFace) {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        // Tête : crâne bombé en haut (12 wide), visage plus étroit en dessous (8 wide)
        PartDefinition head = root.addOrReplaceChild("head",
                CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-6F, -19F, -3F, 12F, 8F, 6F, d),
                PartPose.ZERO);
        if (withFace) {
            head.addOrReplaceChild("face",
                    CubeListBuilder.create().texOffs(0, 20)
                            .addBox(-4F, -11F, -3F, 8F, 11F, 6F, d),
                    PartPose.ZERO);
        }
        head.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);

        // Corps : fin, 6 x 12 x 3
        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(16, 16)
                        .addBox(-3F, 0F, -1.5F, 6F, 12F, 3F, d),
                PartPose.ZERO);
        body.addOrReplaceChild("jacket", CubeListBuilder.create(), PartPose.ZERO);

        // Bras : longs et fins, pendants
        PartDefinition rightArm = root.addOrReplaceChild("right_arm",
                CubeListBuilder.create().texOffs(40, 16)
                        .addBox(-1.5F, -2F, -1.5F, 3F, 14F, 3F, d),
                PartPose.offset(-4.5F, 2F, 0F));
        PartDefinition leftArm = root.addOrReplaceChild("left_arm",
                CubeListBuilder.create().texOffs(32, 46)
                        .addBox(-1.5F, -2F, -1.5F, 3F, 14F, 3F, d),
                PartPose.offset(4.5F, 2F, 0F));
        rightArm.addOrReplaceChild("right_sleeve", CubeListBuilder.create(), PartPose.ZERO);
        leftArm.addOrReplaceChild("left_sleeve", CubeListBuilder.create(), PartPose.ZERO);

        // Jambes : fines
        PartDefinition rightLeg = root.addOrReplaceChild("right_leg",
                CubeListBuilder.create().texOffs(0, 30)
                        .addBox(-1.5F, 0F, -1.5F, 3F, 12F, 3F, d),
                PartPose.offset(-1.5F, 12F, 0F));
        PartDefinition leftLeg = root.addOrReplaceChild("left_leg",
                CubeListBuilder.create().texOffs(16, 46)
                        .addBox(-1.5F, 0F, -1.5F, 3F, 12F, 3F, d),
                PartPose.offset(1.5F, 12F, 0F));
        rightLeg.addOrReplaceChild("right_pants", CubeListBuilder.create(), PartPose.ZERO);
        leftLeg.addOrReplaceChild("left_pants", CubeListBuilder.create(), PartPose.ZERO);

        return mesh;
    }
}
