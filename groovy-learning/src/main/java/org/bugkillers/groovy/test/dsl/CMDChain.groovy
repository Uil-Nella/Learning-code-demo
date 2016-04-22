package org.bugkillers.groovy.test.dsl

/**
 * Created by liuxinyu on 16/4/21.
 */



// 等同于：turn(left).then(right)
turn left then right

// 等同于：take(2.pills).of(chloroquinine).after(6.hours)
take 2.pills of chloroquinine after 6.hours

// 等同于：paint(wall).with(red, green).and(yellow)
paint wall with red, green and yellow

// 命名参数
// 等同于：check(that: margarita).tastes(good)
check that: margarita tastes good

// 闭包作为参数
// 等同于：given({}).when({}).then({})
given { } when { } then { }

// 等同于：select(all).unique().from(names)
select all unique() from names

// 等同于：take(3).cookies
// 同样也等于：take(3).getCookies()
take 3 cookies