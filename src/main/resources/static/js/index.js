window.onload=function() {
    var nos=document.getElementById("nos");
    var noshow=document.getElementById("noshow");
    var nowshow=document.getElementById("nowshow");
    var nows=document.getElementById("nows");
    var oBox = $('.show');
    var oBoxWidth = $('.show').width();
    var thediv= oBox.find('div');
    var thisWidth = thediv.width();
    // var moveLeft = thediv.offset().marginLeft;
    var right=$('#right');
    var left=$('#left');
    var cover=document.getElementsByClassName("cover");
    // }
    function showtext(){
        var text_d=document.getElementsByClassName("text-desc");
        for (var i=0;i<text_d.length;i++){
            text_d[i].style.display="block";
        }

    };
    function dishow(){
        var text_d=document.getElementsByClassName("text-desc");
        for (var i=0;i<text_d.length;i++){
            text_d[i].style.display="none";
        }

    };
    // cover.onmouseover=function(){
    //     for (var i=0;i<cover.length;i++){
    //         text_d[i].style.display="block";
    //     }
    //     cover[i].style.display="none";
    //     cover[i].style.zIndex="0";
    //     cover[i].style.width="105%";
    //     cover[i].style.height="105%";
    // };


        nows.onmouseover = function () {
            nows.style.fontSize = "20px";
            nowshow.style.display = "block";
            noshow.style.display = "none";
            nos.style.fontSize = "16px";
            nows.style.color = "red";
            nos.style.color = "black";

        };
        document.getElementById("nos").onmouseover = function () {
            nos.style.fontSize = "20px";
            noshow.style.display = "block";
            nowshow.style.display = "none";
            nows.style.fontSize = "16px";
            nos.style.color = "red";
            nows.style.color = "black";

        };
    $('#right').on('click',function(){
        oBox.animate({scrollLeft:100});
    });
    $('#left').on('click',function(){
        oBox.animate({scrollRight:100});
    });

};
function toSearch() {
    var mName=$("#searchText").val();
    // alert(mName);
    $.ajax({
        dataType:'json',
        type: "get",
        url: "/movie/userlist",//向后端请求数据的url
        traditiona: true,
        contentType:"application/json;charset=utf-8",
        data: {
            mName:mName
        },
        success: function (data) {
        }
    });

}







