var seatArray = new Array();
var tdsche=document.getElementById("tdsche");
var tmsche=document.getElementById("tmsche");
var aftche=document.getElementById("aftche");
var today=document.getElementById("today")
var tommorrow=document.getElementById("tommorrow")
var aftertomm=document.getElementById("aftertomm")
var seatlist = new Array();
var row=0,col=0;
window.onload=function (){
    today.onmouseover=function () {
        tdsche.style.display="block";
        tmsche.style.display="none";
        aftche.style.display="none";
        today.style.color="red";
        today.style.fontSize="27px"
        tommorrow.style.color="black";
        tommorrow.style.fontSize="25px";
        aftertomm.style.color="black";
        aftertomm.style.fontSize="25px";
    };
    tommorrow.onmouseover=function () {
        tdsche.style.display="none";
        tmsche.style.display="block";
        aftche.style.display="none";
        tommorrow.style.color="red";
        tommorrow.style.fontSize="27px"
        today.style.color="black";
        today.style.fontSize="25px";
        aftertomm.style.color="black";
        aftertomm.style.fontSize="25px";
    };
    aftertomm.onmouseover=function () {
        tdsche.style.display="none";
        tmsche.style.display="none";
        aftche.style.display="block";
        aftertomm.style.color="red";
        aftertomm.style.fontSize="27px";
        today.style.color="black";
        today.style.fontSize="25px";
        tommorrow.style.color="black";
        tommorrow.style.fontSize="25px"
    };

}
function closemodal() {
    document.getElementById("seatModal").style.display = "none";
    document.getElementById("seatModal").style.display = "none";
    seatArray=new Array();
    seatlist=new Array();
    $('#seats ul').remove();

}
function chooseSeats()  {
    var hId=$("#hId").text();
    var json={
        hId:hId
    }
    $.ajax({
        type : 'GET',
        dataType : 'json',
        url: "/hall/getSeat",    //向后端请求数据的url
        data:{hId:hId},
        success: function (data) {
            var max=data.data.length;
            var data=eval(JSON.stringify(data.data));
            for (var index = 0; index  <  max; index++) {//座位表数组
                var seat=new Array(data[index].sdId,data[index].xaxis,data[index].yaxis,data[index].useState);
                seatlist[index]=seat;
                if(data[index].xaxis>row){
                    row=data[index].xaxis;
                }
                if (data[index].yaxis>col){
                    col=data[index].yaxis;
                }
            }

            var seatModal= document.getElementById('seatModal');
            var seats =document.getElementById('seats');

            for (var i = 0; i  <= row; i++) {//座位表数组
                seatArray[i] = new Array();
                for (var j = 0; j  <= col; j++) {
                    seatArray[i][j] = 0;
                }
            }
            // alert(seatlist[0]);
            for(var index=0;index<seatlist.length;index++){
                // alert(seatlist[index][3]);
                if(seatlist[index][3]==1){
                    seatArray[seatlist[index][1]][seatlist[index][2]]=1;//编辑座位使用情况
                }
                if(seatlist[index][3]==2){
                    seatArray[seatlist[index][1]][seatlist[index][2]]=2;//编辑座位使用情况
                }
            }

            for (var v = 0; v  <= row; v++) {
                var string = "<ul name="+"\"chair\" >";
                for (var i = 0; i  <= col; i++) {
                    if (seatArray[v][i]==1){
                        string = string + '<li><img src='+'/img/seat_selected.png '+'/></li>';
                    }else if (seatArray[v][i]==2){
                        string = string + '<li><img src='+'/img/seat_sale.png '+'/></li>';
                    }else if (seatArray[v][i]==0){
                        string = string + '<li></li>';
                    }
                }
                string = string + '</ul>';
                $('#seats').append(string);
            }
            var list = document.querySelectorAll('ul[name="chair"] li');
            // alert("ssdsd");

            for (var i = 0; i  <  list.length; i++) {
                list[i]._index = i;
                list[i].onclick = function () {
                    var indexCol = this._index % (col+1);
                    var indexRow = (this._index - indexCol) / (col+1);
                    //报行列号
                    // alert(indexRow + " " + indexCol);
                    // 如果可选则更新状态
                    if (seatArray[indexRow][indexCol] == 2) {
                        $(this).replaceWith('<li><img src="/img/seat_book.png"/></li>');//更换图片
                        seatArray[indexRow][indexCol] = 3;//更新自己选定的数组位置状态
                        alert(indexRow+" "+indexCol);
                    }else if (seatArray[indexRow][indexCol] == 3) {
                        $(this).replaceWith('<li><img src="/img/seat_sale.png"/></li>');//更换图片
                        seatArray[indexRow][indexCol] = 2;//更新自己选定的数组位置状态
                    }
                }
            }
            seats.style.width = "(col* 38 + 160)"+ "px";
            seatModal.style.display="block";
        }
    });
    // alert("ads")



}
function addOrders() {
    var sId=$("#sId").text();
    var x=0,index=0;
    var seatVos=[];
    for(var i=0;i<=row;i++){
        for (var j=0;j<=col;j++){
            // alert(seatArray[i][j])
            if (seatArray[i][j] == 3){
                for (var x=0;x<seatlist.length;x++) {
                    if(seatlist[x][1]==i&&seatlist[x][2]==j){
                        var seat={};
                        seat["sId"]=Number(sId);
                        seat["sdId"]=seatlist[x][0];
                        seatVos.push(seat);
                    }
                }
            }
        }
    }
    document.getElementById("seatModal").style.display = "none";
    seatArray=new Array();
    seatlist=new Array();
    $('#seats ul').remove();
    $.ajax({
        dataType:'json',
        type: "post",
        url: "/orders/add",//向后端请求数据的url
        traditiona: true,
        contentType:"application/json;charset=utf-8",
        data: JSON.stringify(seatVos),
        success: function (data) {
            viewmodel.text = "数据请求成功，已渲染";
        }
    });

}