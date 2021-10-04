package utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ResultSetIterator implements Iterable{


    private List<String> stringList = new ArrayList<>();

    public ResultSetIterator(String... strings){
        stringList.addAll(List.of(strings));
    }

    @Override
    public Iterator iterator() {
        return this.stringList.iterator();
    }

}
