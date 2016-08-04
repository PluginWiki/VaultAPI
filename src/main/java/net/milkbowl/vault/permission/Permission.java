/* This file is part of Vault.

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
 */
package net.milkbowl.vault.permission;

import java.util.logging.Logger;

import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

/**
 * The main Permission API - allows for group and player based permission tests
 * 权限API - 用于玩家和组的基础权限测试
 */
public abstract class Permission {

	protected static final Logger log = Logger.getLogger("Minecraft");
    protected Plugin plugin = null;

    /**
     * Gets name of permission method
     * 获得权限方法名
     * @return Name of Permission Method
     * 权限方法名
     */
    abstract public String getName();

    /**
     * Checks if permission method is enabled.
     * 检查是否开启权限方法
     * @return Success or Failure
     * 成功或失败 (true 或者 false)
     */
    abstract public boolean isEnabled();
    
    /**
     * Returns if the permission system is or attempts to be compatible with super-perms.
     * 返回权限系统是否尝试兼容超级权限系统
     * @return True if this permission implementation works with super-perms
     * 返回true如果权限系统实现给予超级权限工作
     */
    abstract public boolean hasSuperPermsCompat();
    
    /**
     * @deprecated As of VaultAPI 1.4 use {@link #playerHas(String, OfflinePlayer, String)} instead.
     * 从VaultAPI 1.4开始使用{@link #playerHas(String, OfflinePlayer, String)}替代
     */
    @Deprecated
    public boolean has(String world, String player, String permission) {
        if (world == null) {
            return playerHas((String) null, player, permission);
        }
        return playerHas(world, player, permission);
    }

    /**
     * @deprecated As of VaultAPI 1.4 use {@link #playerHas(String, OfflinePlayer, String)} instead.
     * 从VaultAPI 1.4开始使用{@link #playerHas(String, OfflinePlayer, String)}替代
     */
    @Deprecated
    public boolean has(World world, String player, String permission) {
        if (world == null) {
            return playerHas((String) null, player, permission);
        }
        return playerHas(world.getName(), player, permission);
    }

    /**
     * Checks if a CommandSender has a permission node.
     * This will return the result of bukkits, generic .hasPermission() method and is identical in all cases.
     * This method will explicitly fail if the registered permission system does not register permissions in bukkit.
     * 检查一个命令发送者的权限节点
     * 这将会返回bukkit通用的结果 .hasPremission()发发并且完全相同
     * 如果权限系统没有在bukkit注册则这个方法必定是失败的
     * 
     * For easy checking of a commandsender
     * 方便检查指令发送者权限
     * 
     * @param sender to check permissions on
     * 发送者
     * @param permission to check for
     * 权限
     * @return true if the sender has the permission
     * 如果有权限返回true 否则返回false
     */
    public boolean has(CommandSender sender, String permission) {
        return sender.hasPermission(permission);
    }

    /**
     * Checks if player has a permission node. (Short for playerHas(...)
     * 检查玩家是否有权限节点(playerHas()方法的缩写)
     * 
     * @param player Player Object
     * 玩家对象
     * @param permission Permission node
     * 权限节点
     * @return Success or Failure
     * 成功或失败 (true 或者 false)
     */
    public boolean has(Player player, String permission) {
        return player.hasPermission(permission);
    }

    /**
     * @deprecated As of VaultAPI 1.4 use {@link #playerHas(String, OfflinePlayer, String)} instead.
     * 从VaultAPI 1.4开始使用{@link #playerHas(String, OfflinePlayer, String)}替代
     */
    @Deprecated
    abstract public boolean playerHas(String world, String player, String permission);

    /**
     * @deprecated As of VaultAPI 1.4 use {@link #playerHas(String, OfflinePlayer, String)} instead.
     * 从VaultAPI 1.4开始使用{@link #playerHas(String, OfflinePlayer, String)}替代
     */
    @Deprecated
    public boolean playerHas(World world, String player, String permission) {
        if (world == null) {
            return playerHas((String) null, player, permission);
        }
        return playerHas(world.getName(), player, permission);
    }
    
