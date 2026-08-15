package dev.shadmage.claimflygp.settings;

import org.mineacademy.fo.settings.SimpleSettings;

import java.util.List;

public class Settings extends SimpleSettings {
	public static String LOG_PREFIX;

	private static void init() {
		setPathPrefix("");
		LOG_PREFIX = getString("Prefix");
	}

	@Override
	protected boolean saveComments() {
		return true;
	}

	@Override
	protected int getConfigVersion() {
		setPathPrefix("");
		return getInteger("Version");
	}

	public static class ClaimFly {
		public static String CLAIMFLY_CHAT_PREFIX;
		public static Boolean IGNORE_CREATIVE;
		public static Boolean IGNORE_SPECTATOR;
		public static Boolean AUTO_ALLOW_FLIGHT;
		public static Boolean MESSAGE_ON_ACTIONBAR;

		private static void init() {
			setPathPrefix("ClaimFly");
			CLAIMFLY_CHAT_PREFIX = getString("ChatPrefix");
			IGNORE_CREATIVE = getBoolean("ignoreCreativeMode");
			IGNORE_SPECTATOR = getBoolean("ignoreSpectatorMode");
			AUTO_ALLOW_FLIGHT = getBoolean("AutoAllowFlightOnEnterClaim");
			MESSAGE_ON_ACTIONBAR = getBoolean("MessageOnActionBar");
		}
	}

	public static class Rules {
		public static List<String> ENABLED_WORLDS;
		public static List<String> DISABLED_WORLDS;
		public static Boolean ADMIN_CLAIMS_ALLOW_ACCESS_TRUST;

		private static void init() {
			setPathPrefix("Rules");
			ENABLED_WORLDS = getStringList("EnabledWorlds");
			DISABLED_WORLDS = getStringList("DisabledWorlds");
			ADMIN_CLAIMS_ALLOW_ACCESS_TRUST = getBoolean("AdminClaimsAllowAccessTrust");
		}
	}

	public static class Performance {
		public static Integer CHECK_INTERVAL_TICKS;

		private static void init() {
			setPathPrefix("Performance");
			CHECK_INTERVAL_TICKS = getInteger("CheckFlyingPlayersIntervalTicks");
		}
	}

	public static class Safety {
		public static Boolean SLOW_FALLING_ENABLED;
		public static Integer SLOW_FALLING_TICKS;
		public static Integer SLOW_FALLING_AMPLIFIER;

		private static void init() {
			setPathPrefix("Safety");
			SLOW_FALLING_ENABLED = getBoolean("SlowFallingEnabled");
			SLOW_FALLING_TICKS = getInteger("SlowFallingTicks");
			SLOW_FALLING_AMPLIFIER = getInteger("SlowFallingAmplifier");
		}
	}

	public static class Particles {
		public static Boolean SHOW_BOUNDARIES;
		public static Integer BOUNDARY_DISTANCE;

		private static void init() {
			setPathPrefix("Particles");
			SHOW_BOUNDARIES = getBoolean("ShowClaimBoundaries");
			BOUNDARY_DISTANCE = getInteger("BoundaryDistance");
		}
	}

	public static class Messages {
		public static String NO_FLY;
		public static String NO_FLY_THIS_CLAIM;
		public static String NO_FLY_OUTSIDE_CLAIM;
		public static String NO_FLY_ADMIN_CLAIM;
		public static String NO_FLY_WORLD;
		public static String FLIGHT_ENABLED;
		public static String FLIGHT_DISABLED;
		public static String FLY_BYPASS;
		public static String FLY_OWN_CLAIM;
		public static String FLY_TRUSTED_CLAIM;
		public static String FLY_ADMIN_CLAIM;
		public static String FLY_UNCLAIMED;

		private static void init() {
			setPathPrefix("Messages");
			NO_FLY = getString("noFlyPerms");
			NO_FLY_THIS_CLAIM = getString("noFlyThisClaim");
			NO_FLY_OUTSIDE_CLAIM = getString("noFlyOutsideClaims");
			NO_FLY_ADMIN_CLAIM = getString("noFlyAdminClaim");
			NO_FLY_WORLD = getString("noFlyWorld");
			FLIGHT_ENABLED = getString("FlightEnabled");
			FLIGHT_DISABLED = getString("FlightDisabled");
			FLY_BYPASS = getString("FlyBypass");
			FLY_OWN_CLAIM = getString("FlyOwnClaim");
			FLY_TRUSTED_CLAIM = getString("FlyTrustedClaim");
			FLY_ADMIN_CLAIM = getString("FlyAdminClaim");
			FLY_UNCLAIMED = getString("FlyUnclaimed");
		}
	}

}
