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

/**
 * Indicates a typical Return for an Economy method.  
 * It includes a {@link ResponseType} indicating whether the plugin currently being used for Economy actually allows
 * the method, or if the operation was a success or failure.
 * 一个表示经济方法的典型回报.
 * 它包含了{@link ResponseType}来表示当前经济插件是否支持, 或者操作是否成功.
 *
 */
public class EconomyResponse {

    /**
     * Enum for types of Responses indicating the status of a method call.
     * 枚举.表示回报一个方法的状态类型
     */
    public static enum ResponseType {
        SUCCESS(1),
        FAILURE(2),
        NOT_IMPLEMENTED(3);

        private int id;

        ResponseType(int id) {
            this.id = id;
        }

        int getId() {
            return id;
        }
    }

    /**
     * Amount modified by calling method
     * 调用方法传递的金额
     */
    public final double amount;
    /**
     * New balance of account
     * 新的账户余额
     */
    public final double balance;
    /**
     * Success or failure of call. Using Enum of ResponseType to determine valid
     * outcomes
     * 调用是否成功. 使用枚举类型{@link ResponseType}来表示错误的结果
     */
    public final ResponseType type;
    /**
     * Error message if the variable 'type' is ResponseType.FAILURE
     * 如果变量的"类型"为{@link ResponseType.FAILURE}的错误信息
     */
    public final String errorMessage;

    /**
     * Constructor for EconomyResponse
     * {@link EconomyRespone}的构造函数
     * 
     * @param amount Amount modified during operation
     * 操作中修改的金额
     * @param balance New balance of account
     * 新的账户余额
     * @param type Success or failure type of the operation
     * 操作成功或者失败
     * @param errorMessage Error message if necessary (commonly null)
     * 错误信息(如果有必要)(一般为null)
     */
    public EconomyResponse(double amount, double balance, ResponseType type, String errorMessage) {
        this.amount = amount;
        this.balance = balance;
        this.type = type;
        this.errorMessage = errorMessage;
    }

    /**
     * Checks if an operation was successful
     * 检查操作是否成功
     * @return Value
     * 是否成功
     */
    public boolean transactionSuccess() {
        switch (type) {
        case SUCCESS:
            return true;
        default:
            return false;
        }
    }
}