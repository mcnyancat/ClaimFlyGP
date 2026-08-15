package dev.shadmage.claimflygp.utils;

import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.ClaimPermission;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class ClaimUtils {

	public static Claim getClaim(Player player) {
		return getClaim(player.getLocation());
	}

	public static Claim getClaim(Location location) {
		return GriefPrevention.instance.dataStore.getClaimAt(location, true, null);
	}

	public static boolean hasAccessTrust(Player player) {
		Claim claim = getClaim(player);
		if (claim == null)
			return false;

		Supplier<String> supplier = claim.checkPermission(player, ClaimPermission.Access, null);
		return supplier == null;
	}

	public static boolean hasAccessTrust(Player player, Location location) {
		Claim claim = getClaim(location);
		if (claim == null)
			return false;

		Supplier<String> supplier = claim.checkPermission(player, ClaimPermission.Access, null);
		return supplier == null;
	}

	public static boolean hasAccessTrust(Player player, @Nonnull Claim claim) {
		if (claim == null)
			return false;

		Supplier<String> supplier = claim.checkPermission(player, ClaimPermission.Access, null);
		return supplier == null;
	}

	public static boolean isClaimOwner(Player player) {
		return isClaimOwner(player, getClaim(player));
	}

	public static boolean isClaimOwner(Player player, Location location) {
		return isClaimOwner(player, getClaim(location));
	}

	public static boolean isClaimOwner(Player player, Claim claim) {
		return claim != null && claim.getOwnerID() != null && claim.getOwnerID().equals(player.getUniqueId());
	}

	public static boolean isInClaim(Player player) {
		return getClaim(player) != null;
	}

	public static boolean isClaimed(Location location) {
		return getClaim(location) != null;
	}

	public static boolean isInAdminClaim(Player player) {
		Claim claim = getClaim(player);

		if (claim != null) {
			return claim.isAdminClaim();
		} else {
			return false;
		}
	}

	public static boolean isAnAdminClaim(Location location) {
		Claim claim = getClaim(location);
		return claim != null && claim.isAdminClaim();
	}

}
