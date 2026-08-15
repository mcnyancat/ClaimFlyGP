package dev.shadmage.claimflygp.policy;

import dev.shadmage.claimflygp.settings.PermissionData;
import dev.shadmage.claimflygp.settings.Settings;
import dev.shadmage.claimflygp.utils.ClaimUtils;
import me.ryanhamshire.GriefPrevention.Claim;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public final class FlightPolicy {

	public FlightResult evaluate(Player player) {
		return evaluate(player, player.getLocation());
	}

	public FlightResult evaluate(Player player, Location location) {
		if (ignorePlayerGamemode(player))
			return FlightResult.allowed(FlightReason.IGNORED_GAMEMODE, ClaimUtils.getClaim(location), location);

		if (!isWorldEnabled(location))
			return FlightResult.denied(FlightReason.DENIED_WORLD_DISABLED, ClaimUtils.getClaim(location), location);

		Claim claim = ClaimUtils.getClaim(location);

		if (player.hasPermission(PermissionData.PERMISSION_CLAIMFLY_BYPASS))
			return FlightResult.allowed(FlightReason.BYPASS, claim, location);

		if (!hasAnyFlightPermission(player))
			return FlightResult.denied(FlightReason.DENIED_NO_SERVER_PERMISSION, claim, location);

		if (claim == null) {
			return player.hasPermission(PermissionData.PERMISSION_CLAIMFLY_UNCLAIMED)
					? FlightResult.allowed(FlightReason.UNCLAIMED, null, location)
					: FlightResult.denied(FlightReason.DENIED_UNCLAIMED, null, location);
		}

		if (claim.isAdminClaim()) {
			boolean trusted = Settings.Rules.ADMIN_CLAIMS_ALLOW_ACCESS_TRUST && ClaimUtils.hasAccessTrust(player, claim);

			return player.hasPermission(PermissionData.PERMISSION_CLAIMFLY_ADMIN) || trusted
					? FlightResult.allowed(FlightReason.ADMIN_CLAIM, claim, location)
					: FlightResult.denied(FlightReason.DENIED_ADMIN_CLAIM, claim, location);
		}

		if (ClaimUtils.isClaimOwner(player, claim)) {
			return player.hasPermission(PermissionData.PERMISSION_CLAIMFLY_USE)
					? FlightResult.allowed(FlightReason.OWN_CLAIM, claim, location)
					: FlightResult.denied(FlightReason.DENIED_OWN_CLAIM, claim, location);
		}

		boolean trusted = player.hasPermission(PermissionData.PERMISSION_CLAIMFLY_OTHERS)
				&& ClaimUtils.hasAccessTrust(player, claim);

		return trusted
				? FlightResult.allowed(FlightReason.TRUSTED_CLAIM, claim, location)
				: FlightResult.denied(FlightReason.DENIED_OTHER_CLAIM, claim, location);
	}

	private boolean hasAnyFlightPermission(Player player) {
		return player.hasPermission(PermissionData.PERMISSION_CLAIMFLY_USE)
				|| player.hasPermission(PermissionData.PERMISSION_CLAIMFLY_ADMIN)
				|| player.hasPermission(PermissionData.PERMISSION_CLAIMFLY_OTHERS)
				|| player.hasPermission(PermissionData.PERMISSION_CLAIMFLY_UNCLAIMED);
	}

	private boolean ignorePlayerGamemode(Player player) {
		GameMode gameMode = player.getGameMode();

		return Settings.ClaimFly.IGNORE_CREATIVE && gameMode == GameMode.CREATIVE
				|| Settings.ClaimFly.IGNORE_SPECTATOR && gameMode == GameMode.SPECTATOR;
	}

	private boolean isWorldEnabled(Location location) {
		if (location == null || location.getWorld() == null)
			return false;

		String worldName = location.getWorld().getName();

		if (!Settings.Rules.ENABLED_WORLDS.isEmpty() && !Settings.Rules.ENABLED_WORLDS.contains(worldName))
			return false;

		return !Settings.Rules.DISABLED_WORLDS.contains(worldName);
	}
}
