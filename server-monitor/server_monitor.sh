#!/usr/bin/env bash


# top信息获取
top

# 将top信息的前5行写入文件
top -bn 1 | head -5 > top.txt

# 使用jps查看本机的jvm进程并截取出pid
jps | grep 'Bootstrap' | awk '{print $1}'

# 查看指定pid的对象存货情况
jmap -histo:live $pid | head -13

# 查看jvm 堆情况
jmap -heap $pid

# gc统计，采样时间间隔为250ms，采样数为4
jstat -gc $pid 250 4

# JVM线程快照
jstack -l  $pid

#