    /**
     * Checks if player has a permission node.
     * Supports NULL value for World if the permission system registered supports global permissions.
     * But May return odd values if the servers registered permission system does not have a global permission store.
     * 检查玩家是否有权限节点
     * 支持设置世界为null值如果权限系统支持
     * 但是可能会返回异常值如果权限系统不支持的话
     * 
     * @param world String world name
     * 世界名
     * @param player to check
     * 玩家
     * @param permission Permission node
     * 权限节点
     * @return Success or Failure
     * 成功或失败 (true 或者 false)
     */
    public boolean playerHas(String world, OfflinePlayer player, String permission) {
    	if (world == null) {
    		return has((String) null, player.getName(), permission);
    	}
        return has(world, player.getName(), permission);
    }

    /**
     * Checks if player has a permission node.
     * Defaults to world-specific permission check if the permission system supports it.
     * See {@link #playerHas(String, OfflinePlayer, String)} for explicit global or world checks.
     * 检查玩家是否有权限节点
     * 该检查默认指定世界如果权限系统支持
     * 查看{@link #playerHas(String, OfflinePlayer, String)}
     *  
     * @param player Player Object
     * 玩家对象
     * @param permission Permission node
     * 权限节点
     * @return Success or Failure
     * 成功或失败 (true 或者 false)
     */
    public boolean playerHas(Player player, String permission) {
        return has(player, permission);
    }

    /**
     * @deprecated As of VaultAPI 1.4 use {@link #playerAdd(String, OfflinePlayer, String)} instead.
     * 从VaultAPI 1.4开始使用{@link #playerAdd(String, OfflinePlayer, String)}替代
     * 
     * Add permission to a player.
     * Supports NULL value for World if the permission system registered supports global permissions.
     * But May return odd values if the servers registered permission system does not have a global permission store.
     * 添加权限给玩家
     * 支持将世界设为null值如果权限系统支持全局权限的话
     * 但是如果权限系统不支持则可能返回异常值
     * 
     * @param world World name
     * 世界名
     * @param player Player name
     * 玩家
     * @param permission Permission node
     * 权限节点
     * @return Success or Failure
     * 成功或失败 (true 或者 false)
     */
    @Deprecated
    abstract public boolean playerAdd(String world, String player, String permission);

    /**
     * @deprecated As of VaultAPI 1.4 use {@link #playerAdd(String, OfflinePlayer, String)} instead.
     * 从VaultAPI 1.4开始使用{@link #playerAdd(String, OfflinePlayer, String)}替代
     */
    @Deprecated
    public boolean playerAdd(World world, String player, String permission) {
        if (world == null) {
            return playerAdd((String) null, player, permission);
        }
        return playerAdd(world.getName(), player, permission);
    }

    /**
     * Add permission to a player.
     * Supports NULL value for World if the permission system registered supports global permissions.
     * But May return odd values if the servers registered permission system does not have a global permission store.
     * 添加权限给玩家
     * 支持世界为null值如果权限系统支持全局权限
     * 但是可能返回异常值如果权限系统不支持的话.
     * 
     * @param world String world name
     * 世界名
     * @param player to add to
     * 玩家
     * @param permission Permission node
     * 权限节点
     * @return Success or Failure
     * 成功或失败 (true 或者 false)
     */
    public boolean playerAdd(String world, OfflinePlayer player, String permission) {
        if (world == null) {
            return playerAdd((String) null, player.getName(), permission);
        }
        return playerAdd(world, player.getName(), permission);
    }

    /**
     * Add permission to a player ONLY for the world the player is currently on.
     * This is a world-specific operation, if you want to add global permission you must explicitly use NULL for the world.
     * See {@link #playerAdd(String, OfflinePlayer, String)} for global permission use.
     * 在玩家所在的世界添加权限给玩家
     * 这是一个指定世界的操作, 如果你想要去添加全局权限你必须设置世界为null值
     * 查看{@link #playerAdd(String, OfflinePlayer, String)}来获得如何使用全局权限
     * 
     * @param player Player Object
     * 玩家对象
     * @param permission Permission node
     * 权限节点
     * @return Success or Failure
     * 成功或失败 (true 或者 false)
     */
    public boolean playerAdd(Player player, String permission) {
        return playerAdd(player.getWorld().getName(), player, permission);
    }
    
    /**
     * @deprecated As of VaultAPI 1.4 use {@link #playerAddTransient(OfflinePlayer, String)} instead.
     * 从VaultAPI 1.4开始使用{@link #playerAddTransient(OfflinePlayer, String)}替代
     */
    @Deprecated
    public boolean playerAddTransient(String player, String permission) throws UnsupportedOperationException {
		Player p = plugin.getServer().getPlayer(player);
		if (p == null) {
			throw new UnsupportedOperationException(getName() + " does not support offline player transient permissions!");
		}
		return playerAddTransient(p, permission);
	}
    
