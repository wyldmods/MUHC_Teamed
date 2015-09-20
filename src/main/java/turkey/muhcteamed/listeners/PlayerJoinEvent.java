package turkey.muhcteamed.listeners;

import turkey.muhcteamed.TeamedCore;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;

public class PlayerJoinEvent
{
	boolean hasChecked = false;
	
	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent event)
	{
		if(event.player.worldObj.isRemote)
			return;
		
		String team = TeamedCore.manager.assignPlayerRandomly(event.player);
		TeamedCore.manager.sendMessageToAll(event.player.getCommandSenderName() + " was assigned to " + team);
	}
	
	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedOutEvent event)
	{
		if(event.player.worldObj.isRemote)
			return;
		TeamedCore.manager.removePlayerFromTeam(event.player);
	}
}