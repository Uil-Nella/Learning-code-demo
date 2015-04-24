#!/bin/bash
#使用uptime命令监控linux系统负载变化

#提取本服务器的IP地址信息
IP=`ifconfig eth0 | grep "inet addr" | cut -f 2 -d ":" | cut -f 1 -d " "`

#抓取cpu的总核数
cpu_num=`grep -c 'model name' /proc/cpuinfo`

#抓取当前系统15分钟的平均负载值
load_15=`uptime | awk '{print $NF}'`

#计算当前系统单个核心15分钟的平均负载值，结果小于1.0时前面个位数补0。
average_load=`echo "scale=2;a=$load_15/$cpu_num;if(length(a)==scale(a)) print 0;print a" | bc`

#取上面平均负载值的个位整数
average_int=`echo $average_load | cut -f 1 -d "."`


#当单个核心15分钟的平均负载值大于等于1.0（即个位整数大于0） ，直接发邮件告警
if (($average_int > 0)); then
      python  /opt/meituan/monitor/monitor.py "服务器15分钟的系统单个核心平均负载为$average_load，超过警戒值1.0，请立即处理！！！"

fi