package dev.shadmage.claimflygp.events;

import dev.shadmage.claimflygp.utils.FlightCheck;
import me.ryanhamshire.GriefPrevention.events.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.mineacademy.fo.annotation.AutoRegister;

@AutoRegister
public final class GPEventListener implements Listener {
	private final FlightCheck flightCheck = new FlightCheck();

	@EventHandler
	public void onClaimDelete(ClaimDeletedEvent claimDeletedEvent){
		flightCheck.checkAllPlayersForIllegalFlight();
	}

	@EventHandler
	public void onClaimCreate(ClaimCreatedEvent claimCreatedEvent){
		flightCheck.checkAllPlayersForIllegalFlight();
	}

	@EventHandler
	public void onClaimExtend(ClaimResizeEvent claimResizeEvent){
		flightCheck.checkAllPlayersForIllegalFlight();
	}

	@EventHandler
	public void onClaimTransferEvent(ClaimTransferEvent claimTransferEvent){
		flightCheck.checkAllPlayersForIllegalFlight();
	}

	@EventHandler
	public void onTrustChangedEvent(TrustChangedEvent trustChangedEvent){
		flightCheck.checkAllPlayersForIllegalFlight();
	}





}
