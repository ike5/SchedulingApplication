package utils;

import java.util.List;

@FunctionalInterface
public interface QueryStatements {
    // it already knows what type of object to pass into the method. No need to define it twice.
    abstract List<String> whileLoop(String sql, ResultSetIterator resultSetIterator);
}