    /**
     * Add transient permission to a player.
     * This implementation can be used by any subclass which implements a "pure" superperms plugin, i.e. 
     * one that only needs the built-in Bukkit API to add transient permissions to a player.
     * 添加临时权限给玩家
     * 这个操作添加一个权限在玩家对象中通过Bukkit的权限接口
     * 
     * @param player to add to
     * 玩家
     * @param permission Permission node
     * 权限节点
     * @return Success or Failure
     * 成功或失败 (true 或者 false)
     */
    public boolean playerAddTransient(OfflinePlayer player, String permission) throws UnsupportedOperationException {
    	if (player.isOnline()) {
    		return playerAddTransient((Player) player, permission);
    	}
		throw new UnsupportedOperationException(getName() + " does not support offline player transient permissions!");
	}

    /**
     * Add transient permission to a player.
     * This operation adds a permission onto the player object in bukkit via Bukkit's permission interface.
     * 添加临时权限给玩家
     * 这个操作添加一个权限在玩家对象中通过Bukkit的权限接口
     * 
     * @param player Player Object
     * 玩家对象
     * @param permission Permission node
     * 权限节点
     * @return Success or Failure
     * 成功或失败 (true 或者 false)
     */
    public boolean playerAddTransient(Player player, String permission) {
		for (PermissionAttachmentInfo paInfo : player.getEffectivePermissions()) {
			if (paInfo.getAttachment() != null && paInfo.getAttachment().getPlugin().equals(plugin)) {
				paInfo.getAttachment().setPermission(permission, true);
				return true;
			}
		}

		PermissionAttachment attach = player.addAttachment(plugin);
		attach.setPermission(permission, true);

		return true;
    }

    /**
     * Adds a world specific transient permission to the player, may only work with some permission managers.
     * Defaults to GLOBAL permissions for any permission system that does not support world-specific transient permissions!
     * 在一个世界添加临时权限给玩家, 这可能只能在某些权限插件下工作
     * 如果权限系统不支持临时权限则默认添加全局权限!
     * 
     * @param worldName to check on
     * 世界名
     * @param player to add to
     * 玩家
     * @param permission to test
     * 权限
     * @return Success or Failure
     * 成功或失败 (true 或者 false)
     */
    public boolean playerAddTransient(String worldName, OfflinePlayer player, String permission) {
    	return playerAddTransient(worldName, player.getName(), permission);
    }
    
    /**
     * Adds a world specific transient permission to the player, may only work with some permission managers.
     * Defaults to GLOBAL permissions for any permission system that does not support world-specific transient permissions!
     * 在一个世界添加临时权限给玩家, 这可能只能在某些权限插件下工作
     * 如果权限系统不支持临时权限则默认添加全局权限!
     * 
     * @param worldName to check on
     * 世界名
     * @param player to check
     * 玩家名
     * @param permission to check for
     * 权限节点
     * @return Success or Failure
     * 成功或失败 (true 或者 false)
     */
    public boolean playerAddTransient(String worldName, Player player, String permission) {
    	return playerAddTransient(player, permission);
    }
    
    /**
     * @deprecated As of VaultAPI 1.4 use {@link #playerAddTransient(String, OfflinePlayer, String)} instead.
     * 从VaultAPI 1.4开始使用{@link #playerAddTransient(String, OfflinePlayer, String)}替代
     */
    @Deprecated
    public boolean playerAddTransient(String worldName, String player, String permission) {
		Player p = plugin.getServer().getPlayer(player);
		if (p == null) {
			throw new UnsupportedOperationException(getName() + " does not support offline player transient permissions!");
		}
		return playerAddTransient(p, permission);
    }
    
    /**
     * @deprecated As of VaultAPI 1.4 use {@link #playerRemoveTransient(String, OfflinePlayer, String)} instead.
     * 从VaultAPI 1.4开始使用{@link #playerRemoveTransient(String, OfflinePlayer, String)}替代
     */
    @Deprecated
	public boolean playerRemoveTransient(String worldName, String player, String permission) {
		Player p = plugin.getServer().getPlayer(player);
		if (p == null)
			return false;
		
		return playerRemoveTransient(p, permission);
	}
	
