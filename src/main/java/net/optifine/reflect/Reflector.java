package net.optifine.reflect;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiEnchantment;
import net.minecraft.client.gui.GuiHopper;
import net.minecraft.client.gui.inventory.GuiBeacon;
import net.minecraft.client.gui.inventory.GuiBrewingStand;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.client.model.ModelBanner;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBat;
import net.minecraft.client.model.ModelBlaze;
import net.minecraft.client.model.ModelBook;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.model.ModelDragon;
import net.minecraft.client.model.ModelEnderCrystal;
import net.minecraft.client.model.ModelEnderMite;
import net.minecraft.client.model.ModelGhast;
import net.minecraft.client.model.ModelGuardian;
import net.minecraft.client.model.ModelHorse;
import net.minecraft.client.model.ModelHumanoidHead;
import net.minecraft.client.model.ModelLeashKnot;
import net.minecraft.client.model.ModelMagmaCube;
import net.minecraft.client.model.ModelOcelot;
import net.minecraft.client.model.ModelRabbit;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelSign;
import net.minecraft.client.model.ModelSilverfish;
import net.minecraft.client.model.ModelSkeletonHead;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.model.ModelSquid;
import net.minecraft.client.model.ModelWitch;
import net.minecraft.client.model.ModelWither;
import net.minecraft.client.model.ModelWolf;
import net.minecraft.client.multiplayer.ChunkProviderClient;
import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraft.client.renderer.entity.RenderBoat;
import net.minecraft.client.renderer.entity.RenderLeashKnot;
import net.minecraft.client.renderer.entity.RenderMinecart;
import net.minecraft.client.renderer.tileentity.RenderEnderCrystal;
import net.minecraft.client.renderer.tileentity.RenderWitherSkull;
import net.minecraft.client.renderer.tileentity.TileEntityBannerRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityChestRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityEnchantmentTableRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityEnderChestRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySignRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.tileentity.TileEntityBrewingStand;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.LongHashMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IWorldNameable;
import net.optifine.Log;
import net.optifine.util.ArrayUtils;

public class Reflector {
    // Reflector Forge
    public static final ReflectorClass ModelLoader = new ReflectorClass("net.minecraftforge.client.model.ModelLoader");

