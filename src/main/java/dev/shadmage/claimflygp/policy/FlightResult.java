package dev.shadmage.claimflygp.policy;

import dev.shadmage.claimflygp.settings.Settings;
import me.ryanhamshire.GriefPrevention.Claim;
import org.bukkit.Location;

public final class FlightResult {
	private final boolean allowed;
	private final FlightReason reason;
	private final Claim claim;
	private final Location location;

	private FlightResult(boolean allowed, FlightReason reason, Claim claim, Location location) {
		this.allowed = allowed;
		this.reason = reason;
		this.claim = claim;
		this.location = location;
	}

	public static FlightResult allowed(FlightReason reason, Claim claim, Location location) {
		return new FlightResult(true, reason, claim, location);
	}

	public static FlightResult denied(FlightReason reason, Claim claim, Location location) {
		return new FlightResult(false, reason, claim, location);
	}

	public boolean isAllowed() {
		return allowed;
	}

	public FlightReason getReason() {
		return reason;
	}

	public Claim getClaim() {
		return claim;
	}

	public Location getLocation() {
		return location;
	}

	public String getMessage() {
		switch (reason) {
			case BYPASS:
				return Settings.Messages.FLY_BYPASS;
			case OWN_CLAIM:
				return Settings.Messages.FLY_OWN_CLAIM;
			case TRUSTED_CLAIM:
				return withClaimOwner(Settings.Messages.FLY_TRUSTED_CLAIM);
			case ADMIN_CLAIM:
				return Settings.Messages.FLY_ADMIN_CLAIM;
			case UNCLAIMED:
				return Settings.Messages.FLY_UNCLAIMED;
			case DENIED_WORLD_DISABLED:
				return withWorld(Settings.Messages.NO_FLY_WORLD);
			case DENIED_NO_SERVER_PERMISSION:
				return Settings.Messages.NO_FLY;
			case DENIED_ADMIN_CLAIM:
				return Settings.Messages.NO_FLY_ADMIN_CLAIM;
			case DENIED_UNCLAIMED:
				return Settings.Messages.NO_FLY_OUTSIDE_CLAIM;
			case DENIED_OWN_CLAIM:
			case DENIED_OTHER_CLAIM:
				return withClaimOwner(Settings.Messages.NO_FLY_THIS_CLAIM);
			case IGNORED_GAMEMODE:
			default:
				return "";
		}
	}

	private String withWorld(String message) {
		String world = location != null && location.getWorld() != null ? location.getWorld().getName() : "this world";
		return message.replace("%world%", world);
	}

	private String withClaimOwner(String message) {
		String owner = claim != null ? claim.getOwnerName() : "Unclaimed";

		return message
				.replace("%ClaimOwner%", owner)
				.replace("%claim_owner%", owner)
				.replace("%claimowner%", owner);
	}
}
