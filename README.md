# VaultAPI - Abstraction Library API for Bukkit Plugins
# VaultAPI - 提供给Bukkit插件的一个抽象化库 - ![Travis-ci](https://travis-ci.org/MilkBowl/VaultAPI.svg?branch=master)

How to use with maven: note that the VaultAPI version is 2 numerals, unlike Vault versions which are 3.  The 2 numerals in the VaultAPI will always correspond to the 2 beginning numerals in a Vault version to make it clear what versions your plugin will for sure work with.

如何在Maven中使用: 注意VaultAPI的版本号是两个数字, 区别于Vault的版本号为三个数字. VaultAPI版本号中的两位数总是对应Vault版本号的前两个数字, 这让你更加明确应该使用哪个版本的Vault做为前置.
将以下代码添加到pom.xml中即可.
```
    <repositories>
        <repository>
	        <id>vault-repo</id>
	        <url>http://nexus.hc.to/content/repositories/pub_releases</url>
        </repository>
    </repositories>
    <dependencies>
        <dependency>
            <groupId>net.milkbowl.vault</groupId>
            <artifactId>VaultAPI</artifactId>
            <version>1.6</version>
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

我并没有想告诉你如何选择一个适合的库以方便你的开发. 事实上, 我认为使用一个汇总起来的库(Vault)是对(需要经济系统的)一整类插件的合适的解决方案. 这就是我对Vault的开始想法发挥作用的地方.

So, what features do I _think_ you'll like the most?

 * No need to include my source code in your plugin
 * Broad range of supported plugins
 * Choice!
 
因此, 什么特性是我偏好的?
 * 无需在你的插件中包含我的代码
 * 能被广泛支持
 * 多种选择!
 
## License
## 许可协议
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
## 构建
VaultAPI comes with all libraries needed to build from the current branch.

VaultAPI的当前分支带有所有需要被构建的库.


## Implementing Vault
## 使用Vault
Implementing Vault is quite simple. It requires getting the Economy, Permission, or Chat service from the Bukkit ServiceManager. See the example below:

使Vault生效是非常简单的. 它需要从Bukkit的服务管理器获得经济, 权限, 聊天服务. 看看下面的梨子(例子):

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
            log.info("禁止非玩家执行此命令!!!");
            return true;
        }
        
        Player player = (Player) sender;
        
        if(command.getLabel().equals("test-economy")) {
            // 让我们给玩家1.05块钱 (注意: 有些经济插件需要四舍五入!)
            sender.sendMessage(String.format("你有 %s 元", econ.format(econ.getBalance(player.getName()))));
            EconomyResponse r = econ.depositPlayer(player, 1.05);
            if(r.transactionSuccess()) {
                sender.sendMessage(String.format("你得到了 %s 元. 现在你有 %s", econ.format(r.amount), econ.format(r.balance)));
            } else {
                sender.sendMessage(String.format("发生了一个错误: %s", r.errorMessage));
            }
            return true;
        } else if(command.getLabel().equals("test-permission")) {
            // 让我们检查玩家是否有权限节点: "example.plugin.903" 以确定他们是903还是逗比.
            if(perms.has(player, "example.plugin.903")) {
                sender.sendMessage("你是903!");
            } else {
                sender.sendMessage("你是逗比!");
            }
            return true;
        } else {
            return false;
        }
    }
}
```
