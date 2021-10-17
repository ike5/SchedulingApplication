package test;

import java.util.function.Supplier;

public class FactoryPattern {
    public static void main(String[] args) {
        System.out.println(CoinFactory.getCoin(CoinType.COPPER).getDescription());
    }
}

interface Coin {
    String getDescription();
}

class GoldCoin implements Coin {
    static final String DESCRIPTION = "This beautiful coin is made from gold.";

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}

class CopperCoin implements Coin {
    static final String DESCRIPTION = "The rustic color suggests this is made from copper.";

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}


enum CoinType {
    COPPER(CopperCoin::new),
    GOLD(GoldCoin::new);

    private final Supplier<Coin> constructor;

    CoinType(Supplier<Coin> constructor) {
        this.constructor = constructor;
    }

    public Supplier<Coin> getConstructor() {
        return constructor;
    }
}

class CoinFactory {
    public static Coin getCoin(CoinType type) {
        return type.getConstructor().get();
    }
}

