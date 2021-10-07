package test;

public @interface Exercise {
    int hoursPerDay();

    int startHour() default 6;
    // any element without a default values is considered REQUIRED
}

@interface Fluffy {
    int cuteness();

    public abstract int softness() default 11;
}

@interface Panda {
    //    Integer height(); // DNC wrapper classes

    String[] generalInfo();

    //    String[][] otherGeneralInfo(); // DNC multidimensional array

    Size size() default Size.LARGE;

    //    Bear friendlyBear(); // DNC

    Class<Bear> t();

    Exercise exercise() default @Exercise(hoursPerDay = 2);

    String friendly();

    boolean isBunny() default true;
}

enum Size {SMALL, MEDIUM, LARGE}

class Bear {
}


@interface ElectricitySource {
    public int voltage();

    int MIN_VOLTAGE = 2;
    public static final int MAX_VOLTAGE = 18;
}

@interface Swimmer {
    int armLength = 10;

    String stroke();

    String name();

    String favoriteStroke() default "Backstroke";
}

@Swimmer(stroke = "", name = "")
class Amphibian {
}

@Swimmer(favoriteStroke = "Breaststroke", name = "Sally", stroke = "")
class Tadpole {
}

@Swimmer(stroke = "FrogKick", name = "Kermit")
class Frog {
}

@Swimmer(stroke = "Butterfly", name = "Kip")
class Reptile {
}

@Swimmer(stroke = "", name = "", favoriteStroke = "")
class Snake {
}

@interface Injured {
    String veterinarian() default "unassigned";

    String value() default "foot";

    int age() default 1;
}

abstract class Elephant {
    @Injured("Legs")
    public void fallDown() {
    }

    @Injured(value = "Legs")
    public abstract int trip();

    @Injured
    String injuries[];
}
