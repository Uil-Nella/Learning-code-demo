##1、 shell监控负载
监控原理：使用uptime来获取负载的信息，然后通过字符串截取的方式来获取load值来获取单个核心的负载，在将负载与阈值比较确定是否报警。

loard_monitor.sh脚本：
``` shell
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
```
## 2、python监控，并邮件报警，同时记录JVM等相关参数
原理：使用crontab定时任务来执行python脚本，在脚本中来调用shell命令或jvm命令获取信息，最终使用python发送监控邮件。

monitor.py
```python
#!/usr/bin/env Python
# coding=utf-8
"""
配合crontab来定时的读取服务器的部分信息
1、top信息
2、JVM实例信息
3、GC信息
组装成html发送邮件
"""

import smtplib
import os
import socket
import fcntl
import struct
import time
import sys
from email.mime.text import MIMEText

# 获取本机ip和名称
def get_ip_address(ifname):
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    return socket.inet_ntoa(fcntl.ioctl(
        s.fileno(),
        0x8915,  # SIOCGIFADDR
        struct.pack('256s', ifname[:15])
    )[20:24])


# 邮件发动方法
def send_mail(to_list, sub, content):  # to_list：收件人；sub：主题；content：邮件内容
    me = mail_title + "<" + mail_user + "@" + mail_postfix + ">"  # 这里的hello可以任意设置，收到信后，将按照设置显示
    msg = MIMEText(content, _subtype='html', _charset='utf-8')  # 创建一个实例，这里设置为html格式邮件
    msg['Subject'] = sub  # 设置主题
    msg['From'] = me
    msg['To'] = ";".join(to_list)
    try:
        s = smtplib.SMTP()
        s.connect(mail_host)  # 连接smtp服务器
        s.login(mail_user, mail_pass)  # 登陆服务器
        s.sendmail(me, to_list, msg.as_string())  # 发送邮件
        s.close()
        return True
    except Exception, e:
        print str(e)
        return False


# 根据shell命令返回一个list 文本
def get_text_sh(bash_sh):
    result = os.popen(bash_sh).read()
    return result.split("\n")


# top信息的获取
bash_top = "top -bn 1 | head -5 "
top_arr_txt = get_text_sh(bash_top)
# 服务器的JVM的pid 并去掉空格
bash_pid = "/usr/local/java/bin/jps | grep 'Bootstrap' | awk '{print $1}'"
jvm_pid = os.popen(bash_pid).read().strip()

# 获取JVM中存活得对象
bash_jmap = "/usr/local/java/bin/jmap -histo:live " + jvm_pid + " | head -13 "
jvm_instance_arr = get_text_sh(bash_jmap)

# JVM堆信息
bash_jmap_heap = "/usr/local/java/bin/jmap -heap " + jvm_pid
jvm_heap_arr = get_text_sh(bash_jmap_heap)

# gc统计，采样时间间隔为250ms，采样数为4
bash_gc = "/usr/local/java/bin/jstat -gc " + jvm_pid + " 250 4 "
jvm_gc_arr = get_text_sh(bash_gc)

# JVM线程快照
bash_jvm_thread = "jstack -l " + jvm_pid
jvm_thread_arr = get_text_sh(bash_jvm_thread)

# 获取本机名称和IP
server_name = socket.getfqdn(socket.gethostname())
# 内网IP
inner_ip = get_ip_address("lo")
# 公网IP
out_ip = get_ip_address("eth0")

# 邮件接收者
mailto_list = ["yourname@meituan.com"]
# 设置服务器
mail_host = "smtp.163.com"
# 用户名
mail_user = "mt_server_monitor"
# 动态客户端口令
mail_pass = "#######"
# 发件箱的后缀
mail_postfix = "163.com"
# 标题名称
mail_title = "MTAServerMonitor"
# 标题时间
mail_time = time.strftime("%Y-%m-%d %X", time.localtime(time.time()))
# 邮件主题
mail_sub = "【监控邮件】服务器(" + server_name + ")--IP(" + out_ip + ")--时间(" + mail_time + ")"
# 目录导航
mail_catalog = "<ul>" \
               "<li><a href = '#top'>服务器top信息</a></li>" \
               "<li><a href = '#instance'>JVM存活实例</a></li>" \
               "<li><a href = '#gc'>GC情况</a></li>" \
               "<li><a href = '#heap'>JVM堆信息</a></li>" \
               "<li><a href = '#thread'>JVM线程快照及锁</a></li>" \
               "</ul>"
# 报警内容
mail_context = "<h2><font color='red'>" + sys.argv[1] + "</font></h2>"
# 邮件正文
mail_context += mail_catalog + "<h3><a name = 'top'>服务器top信息:</a></h3><hr>"

# 处理top信息
for line in top_arr_txt:
    mail_context += "<pre>" + line + "</pre>"

mail_context += "<h3><a name = 'instance'>JVM存活实例10:</a></h3><hr>"

# 处理jvm,并将标签退换掉
for line in jvm_instance_arr:
    # 并將标签符号替换成html的符号
    mail_context += "<pre>" + line.replace("<", "&lt;").replace(">", "&gt;") + "</pre>"

mail_context += "<h3><a name = 'gc'>GC情况 采样时间间隔为250ms，采样数为4:</a></h3><hr>"

# 处理gc信息
for line in jvm_gc_arr:
    # 并將标签符号替换成html的符号
    mail_context += "<pre>" + line + "</pre>"

mail_context += "<h3><a name = 'heap'>JVM堆信息:</a></h3><hr>"

# 处理heap信息
for line in jvm_heap_arr:
    # 并將标签符号替换成html的符号
    mail_context += "<pre>" + line + "</pre>"

mail_context += "<h3><a name = 'thread'>JVM线程快照及锁情况:</a></h3><hr>"

# 处理JVM线程快照及锁情况
for line in jvm_thread_arr:
    # 并將标签符号替换成html的符号
    mail_context += "<pre>" + line + "</pre>"

mail_context += "<pre>线程快照过大，暂时未提供显示，如有需要请联系<a href = 'XXX'></pre>"
# 入口
if __name__ == '__main__':
    if send_mail(mailto_list, mail_sub, mail_context):
        print "发送成功"
    else:
        print "发送失败"
```
##3、crontab定时任务
```shell
#开始设置定时任务
crontab -e
#15分钟执行一次
0,15,30,45  * * * * python  /opt/meituan/monitor/monitor.py 2>&1
#查看任务
crontab -l
```