    // Reflector Vanilla
    public static final ReflectorClass ChunkProviderClient = new ReflectorClass(ChunkProviderClient.class);
    public static final ReflectorField ChunkProviderClient_chunkMapping = new ReflectorField(ChunkProviderClient, LongHashMap.class);
    public static final ReflectorClass EntityVillager = new ReflectorClass(EntityVillager.class);
    public static final ReflectorField EntityVillager_careerId = new ReflectorField(new FieldLocatorTypes(EntityVillager.class, new Class[0], Integer.TYPE, new Class[]{Integer.TYPE, Boolean.TYPE, Boolean.TYPE, InventoryBasic.class}, "EntityVillager.careerId"));
    public static final ReflectorField EntityVillager_careerLevel = new ReflectorField(new FieldLocatorTypes(EntityVillager.class, new Class[]{Integer.TYPE}, Integer.TYPE, new Class[]{Boolean.TYPE, Boolean.TYPE, InventoryBasic.class}, "EntityVillager.careerLevel"));
    public static final ReflectorClass GuiBeacon = new ReflectorClass(GuiBeacon.class);
    public static final ReflectorField GuiBeacon_tileBeacon = new ReflectorField(GuiBeacon, IInventory.class);
    public static final ReflectorClass GuiBrewingStand = new ReflectorClass(GuiBrewingStand.class);
    public static final ReflectorField GuiBrewingStand_tileBrewingStand = new ReflectorField(GuiBrewingStand, IInventory.class);
    public static final ReflectorClass GuiChest = new ReflectorClass(GuiChest.class);
    public static final ReflectorField GuiChest_lowerChestInventory = new ReflectorField(GuiChest, IInventory.class, 1);
    public static final ReflectorClass GuiEnchantment = new ReflectorClass(GuiEnchantment.class);
    public static final ReflectorField GuiEnchantment_nameable = new ReflectorField(GuiEnchantment, IWorldNameable.class);
    public static final ReflectorClass GuiFurnace = new ReflectorClass(GuiFurnace.class);
    public static final ReflectorField GuiFurnace_tileFurnace = new ReflectorField(GuiFurnace, IInventory.class);
    public static final ReflectorClass GuiHopper = new ReflectorClass(GuiHopper.class);
    public static final ReflectorField GuiHopper_hopperInventory = new ReflectorField(GuiHopper, IInventory.class, 1);
    public static final ReflectorClass Minecraft = new ReflectorClass(Minecraft.class);
    public static final ReflectorField Minecraft_defaultResourcePack = new ReflectorField(Minecraft, DefaultResourcePack.class);
    public static final ReflectorClass ModelHumanoidHead = new ReflectorClass(ModelHumanoidHead.class);
    public static final ReflectorField ModelHumanoidHead_head = new ReflectorField(ModelHumanoidHead, ModelRenderer.class);
    public static final ReflectorClass ModelBat = new ReflectorClass(ModelBat.class);
    public static final ReflectorFields ModelBat_ModelRenderers = new ReflectorFields(ModelBat, ModelRenderer.class, 6);
    public static final ReflectorClass ModelBlaze = new ReflectorClass(ModelBlaze.class);
    public static final ReflectorField ModelBlaze_blazeHead = new ReflectorField(ModelBlaze, ModelRenderer.class);
    public static final ReflectorField ModelBlaze_blazeSticks = new ReflectorField(ModelBlaze, ModelRenderer[].class);
    public static final ReflectorClass ModelBlock = new ReflectorClass(ModelBlock.class);
    public static final ReflectorField ModelBlock_parentLocation = new ReflectorField(ModelBlock, ResourceLocation.class);
    public static final ReflectorField ModelBlock_textures = new ReflectorField(ModelBlock, Map.class);
    public static final ReflectorClass ModelDragon = new ReflectorClass(ModelDragon.class);
    public static final ReflectorFields ModelDragon_ModelRenderers = new ReflectorFields(ModelDragon, ModelRenderer.class, 12);
    public static final ReflectorClass ModelEnderCrystal = new ReflectorClass(ModelEnderCrystal.class);
    public static final ReflectorFields ModelEnderCrystal_ModelRenderers = new ReflectorFields(ModelEnderCrystal, ModelRenderer.class, 3);
    public static final ReflectorClass RenderEnderCrystal = new ReflectorClass(RenderEnderCrystal.class);
    public static final ReflectorField RenderEnderCrystal_modelEnderCrystal = new ReflectorField(RenderEnderCrystal, ModelBase.class, 0);
    public static final ReflectorClass ModelEnderMite = new ReflectorClass(ModelEnderMite.class);
    public static final ReflectorField ModelEnderMite_bodyParts = new ReflectorField(ModelEnderMite, ModelRenderer[].class);
    public static final ReflectorClass ModelGhast = new ReflectorClass(ModelGhast.class);
    public static final ReflectorField ModelGhast_body = new ReflectorField(ModelGhast, ModelRenderer.class);
    public static final ReflectorField ModelGhast_tentacles = new ReflectorField(ModelGhast, ModelRenderer[].class);
    public static final ReflectorClass ModelGuardian = new ReflectorClass(ModelGuardian.class);
    public static final ReflectorField ModelGuardian_body = new ReflectorField(ModelGuardian, ModelRenderer.class, 0);
    public static final ReflectorField ModelGuardian_eye = new ReflectorField(ModelGuardian, ModelRenderer.class, 1);
    public static final ReflectorField ModelGuardian_spines = new ReflectorField(ModelGuardian, ModelRenderer[].class, 0);
    public static final ReflectorField ModelGuardian_tail = new ReflectorField(ModelGuardian, ModelRenderer[].class, 1);
    public static final ReflectorClass ModelHorse = new ReflectorClass(ModelHorse.class);
    public static final ReflectorFields ModelHorse_ModelRenderers = new ReflectorFields(ModelHorse, ModelRenderer.class, 39);
    public static final ReflectorClass RenderLeashKnot = new ReflectorClass(RenderLeashKnot.class);
    public static final ReflectorField RenderLeashKnot_leashKnotModel = new ReflectorField(RenderLeashKnot, ModelLeashKnot.class);
    public static final ReflectorClass ModelMagmaCube = new ReflectorClass(ModelMagmaCube.class);
    public static final ReflectorField ModelMagmaCube_core = new ReflectorField(ModelMagmaCube, ModelRenderer.class);
    public static final ReflectorField ModelMagmaCube_segments = new ReflectorField(ModelMagmaCube, ModelRenderer[].class);
    public static final ReflectorClass ModelOcelot = new ReflectorClass(ModelOcelot.class);
    public static final ReflectorFields ModelOcelot_ModelRenderers = new ReflectorFields(ModelOcelot, ModelRenderer.class, 8);
    public static final ReflectorClass ModelRabbit = new ReflectorClass(ModelRabbit.class);
    public static final ReflectorFields ModelRabbit_renderers = new ReflectorFields(ModelRabbit, ModelRenderer.class, 12);
    public static final ReflectorClass ModelSilverfish = new ReflectorClass(ModelSilverfish.class);
    public static final ReflectorField ModelSilverfish_bodyParts = new ReflectorField(ModelSilverfish, ModelRenderer[].class, 0);
    public static final ReflectorField ModelSilverfish_wingParts = new ReflectorField(ModelSilverfish, ModelRenderer[].class, 1);
    public static final ReflectorClass ModelSlime = new ReflectorClass(ModelSlime.class);
    public static final ReflectorFields ModelSlime_ModelRenderers = new ReflectorFields(ModelSlime, ModelRenderer.class, 4);
    public static final ReflectorClass ModelSquid = new ReflectorClass(ModelSquid.class);
    public static final ReflectorField ModelSquid_body = new ReflectorField(ModelSquid, ModelRenderer.class);
    public static final ReflectorField ModelSquid_tentacles = new ReflectorField(ModelSquid, ModelRenderer[].class);
    public static final ReflectorClass ModelWitch = new ReflectorClass(ModelWitch.class);
    public static final ReflectorField ModelWitch_mole = new ReflectorField(ModelWitch, ModelRenderer.class, 0);
    public static final ReflectorField ModelWitch_hat = new ReflectorField(ModelWitch, ModelRenderer.class, 1);
    public static final ReflectorClass ModelWither = new ReflectorClass(ModelWither.class);
    public static final ReflectorField ModelWither_bodyParts = new ReflectorField(ModelWither, ModelRenderer[].class, 0);
    public static final ReflectorField ModelWither_heads = new ReflectorField(ModelWither, ModelRenderer[].class, 1);
    public static final ReflectorClass ModelWolf = new ReflectorClass(ModelWolf.class);
    public static final ReflectorField ModelWolf_tail = new ReflectorField(ModelWolf, ModelRenderer.class, 6);
    public static final ReflectorField ModelWolf_mane = new ReflectorField(ModelWolf, ModelRenderer.class, 7);
    public static final ReflectorClass RenderBoat = new ReflectorClass(RenderBoat.class);
    public static final ReflectorField RenderBoat_modelBoat = new ReflectorField(RenderBoat, ModelBase.class);
    public static final ReflectorClass RenderMinecart = new ReflectorClass(RenderMinecart.class);
    public static final ReflectorField RenderMinecart_modelMinecart = new ReflectorField(RenderMinecart, ModelBase.class);
    public static final ReflectorClass RenderWitherSkull = new ReflectorClass(RenderWitherSkull.class);
    public static final ReflectorField RenderWitherSkull_model = new ReflectorField(RenderWitherSkull, ModelSkeletonHead.class);
    public static final ReflectorClass TileEntityBannerRenderer = new ReflectorClass(TileEntityBannerRenderer.class);
    public static final ReflectorField TileEntityBannerRenderer_bannerModel = new ReflectorField(TileEntityBannerRenderer, ModelBanner.class);
    public static final ReflectorClass TileEntityBeacon = new ReflectorClass(TileEntityBeacon.class);
    public static final ReflectorField TileEntityBeacon_customName = new ReflectorField(TileEntityBeacon, String.class);
    public static final ReflectorClass TileEntityBrewingStand = new ReflectorClass(TileEntityBrewingStand.class);
    public static final ReflectorField TileEntityBrewingStand_customName = new ReflectorField(TileEntityBrewingStand, String.class);
    public static final ReflectorClass TileEntityChestRenderer = new ReflectorClass(TileEntityChestRenderer.class);
    public static final ReflectorField TileEntityChestRenderer_simpleChest = new ReflectorField(TileEntityChestRenderer, ModelChest.class, 0);
    public static final ReflectorField TileEntityChestRenderer_largeChest = new ReflectorField(TileEntityChestRenderer, ModelChest.class, 1);
    public static final ReflectorClass TileEntityEnchantmentTable = new ReflectorClass(TileEntityEnchantmentTable.class);
    public static final ReflectorField TileEntityEnchantmentTable_customName = new ReflectorField(TileEntityEnchantmentTable, String.class);
    public static final ReflectorClass TileEntityEnchantmentTableRenderer = new ReflectorClass(TileEntityEnchantmentTableRenderer.class);
    public static final ReflectorField TileEntityEnchantmentTableRenderer_modelBook = new ReflectorField(TileEntityEnchantmentTableRenderer, ModelBook.class);
    public static final ReflectorClass TileEntityEnderChestRenderer = new ReflectorClass(TileEntityEnderChestRenderer.class);
    public static final ReflectorField TileEntityEnderChestRenderer_modelChest = new ReflectorField(TileEntityEnderChestRenderer, ModelChest.class);
    public static final ReflectorClass TileEntityFurnace = new ReflectorClass(TileEntityFurnace.class);
    public static final ReflectorField TileEntityFurnace_customName = new ReflectorField(TileEntityFurnace, String.class);
    public static final ReflectorClass TileEntitySignRenderer = new ReflectorClass(TileEntitySignRenderer.class);
    public static final ReflectorField TileEntitySignRenderer_model = new ReflectorField(TileEntitySignRenderer, ModelSign.class);
    public static final ReflectorClass TileEntitySkullRenderer = new ReflectorClass(TileEntitySkullRenderer.class);
    public static final ReflectorField TileEntitySkullRenderer_humanoidHead = new ReflectorField(TileEntitySkullRenderer, ModelSkeletonHead.class, 1);

