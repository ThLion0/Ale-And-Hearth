package com.thlion_.brewery.utils;

import com.hypixel.hytale.server.core.asset.type.item.config.Item;
import com.hypixel.hytale.server.core.entity.effect.ActiveEntityEffect;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;

public class Utils {
    private static final String[] effectIdList = new String[]{
        "Brewery_Drink_Effect_Sober",
        "Brewery_Drink_Effect_Little_Drunk",
        "Brewery_Drink_Effect_Drunk",
        "Brewery_Drink_Effect_Very_Drunk"
    };

    public static boolean isEffectDrunkRelated(ActiveEntityEffect entityEffect) {
        try {
            String effectId = getActiveEntityEffectId(entityEffect);
            for (String id : effectIdList) {
                if (effectId.equals(id)) {
                    return true;
                }
            }

            return false;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getActiveEntityEffectId(ActiveEntityEffect entityEffect) throws NoSuchFieldException, IllegalAccessException {
        Field f = ActiveEntityEffect.class.getDeclaredField("entityEffectId");
        f.setAccessible(true);
        return (String) f.get(entityEffect);
    }

    public static boolean isItemStackHasTag(@Nonnull Item item, String key, String tag) {
        String[] tagTypeValues = item.getData().getRawTags().get(key);

        for (String itemTag : tagTypeValues) {
            if (itemTag.equals(tag)) {
                return true;
            }
        }

        return false;
    }
}
