package org.bugkillers.groovy.test.dsl

/**
 * Created by liuxinyu on 16/4/21.
 */

//@Grab('com.google.guava:guava:r09')
import com.google.common.base.*

def result = Splitter.on(',').trimResults(CharMatcher.is('_' as char)).split("_a ,_b_ ,c__").iterator().toList()

print(result)


def split(string) {
    [on: { sep ->
        [trimming: { trimChar ->
            Splitter.on(sep).trimResults(CharMatcher.is(trimChar as char)).split(string).iterator().toList()
        }]
    }]
}

def result2 = split "_a ,_b_ ,c__" on ',' trimming '_\''

print(result2)