    public static Object call(ReflectorMethod refMethod, Object... params) {
        try {
            Method method = refMethod.getTargetMethod();

            if (method == null) {
                return null;
            } else {
                return method.invoke(null, params);
            }
        } catch (Throwable throwable) {
            handleException(throwable, null, refMethod, params);
            return null;
        }
    }

    public static Object call(Object obj, ReflectorMethod refMethod, Object... params) {
        try {
            Method method = refMethod.getTargetMethod();

            if (method == null) {
                return null;
            } else {
                return method.invoke(obj, params);
            }
        } catch (Throwable throwable) {
            handleException(throwable, obj, refMethod, params);
            return null;
        }
    }

    public static Object getFieldValue(ReflectorField refField) {
        return getFieldValue(null, refField);
    }

    public static Object getFieldValue(Object obj, ReflectorField refField) {
        try {
            Field field = refField.getTargetField();

            if (field == null) {
                return null;
            } else {
                return field.get(obj);
            }
        } catch (Throwable throwable) {
            Log.error("", throwable);
            return null;
        }
    }

    public static boolean getFieldValueBoolean(Object obj, ReflectorField refField, boolean def) {
        try {
            Field field = refField.getTargetField();

            if (field == null) {
                return def;
            } else {
                return field.getBoolean(obj);
            }
        } catch (Throwable throwable) {
            Log.error("", throwable);
            return def;
        }
    }

