package me.mpg.my_maintainer;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.*;
import org.bukkit.command.TabExecutor;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public final class My_maintainer extends JavaPlugin {

    public Statement statement = null;
    public  Connection connection = null;

    List<String> arguments = new ArrayList<String>();
    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("My Maintainer is working now!");

        String url = "jdbc:mysql://212.22.92.26:3306/qemgkhrw_43131";
        String user = "qemgkhrw";
        String password = "changeme";

        try{
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the Properties Database");
            statement = connection.createStatement();
        }catch (SQLException e){
            System.out.println("Unable to connect to the Properties Database");

            e.printStackTrace();
        }

        String sql = "UPDATE properties SET working_state=1";

        try {
            statement.executeUpdate(sql);
            System.out.println("Working state set to 1");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        System.out.println("My Maintainer is no longer working!");

        String sql = "UPDATE properties SET working_state=0";

        try {
            statement.executeUpdate(sql);
            System.out.println("Working state set to 0");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("statement")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;

                if (args.length == 0) {
                    p.sendMessage(ChatColor.RED + "You did not provide any statement to set when running the command!");
                    p.sendMessage("Example: /statement <argument>");

                    return true;
                } else if (args.length == 1){
                    if (args[0].equalsIgnoreCase("penis")) {
                        p.sendMessage("Suck my cock!");
                    }
                    if (args[0].equalsIgnoreCase("working")) {
                        String sql = "UPDATE properties SET working_state=1";
                        try {
                            statement.executeUpdate(sql);
                            System.out.println("Working state set to 1");
                            p.sendMessage("You have set working state value to " + ChatColor.GREEN + "WORKING");
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    if (args[0].equalsIgnoreCase("stopped")) {
                        String sql = "UPDATE properties SET working_state=0";
                        try {
                            statement.executeUpdate(sql);
                            System.out.println("Working state set to 0");
                            p.sendMessage("You have set working state value to " + ChatColor.RED + "STOPPED");
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    if (args[0].equalsIgnoreCase("maintenance")) {
                        String sql = "UPDATE properties SET working_state=2";
                        try {
                            statement.executeUpdate(sql);
                            System.out.println("Working state set to 2");
                            p.sendMessage("You have set working state value to " + ChatColor.YELLOW + "MAINTENANCE");
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }


        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if (arguments.isEmpty()) {
            arguments.add("working");
            arguments.add("stopped");
            arguments.add("maintenance");
        }

        List<String> result = new ArrayList<String>();
        if (args.length == 1) {
            for (String a : arguments) {
                if (a.toLowerCase().startsWith(args[0].toLowerCase()))
                    result.add(a);
            }
            return result;
        }

        return null;
    }
}
