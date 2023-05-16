package one.suhas.rmc.utils;

import java.util.Map;

public class Constants {
    public static final Map<StarValue, String> starValueToString = Map.of(
            StarValue.OneStar, "⭐️",
            StarValue.TwoStars,"⭐️⭐️",
            StarValue.ThreeStars, "⭐️⭐️⭐️",
            StarValue.FourStars, "⭐️⭐️⭐️⭐️",
            StarValue.FiveStars, "⭐️⭐️⭐️⭐️⭐️"
    );
}
