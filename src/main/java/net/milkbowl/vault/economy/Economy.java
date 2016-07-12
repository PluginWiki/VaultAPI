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

package net.milkbowl.vault.economy;

import java.util.List;

import org.bukkit.OfflinePlayer;

/**
 * The main economy API 主要的经济API
 *
 */
public interface Economy {

	/**
	 * Checks if economy method is enabled. 检查经济(economy)方法是否已启用。
	 * 
	 * @return Success or Failure 成功或失败
	 */
	public boolean isEnabled();

	/**
	 * Gets name of economy method 获取经济(economy)方法的名称
	 * 
	 * @return Name of Economy Method 经济(Economy)方法的名称
	 */
	public String getName();

	/**
	 * Returns true if the given implementation supports banks.
	 * 如果此接口支持银行(bank)功能则返回真。
	 * 
	 * @return true if the implementation supports banks 返回真如果此接口支持银行(bank)功能
	 */
	public boolean hasBankSupport();

	/**
	 * Some economy plugins round off after a certain number of digits.
	 * 部分经济插件会在某个小数位后进行四舍五入。
	 * 
	 * This function returns the number of digits the plugin keeps or -1 if no
	 * rounding occurs. 这个函数返回插件保留的小数位数，若返回-1则不进行四舍五入。
	 * 
	 * @return number of digits after the decimal point kept 小数点后保留的位数
	 */
	public int fractionalDigits();

	/**
	 * Format amount into a human readable String This provides translation into
	 * economy specific formatting to improve consistency between plugins.
	 * 将一个数值转换成为一个人能读懂的字符串的格式。这个方法提供具体的经济格式转化方法来提高插件之间通信的连续性。
	 * 
	 * @param amount
	 *            to format 要转换格式的数值
	 * @return Human readable string describing amount 人类可读的用来描述这个数值的字符串
	 */
	public String format(double amount);

	/**
	 * Returns the name of the currency in plural form. 返回货币名称的复数形式。
	 * 
	 * If the economy being used does not support currency names then an empty
	 * string will be returned. 如果使用的经济系统不支持货币名称的话，将会返回一个空字符串。
	 * 
	 * @return name of the currency (plural) (复数的)货币名称
	 */
	public String currencyNamePlural();

	/**
	 * Returns the name of the currency in singular form. 返回货币名称的单数形式。 If the
	 * economy being used does not support currency names then an empty string
	 * will be returned. 如果使用的经济系统不支持货币名称的话，将会返回一个空字符串。
	 * 
	 * @return name of the currency (singular) (单数的)货币名称
	 */
	public String currencyNameSingular();

	/**
	 * 
	 * @deprecated As of VaultAPI 1.4 use {@link #hasAccount(OfflinePlayer)}
	 *             instead. 因为VaultAPI 1.4使用 {@link #hasAccount(OfflinePlayer)}
	 *             方法作为代替。
	 */
	@Deprecated
	public boolean hasAccount(String playerName);

	/**
	 * Checks if this player has an account on the server yet This will always
	 * return true if the player has joined the server at least once as all
	 * major economy plugins auto-generate a player account when the player
	 * joins the server 检测玩家是否在服务器上已经有一个账户
	 * 因为所有主要的经济插件会在玩家加入服务器时自动生成一个玩家账户，这个方法在玩家至少加入服务器一次的情况下总是返回真
	 * 
	 * @param player
	 *            to check 检查的玩家
	 * @return if the player has an account 如果玩家已有账户则返回真
	 */
	public boolean hasAccount(OfflinePlayer player);

	/**
	 * @deprecated As of VaultAPI 1.4 use
	 *             {@link #hasAccount(OfflinePlayer, String)} instead.
	 *             因为VaultAPI 1.4使用 {@link #hasAccount(OfflinePlayer, String)}
	 *             方法作为代替。
	 */
	@Deprecated
	public boolean hasAccount(String playerName, String worldName);

	/**
	 * Checks if this player has an account on the server yet on the given world
	 * This will always return true if the player has joined the server at least
	 * once as all major economy plugins auto-generate a player account when the
	 * player joins the server 检测玩家在指定世界是否有一个账号
	 * 因为所有主要的经济插件会在玩家加入服务器时自动生成一个玩家账户，这个方法在玩家至少加入服务器一次的情况下总是返回真
	 * 
	 * @param player
	 *            to check in the world 在这个世界里需要检测的玩家
	 * @param worldName
	 *            world-specific account 世界的具体名称(对于某个世界特定的账户来说)
	 * @return if the player has an account 如果玩家有一个账户则返回真
	 */
	public boolean hasAccount(OfflinePlayer player, String worldName);

	/**
	 * @deprecated As of VaultAPI 1.4 use {@link #getBalance(OfflinePlayer)}
	 *             instead. 因为VaultAPI 1.4 使用 {@link #getBalance(OfflinePlayer)}
	 *             方法作为代替。
	 */
	@Deprecated
	public double getBalance(String playerName);