    public static Object getFieldValue(ReflectorFields refFields, int index) {
        ReflectorField reflectorfield = refFields.getReflectorField(index);
        return reflectorfield == null ? null : getFieldValue(reflectorfield);
    }

    public static Object getFieldValue(Object obj, ReflectorFields refFields, int index) {
        ReflectorField reflectorfield = refFields.getReflectorField(index);
        return reflectorfield == null ? null : getFieldValue(obj, reflectorfield);
    }

    public static int getFieldValueInt(Object obj, ReflectorField refField, int def) {
        try {
            Field field = refField.getTargetField();

            if (field == null) {
                return def;
            } else {
                return field.getInt(obj);
            }
        } catch (Throwable throwable) {
            Log.error("", throwable);
            return def;
        }
    }

    public static boolean setFieldValue(ReflectorField refField, Object value) {
        return setFieldValue(null, refField, value);
    }

    public static boolean setFieldValue(Object obj, ReflectorField refField, Object value) {
        try {
            Field field = refField.getTargetField();

            if (field == null) {
                return false;
            } else {
                field.set(obj, value);
                return true;
            }
        } catch (Throwable throwable) {
            Log.error("", throwable);
            return false;
        }
    }

    public static void setFieldValueInt(Object obj, ReflectorField refField, int value) {
        try {
            Field field = refField.getTargetField();

            if (field == null) {
            } else {
                field.setInt(obj, value);
            }
        } catch (Throwable throwable) {
            Log.error("", throwable);
        }
    }

