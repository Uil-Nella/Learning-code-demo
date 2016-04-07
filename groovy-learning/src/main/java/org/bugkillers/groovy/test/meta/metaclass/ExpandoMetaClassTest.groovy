/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package org.bugkillers.groovy.test.meta.metaclass

/**
 * Created by liuxinyu on 16/3/14.
 */
class Book {
    String title
}

//方法操作
Book.metaClass.titleInUpperCase << { -> title.toUpperCase() }

Book.metaClass.toString = { -> return "hahaha" }

def b = new Book(title: "The Stand")

assert "THE STAND" == b.titleInUpperCase()

assert b.toString() == "hahaha"

//属性操作

Book.metaClass.name = "东野圭吾"

assert b.name == "东野圭吾"

//get方法


Book.metaClass.getAge << { -> 1 }

assert b.age == 1


//构造方法

Book.metaClass.constructor << {String title -> new Book(title: title)}

def book= new Book("Groovy in action")

assert book.title=="Groovy in action"

//静态方法操作

Book.metaClass.static.create << {String title -> new Book(title: title)}

def bb = Book.create("hah")
assert bb.title =="hah"


//借用方法
class Person{
    String name="Fred"
}

class MorgageLender{
    def borrowMoney(){
        "by house"
    }
}

def lender = new MorgageLender();

Person.metaClass.buyHouse = lender.&borrowMoney

def p = new Person()
//assert "buy house" == p.buyHouse()


//动态方法名
def methodName = "Bob"
Person.metaClass."changeNameTo${methodName}" = {-> delegate.name="Bob"}
def pp = new Person()
assert "Fred" == pp.name
pp.changeNameToBob()
assert "Bob" ==pp.name