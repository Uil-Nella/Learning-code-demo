/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package org.bugkillers.groovy.test

/**
 * Created by liuxinyu on 15/12/21.
 */


//List 测试
def range=0..4

println(range.class)

assert range instanceof List

//Collection
def coll = ["Groovy","Python","Java"]

println coll instanceof Collection

println coll instanceof ArrayList

//集合操作
println(coll)
coll.add("PHP")
coll << "Smalltalk"
coll[6] = "Perl"

def numbers = 1..4

println numbers + 5

println numbers - [1,2]


//Groovy中的特殊方法
def numbers2 = 1..4

println numbers2.join(",")

println ([1,2,3,4,4,5,2,2].count(2))

println(["java","Groovy"]*.toUpperCase())

//Map
def hash = [name:"Tom",age:22]

println(hash)

//get and set

//方法1
hash.put("id", 23)
assert hash.get("name") == "Tom"

//方法2
hash.dob = "01/29/76"
//. 符号还可以用来获取项。例如，使用以下方法可以获取 dob 的值：
assert hash.dob == "01/29/76"

//方法3
assert hash["name"] == "Tom"
hash["gender"] = "male"
assert hash.gender == "male"
assert hash["gender"] == "male"

//闭包
def list = ["Java","Groovy","Python"]

//it 是关键字
list.each {
    println(it)
}

//使用value代替it
list.each{ value ->
    println value
}

def userMap = [name:"Tom", "age":45]
userMap.each{ key, value ->
    println "${key} : ${value}"
}

"meituan".each{
    println it.toUpperCase();
}


//定义闭包

def excite = {
    word-> return "this is ${word} "
};

//可以通过两种方法调用闭包：直接调用或者通过 call() 方法调用。
println excite("Java");
println excite.call("Groovy")