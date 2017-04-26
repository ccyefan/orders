 var l = echarts.init(document.getElementById("echarts-pie-chart2")),
    u =  {
    tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
        orient : 'vertical',
        x : 'left',
        data:['图片','美食','购物','交通','医疗保险']
    },
    calculable : true,
    series : [
        {
            name:'访问来源',
            type:'pie',
            radius : ['50%', '70%'],
            itemStyle : {
                normal : {
                    label : {
                        show : false
                    },
                    labelLine : {
                        show : false
                    }
                },
                emphasis : {
                    label : {
                        show : true,
                        position : 'center',
                        textStyle : {
                            fontSize : '30',
                            fontWeight : 'bold'
                        }
                    }
                }
            },
            data:[
                {value:335, name:'图片'},
                {value:310, name:'美食'},
                {value:234, name:'购物'},
                {value:135, name:'交通'},
                {value:1548, name:'医疗保险'}
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
        textRotation : [0, 45, 50, 70],
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
	
	