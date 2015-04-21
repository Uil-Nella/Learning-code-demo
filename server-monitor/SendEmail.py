#!/usr/bin/env Python
# coding=utf-8

'''
发送html文本邮件
'''
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

# top信息的获取
bash_top = "top -bn 1 | head -5 > top.txt"
os.system(bash_top)
bash_pid = "jps | grep 'Bootstrap' | awk '{print $1}'"
# 服务器的JVM的pid 并去掉空格
jvm_pid = os.popen(bash_pid).read()
bash_jmap = "jmap -histo:live " + jvm_pid.strip() + " | head -13  > jvm_instance.txt"
os.popen(bash_jmap)

# 获取电脑名称
# 获取本机电脑名
server_name = socket.getfqdn(socket.gethostname())
inner_ip = get_ip_address("lo")
out_ip = get_ip_address("eth0")

mailto_list = ["liuxinyu03@meituan.com"]
mail_host = "smtp.163.com"  # 设置服务器
mail_user = "mt_server_monitor"  # 用户名
mail_pass = "jyzjrjevzeiynhrd"  # 口令
mail_postfix = "163.com"  # 发件箱的后缀
mail_title = "MTServerMonitor"
mail_context = "<h3>服务器top信息:</h3><hr>"
mail_time = time.strftime("%Y-%m-%d %X", time.localtime(time.time()))
mail_sub = "【监控邮件】服务器(" + server_name + ")--公网IP(" + out_ip + ")--内网IP(" + inner_ip + ")--时间(" + mail_time + ")"

# 讀取top文件
for line in fileinput.input("top.txt"):
    mail_context = mail_context + "<pre>" + line + "</pre>"

mail_context += "<h3>JVM存活实例排行10</h3><hr>"

# 讀取jvm_instance文件
for line in fileinput.input("jvm_instance.txt"):
    # 并將标签符号替换成html的符号
    mail_context = mail_context + "<pre>" + line.replace("<", "&lt;").replace(">", "&gt;") + "</pre>"

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


if __name__ == '__main__':
    if send_mail(mailto_list, mail_sub, mail_context):
        print "发送成功"
    else:
        print "发送失败"