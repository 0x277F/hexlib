package __0x277F.plugins.hexlib.test;

import __0x277F.plugins.hexlib.HexLibPlugin;
import __0x277F.plugins.hexlib.render.ParticleColor;
import __0x277F.plugins.hexlib.render.ParticleEffect;
import __0x277F.plugins.hexlib.render.Render1DLineAgent;
import __0x277F.plugins.hexlib.render.Render2DRectangleAgent;
import __0x277F.plugins.hexlib.render.Render3DCuboidAgent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ParticleTestCommand implements Subcommand {
    private ParticleEffect effect;

    {
        HexLibCommandExecutor.registerSubcommand(this, "particle");
        this.effect = ParticleEffect.newParticleEffect();
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        Player p = (Player) sender;
        Location l = p.getLocation().add(0, 3, 0);
        Vector v = p.getLocation().getDirection().clone().multiply(2);
        switch (Integer.valueOf(args[0])) {
            case 0:
                Bukkit.getScheduler().runTaskTimer(HexLibPlugin.getInstance(), new BukkitRunnable() {
                    int time = 20 * 15;

                    @Override
                    public void run() {
                        effect.displayParticles(128, 6, 6, l);
                        time--;
                        if (time <= 0) this.cancel();
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

    @Override
    public boolean isValid(CommandSender sender) {
        return sender instanceof Player && sender.hasPermission("hexlib.debug");
    }
}