    /**
     * Removes a world specific transient permission from the player, may only work with some permission managers.
     * Defaults to GLOBAL permissions for any permission system that does not support world-specific transient permissions!
     * 移除玩家在一个世界的临时权限, 但是可能只在某些权限插件下工作
     * 对于任何不支持临时权限的权限系统都会默认为全局权限
     * 
     * @param worldName to remove for
     * 世界名
     * @param player to remove for
     * 玩家名
     * @param permission to remove
     * 权限节点
     * @return Success or Failure
     * 成功或失败 (true 或者 false)
     */
    public boolean playerRemoveTransient(String worldName, OfflinePlayer player, String permission) {
    	return playerRemoveTransient(worldName, player.getName(), permission);
    }
	
    /**
     * Removes a world specific transient permission from the player, may only work with some permission managers.
     * Defaults to GLOBAL permissions for any permission system that does not support world-specific transient permissions!
     * 移除玩家在一个世界的临时权限, 但是可能只在某些权限插件下工作
     * 对于任何不支持临时权限的权限系统都会默认为全局权限
     * 
     * @param worldName to check on
     * 世界名
     * @param player to check
     * 玩家名
     * @param permission to check for
     * 权限
     * @return Success or Failure
     * 成功或失败 (true 或者 false)
     */
    public boolean playerRemoveTransient(String worldName, Player player, String permission) {
    	return playerRemoveTransient(worldName, (OfflinePlayer) player, permission);
    }
    
    /**
     * @deprecated As of VaultAPI 1.4 use {@link #playerRemove(String, OfflinePlayer, String)} instead.
     * 从VaultAPI 1.4开始使用{@link #playerRemove(String, OfflinePlayer, String)}替代
     */
    @Deprecated
    abstract public boolean playerRemove(String world, String player, String permission);

    /**
     * Remove permission from a player.
     * Supports NULL value for World if the permission system registered supports global permissions.
     * But May return odd values if the servers registered permission system does not have a global permission store.
     * 移除玩家的权限
     * 支持将世界设置为null值如果权限系统支持全局权限的话.
     * 但是如果不支持则可能返回异常的值
     * 
     * @param world World name
     * 世界名
     * @param player OfflinePlayer
     * 玩家
     * @param permission Permission node
     * 权限
     * @return Success or Failure
     * 成功或失败 (true 或者 false)
     */
    public boolean playerRemove(String world, OfflinePlayer player, String permission) {
        if (world == null) {
            return playerRemove((String) null, player.getName(), permission);
        }
        return playerRemove(world, player.getName(), permission);
    }

    /**
     * Remove permission from a player.
     * Supports NULL value for World if the permission system registered supports global permissions.
     * But May return odd values if the servers registered permission system does not have a global permission store.
     * 移除玩家的权限
     * 支持将世界设置为null值如果权限系统支持全局权限的话.
     * 但是如果不支持则可能返回异常的值
     * 
     * @param world World name
     * 世界名
     * @param player Player name
     * 玩家名
     * @param permission Permission node
     * 权限节点
     * @return Success or Failure
     * 成功或失败 (true 或者 false)
     */
    @Deprecated
    public boolean playerRemove(World world, String player, String permission) {
        if (world == null) {
            return playerRemove((String) null, player, permission);
        }
        return playerRemove(world.getName(), player, permission);
    }

    /**
     * Remove permission from a player.
     * Will attempt to remove permission from the player on the player's current world.  This is NOT a global operation.
     * 移除玩家的权限
     * 这将会移除玩家在当前世界的权限. 这不是全局操作
     * 
     * @param player Player Object
     * 玩家对象
     * @param permission Permission node
     * 权限节点
     * @return Success or Failure
     * 成功或失败 (true 或者 false)
     */
    public boolean playerRemove(Player player, String permission) {
        return playerRemove(player.getWorld().getName(), player, permission);
    }
    
    /**
     * @deprecated As of VaultAPI 1.4 use {@link #playerRemoveTransient(OfflinePlayer, String)} instead.
     * 从VaultAPI 1.4开始使用{@link #playerRemoveTransient(OfflinePlayer, String)}替代
     */
    @Deprecated
	public boolean playerRemoveTransient(String player, String permission) {
		Player p = plugin.getServer().getPlayer(player);
		if (p == null)
			return false;
		
		return playerRemoveTransient(p, permission);
	}

