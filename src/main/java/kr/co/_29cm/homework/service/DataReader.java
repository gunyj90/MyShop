package kr.co._29cm.homework.service;

import java.util.List;

public interface DataReader {

    String DIRECTORY = "/readable";

    <T> List<T> read(Parseable<T> parseable);

    interface Parseable<T> {
        T parse(String[] line);
    }
}
