$(function(){
	var t = echarts.init(document.getElementById("echarts-bar-chart")),
    n = {
        title: {
            text: "搜索指数"
        },
        tooltip: {
            trigger: "axis"
        },
        
        grid: {
            x: 30,
            x2: 40,
            y2: 24
        },
        calculable: !0,
        xAxis: [{
            type: "category",
            data: ["秋装新款", "连衣裙", "上衣", "短袖", "牛仔裤", "休闲装", "七分袖", "T恤", "外套", "牛仔"]
        }],
        yAxis: [{
            type: "value"
        }],
        series: [{
            
            type: "bar",
            data: [499, 476, 401, 377, 350, 320, 310, 294, 288, 235],
            markPoint: {
                data: [{
                    type: "max",
                    name: "最大值"
                },
                {
                    type: "min",
                    name: "最小值"
                }]
            },
            markLine: {
                data: [{
                    type: "average",
                    name: "平均值"
                }]
            }
        }
        ]
    };
    t.setOption(n),
    window.onresize = t.resize;
	
	
	    var l = echarts.init(document.getElementById("echarts-pie-chart")),
    u =  {

    tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
        x : 'center',
        y : 'bottom',
        data:['rose1','rose2','rose3','rose4','rose5','rose6','rose7','rose8']
    },
   
    calculable : true,
    series : [
       
        {
            name:'面积模式',
            type:'pie',
            radius : [30, 80],
            center : ['50%', 100],
            roseType : 'area',
            x: '100%',               // for funnel
            max: 30,                // for funnel
            sort : 'ascending',     // for funnel
            data:[
                {value:20, name:'电脑办公'},
                {value:20, name:'男女服装'},
                {value:15, name:'皮衣皮鞋'},
                {value:25, name:'手机数码'},
                {value:20, name:'日用百货'}
            ]
        }
    ]
};
    l.setOption(u),
    $(window).resize(l.resize);
	
	
	
	
	
	function createRandomItemStyle() {
    return {
        normal: {
            color: 'rgb(' + [
                Math.round(Math.random() * 160),
                Math.round(Math.random() * 160),
                Math.round(Math.random() * 160)
            ].join(',') + ')'
        }
    };
}

    var lt = echarts.init(document.getElementById("echarts-pie-chart1")),
    u=  
 {
    tooltip: {
        show: true
    },
    series: [{
        name: 'Google Trends',
        type: 'wordCloud',
        size: ['80%', '80%'],
        textRotation : [0, 45, 90, 45],
        textPadding: 10,
        data: [
            {
                name: "男装",
                value: 10000,
                itemStyle: {
                    normal: {
                        color: 'black'
                    }
                }
            },
            {
                name: "女装",
                value: 6181,
                itemStyle: createRandomItemStyle()
            },
            {
                name: "手机",
                value: 4386,
                itemStyle: createRandomItemStyle()
            },
            {
                name: "牛仔裤",
                value: 4055,
                itemStyle: createRandomItemStyle()
            },
            {
                name: "冲热器",
                value: 2467,
                itemStyle: createRandomItemStyle()
            },
            {
                name: "手机套",
                value: 2244,
                itemStyle: createRandomItemStyle()
            },
            {
                name: "优衣库",
                value: 1898,
                itemStyle: createRandomItemStyle()
            },
            {
                name: "睡袍",
                value: 1484,
                itemStyle: createRandomItemStyle()
            },
            {
                name: "奶粉",
                value: 1112,
                itemStyle: createRandomItemStyle()
            },
            {
                name: "秋装",
                value: 965,
                itemStyle: createRandomItemStyle()
            },
            {
                name: "旗舰店",
                value: 847,
                itemStyle: createRandomItemStyle()
            },
            {
                name: "时尚",
                value: 582,
                itemStyle: createRandomItemStyle()
            },
            {
                name: "防晒霜",
                value: 555,
                itemStyle: createRandomItemStyle()
            },
            {
                name: "时尚",
                value: 550,
                itemStyle: createRandomItemStyle()
            },
            {
                name: "手机包",
                value: 462,
                itemStyle: createRandomItemStyle()
            },
            {
                name: "青龙洞",
                value: 366,
                itemStyle: createRandomItemStyle()
            },
            {
                name: "古镇",
                value: 360,
                itemStyle: createRandomItemStyle()
            },
            {
                name: "婴儿内衣",
                value: 282,
                itemStyle: createRandomItemStyle()
            },
            {
                name: "T-Shirt",
                value: 273,
                itemStyle: createRandomItemStyle()
            },
            {
                name: "花溪公园",
                value: 265,
                itemStyle: createRandomItemStyle()
            }
        ]
    }]
};
                    
    lt.setOption(u),
    $(window).resize(lt.resize);
	
	

	
	
	
});