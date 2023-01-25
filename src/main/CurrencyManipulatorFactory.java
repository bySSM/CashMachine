package main;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * фабрика, которая будет создавать и хранить манипуляторы
 */

public class CurrencyManipulatorFactory {

    // мапа, где будут храниться манипуляторы
    private static Map<String, CurrencyManipulator> map = new HashMap<>();

    // Приватный конструктор
    private CurrencyManipulatorFactory() {
    }

    // будем создавать нужный манипулятор, если он еще не существует,
    // либо возвращать ранее созданный.
    // Регистр при поиске манипулятора валюты не должен учитываться.
    public static CurrencyManipulator getManipulatorByCurrencyCode(String currencyCode) {
        currencyCode = currencyCode.toUpperCase();
        if (!map.containsKey(currencyCode)) {
            CurrencyManipulator manipulator = new CurrencyManipulator(currencyCode);
            map.put(manipulator.getCurrencyCode(), manipulator);
        }

        return map.get(currencyCode);
    }

    // вернет Collection всех манипуляторов
    public static Collection<CurrencyManipulator> getAllCurrencyManipulators() {
        return map.values();
    }
}
