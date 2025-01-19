package net.optifine.reflect;

import java.lang.reflect.Field;

import net.optifine.Log;

public class FieldLocatorType implements IFieldLocator {
    private final ReflectorClass reflectorClass;
    private final Class targetFieldType;
    private final int targetFieldIndex;

    public FieldLocatorType(ReflectorClass reflectorClass, Class targetFieldType, int targetFieldIndex) {
        this.reflectorClass = reflectorClass;
        this.targetFieldType = targetFieldType;
        this.targetFieldIndex = targetFieldIndex;
    }

    public Field field() {
        Class oclass = this.reflectorClass.getTargetClass();

        if (oclass == null) {
            return null;
        } else {
            try {
                Field[] afield = oclass.getDeclaredFields();
                int i = 0;

                for (Field field : afield) {
                    if (field.getType() == this.targetFieldType) {
                        if (i == this.targetFieldIndex) {
                            field.setAccessible(true);
                            return field;
                        }

                        ++i;
                    }
                }

                Log.log("(Reflector) Field not present: " + oclass.getName() + ".(type: " + this.targetFieldType + ", index: " + this.targetFieldIndex + ")");
                return null;
            } catch (SecurityException securityexception) {
                securityexception.printStackTrace();
                return null;
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                return null;
            }
        }
    }
}
