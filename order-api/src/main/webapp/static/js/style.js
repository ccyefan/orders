

function isHasImg(imgurl){
    var img = new Image();
	// 为Image对象添加图片加载成功的处理方法
	img.onload = function() {
	    document.body.style.backgroundImage = 'url('+imgurl+')';
	};
	// 为Image对象添加图片加载失败的处理方法
	img.onerror = function() {
	    document.body.style.backgroundImage = 'url(img/background.png)';
	}
	// 开始加载图片
	img.src = imgurl;
}

var d = new Date();
var date = d.getFullYear() + '-' + (d.getMonth()+1) + '-' +d.getDate()+ '.png';
var path = 'img/' + date;
image_url = 'url('+path+')';
document.body.style.backgroundImage = image_url;

isHasImg(path)





