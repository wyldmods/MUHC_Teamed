package turkey.muhcteamed;

import org.apache.logging.log4j.Logger;

import turkey.muhcteamed.config.ConfigLoader;
import turkey.muhcteamed.listeners.CommandListener;
import turkey.muhcteamed.listeners.PlayerConnectionListener;
import turkey.muhcteamed.proxy.CommonProxy;
import turkey.muhcteamed.teams.TeamManager;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

@Mod(modid = TeamedCore.MODID, version = TeamedCore.VERSION)
public class TeamedCore
{
	public static final String MODID = "muhcteamed";
	public static final String VERSION = "@Version@";
	public static final String NAME = "MUHC Teamed";

	@Instance(value = MODID)
	public static TeamedCore instance;
	@SidedProxy(clientSide = "turkey.muhcteamed.proxy.ClientProxy", serverSide = "turkey.muhcteamed.proxy.CommonProxy")
	public static CommonProxy proxy;
	public static Logger logger;
	public static TeamManager manager;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();
		ConfigLoader.loadConfigSettings(event.getSuggestedConfigurationFile());
		manager = new TeamManager();

		FMLCommonHandler.instance().bus().register(new PlayerConnectionListener());
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{

	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{

	}

	@EventHandler
	public void serverLoad(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new CommandListener());
	}
}