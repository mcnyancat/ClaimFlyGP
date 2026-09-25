package dev.shadmage.claimflygp.placeholders;

import dev.shadmage.claimflygp.ClaimFlyGPPlugin;
import dev.shadmage.claimflygp.policy.FlightPolicy;
import dev.shadmage.claimflygp.policy.FlightResult;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.ryanhamshire.GriefPrevention.Claim;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class ClaimFlyPlaceholderExpansion extends PlaceholderExpansion {
	private final ClaimFlyGPPlugin plugin;
	private final FlightPolicy flightPolicy = new FlightPolicy();

	public ClaimFlyPlaceholderExpansion(ClaimFlyGPPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public @NotNull String getIdentifier() {
		return "claimfly";
	}

	@Override
	public @NotNull String getAuthor() {
		return String.join(", ", plugin.getDescription().getAuthors());
	}

	@Override
	public @NotNull String getVersion() {
		return plugin.getDescription().getVersion();
	}

	@Override
	public boolean persist() {
		return true;
	}

	@Override
	public String onRequest(OfflinePlayer offlinePlayer, @NotNull String params) {
		if (offlinePlayer == null || !offlinePlayer.isOnline() || !(offlinePlayer.getPlayer() instanceof Player))
			return "";

		Player player = offlinePlayer.getPlayer();
		FlightResult result = flightPolicy.evaluate(player);

		if ("allowed".equalsIgnoreCase(params) || "can_fly".equalsIgnoreCase(params))
			return Boolean.toString(result.isAllowed());

		if ("reason".equalsIgnoreCase(params))
			return result.getReason().name();

		if ("message".equalsIgnoreCase(params))
			return result.getMessage();

		if ("claim_owner".equalsIgnoreCase(params))
			return getClaimOwner(result.getClaim());

		if ("claim_type".equalsIgnoreCase(params))
			return getClaimType(result.getClaim());

		if ("world".equalsIgnoreCase(params))
			return player.getWorld().getName();

		if ("allow_flight".equalsIgnoreCase(params))
			return Boolean.toString(player.getAllowFlight());

		if ("is_flying".equalsIgnoreCase(params))
			return Boolean.toString(player.isFlying());

		return null;
	}

	private String getClaimOwner(Claim claim) {
		return claim == null ? "Unclaimed" : claim.getOwnerName();
	}

	private String getClaimType(Claim claim) {
		if (claim == null)
			return "unclaimed";

		return claim.isAdminClaim() ? "admin" : "player";
	}
}