    /**
     * Remove transient permission from a player.
     * This implementation can be used by any subclass which implements a "pure" superperms plugin, i.e. 
     * one that only needs the built-in Bukkit API to remove transient permissions from a player.  Any subclass
     * implementing a plugin which provides its own API for this needs to override this method.
     * 删除玩家的临时权限
     * 这个实现可以被用于继承了"pure"的子类 
     * 例如:
     * 只需要一个内置的Bukkit API去移除玩家的临时权限. 任何子类实现都对于这个需要重写的方法给出了自己的API
     * 
     * @param player OfflinePlayer
     * 玩家
     * @param permission Permission node
     * 权限节点
     * @return Success or Failure
     * 成功或失败 (true 或者 false)
     */
	public boolean playerRemoveTransient(OfflinePlayer player, String permission) {
		if (player.isOnline()) {
			return playerRemoveTransient((Player) player, permission);
		} else {
			return false;
		}
	}
    
    /**
     * Remove transient permission from a player.
     * 移除玩家的临时权限
     * 
     * @param player Player Object
     * 玩家对象
     * @param permission Permission node
     * 权限节点
     * @return Success or Failure
     * 成功或失败 (true 或者 false)
     */
    public boolean playerRemoveTransient(Player player, String permission) {
		for (PermissionAttachmentInfo paInfo : player.getEffectivePermissions()) {
			if (paInfo.getAttachment() != null && paInfo.getAttachment().getPlugin().equals(plugin)) {
				paInfo.getAttachment().unsetPermission(permission);
				return true;
			}
		}
		return false;
    }
    
    /**
     * Checks if group has a permission node.
     * Supports NULL value for World if the permission system registered supports global permissions.
     * But May return odd values if the servers registered permission system does not have a global permission store.
     * 检查组是否拥有权限
     * 支持将世界设为null值如果权限系统支持的话
     * 但是可能会返回异常值当权限系统不支持时
     * 
     * @param world World name
     * 世界名
     * @param group Group name
     * 组名
     * @param permission Permission node
     * 权限节点
     * @return Success or Failure
     * 成功或失败 (true 或者 false)
     */
    abstract public boolean groupHas(String world, String group, String permission);

    /**
     * Checks if group has a permission node.
     * Supports NULL value for World if the permission system registered supports global permissions.
     * But May return odd values if the servers registered permission system does not have a global permission store.
     * 检查组是否拥有权限
     * 支持将世界设为null值如果权限系统支持的话
     * 但是可能会返回异常值当权限系统不支持时
     * 
     * @param world World Object
     * 世界对象
     * @param group Group name
     * 组名
     * @param permission Permission node
     * 权限节点
     * @return Success or Failure
     * 成功或失败 (true 或者 false)
     */
    public boolean groupHas(World world, String group, String permission) {
        if (world == null) {
            return groupHas((String) null, group, permission);
        }
        return groupHas(world.getName(), group, permission);
    }

    /**
     * Add permission to a group.
     * Supports NULL value for World if the permission system registered supports global permissions.
     * But May return odd values if the servers registered permission system does not have a global permission store.
     * 添加一个组的权限
     * 支持将世界设为null值如果权限系统支持的话
     * 但是可能会返回异常值当权限系统不支持时
     * 
     * @param world World name
     * 世界名
     * @param group Group name
     * 组名
     * @param permission Permission node
     * 权限节点
     * @return Success or Failure
     * 成功或失败 (true 或者 false)
     */
    abstract public boolean groupAdd(String world, String group, String permission);

    /**
     * Add permission to a group.
     * Supports NULL value for World if the permission system registered supports global permissions.
     * But May return odd values if the servers registered permission system does not have a global permission store.
     * 添加一个组的权限
     * 支持将世界设为null值如果权限系统支持的话
     * 但是可能会返回异常值当权限系统不支持时
     * 
     * @param world World Object
     * 世界对象
     * @param group Group name
     * 组名
     * @param permission Permission node
     * 权限节点
     * @return Success or Failure
     * 成功或失败 (true 或者 false)
     */
    public boolean groupAdd(World world, String group, String permission) {
        if (world == null) {
            return groupAdd((String) null, group, permission);
        }
        return groupAdd(world.getName(), group, permission);
    }

