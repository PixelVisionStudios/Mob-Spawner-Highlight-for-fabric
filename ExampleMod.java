package net.fabricmc.example;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.MobSpawnerLogic;
import net.fabricmc.example.mixin.AbstractSpawnerAccessor;
import net.minecraft.client.render.*;

public class ExampleMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        WorldRenderEvents.AFTER_ENTITIES.register(context -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.world == null || client.player == null) return;

            MatrixStack matrices = context.matrixStack();
            Camera camera = context.camera();
            Vec3d camPos = camera.getPos();

            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShader(GameRenderer::getPositionColorShader);

            BufferBuilder buffer = Tessellator.getInstance().getBuffer();
            buffer.begin(VertexFormat.DrawMode.LINES, VertexFormats.POSITION_COLOR);

            for (var be : client.world.blockEntities) {
                if (be instanceof MobSpawnerBlockEntity spawner) {
                    MobSpawnerLogic logic = spawner.getLogic();
                    int delay = ((AbstractSpawnerAccessor)(Object)logic).getSpawnDelay();

                    if (delay != 2) {
                        BlockPos pos = spawner.getPos();
                        Box box = new Box(pos).expand(0.01);

                        box = box.offset(-camPos.x, -camPos.y, -camPos.z);

                        drawBox(buffer, box, 1.0f, 0.0f, 0.0f, 1.0f); // red box
                    }
                }
            }

            BufferRenderer.drawWithShader(buffer.end());
            RenderSystem.disableBlend();
        });
    }

    private void drawBox(BufferBuilder buffer, Box box, float r, float g, float b, float a) {
        double x1 = box.minX, y1 = box.minY, z1 = box.minZ;
        double x2 = box.maxX, y2 = box.maxY, z2 = box.maxZ;

        // draw 12 edges of the box
        line(buffer, x1, y1, z1, x2, y1, z1, r, g, b, a);
        line(buffer, x2, y1, z1, x2, y1, z2, r, g, b, a);
        line(buffer, x2, y1, z2, x1, y1, z2, r, g, b, a);
        line(buffer, x1, y1, z2, x1, y1, z1, r, g, b, a);

        line(buffer, x1, y2, z1, x2, y2, z1, r, g, b, a);
        line(buffer, x2, y2, z1, x2, y2, z2, r, g, b, a);
        line(buffer, x2, y2, z2, x1, y2, z2, r, g, b, a);
        line(buffer, x1, y2, z2, x1, y2, z1, r, g, b, a);

        line(buffer, x1, y1, z1, x1, y2, z1, r, g, b, a);
        line(buffer, x2, y1, z1, x2, y2, z1, r, g, b, a);
        line(buffer, x2, y1, z2, x2, y2, z2, r, g, b, a);
        line(buffer, x1, y1, z2, x1, y2, z2, r, g, b, a);
    }

    private void line(BufferBuilder buffer, double x1, double y1, double z1, double x2, double y2, double z2, float r, float g, float b, float a) {
        buffer.vertex(x1, y1, z1).color(r, g, b, a).next();
        buffer.vertex(x2, y2, z2).color(r, g, b, a).next();
    }
}
