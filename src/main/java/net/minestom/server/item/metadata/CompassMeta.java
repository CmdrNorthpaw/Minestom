package net.minestom.server.item.metadata;

import org.jetbrains.annotations.NotNull;
import org.jglrxavpok.hephaistos.nbt.NBTCompound;

public class CompassMeta implements ItemMeta {

    private boolean lodestoneTracked;
    private String lodestoneDimension;
    private int x, y, z;

    public boolean isLodestoneTracked() {
        return lodestoneTracked;
    }

    public void setLodestoneTracked(boolean lodestoneTracked) {
        this.lodestoneTracked = lodestoneTracked;
    }

    public String getLodestoneDimension() {
        return lodestoneDimension;
    }

    public void setLodestoneDimension(String lodestoneDimension) {
        this.lodestoneDimension = lodestoneDimension;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    @Override
    public boolean hasNbt() {
        return true;
    }

    @Override
    public boolean isSimilar(@NotNull ItemMeta itemMeta) {
        if (!(itemMeta instanceof CompassMeta))
            return false;
        CompassMeta compassMeta = (CompassMeta) itemMeta;
        return (compassMeta.lodestoneTracked == lodestoneTracked) &&
                (compassMeta.lodestoneDimension.equals(lodestoneDimension)) &&
                (compassMeta.x == x && compassMeta.y == y && compassMeta.z == z);
    }

    @Override
    public void read(@NotNull NBTCompound compound) {
        if (compound.containsKey("LodestoneTracked")) {
            this.lodestoneTracked = compound.getByte("LodestoneTracked") == 1;
            // TODO get boolean
        }
        if (compound.containsKey("LodestoneDimension")) {
            this.lodestoneDimension = compound.getString("LodestoneDimension");
        }
        if (compound.containsKey("LodestonePos")) {
            final NBTCompound posCompound = compound.getCompound("LodestonePos");
            this.x = posCompound.getInt("X");
            this.y = posCompound.getInt("Y");
            this.z = posCompound.getInt("Z");
        }
    }

    @Override
    public void write(@NotNull NBTCompound compound) {
        compound.setByte("LodestoneTracked", (byte) (lodestoneTracked ? 1 : 0));
        if(lodestoneDimension != null) {
            compound.setString("LodestoneDimension", lodestoneDimension);
        }

        {
            NBTCompound posCompound = new NBTCompound();
            posCompound.setInt("X", x);
            posCompound.setInt("Y", y);
            posCompound.setInt("Z", z);
            compound.set("LodestonePos", posCompound);
        }
    }

    @NotNull
    @Override
    public ItemMeta copy() {
        CompassMeta compassMeta = new CompassMeta();
        compassMeta.lodestoneTracked = lodestoneTracked;
        compassMeta.lodestoneDimension = lodestoneDimension;
        compassMeta.x = x;
        compassMeta.y = y;
        compassMeta.z = z;

        return compassMeta;
    }
}
