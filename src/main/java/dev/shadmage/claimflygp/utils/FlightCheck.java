package dev.shadmage.claimflygp.utils;

import dev.shadmage.claimflygp.policy.FlightPolicy;
import dev.shadmage.claimflygp.policy.FlightReason;
import dev.shadmage.claimflygp.policy.FlightResult;
import dev.shadmage.claimflygp.settings.DebugValues;
import dev.shadmage.claimflygp.settings.Settings;
import me.ryanhamshire.GriefPrevention.Claim;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.remain.CompParticle;
import org.mineacademy.fo.remain.Remain;

public class FlightCheck {
	public final static String FLIGHT_ALLOWED = "allow";
	private final FlightPolicy flightPolicy = new FlightPolicy();

	public String check(Player player) {
		FlightResult result = evaluate(player);

		return result.isAllowed() ? FLIGHT_ALLOWED : result.getMessage();
	}

	public FlightResult evaluate(Player player) {
		FlightResult result = flightPolicy.evaluate(player);

		if(Settings.DEBUG_SECTIONS.contains(DebugValues.FLIGHTCHECK_COMMAND))
			Common.logFramed(
					"Allowed: " + result.isAllowed(),
					"Reason: " + result.getReason(),
					"ClaimOwner: " + (result.getClaim() != null ? result.getClaim().getOwnerName() : "Unclaimed")
			);

		if (result.isAllowed() && result.getReason() != FlightReason.BYPASS && result.getReason() != FlightReason.IGNORED_GAMEMODE)
			showFlightBoundaries(player);

		return result;
	}

	private void showFlightBoundaries(Player player) {

		if(Settings.Particles.SHOW_BOUNDARIES) {
			Claim claimAtPlayer = ClaimUtils.getClaim(player);
			Location playerLoc = player.getLocation();

			if (claimAtPlayer != null) {
				Location[] locs = new Location[4];
				locs[0] = new Location(player.getWorld(), playerLoc.getBlockX() + .5, playerLoc.getBlockY() + 2, claimAtPlayer.getLesserBoundaryCorner().getBlockZ() + .5);
				locs[1] = new Location(player.getWorld(), claimAtPlayer.getLesserBoundaryCorner().getBlockX() + .5, playerLoc.getBlockY() + 2, playerLoc.getBlockZ() + .5);
				locs[2] = new Location(player.getWorld(), playerLoc.getBlockX() + .5, playerLoc.getBlockY() + 2, claimAtPlayer.getGreaterBoundaryCorner().getBlockZ() + .5);
				locs[3] = new Location(player.getWorld(), claimAtPlayer.getGreaterBoundaryCorner().getBlockX() + .5, playerLoc.getBlockY() + 2, playerLoc.getBlockZ() + .5);

				for (int i = 0; i <= 3; i++) {
					if (playerLoc.distance(locs[i]) <= Settings.Particles.BOUNDARY_DISTANCE) {
						CompParticle.COMPOSTER.spawn(player, locs[i]);
						CompParticle.COMPOSTER.spawn(player, locs[i].subtract(0, 1, 0));
						CompParticle.COMPOSTER.spawn(player, locs[i].add(0, 1, 0));
					}
				}
			}
		}
	}

	public void CheckAllPlayersForIllegalFlight(){
		checkAllPlayersForIllegalFlight();
	}

	public void checkAllPlayersForIllegalFlight(){
		Common.runLater(() ->{
			for(Player player : Remain.getOnlinePlayers()){
				if(player.isFlying())
					checkPlayerForIllegalFlight(player);
			}
		});
	}

	private void checkPlayerForIllegalFlight(Player player) {
		FlightResult result = evaluate(player);
		if (!result.isAllowed()) {
			PlayerUtils.applySlowFalling(player);
			PlayerUtils.TogglePlayerFlight(player, false);
		}
	}
}
