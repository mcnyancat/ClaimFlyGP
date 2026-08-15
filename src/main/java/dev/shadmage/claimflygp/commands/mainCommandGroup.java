package dev.shadmage.claimflygp.commands;

import dev.shadmage.claimflygp.policy.FlightPolicy;
import dev.shadmage.claimflygp.policy.FlightResult;
import dev.shadmage.claimflygp.settings.PermissionData;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;
import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.command.PermsCommand;
import org.mineacademy.fo.command.ReloadCommand;
import org.mineacademy.fo.command.SimpleCommandGroup;
import org.mineacademy.fo.command.SimpleSubCommand;

@AutoRegister
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class mainCommandGroup extends SimpleCommandGroup {
	@Getter(value = AccessLevel.PRIVATE)
	private static final mainCommandGroup instance = new mainCommandGroup();

	@Override
	protected void registerSubcommands() {
		registerSubcommand(new StatusSubCommand());
		registerSubcommand(new InspectSubCommand());
		registerSubcommand(new ReloadCommand(PermissionData.Admin.PERMISSION_CLAIMFLY_RELOAD));
		registerSubcommand(new PermsCommand(PermissionData.class, PermissionData.Admin.PERMISSION_CLAIMFLY_PERMS));
	}

	@Override
	protected String getCredits() {
		return "&bVisit &dhttps://dirtydogsa.co.za";
	}

	private static final class StatusSubCommand extends SimpleSubCommand {
		private StatusSubCommand() {
			super("status");
			setPermission(PermissionData.Admin.PERMISSION_CLAIMFLY_STATUS);
		}

		@Override
		protected void onCommand() {
			tell(
					"&8&m--------------------------------",
					"&9ClaimFlyGP Status",
					"&7Auto flight: &f" + dev.shadmage.claimflygp.settings.Settings.ClaimFly.AUTO_ALLOW_FLIGHT,
					"&7Check interval: &f" + dev.shadmage.claimflygp.settings.Settings.Performance.CHECK_INTERVAL_TICKS + " ticks",
					"&7Boundary particles: &f" + dev.shadmage.claimflygp.settings.Settings.Particles.SHOW_BOUNDARIES,
					"&7Admin trust allowed: &f" + dev.shadmage.claimflygp.settings.Settings.Rules.ADMIN_CLAIMS_ALLOW_ACCESS_TRUST,
					"&8&m--------------------------------");
		}
	}

	private static final class InspectSubCommand extends SimpleSubCommand {
		private final FlightPolicy flightPolicy = new FlightPolicy();

		private InspectSubCommand() {
			super("inspect|debug");
			setPermission(PermissionData.Admin.PERMISSION_CLAIMFLY_INSPECT);
		}

		@Override
		protected void onCommand() {
			checkArgs(1, "Usage: /{label} inspect <player>");

			Player player = findPlayer(args[0]);
			FlightResult result = flightPolicy.evaluate(player);

			tell(
					"&8&m--------------------------------",
					"&9ClaimFlyGP Inspect: &f" + player.getName(),
					"&7Allowed: &f" + result.isAllowed(),
					"&7Reason: &f" + result.getReason(),
					"&7World: &f" + player.getWorld().getName(),
					"&7Claim: &f" + (result.getClaim() == null ? "Unclaimed" : result.getClaim().getOwnerName()),
					"&7Message: &f" + result.getMessage(),
					"&8&m--------------------------------");
		}
	}

}
