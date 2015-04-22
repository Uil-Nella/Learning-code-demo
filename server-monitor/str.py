__author__ = 'liuxinyu'
# coding=utf-8








strtest = "fasjdf \nsdfsd \nsdfsdf \n"

strarr = strtest.split("\n")

# 入口
if __name__ == '__main__':
    print(strarr)
    print(strarr[0])
    for line in strarr:
        print(line)
