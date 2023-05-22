package one.suhas.rmc.utils;

import java.util.Map;

/**
 * Constant values for our app
 */
public class Constants {
    /**
     * A map translating StarValue enums to an emoji of stars
     * @see StarValue
     */
    public static final Map<StarValue, String> starValueToString = Map.of(
            StarValue.OneStar, "⭐️",
            StarValue.TwoStars,"⭐️⭐️",
            StarValue.ThreeStars, "⭐️⭐️⭐️",
            StarValue.FourStars, "⭐️⭐️⭐️⭐️",
            StarValue.FiveStars, "⭐️⭐️⭐️⭐️⭐️"
    );
}
