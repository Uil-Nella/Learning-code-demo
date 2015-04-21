#!/usr/bin/env Python
# coding=utf-8
"""
配合crontab来定时的读取服务器的部分信息
1、top信息
2、JVM实例信息
组装成html发送邮件
"""

import smtplib
import fileinput
import os
import socket
import fcntl
import struct
import time
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

# top信息的获取
bash_top = "top -bn 1 | head -5 > top.txt"
os.system(bash_top)

# 服务器的JVM的pid 并去掉空格
bash_pid = "jps | grep 'Bootstrap' | awk '{print $1}'"
jvm_pid = os.popen(bash_pid).read().strip()

# 获取JVM中存活得对象
bash_jmap = "jmap -histo:live " + jvm_pid + " | head -13  > jvm_instance.txt"
os.popen(bash_jmap)

# gc统计 输出的是GC信息，采样时间间隔为250ms，采样数为4
bash_gc = "jstat -gc "+jvm_pid+" 250 4 > jvm_gc.txt"
os.system(bash_gc)

# 延时1秒
time.sleep(1)

# 获取本机名称和IP
server_name = socket.getfqdn(socket.gethostname())
# 内网IP
inner_ip = get_ip_address("lo")
# 公网IP
out_ip = get_ip_address("eth0")

# 邮件接收者
mailto_list = ["liuxinyu03@meituan.com"]
# 设置服务器
mail_host = "smtp.163.com"
# 用户名
mail_user = "mt_server_monitor"
# 动态客户端口令
mail_pass = "jyzjrjevzeiynhrd"
# 发件箱的后缀
mail_postfix = "163.com"
# 标题名称
mail_title = "MTAServerMonitor"
# 标题时间
mail_time = time.strftime("%Y-%m-%d %X", time.localtime(time.time()))
# 邮件主题
mail_sub = "【监控邮件】服务器(" + server_name + ")--IP(" + out_ip + ")--时间(" + mail_time + ")"
# 邮件正文
mail_context = "<h3>服务器top信息:</h3><hr>"

# 读取top文件
for line in fileinput.input("top.txt"):
    mail_context += "<pre>" + line + "</pre>"

mail_context += "<h3>JVM存活实例排行10:</h3><hr>"

# 读取jvm_instance文件,并将标签退换掉
for line in fileinput.input("jvm_instance.txt"):
    # 并將标签符号替换成html的符号
    mail_context += "<pre>" + line.replace("<", "&lt;").replace(">", "&gt;") + "</pre>"


# 入口
if __name__ == '__main__':
    if send_mail(mailto_list, mail_sub, mail_context):
        print "发送成功"
    else:
        print "发送失败"