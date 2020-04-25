package net.minestom.server.entity.pathfinding;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Entity;
import net.minestom.server.utils.BlockPosition;
import net.minestom.server.utils.Position;
import net.minestom.server.utils.thread.MinestomThread;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

public class EntityPathFinder {

    private static ExecutorService pathfindingPool = new MinestomThread(MinecraftServer.THREAD_COUNT_ENTITIES_PATHFINDING, MinecraftServer.THREAD_NAME_ENTITIES_PATHFINDING);


    private Entity entity;

    public EntityPathFinder(Entity entity) {
        this.entity = entity;
    }

    public void getPath(Position target, Consumer<LinkedList<BlockPosition>> consumer) {
        pathfindingPool.execute(() -> {
            LinkedList<BlockPosition> blockPositions = new LinkedList<>();

            JPS jps = new JPS(entity.getInstance(), entity.getPosition(), target);

            boolean first = true;
            for (Position position : jps.getPath()) {
                if (first) {
                    first = false;
                    continue;
                }
                blockPositions.add(position.toBlockPosition());
            }

            consumer.accept(blockPositions);
        });
    }

}