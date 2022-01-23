package commoble.useitemonblockevent.internal;

import net.minecraftforge.fml.IExtensionPoint.DisplayTest;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkConstants;

@Mod(UseItemOnBlockEventMod.MODID)
public class UseItemOnBlockEventMod
{
	public static final String MODID = "useitemonblockevent";
	
	public UseItemOnBlockEventMod()
	{
		ModLoadingContext modContext = ModLoadingContext.get();

		// mod is not required to be on both sides, greenlight mismatched servers in client's server list
		modContext.registerExtensionPoint(DisplayTest.class,
			() -> new DisplayTest(
				() -> NetworkConstants.IGNORESERVERONLY,
				(s, networkBool) -> true));
	}
}
