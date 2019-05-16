package org.featx.fundament.sort;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

class StringCharsTimesTest {

    @ParameterizedTest
    @CsvSource({
            "fdasjdfklajhfpdoiashpodinqlkrenqlvnpozihjfldnqflkejnqpofihdopasihfdansk, 'r:1,v:1,z:1,e:2,j:4,k:4,q:4,s:4,a:5,h:5,i:5,l:5,o:5,p:5,n:6,d:7,f:7'",
    })
    void test(String string, String result) {
        final Queue<String> resultQueue = new LinkedList<>();
        Arrays.stream(result.split(",")).forEach(resultQueue::offer);

        StringCharsTime stringCharsTime = new StringCharsTimes();

        stringCharsTime.timesOf(string)
                .forEach((key, value) -> Assertions.assertEquals(resultQueue.poll(), key + ":" + value));
    }
}
