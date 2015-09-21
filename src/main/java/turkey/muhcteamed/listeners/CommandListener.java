package turkey.muhcteamed.listeners;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import turkey.muhcteamed.TeamedCore;
import turkey.muhcteamed.teams.Team;

public class CommandListener extends CommandBase
{

	public CommandListener()
	{
		
	}

	@Override
	public String getCommandName()
	{
		return "muhc";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender)
	{
		return "muhc <create:group:help:settings> ";
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] args)
	{
		if(args.length == 0)
		{
			icommandsender.addChatMessage(new ChatComponentText("Invalid arguments"));
			return;
		}

		if(args[0].equalsIgnoreCase("help"))
		{
			icommandsender.addChatMessage(new ChatComponentText("Valid command examples"));
			icommandsender.addChatMessage(new ChatComponentText("/muhc <create> <teamName> [default(true/false)]"));
			icommandsender.addChatMessage(new ChatComponentText("/muhc <team> <teamName> <addUser:removeUser> <userName>"));
			icommandsender.addChatMessage(new ChatComponentText("/muhc <settings> <setting> <value>"));
		}
		else if(args[0].equalsIgnoreCase("create"))
		{
			if(args.length > 1)
			{
				if(TeamedCore.manager.getTeamFromName(args[1]) != null)
				{
					icommandsender.addChatMessage(new ChatComponentText("That team already exists!"));
				}
				else
				{
					if(args.length == 3 && args[2].equalsIgnoreCase("true"))
					{
						TeamedCore.manager.addDefaultTeam(args[1]);
						icommandsender.addChatMessage(new ChatComponentText("Default team " + args[1] + " has been added!"));
					}
					else
					{
						TeamedCore.manager.addTeam(args[1]);
						icommandsender.addChatMessage(new ChatComponentText("Team " + args[1] + " has been added!"));
					}
				}

			}
		}
		else if(args[0].equalsIgnoreCase("team"))
		{
			if(args.length == 4)
			{
				Team team = TeamedCore.manager.getTeamFromName(args[1]);
				EntityPlayer player = MinecraftServer.getServer().getEntityWorld().getPlayerEntityByName(args[3]);
				if(team != null && player != null)
				{
					if(args[2].equalsIgnoreCase("addUser"))
					{
						TeamedCore.manager.assignPlayerToTeam(player, args[1]);
						icommandsender.addChatMessage(new ChatComponentText(player.getCommandSenderName() + " was added to " + args[1]));
						TeamedCore.manager.sendMessageToAll(player.getCommandSenderName() + " was assigned to " + args[1]);
					}
					else if(args[2].equalsIgnoreCase("removeUser"))
					{
						TeamedCore.manager.removePlayerFromTeam(player);
						icommandsender.addChatMessage(new ChatComponentText(player.getCommandSenderName() + " was removed from " + args[1]));
					}
					else
					{
						icommandsender.addChatMessage(new ChatComponentText(args[2] + " was not recognized!"));
					}
				}
				else if(team == null)
				{
					icommandsender.addChatMessage(new ChatComponentText(args[1] + " is not a valid team!"));
				}
				else
				{
					icommandsender.addChatMessage(new ChatComponentText(args[3] + " is not a valid player!!!"));
				}
			}
		}
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender icommandsender)
	{
		return true;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List addTabCompletionOptions(ICommandSender icommandsender, String[] astring)
	{
		//This is set up like this for expansion purposes
		if(astring.length > 1)
		{
			if(astring[0].equalsIgnoreCase("team"))
			{
				if(astring.length == 4)
				{
					return getListOfStringsMatchingLastWord(astring, MinecraftServer.getServer().getAllUsernames());
				}
			}
		}
		
		return null;
	}
}