	/**
	 * Gets balance of a player 获得一个玩家的账户余额
	 * 
	 * @param player
	 *            of the player 获取账户余额的玩家
	 * @return Amount currently held in players account 玩家账户目前的金额数目
	 */
	public double getBalance(OfflinePlayer player);

	/**
	 * @deprecated As of VaultAPI 1.4 use
	 *             {@link #getBalance(OfflinePlayer, String)} instead.
	 *             因为VaultAPI 1.4使用 {@link #getBalance(OfflinePlayer, String)}
	 *             方法作为代替。
	 */
	@Deprecated
	public double getBalance(String playerName, String world);

	/**
	 * Gets balance of a player on the specified world. IMPLEMENTATION SPECIFIC
	 * - if an economy plugin does not support this the global balance will be
	 * returned. 获取玩家在某一个指定的世界的账户余额。 特别地 - 如果经济插件不支持多世界多账户这个功能的话，则会返回全体世界的余额数。
	 * 
	 * @param player
	 *            to check 检查的玩家
	 * @param world
	 *            name of the world 世界的名称
	 * @return Amount currently held in players account 玩家账户现有的金额数
	 */
	public double getBalance(OfflinePlayer player, String world);

	/**
	 * @deprecated As of VaultAPI 1.4 use {@link #has(OfflinePlayer, double)}
	 *             instead. 因为VaultAPI 1.4使用 {@link #has(OfflinePlayer, double)}
	 *             方法代替。
	 */
	@Deprecated
	public boolean has(String playerName, double amount);

	/**
	 * Checks if the player account has the amount - DO NOT USE NEGATIVE AMOUNTS
	 * 检测玩家账户里是否有这个数值的金钱 - 请勿使用复数的金钱数值
	 * 
	 * @param player
	 *            to check 检测的玩家
	 * @param amount
	 *            to check for 检测的金额
	 * @return True if <b>player</b> has <b>amount</b>, False else wise 为真则
	 *         <b>玩家</b>有某个<b>数值</b>的金钱，为假则相反
	 */
	public boolean has(OfflinePlayer player, double amount);

	/**
	 * @deprecated As of VaultAPI 1.4 use @{link
	 *             {@link #has(OfflinePlayer, String, double)} instead.
	 *             因为VaultAPI 1.4使用 {@link #has(OfflinePlayer, String, double)}
	 *             作为代替。
	 */
	@Deprecated
	public boolean has(String playerName, String worldName, double amount);

	/**
	 * Checks if the player account has the amount in a given world - DO NOT USE
	 * NEGATIVE AMOUNTS IMPLEMENTATION SPECIFIC - if an economy plugin does not
	 * support this the global balance will be returned. 检测玩家在指定世界的账户里是否有这个数值的金钱
	 * - 请勿使用复数的金钱数值 - 如果经济插件不支持多世界多账户这个功能的话，则会检测全体世界的账户。
	 * 
	 * @param player
	 *            to check 检查的玩家
	 * @param worldName
	 *            to check with 检测的账户所在的世界名称
	 * @param amount
	 *            to check for 检测的金额
	 * @return True if <b>player</b> has <b>amount</b>, False else wise 为真则
	 *         <b>玩家</b>有某个<b>数值</b>的金钱，为假则相反
	 */
	public boolean has(OfflinePlayer player, String worldName, double amount);

	/**
	 * @deprecated As of VaultAPI 1.4 use
	 *             {@link #withdrawPlayer(OfflinePlayer, double)} instead.
	 */
	@Deprecated
	public EconomyResponse withdrawPlayer(String playerName, double amount);

