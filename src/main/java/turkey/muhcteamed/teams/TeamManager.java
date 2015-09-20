package turkey.muhcteamed.teams;

import java.util.List;

import org.apache.logging.log4j.Level;

import turkey.muhcteamed.TeamedCore;
import turkey.muhcteamed.config.TeamedSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

import com.google.common.collect.Lists;

public class TeamManager
{
	private List<Team> teams = Lists.newArrayList();
	private List<EntityPlayer> unassinged = Lists.newArrayList();

	public TeamManager()
	{
		if(!TeamedSettings.ffa && TeamedSettings.defaultTeams.length > 0)
			for(String teamName : TeamedSettings.defaultTeams)
				this.addTeam(teamName);
	}

	public void addTeam(String name)
	{
		this.teams.add(new Team(name));
	}

	public void addDefaultTeam(String name)
	{
		this.teams.add(new Team(name));
		// TODO: Add this team to the config
	}
	
	public Team getTeamFromName(String teamName)
	{
		for(Team t : teams)
			if(t.getTeamName().equals(teamName))
				return t;
		return null;
	}

	public boolean assignPlayerToTeam(EntityPlayer player, String teamName)
	{
		for(Team t : teams)
		{
			if(t.getTeamName().equals(teamName))
			{
				t.addPlayerToTeam(player);
				this.unassinged.remove(player);
				return true;
			}
		}
		return false;
	}

	public String assignPlayerRandomly(EntityPlayer player)
	{
		if(TeamedSettings.ffa || !TeamedSettings.autoAssignTeam || this.teams.size() == 0)
		{
			this.unassinged.add(player);
			return "Unnasigned";
		}
		else
		{
			Team stored = teams.get(0);
			boolean assigned = false;
			for(int i = 1; i < teams.size(); i++)
			{
				Team team = teams.get(i);
				if(team.getNumberofPlayers() > stored.getNumberofPlayers())
				{
					stored.addPlayerToTeam(player);
					assigned = true;
					return stored.getTeamDisplayName();
				}
				else if(team.getNumberofPlayers() < stored.getNumberofPlayers())
				{
					team.addPlayerToTeam(player);
					assigned = true;
					return team.getTeamDisplayName();
				}
			}
			if(!assigned)
			{
				teams.get(0).addPlayerToTeam(player);
				return teams.get(0).getTeamDisplayName();
			}
		}
		return "Failed to assign a team!!";
	}

	public void assignPlayerToUnassinged(EntityPlayer player)
	{
		this.unassinged.add(player);
	}

	public void removePlayerFromTeam(EntityPlayer player)
	{
		if(!this.unassinged.remove(player))
		{
			for(Team t : teams)
			{
				if(t.isPlayerOnTeam(player))
				{
					t.removePlayerFromTeam(player);
				}
			}
		}
	}
	
	public void sendMessageToAll(String msg)
	{
		TeamedCore.logger.log(Level.INFO, msg);
		for(EntityPlayer player : this.unassinged)
		{
			player.addChatMessage(new ChatComponentText(msg));
		}
		for(Team t : teams)
		{
			for(EntityPlayer player : t.getPlayers())
			{
				player.addChatMessage(new ChatComponentText(msg));
			}
		}
	}
}
