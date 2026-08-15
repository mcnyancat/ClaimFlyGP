package dev.shadmage.claimflygp.commands;

import dev.shadmage.claimflygp.policy.FlightResult;
import dev.shadmage.claimflygp.settings.PermissionData;
import dev.shadmage.claimflygp.utils.FlightCheck;
import dev.shadmage.claimflygp.utils.PlayerUtils;
import org.bukkit.entity.Player;
import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.command.SimpleCommand;

@AutoRegister
public final class claimflyCommand extends SimpleCommand {
	public claimflyCommand() {
		super("claimfly|cfly|fly");
		setPermission(PermissionData.PERMISSION_CLAIMFLY_USE);
	}

	@Override
	protected void onCommand() {
		checkConsole();
		Player player = getPlayer();

		FlightCheck flightCheck = new FlightCheck();
		FlightResult result = flightCheck.evaluate(player);

		if ("status".equalsIgnoreCase(args.length > 0 ? args[0] : "")) {
			PlayerUtils.PlayerNotification(player, result.getMessage());
			return;
		}

		if (!result.isAllowed()) {
			PlayerUtils.PlayerNotification(player, result.getMessage());
			return;
		}

		boolean newFlightStatus;
		if ("on".equalsIgnoreCase(args.length > 0 ? args[0] : ""))
			newFlightStatus = true;
		else if ("off".equalsIgnoreCase(args.length > 0 ? args[0] : ""))
			newFlightStatus = false;
		else
			newFlightStatus = !(player.getAllowFlight());

		PlayerUtils.TogglePlayerFlight(player, newFlightStatus);
	}
}
