package __0x277F.plugins.hexlib.test;

import __0x277F.plugins.hexlib.HexLibPlugin;
import __0x277F.plugins.hexlib.render.ParticleColor;
import __0x277F.plugins.hexlib.render.ParticleEffect;
import __0x277F.plugins.hexlib.render.Render1DLineAgent;
import __0x277F.plugins.hexlib.render.Render2DRectangleAgent;
import __0x277F.plugins.hexlib.render.Render3DCuboidAgent;
import com.torchmind.minecraft.annotation.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@com.torchmind.minecraft.annotation.command.Command(name = "hexlib", description = "HexLib debug command", permission = "hexlib.debug", usage = "/hexlib <subcommand>")
@Permission(name = "hexlib.debug", defaultValue = PermissionDefault.OP, description = "Provides access to HexLib debug commands")
public class HexLibCommandExecutor implements CommandExecutor {
    private static Map<String, Subcommand> subcommandMap = new HashMap<>();

    public static void registerSubcommand(Subcommand subcommand, String... names) {
        Arrays.stream(names).forEach(name -> subcommandMap.put(name.toLowerCase(), subcommand));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ParticleEffect effect = ParticleEffect.newParticleEffect();
        /*if(subcommandMap.containsKey(s.toLowerCase())) {
            Subcommand subcommand = subcommandMap.get(s.toLowerCase());
            if (subcommand.isValid(commandSender)) {
                return subcommand.execute(commandSender, args[0], Arrays.copyOfRange(args, 1, args.length));
            }
            commandSender.sendMessage(ChatColor.RED + "You do not meet the requirements to use this command.");
        }
        commandSender.sendMessage(ChatColor.RED + "[HexLib] No such subcommand.");
        return true;*/
        Player p = (Player) sender;
        Location l = p.getLocation().add(0, 3, 0);
        Vector v = p.getLocation().getDirection().clone().multiply(2);
        switch (Integer.valueOf(args[0])) {
            case 0:
                Bukkit.getScheduler().runTaskTimer(HexLibPlugin.getInstance(), new BukkitRunnable() {
                    @Override
                    public void run() {
                        effect.displayParticles(128, 6, 6, l);
                    }
                }, 10L, 1L);
                break;
            case 1:
                Render1DLineAgent agent = new Render1DLineAgent(ParticleColor.RED, v, l, 2, 10);
                agent.drawRepeatedly(1);
                break;
            case 2:
                Render2DRectangleAgent agent2D = new Render2DRectangleAgent(ParticleColor.RED, l, v, v, 2, 10);
                agent2D.drawRepeatedly(1);
                break;
            case 3:
                Render3DCuboidAgent agent3D = new Render3DCuboidAgent(ParticleColor.RED, l, v, v, v, 2, 10);
                agent3D.drawRepeatedly(1);
        }
        return true;

    }
}
