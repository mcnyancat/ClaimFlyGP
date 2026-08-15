package dev.shadmage.claimflygp;

import dev.shadmage.claimflygp._external.Metrics;
import dev.shadmage.claimflygp._external.SpigotUpdateChecker;
import dev.shadmage.claimflygp.settings.Settings;
import dev.shadmage.claimflygp.tasks.CheckFlyingPlayersTask;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.model.SimpleTask;
import org.mineacademy.fo.plugin.SimplePlugin;

public class ClaimFlyGPPlugin extends SimplePlugin {
	private SimpleTask flightCheckTask;

	@Override
	protected void onPluginStart() {

	}

	@Override
	protected void onReloadablesStart() {

		Common.setLogPrefix(Settings.LOG_PREFIX);
		Common.setTellPrefix(Settings.ClaimFly.CLAIMFLY_CHAT_PREFIX);

		setupBStats();
		runUpdateCheck();

		// If auto enable/disable flight is not enabled, run the CheckFlyingPlayersTask
		startFlightCheckTask();
	}

	@Override
	protected void onPluginPreReload() {
		stopFlightCheckTask();
	}

	@Override
	protected void onPluginStop() {
		stopFlightCheckTask();
	}

	private void startFlightCheckTask() {
		stopFlightCheckTask();

		if(!Settings.ClaimFly.AUTO_ALLOW_FLIGHT)
			flightCheckTask = Common.runTimer(0, Math.max(1, Settings.Performance.CHECK_INTERVAL_TICKS), new CheckFlyingPlayersTask());
	}

	private void stopFlightCheckTask() {
		if (flightCheckTask != null && !flightCheckTask.isCancelled())
			flightCheckTask.cancel();

		flightCheckTask = null;
	}

	private void setupBStats() {
		Metrics metrics = new Metrics(this, 24525);
		metrics.addCustomChart(new Metrics.SimplePie("using_autoenable_claimfly", () -> {
			return Settings.ClaimFly.AUTO_ALLOW_FLIGHT.toString();
		}));
		metrics.addCustomChart(new Metrics.SimplePie("message_notification_method", () -> {
			return Settings.ClaimFly.MESSAGE_ON_ACTIONBAR ? "Actionbar" : "Chat";
		}));
		metrics.addCustomChart(new Metrics.SimplePie("ignoring_creative", () -> {
			return Settings.ClaimFly.IGNORE_CREATIVE.toString();
		}));
		metrics.addCustomChart(new Metrics.SimplePie("ignoring_spectators", () -> {
			return Settings.ClaimFly.IGNORE_SPECTATOR.toString();
		}));
	}

	private void runUpdateCheck() {
		SpigotUpdateChecker spigotUpdateChecker = new SpigotUpdateChecker(this, 122058);
	}
}