    /**
     * Remove permission from a group.
     * Supports NULL value for World if the permission system registered supports global permissions.
     * But May return odd values if the servers registered permission system does not have a global permission store.
     * 移除一个组的权限
     * 支持将世界设为null值如果权限系统支持的话
     * 但是可能会返回异常值当权限系统不支持时
     * 
     * @param world World name
     * 世界名
     * @param group Group name
     * 组名
     * @param permission Permission node
     * 权限节点
     * @return Success or Failure
     * 成功或失败 (true 或者 false)
     */
    abstract public boolean groupRemove(String world, String group, String permission);

    /**
     * Remove permission from a group.
     * Supports NULL value for World if the permission system registered supports global permissions.
     * But May return odd values if the servers registered permission system does not have a global permission store.
     * 移除一个组的权限
     * 支持将世界设为null值如果权限系统支持的话
     * 但是可能会返回异常值当权限系统不支持时
     * 
     * @param world World Object
     * 世界对象
     * @param group Group name
     * 组名
     * @param permission Permission node
     * 权限节点
     * @return Success or Failure
     * 成功或失败 (true 或者 false)
     */
    public boolean groupRemove(World world, String group, String permission) {
        if (world == null) {
            return groupRemove((String) null, group, permission);
        }
        return groupRemove(world.getName(), group, permission);
    }

    /**
     * @deprecated As of VaultAPI 1.4 use {@link #playerInGroup(String, OfflinePlayer, String)} instead.
     * 从VaultAPI 1.4开始使用{@link #playerInGroup(String, OfflinePlayer, String)}替代
     */
    @Deprecated
    abstract public boolean playerInGroup(String world, String player, String group);

    /**
     * @deprecated As of VaultAPI 1.4 use {@link #playerInGroup(String, OfflinePlayer, String)} instead.
     * 从VaultAPI 1.4开始使用{@link #playerInGroup(String, OfflinePlayer, String)}替代
     */
    @Deprecated
    public boolean playerInGroup(World world, String player, String group) {
        if (world == null) {
            return playerInGroup((String) null, player, group);
        }
        return playerInGroup(world.getName(), player, group);
    }
    
    /**
     * Check if player is member of a group.
     * Supports NULL value for World if the permission system registered supports global permissions.
     * But May return odd values if the servers registered permission system does not have a global permission store.
     * 检查玩家是否在一个组内
     * 这个方法指挥检查玩家当前世界定义的组.
     * 可能会返回异常的习惯?这取决于权限系统如何在服务端注册的
     * 
     * @param world World Object
     * 世界对象
     * @param player to check
     * 要检查的玩家
     * @param group Group name
     * 组名
     * @return Success or Failure
     * 成功或失败 (true 或者 false)
     */
    public boolean playerInGroup(String world, OfflinePlayer player, String group) {
        if (world == null) {
            return playerInGroup((String) null, player.getName(), group);
        }
        return playerInGroup(world, player.getName(), group);
    }

    /**
     * Check if player is member of a group.
     * This method will ONLY check groups for which the player is in that are defined for the current world.
     * This may result in odd return behaviour depending on what permission system has been registered.
     * 检查玩家是否在一个组内
     * 这个方法指挥检查玩家当前世界定义的组.
     * 可能会返回异常的习惯?这取决于权限系统如何在服务端注册的
     * 
     * @param player Player Object
     * 玩家对象
     * @param group Group name
     * 组名
     * @return Success or Failure
     * 成功或失败 (true 或者 false)
     */
    public boolean playerInGroup(Player player, String group) {
        return playerInGroup(player.getWorld().getName(), player, group);
    }

    /**
     * @deprecated As of VaultAPI 1.4 use {@link #playerAddGroup(String, OfflinePlayer, String)} instead.
     * 从VaultAPI 1.4开始使用{@link #playerAddGroup(String, OfflinePlayer, String)}替代
     */
    @Deprecated
    abstract public boolean playerAddGroup(String world, String player, String group);

    /**
     * @deprecated As of VaultAPI 1.4 use {@link #playerAddGroup(String, OfflinePlayer, String)} instead.
     * 从VaultAPI 1.4开始使用{@link #playerAddGroup(String, OfflinePlayer, String)}替代
     */
    @Deprecated
    public boolean playerAddGroup(World world, String player, String group) {
        if (world == null) {
            return playerAddGroup((String) null, player, group);
        }
        return playerAddGroup(world.getName(), player, group);
    }

