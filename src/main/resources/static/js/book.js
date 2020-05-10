var seatArray = new Array();
var tdsche=document.getElementById("tdsche");
var tmsche=document.getElementById("tmsche");
var aftche=document.getElementById("aftche");
var today=document.getElementById("today")
var tommorrow=document.getElementById("tommorrow")
var aftertomm=document.getElementById("aftertomm")
var seatlist = new Array();
var row=0,col=0;
var sId;
function closemodal() {
    document.getElementById("seatModal").style.display = "none";
    document.getElementById("seatModal").style.display = "none";
    seatArray=new Array();
    seatlist=new Array();
    $('#seats ul').remove();

}
function chooseSeats(e)  {
    sId=e.name;

    $.ajax({
        type : 'GET',
        dataType : 'json',
        url: "/hall/getSeat",    //向后端请求数据的url
        data:{
            sId:sId
        },
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
            row=row+1;
            col=col+1;

            var seatModal= document.getElementById('seatModal');
            var seats =document.getElementById('seats');
            for (var i = 0; i  < row; i++) {//座位表数组
                seatArray[i] = new Array();
                for (var j = 0; j  < col; j++) {
                    seatArray[i][j] = 0;
                }
            }
            for(var index=0;index<seatlist.length;index++){
                if(seatlist[index][3]==1){
                    seatArray[seatlist[index][1]][seatlist[index][2]]=2;//编辑座位使用情况
                }
                if(seatlist[index][3]==2){
                    seatArray[seatlist[index][1]][seatlist[index][2]]=1;//编辑座位使用情况
                }
            }
            for (var i = 0; i  <row; i++) {
                // alert(row+" "+col);
                var string = "<ul name="+"\"chair\" >";
                for (var j = 0; j  < col; j++) {
                    var index=i * col + j;
                    // alert(index)

                    if (seatArray[i][j]==1){
                        string = string + '<li name="' + index + '"  onclick="chooseSeat('+index+')"><img src='+'/img/seat_sale.png '+'/></li>';
                    }else if (seatArray[i][j]==2){
                        string = string + '<li name="' + index + '"  onclick="chooseSeat('+index+')"><img src='+'/img/seat_book.png '+'/></li>';
                    }else if (seatArray[i][j]==0){
                        string = string + '<li name="' + index + '"  onclick="chooseSeat('+index+')"></li>';
                    }
                }
                string = string + '</ul>';
                $('#seats').append(string);
            }

            seats.style.width = "(col* 38)"+ "px";
            seatModal.style.display="block";
        }
    });
    // alert("ads")



}
function chooseSeat(index) {

        var indexCol = index % col;//根据下标得到列数
        var indexRow = (index - indexCol) / col;//根据下标得到行数

        if (seatArray[indexRow][indexCol] == 1) {
            document.getElementsByTagName("img")[index+1].setAttribute("src","/img/seat_selected.png");
            seatArray[indexRow][indexCol] = 2;//更新自己选定的数组位置状态
     }


}
function addOrders() {
    var x=0,index=0;
    var seatVos=[];
    for(var i=0;i< row;i++){
        for (var j=0;j< col;j++){
            if (seatArray[i][j] == 2){
                for (var x=0;x<seatlist.length;x++) {
                    if(seatlist[x][1]==i&&seatlist[x][2]==j){
                        // alert(i+" "+j);
                        var seat={};
                        seat["sId"]=sId;
                        seat["sdId"]=seatlist[x][0];
                        seatVos.push(seat);
                    }
                }
            }
        }
    }
    var json={
        sId:sId,
        seatVos:seatVos
    }
    $.ajax({
        dataType:'json',
        type: "post",
        url: "/orders/add",//向后端请求数据的url
        traditiona: true,
        contentType:"application/json;charset=utf-8",
        data: JSON.stringify(json),
        success: function (data) {
        }
    });
    document.getElementById("seatModal").style.display = "none";
    seatArray=new Array();
    seatlist=new Array();
    $('#seats ul').remove();
    sId=null;
}
window.onload=function (){

};