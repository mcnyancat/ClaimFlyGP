package dev.shadmage.claimflygp.utils;

import dev.shadmage.claimflygp.settings.Settings;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.remain.CompPotionEffectType;
import org.mineacademy.fo.remain.Remain;

public class PlayerUtils {

	public static void TogglePlayerFlight(Player player, Boolean allowFlight) {
		if(player.getAllowFlight() == allowFlight) return;
		if(!allowFlight) {
			if(player.isFlying()) {
				applySlowFalling(player);
				player.setFlying(false);
			}
		}
		player.setAllowFlight(allowFlight);
		PlayerNotification(player, (allowFlight ? Settings.Messages.FLIGHT_ENABLED : Settings.Messages.FLIGHT_DISABLED));
	}

	public static void applySlowFalling(Player player) {
		if (Settings.Safety.SLOW_FALLING_ENABLED)
			player.addPotionEffect(new PotionEffect(CompPotionEffectType.SLOW_FALLING, Settings.Safety.SLOW_FALLING_TICKS, Settings.Safety.SLOW_FALLING_AMPLIFIER));
	}

	public static void PlayerNotification(Player player, String message){
		Common.runLater(5, ()-> {
			if (Settings.ClaimFly.MESSAGE_ON_ACTIONBAR) {
				Remain.sendActionBar(player, Common.colorize(message));
			} else {
				Common.tellNoPrefix(player, Common.colorize(message));
			}
		});
	}
}
