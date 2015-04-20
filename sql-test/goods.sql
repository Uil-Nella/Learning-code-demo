#查询已经上线的预预防产线 450
select count(*) from mta_goods_info where goods_source = 1 and goods_switch = 2 ;