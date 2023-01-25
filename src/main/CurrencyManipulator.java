package main;

import main.exception.NotEnoughMoneyException;

import java.util.*;

/**
 * будет хранить всю информацию про выбранную валюту
 */

public class CurrencyManipulator {

    // код валюты, например, USD. Состоит из трех букв
    private String currencyCode;

    // это Map<номинал, количество>
    private Map<Integer, Integer> denominations;

    public CurrencyManipulator(String currencyCode) {
        this.currencyCode = currencyCode;
        this.denominations = new TreeMap<>();
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    // добавит введенные номинал и количество банкнот
    public void addAmount(int denomination, int count) {
        if (denominations.containsKey(denomination)) {
            denominations.put(denomination, denominations.get(denomination) + count);
        } else {
            denominations.put(denomination, count);
        }
    }

    // посчитает общую сумму денег для выбранной валюты
    public int getTotalAmount() {
        int totalAmount = 0;

        for (Integer denomination : denominations.keySet()) {
            totalAmount += denomination * denominations.get(denomination);
        }
        return totalAmount;
    }

    // будет показывать, добавлены ли какие-то банкноты или нет
    public boolean hasMoney() {
        return !denominations.isEmpty();
    }

    // Проверяет, достаточно ли средств на счету. Вернет true, если денег достаточно для выдачи
    public boolean isAmountAvailable(int expectedAmount) {
        return expectedAmount <= getTotalAmount();
    }

    // Списать деньги со счета
    public Map<Integer, Integer> withdrawAmount(int expectedAmount) throws NotEnoughMoneyException {
        int sum = expectedAmount;
        HashMap<Integer, Integer> copyDenominations = new HashMap<>(denominations);

        ArrayList<Integer> keys = new ArrayList<>(this.denominations.keySet());

        Collections.sort(keys);
        Collections.reverse(keys);

        TreeMap<Integer, Integer> resultMap = new TreeMap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        });

        for (Integer denomination : keys) {
            final int key = denomination;
            int value = copyDenominations.get(key);
            while (true) {
                if (sum < key || value == 0) {
                    copyDenominations.put(key, value);
                    break;
                }
                sum -= key;
                value--;

                if (resultMap.containsKey(key))
                    resultMap.put(key, resultMap.get(key) + 1);
                else
                    resultMap.put(key, 1);
            }
        }

        if (sum > 0)
            throw new NotEnoughMoneyException();
        else {
            this.denominations.clear();
            this.denominations.putAll(copyDenominations);
        }
        return resultMap;
    }
}
