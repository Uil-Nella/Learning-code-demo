#!/usr/bin/env bash

username="liuxinyu03"
pd="*****"
host="relay.sankuai.com"

code=$1

echo "select server"
select server in "hotel01" "hotel02" "hotel03" "hotel04" "admin01" "admin02" "task01" "task02" "task03" "task04" "task05" "db38" "db24" "schedule01" "staging01" \
"hotel-staging01" "hotel-staging02" "hotel-zlsort01" "hotel-zlsort02" "switch01" "switch02" "dx-switch01" "dx-switch02"
do
case $server in
"hotel01")
server="mobile-hotel01";;
"hotel02")
server="mobile-hotel02";;
"hotel03")
server="mobile-hotel03";;
"hotel04")
server="mobile-hotel04";;
"admin01")
server="mobile-hoteladmin01";;
"admin02")
server="mobile-hoteladmin02";;
"task01")
server="mobile-hoteltask01";;
"task02")
server="mobile-hoteltask02";;
"task03")
server="mobile-hoteltask03";;
"task04")
server="mobile-hoteltask04";;
"task05")
server="hotel-hoteltask05";;
"db38")
server="db38";;
"db24")
server="db24";;
"schedule01")
server="mobile-task-schedule01";;
"staging01")
server="mobile-staging01";;
"hotel-staging01")
server="hotel-staging01";;
"hotel-staging02")
server="hotel-staging02";;
   "hotel-zlsort01")
       server="hotel-zlsort01";;
   "hotel-zlsort02")
       server="hotel-zlsort02";;
   "switch01")
       server="hotel-switch01";;
   "switch02")
       server="hotel-switch02";;
   "dx-switch01")
       server="dx-hotel-switch01";;
   "dx-switch02")
       server="dx-hotel-switch02";;
esac
break
done

expect -c "

set timeout -1

puts $server

spawn ssh $username@$host
expect -re \"Password\"
send \"$pd$code\n\"

expect \"\$*\"
send \"ssh $server\n\"

expect \"\$*\"
send \"sudo -u sankuai -i\n\"

interact

"