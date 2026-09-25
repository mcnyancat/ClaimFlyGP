package dev.shadmage.claimflygp.events;

import dev.shadmage.claimflygp.policy.FlightResult;
import dev.shadmage.claimflygp.settings.Settings;
import dev.shadmage.claimflygp.utils.FlightCheck;
import dev.shadmage.claimflygp.utils.PlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.annotation.AutoRegister;

@AutoRegister
public final class FlightToggleListener implements Listener {
	final FlightCheck flightCheck = new FlightCheck();

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onFlightToggle(PlayerToggleFlightEvent event) {
		Common.setTellPrefix(Settings.ClaimFly.CLAIMFLY_CHAT_PREFIX);
		Player player = event.getPlayer();
		FlightResult result = flightCheck.evaluate(player);
		if (event.isFlying()) {
			if (!result.isAllowed()) {
				PlayerUtils.TogglePlayerFlight(player, false);
				event.setCancelled(true);
			}
		}
	}

}
