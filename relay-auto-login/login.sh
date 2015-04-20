#!/bin/bash
#--------------------------------------------
# 这是一个自动登陆线上relay的脚本 https://github.com/qq291462491/Learning-code-demo/tree/master/relay-auto-login
# 功能：直接输入口令即可登陆指定机器
#--------------------------------------------

##### 用户配置区 开始 #####
#
# 用户名和密码配置
#
##### 用户配置区 结束  #####
relay_name=*******
relay_password=******
##### script start ######
echo  '请输入6位的大象口令'
if read -t 30 -p "dynamic_token:" dynamic_token
then 
    echo "hello $USER ,请输入对应的编号来登录"
    echo "###########hotel-goods###############”
    echo "1:dx-hotel-goods-staging01"
    echo "2:dx-hotel-goods01"
    echo "3:dx-hotel-goods02"
    echo "4:hotel-goods01 lf-online"
    echo "###########hotel-inventory###############”
    echo "5:dx-hotel-inventory-staging01"    
    echo "6:dx-hotel-inventory01"
    echo "7:dx-hotel-inventory02"
    echo "8:hotel-inventory01(lf-online)"
    echo "###########hotel-price###############”
    echo "9:dx-hotel-price-staging01"
    echo "10:dx-hotel-price01"
    echo "11:dx-hotel-price02"
    echo "12:hotel-price01 lf-online "
    
   if read -t 30 -p "number:" number
   then
	case "$number" in  
  		1   ) online_host=dx-hotel-goods-staging01;;  
  		2   ) online_host=dx-hotel-goods01;;  
  		3   ) online_host=dx-hotel-goods02;;  
  		4   ) online_host=hotel-goods01;;
		5   ) online_host=dx-hotel-inventory-staging01;;
		6   ) online_host=dx-hotel-inventory01;;
		7   ) online_host=dx-hotel-inventory02;;
		8   ) online_host=hotel-inventory01;;
		9   ) online_host=dx-hotel-price-staging01;;
		10  ) online_host=dx-hotel-price01;;
		11  ) online_host=dx-hotel-price01;;
		12  ) online_host=hotel-price01;;  
	esac
#调用expect脚本  
        ./login.expect $relay_name $relay_password$dynamic_token $online_host  	
   else
       echo -e "\nsorry,too slow" 
       exit 0
   fi
else
    echo -e "\nsorry,too slow ""

    exit 0
fi


