package net.minecraft.entity.ai;

import com.google.common.collect.Lists;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

public class EntitySenses {
    final EntityLiving entityObj;
    final List<Entity> seenEntities = Lists.newArrayList();
    final List<Entity> unseenEntities = Lists.newArrayList();

    public EntitySenses(EntityLiving entityObjIn) {
        this.entityObj = entityObjIn;
    }

    public void clearSensingCache() {
        this.seenEntities.clear();
        this.unseenEntities.clear();
    }

    public boolean canSee(Entity entityIn) {
        if (this.seenEntities.contains(entityIn)) {
            return true;
        } else if (this.unseenEntities.contains(entityIn)) {
            return false;
        } else {
            boolean flag = this.entityObj.canEntityBeSeen(entityIn);

            if (flag) {
                this.seenEntities.add(entityIn);
            } else {
                this.unseenEntities.add(entityIn);
            }

            return flag;
        }
    }
}
