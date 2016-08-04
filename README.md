# VaultAPI - Abstraction Library API for Bukkit Plugins
# VaultAPI - 提供给Bukkit插件的一个抽象化库 - ![Travis-ci](https://travis-ci.org/MilkBowl/VaultAPI.svg?branch=master)

How to use with maven: note that the VaultAPI version is 2 numerals, unlike Vault versions which are 3.  The 2 numerals in the VaultAPI will always correspond to the 2 beginning numerals in a Vault version to make it clear what versions your plugin will for sure work with.
如何在Maven中使用: 注意VaultAPI的版本号是两个数字, 区别于Vault的版本号为三个数字. VaultAPI版本号中的两位数总是对应Vault版本号的前两个数字, 这让你更加明确应该使用哪个版本的Vault做为前置.
将以下代码添加到pom.xml中即可.
```
    <repositories>
        <repository>
	        <id>vault-repo</id>
	        <url>http://nexus.theyeticave.net/content/repositories/pub_releases</url>
        </repository>
    </repositories>
    <dependencies>
        <dependency>
            <groupId>net.milkbowl.vault</groupId>
            <artifactId>VaultAPI</artifactId>
            <version>1.5</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>

```

## Why Vault?
## 为什么选择Vault?
I have no preference which library suits your plugin and development efforts
best.  Really, I thought a central suite (rather...Vault) of solutions was the
the proper avenue than focusing on a single category of plugin.  That's where
the idea for Vault came into play.
我并没有想告诉你如何选择一个适合的库以方便你的开发. 事实上, 我认为使用一个汇总起来的库(类似->Vault)是一个更加适合用于编写单一类别插件的方法. 这就是我对Vault的想法发挥作用的地方.

So, what features do I _think_ you'll like the most?

 * No need to include my source code in your plugin
 * Broad range of supported plugins
 * Choice!

## License
Copyright (C) 2011 Morgan Humes <morgan@lanaddict.com>

Vault is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Vault is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with Vault.  If not, see <http://www.gnu.org/licenses/>.

## Building
VaultAPI comes with all libraries needed to build from the current branch.


## Implementing Vault
Implementing Vault is quite simple. It requires getting the Economy, Permission, or Chat service from the Bukkit ServiceManager. See the example below:

```java
package com.example.plugin;

import java.util.logging.Logger;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class ExamplePlugin extends JavaPlugin {
    
    private static final Logger log = Logger.getLogger("Minecraft");
    public static Economy econ = null;
    public static Permission perms = null;
    public static Chat chat = null;

    @Override
    public void onDisable() {
        log.info(String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));
    }

    @Override
    public void onEnable() {
        if (!setupEconomy() ) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        setupPermissions();
        setupChat();
    }
    
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
    
    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }
    
    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }
    
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if(!(sender instanceof Player)) {
            log.info("Only players are supported for this Example Plugin, but you should not do this!!!");
            return true;
        }
        
        Player player = (Player) sender;
        
        if(command.getLabel().equals("test-economy")) {
            // Lets give the player 1.05 currency (note that SOME economic plugins require rounding!)
            sender.sendMessage(String.format("You have %s", econ.format(econ.getBalance(player.getName()))));
            EconomyResponse r = econ.depositPlayer(player, 1.05);
            if(r.transactionSuccess()) {
                sender.sendMessage(String.format("You were given %s and now have %s", econ.format(r.amount), econ.format(r.balance)));
            } else {
                sender.sendMessage(String.format("An error occured: %s", r.errorMessage));
            }
            return true;
        } else if(command.getLabel().equals("test-permission")) {
            // Lets test if user has the node "example.plugin.awesome" to determine if they are awesome or just suck
            if(perms.has(player, "example.plugin.awesome")) {
                sender.sendMessage("You are awesome!");
            } else {
                sender.sendMessage("You suck!");
            }
            return true;
        } else {
            return false;
        }
    }
}
```
