package com.bonc.lottery.domain;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

//import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;
import com.wandoulabs.jodis.JedisResourcePool;
import com.wandoulabs.jodis.RoundRobinJedisPool;




/**
 * 指令集
 */

public class Cmd {


	public static String LOTTERY_LIST = "lotteryList"; //奖品列表，类型HASH  单元数进行计数
	public static String AWARD_LIST = "awardList"; //抽奖列表，类型LIST  数据可重复
	public static String JOIN_USER_LIST = "joinUserList"; //已参与抽奖用户列表，类型HASH  单元数进行计数
	public static String AWARD_USER_LIST = "awardUserList"; //已中奖用户列表，类型HASH  单元数进行计数
	//用户生成队列总数内的随机数
	public static String AWARD_RESULT_LIST = "awardResultList"; //抽奖结果列表，类型HASH  单元数进行计数
	// public static String ORDER_CAR_PASSENGER = "orderPassengers"; //允许司机抢单的乘客列表


	//	-- 函数：尝试获得订单，如果成功，则返回json字符串，如果不成功，则返回空
	//	-- 参数：1.订单队列名，2.已消费的队列名，3.去重的Map名,4.司机ID，5.允许抢单的司机ID
	//	-- 返回值：nil 或者 json字符串，包含司机ID：driverId，订单ID：id，订单信息：money
	/*
	 * 2、判断用户参与一次抽奖之后不允许再次抽奖。  抽奖次数=>1,增加用户访问次数，返回奖品为空。
       3、将用户加入以参与抽奖队列。 
       4、用户中奖一次的不允许抽奖。 中奖次数.>1，返回奖品为空。 
       5、用随机数去队列里匹配对应的奖品；
        5.1 对应奖品减一，返回奖品数大于等于1，则可中奖  ；将用户加入中奖队列。 ；返回用户所抽中奖品。
        5.2 奖品数<=0，用户没有中奖；返回奖品为空。
         //	-- 参数：1.用户手机号，2.奖品表队列名，3.抽奖队列名,4.已参与用户，5.中奖用户
	 * telNumber--KEYS[1],用户(手机号)
	 * Cmd.LOTTERY_LIST+period---KEYS[2],奖品队列 
	 * Cmd.AWARD_LIST+period---KEYS[3] ,抽奖队列
	 * Cmd.JOIN_USER_LIST+period---KEYS[4],参与抽奖用户
	 * Cmd.AWARD_USER_LIST+period---KEYS[5]中奖用户
	 * a.toString---KEYS[6]随机数
	 * Cmd.AWARD_RESULT_LIST+period---KEYS[7]
	 * time---KEYS[8]
	 */
	public static String tryGetRandom =
		"local a=redis.call('TIME');" 
		+"math.randomseed(a[1]);"
		+"local llen = redis.call('llen',KEYS[1])\n"
		+"local ran = math.random(llen)\n"
		+"return ran-1";

	public static String tryGetOrderScript =

		"if redis.call('hincrby',KEYS[4],KEYS[1],1)> 1 then\n"
		+"   local x = cjson.decode('{}')\n"
		+ "   x['telNumber'] = KEYS[1]\n"
		+ "   x['msg'] = '已经参与过抽奖'\n"
		+ "   x['status'] = 'fail1'\n"
		+ "   local re = cjson.encode(x)\n"
		+ "   return re\n"
		+"end\n"

		+"if redis.call('hincrby',KEYS[5],KEYS[1],1)> 1 then\n"
		+"   local x = cjson.decode('{}')\n"
		+ "   x['telNumber'] = KEYS[1]\n"
		+ "   x['msg'] = '曾经中奖'\n"
		+ "   x['status'] = 'fail2'\n"
		+ "   local re = cjson.encode(x)\n"
		+ "   return re\n"
		+"end\n"


		+"local ll = redis.call('lindex', KEYS[3],KEYS[6])\n"
		+"if redis.call('hincrby',KEYS[2],ll,-1) <0 then\n"
		+"   local x = cjson.decode('{}')\n"
		+ "   x['telNumber'] = KEYS[1]\n"
		+ "   x['msg'] = '没有中奖'\n"
		+ "   x['status'] = 'fail3'\n"
		+ "   local re = cjson.encode(x)\n"
		+ "   return re\n"
		+"end\n"
		+"redis.call('hincrby',KEYS[5],KEYS[1],1)\n"
		

		+ "if ll~=nil then\n"
		+ "   local x = cjson.decode('{\"lottery\":'..ll..'}')\n"
		+ "   x['telNumber'] = KEYS[1]\n"
		+ "   x['msg'] = '恭喜您中奖'\n"
		+ "   x['status'] = 'success'\n"
		+ "   x['time'] = KEYS[8]\n"
		+ "   local re = cjson.encode(x)\n"
		+ "   redis.call('lpush',KEYS[7],re)\n"
		+ "   return re\n"
		+ "end\n"
		

		+"return nil";



	/*
	 * 	+"local ll='{\"lottery\":12}'"
		+"local abc=ll..ll;" 
		+"local ab=cjson.decode(ll);"
		+"return ab.lottery"
	 */

	/*+ "if ll then\n"
	+"local x = cjson.decode('{\"lottery\":'..ll..'}')\n"
	+ "   x['telNumber'] = KEYS[1]\n"
	+ "   x['time'] = redis.call('TIME')"
	+ "   local db = cjson.encode(x)\n"
	+ "   return db\n"
	+"redis.call('hset',KEYS[7],db)\n"
	+"end\n"*/
	public static final String SEND_GEO="01";//geo信息
	public static final String SEND_MSG="02";//发送消息
	public static final String BIND_LOTTERY="03";//绑定奖品
	public static final String BIND_USER="04";//绑定乘客
	public static final String POST_APPOINTMENT="05";//提交预约单
	public static final String RECEIVE_APPOINTMENT="06";//接收预约单
	public static final String CANCEL_APPOINTMENT="07";//接收预约单
	public static final String WAIT_APPOINTMENT="08";//接收预约单   
	public static final String SEND_AWARD="09";//中奖
	public static final String SEARCH_ALREADY="10";//查询已参与抽奖用户

	public static final String CODE_NONE="-1";//错误指令 无法处理
	public static final String DATA_EMPTY="{}";//错误指令 无法处理


}
