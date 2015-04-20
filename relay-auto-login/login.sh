#!/bin/bash
#--------------------------------------------
# 这是一个自动打ipa的脚本，基于webfrogs的ipa-build书写：https://github.com/webfrogs/xcode_shell/blob/master/ipa-build

# 功能：自动为etao ios app打包，产出物为14个渠道的ipa包
# 特色：全自动打包，不需要输入任何参数
#--------------------------------------------

##### 用户配置区 开始 #####
#
# 项目根目录，推荐将此脚本放在项目的根目录，这里就不用改了
# 应用名，确保和Xcode里Product下的target_name.app名字一致
#
##### 用户配置区 结束  #####
relay_name=liuxinyu03
relay_password=LIUXy@2013
##### script start ######
echo  '请输入6位的大象口令'
if read -t 30 -p "dynamic_token:" dynamic_token
then 
    echo "hello $USER ,请输入对应的编号来登录"
    echo "1:dx-hotel-goods-staging01"
    echo "2:dx-hotel-goods01"
    echo "3:dx-hotel-goods02"
    
   if read -t 30 -p "number:" number
   then
	case "$number" in  
  		1   ) online_host=dx-hotel-goods-staging01;;  
  		2   ) online_host=dx-hotel-goods01;;  
  		3   ) online_host=dx-hotel-goods02;;  
  		4   ) echo "Punctuation, whitespace, or other";;  
	esac  
        ./login.expect $relay_name $relay_password$dynamic_token $online_host  	
   else
       echo -e "\nsorry,too slow" 
       exit 0
   fi
else
    echo -e "\nsorry,too slow"
    exit 0
fi
#relay_host=relay.sankuai.com

#relay_name=liuxinyu03
#relay_password=LIUXy@2013
echo $dynamic_token