    /**
     * Add player to a group.
     * Supports NULL value for World if the permission system registered supports global permissions.
     * But May return odd values if the servers registered permission system does not have a global permission store.
     * 添加玩家到组中
     * 这将会添加玩家到当前世界的组. 如果权限系统不支持多世界分组或者这个组已经被添加到了全局组则可能返回异常.
     * 
     * @param world String world name
     * 世界名
     * @param player to add
     * 玩家
     * @param group Group name
     * 组名
     * @return Success or Failure
     * 成功或失败 (true 或者 false)
     */
    public boolean playerAddGroup(String world, OfflinePlayer player, String group) {
        if (world == null) {
            return playerAddGroup((String) null, player.getName(), group);
        }
        return playerAddGroup(world, player.getName(), group);
    }
    
    /**
     * Add player to a group.
     * This will add a player to the group on the current World.  This may return odd results if the permission system
     * being used on the server does not support world-specific groups, or if the group being added to is a global group.
     * 添加玩家到组中
     * 这将会添加玩家到当前世界的组. 如果权限系统不支持多世界分组或者这个组已经被添加到了全局组则可能返回异常.
     * 
     * @param player Player Object
     * 玩家对象
     * @param group Group name
     * 组名
     * @return Success or Failure
     * 成功或失败 (true 或者 false)
     */
    public boolean playerAddGroup(Player player, String group) {
        return playerAddGroup(player.getWorld().getName(), player, group);
    }

    /**
     * @deprecated As of VaultAPI 1.4 use {@link #playerRemoveGroup(String, OfflinePlayer, String)} instead.
     * 从VaultAPI 1.4开始使用{@link #playerRemoveGroup(String, OfflinePlayer, String)}替代
     */
    @Deprecated
    abstract public boolean playerRemoveGroup(String world, String player, String group);

    /**
     * @deprecated As of VaultAPI 1.4 use {@link #playerRemoveGroup(String, OfflinePlayer, String)} instead.
     * 从VaultAPI 1.4开始使用{@link #playerRemoveGroup(String, OfflinePlayer, String)}替代
     */
    @Deprecated
    public boolean playerRemoveGroup(World world, String player, String group) {
        if (world == null) {
            return playerRemoveGroup((String) null, player, group);
        }
        return playerRemoveGroup(world.getName(), player, group);
    }
    
    /**
     * Remove player from a group.
     * Supports NULL value for World if the permission system registered supports global permissions.
     * But May return odd values if the servers registered permission system does not have a global permission store.
     * 将玩家从组中移除
     * 这将会添加玩家到当前世界的组. 这可能返回异常结果如果权限系统不支持指定世界的组或者这个组被添加到全局组
     * 
     * @param world World Object
     * 世界对象
     * @param player to remove
     * 玩家
     * @param group Group name
     * 组名
     * @return Success or Failure
     * 成功或失败 (true 或者 false)
     */
    public boolean playerRemoveGroup(String world, OfflinePlayer player, String group) {
        if (world == null) {
            return playerRemoveGroup((String) null, player.getName(), group);
        }
        return playerRemoveGroup(world, player.getName(), group);
    }

    /**
     * Remove player from a group.
     * This will add a player to the group on the current World.  This may return odd results if the permission system
     * being used on the server does not support world-specific groups, or if the group being added to is a global group.
     * 将玩家从组中移除
     * 这将会添加玩家到当前世界的组. 这可能返回异常结果如果权限系统不支持指定世界的组或者这个组被添加到全局组
     * 
     * @param player Player Object
     * 玩家对象
     * @param group Group name
     * 组名
     * @return Success or Failure
     * 成功或失败 (true 或者 false)
     */
    public boolean playerRemoveGroup(Player player, String group) {
        return playerRemoveGroup(player.getWorld().getName(), player, group);
    }

    /**
     * @deprecated As of VaultAPI 1.4 use {@link #getPlayerGroups(String, OfflinePlayer)} instead.
     * 从VaultAPI 1.4开始使用{@link #getPlayerGroups(String, OfflinePlayer)}替代
     */
    @Deprecated
    abstract public String[] getPlayerGroups(String world, String player);

