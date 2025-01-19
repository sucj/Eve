package net.minecraft.util;

public class LongHashMap<V> {
    private transient LongHashMap.Entry<V>[] hashArray = new LongHashMap.Entry[4096];
    private transient int numHashElements;
    private int mask;
    private int capacity = 3072;
    private final float percentUseable = 0.75F;
    private transient volatile int modCount;

    public LongHashMap() {
        this.mask = this.hashArray.length - 1;
    }

    private static int getHashedKey(long originalKey) {
        return (int) (originalKey ^ originalKey >>> 27);
    }

    private static int hash(int integer) {
        integer = integer ^ integer >>> 20 ^ integer >>> 12;
        return integer ^ integer >>> 7 ^ integer >>> 4;
    }

    private static int getHashIndex(int p_76158_0_, int p_76158_1_) {
        return p_76158_0_ & p_76158_1_;
    }

    public int getNumHashElements() {
        return this.numHashElements;
    }

    public V getValueByKey(long p_76164_1_) {
        int i = getHashedKey(p_76164_1_);

        for (LongHashMap.Entry<V> entry = this.hashArray[getHashIndex(i, this.mask)]; entry != null; entry = entry.nextEntry) {
            if (entry.key == p_76164_1_) {
                return entry.value;
            }
        }

        return null;
    }

    public boolean containsItem(long p_76161_1_) {
        return this.getEntry(p_76161_1_) != null;
    }

    final LongHashMap.Entry<V> getEntry(long p_76160_1_) {
        int i = getHashedKey(p_76160_1_);

        for (LongHashMap.Entry<V> entry = this.hashArray[getHashIndex(i, this.mask)]; entry != null; entry = entry.nextEntry) {
            if (entry.key == p_76160_1_) {
                return entry;
            }
        }

        return null;
    }

    public void add(long p_76163_1_, V p_76163_3_) {
        int i = getHashedKey(p_76163_1_);
        int j = getHashIndex(i, this.mask);

        for (LongHashMap.Entry<V> entry = this.hashArray[j]; entry != null; entry = entry.nextEntry) {
            if (entry.key == p_76163_1_) {
                entry.value = p_76163_3_;
                return;
            }
        }

        ++this.modCount;
        this.createKey(i, p_76163_1_, p_76163_3_, j);
    }

    private void resizeTable(int p_76153_1_) {
        LongHashMap.Entry<V>[] entry = this.hashArray;
        int i = entry.length;

        if (i == 1073741824) {
            this.capacity = Integer.MAX_VALUE;
        } else {
            LongHashMap.Entry<V>[] entry1 = new LongHashMap.Entry[p_76153_1_];
            this.copyHashTableTo(entry1);
            this.hashArray = entry1;
            this.mask = this.hashArray.length - 1;
            this.getClass();
            this.capacity = (int) ((float) p_76153_1_ * 0.75F);
        }
    }

    private void copyHashTableTo(LongHashMap.Entry<V>[] p_76154_1_) {
        LongHashMap.Entry<V>[] entry = this.hashArray;
        int i = p_76154_1_.length;

        for (int j = 0; j < entry.length; ++j) {
            LongHashMap.Entry<V> entry1 = entry[j];

            if (entry1 != null) {
                entry[j] = null;

                while (true) {
                    LongHashMap.Entry<V> entry2 = entry1.nextEntry;
                    int k = getHashIndex(entry1.hash, i - 1);
                    entry1.nextEntry = p_76154_1_[k];
                    p_76154_1_[k] = entry1;
                    entry1 = entry2;

                    if (entry2 == null) {
                        break;
                    }
                }
            }
        }
    }

    public V remove(long p_76159_1_) {
        LongHashMap.Entry<V> entry = this.removeKey(p_76159_1_);
        return entry == null ? null : entry.value;
    }

    final LongHashMap.Entry<V> removeKey(long p_76152_1_) {
        int i = getHashedKey(p_76152_1_);
        int j = getHashIndex(i, this.mask);
        LongHashMap.Entry<V> entry = this.hashArray[j];
        LongHashMap.Entry<V> entry1;
        LongHashMap.Entry<V> entry2;

        for (entry1 = entry; entry1 != null; entry1 = entry2) {
            entry2 = entry1.nextEntry;

            if (entry1.key == p_76152_1_) {
                ++this.modCount;
                --this.numHashElements;

                if (entry == entry1) {
                    this.hashArray[j] = entry2;
                } else {
                    entry.nextEntry = entry2;
                }

                return entry1;
            }

            entry = entry1;
        }

        return entry1;
    }

    private void createKey(int p_76156_1_, long p_76156_2_, V p_76156_4_, int p_76156_5_) {
        LongHashMap.Entry<V> entry = this.hashArray[p_76156_5_];
        this.hashArray[p_76156_5_] = new LongHashMap.Entry(p_76156_1_, p_76156_2_, p_76156_4_, entry);

        if (this.numHashElements++ >= this.capacity) {
            this.resizeTable(2 * this.hashArray.length);
        }
    }

    public double getKeyDistribution() {
        int i = 0;

        for (Entry<V> vEntry : this.hashArray) {
            if (vEntry != null) {
                ++i;
            }
        }

        return 1.0D * i / this.numHashElements;
    }

    static class Entry<V> {
        final long key;
        V value;
        LongHashMap.Entry<V> nextEntry;
        final int hash;

        Entry(int p_i1553_1_, long p_i1553_2_, V p_i1553_4_, LongHashMap.Entry<V> p_i1553_5_) {
            this.value = p_i1553_4_;
            this.nextEntry = p_i1553_5_;
            this.key = p_i1553_2_;
            this.hash = p_i1553_1_;
        }

        public final long getKey() {
            return this.key;
        }

        public final V getValue() {
            return this.value;
        }

        public final boolean equals(Object p_equals_1_) {
            if (!(p_equals_1_ instanceof LongHashMap.Entry entry1)) {
                return false;
            } else {
                Object object = this.getKey();
                Object object1 = ((Entry<V>) entry1).getKey();

                if (object == object1 || object != null && object.equals(object1)) {
                    Object object2 = this.getValue();
                    Object object3 = ((Entry<V>) entry1).getValue();

                    return object2 == object3 || object2 != null && object2.equals(object3);
                }

                return false;
            }
        }

        public final int hashCode() {
            return LongHashMap.getHashedKey(this.key);
        }

        public final String toString() {
            return this.getKey() + "=" + this.getValue();
        }
    }
}