    public static Object newInstance(ReflectorConstructor constr, Object... params) {
        Constructor constructor = constr.getTargetConstructor();

        if (constructor == null) {
            return null;
        } else {
            try {
                return constructor.newInstance(params);
            } catch (Throwable throwable) {
                handleException(throwable, constr, params);
                return null;
            }
        }
    }

    public static boolean matchesTypes(Class[] pTypes, Class[] cTypes) {
        if (pTypes.length != cTypes.length) {
            return false;
        } else {
            for (int i = 0; i < cTypes.length; ++i) {
                Class oclass = pTypes[i];
                Class oclass1 = cTypes[i];

                if (oclass != oclass1) {
                    return false;
                }
            }

            return true;
        }
    }

    private static void handleException(Throwable e, Object obj, ReflectorMethod refMethod, Object[] params) {
        if (e instanceof InvocationTargetException) {
            Throwable throwable = e.getCause();

            if (throwable instanceof RuntimeException runtimeException) {
                throw runtimeException;
            } else {
                Log.error("", e);
            }
        } else {
            Log.warn("*** Exception outside of method ***");
            Log.warn("Method deactivated: " + refMethod.getTargetMethod());
            refMethod.deactivate();

            if (e instanceof IllegalArgumentException) {
                Log.warn("*** IllegalArgumentException ***");
                Log.warn("Method: " + refMethod.getTargetMethod());
                Log.warn("Object: " + obj);
                Log.warn("Parameter classes: " + ArrayUtils.arrayToString(getClasses(params)));
                Log.warn("Parameters: " + ArrayUtils.arrayToString(params));
            }

            Log.warn("", e);
        }
    }

    private static void handleException(Throwable e, ReflectorConstructor refConstr, Object[] params) {
        if (e instanceof InvocationTargetException) {
            Log.error("", e);
        } else {
            Log.warn("*** Exception outside of constructor ***");
            Log.warn("Constructor deactivated: " + refConstr.getTargetConstructor());
            refConstr.deactivate();

            if (e instanceof IllegalArgumentException) {
                Log.warn("*** IllegalArgumentException ***");
                Log.warn("Constructor: " + refConstr.getTargetConstructor());
                Log.warn("Parameter classes: " + ArrayUtils.arrayToString(getClasses(params)));
                Log.warn("Parameters: " + ArrayUtils.arrayToString(params));
            }

            Log.warn("", e);
        }
    }

    private static Object[] getClasses(Object[] objs) {
        if (objs == null) {
            return new Class[0];
        } else {
            Class[] aclass = new Class[objs.length];

            for (int i = 0; i < aclass.length; ++i) {
                Object object = objs[i];

                if (object != null) {
                    aclass[i] = object.getClass();
                }
            }

            return aclass;
        }
    }
}
