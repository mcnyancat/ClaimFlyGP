package dev.shadmage.claimflygp.tasks;

import dev.shadmage.claimflygp.utils.FlightCheck;
import org.bukkit.scheduler.BukkitRunnable;

public class CheckFlyingPlayersTask extends BukkitRunnable {
	private final FlightCheck flightCheck = new FlightCheck();

	@Override
	public void run() {
		flightCheck.checkAllPlayersForIllegalFlight();
	}
}
