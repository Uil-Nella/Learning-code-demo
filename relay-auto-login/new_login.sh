#!/usr/bin/env bash

relay_name="liuxinyu03"
relay_password="*****"
relay_host="relay.sankuai.com"

echo  '请输入6位的大象口令'
if read -t 30 -p 'dynamic_token:' dynamic_token
then
    echo "输入对应编号回车！"
    select server in "dx-hotel-goods-staging01" "dx-hotel-goods01" "dx-hotel-goods02" "hotel-goods01" "dx-hotel-inventory-staging01" \
    "dx-hotel-inventory01" "dx-hotel-inventory01" "dx-hotel-inventory02" "hotel-inventory01" "dx-hotel-price-staging01" "dx-hotel-price01" \
    "dx-hotel-price02"  "hotel-price01"   "db38" "db24"
    do

    break
    done
fi


expect -c "

set timeout -1
puts $server
spawn ssh $relay_name@$relay_host
set timeout 1
expect \"Password & verification code:\" {send \"$relay_password$dynamic_token\r\"}
send \"ssh $server\r\"
send \"sudo -usankuai -i\r\"
send \"cd /opt/logs/mobile \r\"
interact
"