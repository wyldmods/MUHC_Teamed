package turkey.muhcteamed.teams;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;

import com.google.common.collect.Lists;

public class Team
{
	private String name;
	private List<EntityPlayer> players = Lists.newArrayList();
	private String teamColor = "\u00a75";

	public Team(String name)
	{
		this.name = name;
	}

	public String getTeamName()
	{
		return this.name;
	}

	public String getTeamDisplayName()
	{
		return teamColor + this.name;
	}

	public boolean addPlayerToTeam(EntityPlayer player)
	{
		return this.players.add(player);
	}

	public void removePlayerFromTeam(EntityPlayer player)
	{
		this.players.remove(player);
	}

	public boolean isPlayerOnTeam(EntityPlayer player)
	{
		return this.players.contains(player);
	}

	public int getNumberofPlayers()
	{
		return this.players.size();
	}

	public List<EntityPlayer> getPlayers()
	{
		return this.players;
	}

	public String getTeamColor()
	{
		return teamColor;
	}

	public void setTeamColor(String teamColor)
	{
		this.teamColor = teamColor;
	}
}
