package dev.shadmage.claimflygp.events;

import dev.shadmage.claimflygp.policy.FlightPolicy;
import dev.shadmage.claimflygp.policy.FlightResult;
import dev.shadmage.claimflygp.settings.DebugValues;
import dev.shadmage.claimflygp.settings.Settings;
import dev.shadmage.claimflygp.utils.ClaimUtils;
import dev.shadmage.claimflygp.utils.PlayerUtils;
import me.ryanhamshire.GriefPrevention.Claim;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.annotation.AutoRegister;

@AutoRegister
public final class AutoEnableFlightListener implements Listener {
	private final FlightPolicy flightPolicy = new FlightPolicy();

	@EventHandler
	private void AutoEnableFlightOnClaimEnter(PlayerMoveEvent event) {
		if(event.isCancelled()) return;
		if(!Settings.ClaimFly.AUTO_ALLOW_FLIGHT) return;

		Player player = event.getPlayer();
		if(ignorePlayerGamemode(player)) return;

		Location locTo = event.getTo();
		Location locFrom = event.getFrom();
		Claim fromClaim = ClaimUtils.getClaim(locFrom);
		Claim toClaim = ClaimUtils.getClaim(locTo);

		if(fromClaim != toClaim) {
			handleChangedClaimArea(player, toClaim, locTo);
		}
	}

	@EventHandler
	private void onPlayerTeleport(PlayerTeleportEvent event) {
		Player player = event.getPlayer();

		if(ignorePlayerGamemode(player)) return;

		Location locTo = event.getTo();
		Location locFrom = event.getFrom();
		Claim fromClaim = ClaimUtils.getClaim(locFrom);
		Claim toClaim = ClaimUtils.getClaim(locTo);

		if(fromClaim != toClaim) {
			if(Settings.ClaimFly.AUTO_ALLOW_FLIGHT)
				handleChangedClaimArea(player, toClaim, locTo);
			else
				PlayerUtils.TogglePlayerFlight(player, false);
		}
	}

	private boolean ignorePlayerGamemode(Player player){
		FlightResult result = flightPolicy.evaluate(player);
		return result.getReason() == dev.shadmage.claimflygp.policy.FlightReason.IGNORED_GAMEMODE;
	}

	private void handleChangedClaimArea(Player player, Claim toClaim, Location toLocation) {
		FlightResult result = flightPolicy.evaluate(player, toLocation);

		if(Settings.DEBUG_SECTIONS.contains(DebugValues.AUTO_ALLOW_FLIGHT))
			Common.log("[" + DebugValues.AUTO_ALLOW_FLIGHT + "] Player " + player.getName() + " entered " + describeClaim(toClaim) + ". Result: " + result.getReason());

		PlayerUtils.TogglePlayerFlight(player, result.isAllowed());
	}

	private String describeClaim(Claim claim) {
		if (claim == null)
			return "unclaimed";

		return claim.isAdminClaim() ? "admin claim" : "claim owned by " + claim.getOwnerName();
	}

}
