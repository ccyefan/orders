$(function() {
    var s = echarts.init(document.getElementById("echarts-treemap-chart")),
    c = {
        tooltip : {
            trigger: 'item',
            formatter: "{b}: {c}"
        },
        calculable : false,
        series : [
            {
                name:'今日',
                type:'treemap',
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            formatter: "{b}"
                        },
                        borderWidth: 1,
                        borderColor: '#ccc'
                    },
                    emphasis: {
                        label: {
                            show: true
                        },
                        color: '#cc99cc',
                        borderWidth: 3,
                        borderColor: '#996699'
                    }
                },
                data:[
                    {
                        name: '娱乐',
                        itemStyle: {
                            normal: {
                                color: '#99cccc',
                            }
                        },
                        value: 6,
                        children: [
                            {
                                name: 'TVB ',
                                value: 2
                            },
                            {
                                name: '涨工资',
                                value: 3
                            }
                        ]
                    },
                    {
                        name: '社会',
                        itemStyle: {
                            normal: {
                                color: '#99ccff',
                            }
                        },
                        value: 4,
                        children: [
                            {
                                name: '结婚证',
                                value: 6
                            },
                            {
                                name: '有效期',
                                value: 6
                            },
                            {
                                name: '七年',
                                value: 4
                            }
                        ]
                    },
                    {
                        name: '军事',
                        itemStyle: {
                            normal: {
                                color: '#9999cc',
                            }
                        },
                        value: 4,
                        children: [
                            {
                                name: '击落俄罗斯战机',
                                value: 6
                            }
                        ]
                    },
                    {
                        name: '地域风向',
                        itemStyle: {
                            normal: {
                                color: '#ccff99',
                            }
                        },
                        value: 1,
                        children: [
                            {
                                name: '各地网民都在搜什么？',
                                itemStyle: {
                                    normal: {
                                        color: '#ccccff',
                                    }
                                },
                                value: 6
                            }
                        ]
                    }
                ]
            }
        ]
    };
    s.setOption(c),
    $(window).resize(s.resize);

});