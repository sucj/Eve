package net.optifine.util;

public class CacheLocalByte {
    private final int maxX;
    private final int maxY;
    private final int maxZ;
    private int offsetX = 0;
    private int offsetY = 0;
    private int offsetZ = 0;
    private final byte[][][] cache;
    private byte[] lastZs = null;
    private int lastDz = 0;

    public CacheLocalByte(int maxX, int maxY, int maxZ) {
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
        this.cache = new byte[maxX][maxY][maxZ];
        this.resetCache();
    }

    public void resetCache() {
        for (int i = 0; i < this.maxX; ++i) {
            byte[][] abyte = this.cache[i];

            for (int j = 0; j < this.maxY; ++j) {
                byte[] abyte1 = abyte[j];

                for (int k = 0; k < this.maxZ; ++k) {
                    abyte1[k] = -1;
                }
            }
        }
    }

    public void setOffset(int x, int y, int z) {
        this.offsetX = x;
        this.offsetY = y;
        this.offsetZ = z;
        this.resetCache();
    }

    public byte get(int x, int y, int z) {
        try {
            this.lastZs = this.cache[x - this.offsetX][y - this.offsetY];
            this.lastDz = z - this.offsetZ;
            return this.lastZs[this.lastDz];
        } catch (ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) {
            arrayindexoutofboundsexception.printStackTrace();
            return (byte) -1;
        }
    }

    public void setLast(byte val) {
        try {
            this.lastZs[this.lastDz] = val;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
