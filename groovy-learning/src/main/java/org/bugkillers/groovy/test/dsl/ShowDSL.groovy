package org.bugkillers.groovy.test.dsl

/**
 * Created by liuxinyu on 16/4/21.
 */

show = {print it}

square_root = {Math.sqrt(it)}

def please(action){
    [the:{what ->
        [of:{n -> action(what(n))}]
    }
    ]
}

// 等同于：please(show).the(square_root).of(100)
please show the square_root of 100
// ==> 10.0