    /**
     * @deprecated As of VaultAPI 1.4 use {@link #getPlayerGroups(String, OfflinePlayer)} instead.
     * 从VaultAPI 1.4开始使用{@link #getPlayerGroups(String, OfflinePlayer)}替代
     */
    @Deprecated
    public String[] getPlayerGroups(World world, String player) {
        if (world == null) {
            return getPlayerGroups((String) null, player);
        }
        return getPlayerGroups(world.getName(), player);
    }
    
    /**
     * Gets the list of groups that this player has
     * Supports NULL value for World if the permission system registered supports global permissions.
     * But May return odd values if the servers registered permission system does not have a global permission store.
     * 获得玩家所在的组列表
     * 支持将世界设为null值如果权限系统支持
     * 但是可能会返回异常值如果服务器权限系统不支持全局组
     * 
     * @param world String world name
     * 世界名
     * @param player OfflinePlayer
     * 玩家
     * @return Array of groups
     * 组列表(数组)
     */
    public String[] getPlayerGroups(String world, OfflinePlayer player) {
    	return getPlayerGroups(world, player.getName());
    }

    /**
     * Returns a list of world-specific groups that this player is currently in. May return unexpected results if
     * you are looking for global groups, or if the registered permission system does not support world-specific groups.
     * See {@link #getPlayerGroups(String, OfflinePlayer)} for better control of World-specific or global groups.
     * 返回玩家所在的指定 世界里的组. 如果当你查看全局组或者权限系统不支持指定世界的组时会返回异常结果.
     * 查看{@link #getPlayerGroups(String, OfflinePlayer)}以更好地控制指定世界与全局组
     * 
     * @param player Player Object
     * 玩家对象
     * @return Array of groups
     * 组的列表(数组)
     */
    public String[] getPlayerGroups(Player player) {
        return getPlayerGroups(player.getWorld().getName(), player);
    }

    /**
     * @deprecated As of VaultAPI 1.4 use {@link #getPrimaryGroup(String, OfflinePlayer)} instead.
     * 从VaultAPI 1.4后使用{@link #getPrimaryGroup(String, OfflinePlayer)}替代
     */
    @Deprecated
    abstract public String getPrimaryGroup(String world, String player);

    /**
     * @deprecated As of VaultAPI 1.4 use {@link #getPrimaryGroup(String, OfflinePlayer)} instead.
     * 从VaultAPI 1.4后使用{@link #getPrimaryGroup(String, OfflinePlayer)}替代
     */
    @Deprecated
    public String getPrimaryGroup(World world, String player) {
        if (world == null) {
            return getPrimaryGroup((String) null, player);
        }
        return getPrimaryGroup(world.getName(), player);
    }
    
    /**
     * Gets players primary group
     * Supports NULL value for World if the permission system registered supports global permissions.
     * But May return odd values if the servers registered permission system does not have a global permission store.
     * 获得玩家主组
     * 支持将世界设为null如果权限系统支持全局权限
     * 但是也有可能返回怪异的值如果服务器权限系统不支持
     * 
     * @param world String world name
     * 世界名
     * @param player to get from
     * 玩家名
     * @return Players primary group
     * 玩家主组
     */
    public String getPrimaryGroup(String world, OfflinePlayer player) {
        return getPrimaryGroup(world, player.getName());
    }

    /**
     * Get players primary group.
     * Defaults to the players current world, so may return only world-specific groups.
     * In most cases {@link #getPrimaryGroup(String, OfflinePlayer)} is preferable.
     * 获取玩家的主组
     * 默认使用玩家当前世界获取, 所以只返回指定世界的组
     * 大多数情况下 {@link #getPrimaryGroup(String, OfflinePlayer)} 是最好的
     * 
     * 
     * @param player Player Object
     * 玩家对象
     * @return Players primary group
     * 玩家主组
     */
    public String getPrimaryGroup(Player player) {
        return getPrimaryGroup(player.getWorld().getName(), player);
    }
    
    /**
     * Returns a list of all known groups
     * 返回所有已知组的列表
     * 
     * @return an Array of String of all groups
     * 一个String数组包含所有组
     */
    abstract public String[] getGroups();
    
    /**
     * Returns true if the given implementation supports groups.
     * 返回true如果给定的实现支持组
     * 
     * @return true if the implementation supports groups
     * 返回true如果给定的实现支持组
     */
    abstract public boolean hasGroupSupport();
}