	/**
	 * Withdraw an amount from a player - DO NOT USE NEGATIVE AMOUNTS
	 * 
	 * @param player
	 *            to withdraw from
	 * @param amount
	 *            Amount to withdraw
	 * @return Detailed response of transaction
	 */
	public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount);

	/**
	 * @deprecated As of VaultAPI 1.4 use
	 *             {@link #withdrawPlayer(OfflinePlayer, String, double)}
	 *             instead.
	 */
	@Deprecated
	public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount);

	/**
	 * Withdraw an amount from a player on a given world - DO NOT USE NEGATIVE
	 * AMOUNTS IMPLEMENTATION SPECIFIC - if an economy plugin does not support
	 * this the global balance will be returned.
	 * 
	 * @param player
	 *            to withdraw from
	 * @param worldName
	 *            - name of the world
	 * @param amount
	 *            Amount to withdraw
	 * @return Detailed response of transaction
	 */
	public EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount);

	/**
	 * @deprecated As of VaultAPI 1.4 use
	 *             {@link #depositPlayer(OfflinePlayer, double)} instead.
	 */
	@Deprecated
	public EconomyResponse depositPlayer(String playerName, double amount);

	/**
	 * Deposit an amount to a player - DO NOT USE NEGATIVE AMOUNTS
	 * 
	 * @param player
	 *            to deposit to
	 * @param amount
	 *            Amount to deposit
	 * @return Detailed response of transaction
	 */
	public EconomyResponse depositPlayer(OfflinePlayer player, double amount);

	/**
	 * @deprecated As of VaultAPI 1.4 use
	 *             {@link #depositPlayer(OfflinePlayer, String, double)}
	 *             instead.
	 */
	@Deprecated
	public EconomyResponse depositPlayer(String playerName, String worldName, double amount);

	/**
	 * Deposit an amount to a player - DO NOT USE NEGATIVE AMOUNTS
	 * IMPLEMENTATION SPECIFIC - if an economy plugin does not support this the
	 * global balance will be returned.
	 * 
	 * @param player
	 *            to deposit to
	 * @param worldName
	 *            name of the world
	 * @param amount
	 *            Amount to deposit
	 * @return Detailed response of transaction
	 */
	public EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount);

	/**
	 * @deprecated As of VaultAPI 1.4 use {
	 *             {@link #createBank(String, OfflinePlayer)} instead.
	 */
	@Deprecated
	public EconomyResponse createBank(String name, String player);

	/**
	 * Creates a bank account with the specified name and the player as the
	 * owner
	 * 
	 * @param name
	 *            of account
	 * @param player
	 *            the account should be linked to
	 * @return EconomyResponse Object
	 */
	public EconomyResponse createBank(String name, OfflinePlayer player);

	/**
	 * Deletes a bank account with the specified name.
	 * 
	 * @param name
	 *            of the back to delete
	 * @return if the operation completed successfully
	 */
	public EconomyResponse deleteBank(String name);

	/**
	 * Returns the amount the bank has
	 * 
	 * @param name
	 *            of the account
	 * @return EconomyResponse Object
	 */
	public EconomyResponse bankBalance(String name);

	/**
	 * Returns true or false whether the bank has the amount specified - DO NOT
	 * USE NEGATIVE AMOUNTS
	 * 
	 * @param name
	 *            of the account
	 * @param amount
	 *            to check for
	 * @return EconomyResponse Object
	 */
	public EconomyResponse bankHas(String name, double amount);

	/**
	 * Withdraw an amount from a bank account - DO NOT USE NEGATIVE AMOUNTS
	 * 
	 * @param name
	 *            of the account
	 * @param amount
	 *            to withdraw
	 * @return EconomyResponse Object
	 */
	public EconomyResponse bankWithdraw(String name, double amount);

	/**
	 * Deposit an amount into a bank account - DO NOT USE NEGATIVE AMOUNTS
	 * 
	 * @param name
	 *            of the account
	 * @param amount
	 *            to deposit
	 * @return EconomyResponse Object
	 */
	public EconomyResponse bankDeposit(String name, double amount);

	/**
	 * @deprecated As of VaultAPI 1.4 use {
	 *             {@link #isBankOwner(String, OfflinePlayer)} instead.
	 */
	@Deprecated
	public EconomyResponse isBankOwner(String name, String playerName);

	/**
	 * Check if a player is the owner of a bank account
	 * 
	 * @param name
	 *            of the account
	 * @param player
	 *            to check for ownership
	 * @return EconomyResponse Object
	 */
	public EconomyResponse isBankOwner(String name, OfflinePlayer player);

	/**
	 * @deprecated As of VaultAPI 1.4 use {
	 *             {@link #isBankMember(String, OfflinePlayer)} instead.
	 */
	@Deprecated
	public EconomyResponse isBankMember(String name, String playerName);

	/**
	 * Check if the player is a member of the bank account
	 * 
	 * @param name
	 *            of the account
	 * @param player
	 *            to check membership
	 * @return EconomyResponse Object
	 */
	public EconomyResponse isBankMember(String name, OfflinePlayer player);

	/**
	 * Gets the list of banks
	 * 
	 * @return the List of Banks
	 */
	public List<String> getBanks();

	/**
	 * @deprecated As of VaultAPI 1.4 use {
	 *             {@link #createPlayerAccount(OfflinePlayer)} instead.
	 */
	@Deprecated
	public boolean createPlayerAccount(String playerName);

	/**
	 * Attempts to create a player account for the given player
	 * 
	 * @param player
	 *            OfflinePlayer
	 * @return if the account creation was successful
	 */
	public boolean createPlayerAccount(OfflinePlayer player);

	/**
	 * @deprecated As of VaultAPI 1.4 use {
	 *             {@link #createPlayerAccount(OfflinePlayer, String)} instead.
	 */
	@Deprecated
	public boolean createPlayerAccount(String playerName, String worldName);

	/**
	 * Attempts to create a player account for the given player on the specified
	 * world IMPLEMENTATION SPECIFIC - if an economy plugin does not support
	 * this the global balance will be returned.
	 * 
	 * @param player
	 *            OfflinePlayer
	 * @param worldName
	 *            String name of the world
	 * @return if the account creation was successful
	 */
	public boolean createPlayerAccount(OfflinePlayer player, String worldName);
}
