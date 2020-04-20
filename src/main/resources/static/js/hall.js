
avalon.ready(function() {
    viewmodel = avalon.define({
        $id: "listmodal",
        datalist: {},
        text: "请求数据",
    });
    addmodel = avalon.define({
        $id: "addmodel",
        seatVos:[[]],

    });
    avalon.scan();
});
var seatArray = new Array();
var row,col;
var count=0;
function closeSeat() {

    var x=0,index=0;
    // alert(count);
    var seatVos=[];
    for(var i=0;i<row;i++){
        for (var j=0;j<col;j++){
            if (seatArray[i][j] == 1){
                var seat={};
                seat["xAxis"]=i;
                seat["yAxis"]=j;
                seatVos.push(seat);
                // seatVos[x]=seat;
                // seatVos['seatVos['+x+'].xAxis']=i;
                // seatVos['seatVos['+x+'].yAxis']=j;
                x=x+1;
                index=index+1;
            }
        }
    }
    alert(seatVos[0]);
    var hName=$('#addhname').val();
    var cate=$('#addcate').val();
    document.getElementById("seatModal").style.display = "none";
    seatArray=new Array();
    var json={
        rows:row,
        cols:col,
        hName:hName,
        screenCate:cate,
        seatCount:count,
        seatVos:seatVos,
        tId:1
    }
    $('#seats ul').remove();
    $.ajax({
        dataType:'json',
        type: "post",
        url: "/hall/add",//向后端请求数据的url
        traditiona: true,
        contentType:"application/json;charset=utf-8",
        data: JSON.stringify(json),
        success: function (data) {
            viewmodel.text = "数据请求成功，已渲染";
        }
    });
    var seatVos=new Array();

}
function sort() {
    var useState = $('#useState').val();
    var screenCate = $('#screenCate').val();
    var startCount = $('#startCount').val();
    var endCount = $('#endCount').val();

    if (useState=="使用中") {useState=1;}
    else if (useState=="闲置中") {useState=2;}
    else if (useState=="已过期") {useState=3;}
    else if (useState=="故障中") {useState=4;}
    alert(useState);
    // var json={
    //     useState:useState,
    //     screenCate:screenCate,
    //     startCount:startCount,
    //     endCount:endCount
    // }
    var data=new FormData();
    data.append("useState",useState);
    data.append("screenCate",screenCate);
    data.append("startCount",startCount);
    data.append("endCount",endCount);
    $.ajax({
        type: "get",
        url: "/hall/list",    //向后端请求数据的url
        data: {
            useState:useState,
            screenCate:screenCate,
            startCount:startCount,
            endCount:endCount
        },
        cache:false,
        // traditiona: true,
        // contentType:"application/json;charset=utf-8",
        // dataType:'json',
        success: function (data) {

            viewmodel.datalist = data;
            alert("成功")
            viewmodel.text = "数据请求成功，已渲染";
        }
    });

}


function createSeats()  {
    row = $('#row').val();
    col =  $('#col').val();
    count=row*col;
    var hName=document.getElementById('addhname');
    var cate=document.getElementById('addcate');
    var seatModal= document.getElementById('seatModal');
    var seats =document.getElementById('seats');
    // alert(seatArray.length);
    // alert(row+"   "+col);
    /*<![CDATA[*/
    if (!isNaN(row) && !isNaN(col)) {
        if (row > 10 || col > 10) {
            seatModal.style.display = "none";
            alert("行数和列数不可以超过10");
            return;
        } else {
            seatModal.style.display="block";
        }

        /*]]>*/
        for (var i = 0; i  <  row; i++) {//一维数组长度为30
            seatArray[i] = new Array();
            for (var j = 0; j  < col; j++) {//第二维长度为20
                seatArray[i][j] = 1;
            }
        }

        for (var v = 0; v  < row; v++) {
            var string = "<ul name="+"\"chair\" >";
            for (var i = 0; i  < col; i++) {
                string = string + '<li><img src='+'/img/seat_selected.png '+'/></li>';
            }
            string = string + '</ul>';
            $('#seats').append(string);
        }
        var list = document.querySelectorAll('ul[name="chair"] li');
        // alert("ssdsd");

        for (var i = 0; i  <  list.length; i++) {
            list[i]._index = i;
            list[i].onclick = function () {
                var indexCol = this._index % col;
                var indexRow = (this._index - indexCol) / col;
                //报行列号
                // alert(indexRow + " " + indexCol);
                // 如果可选则更新状态
                if (seatArray[indexRow][indexCol] == 1) {
                    $(this).replaceWith('<li><img src="/img/seat_sale.png"/></li>');//更换图片
                    seatArray[indexRow][indexCol] = 0;//更新自己选定的数组位置状态
                    count=count-1;
                } else if (seatArray[indexRow][indexCol] == 0) {
                    $(this).replaceWith('<li><img src="img/seat_selected.png"/></li>');//更换图片
                    seatArray[indexRow][indexCol] = 1;//更新自己选定的数组位置状态
                    count=count+1;

                }
            }
        }
    } else {
        alert("行数和列数必需是数字");
    }
    seats.style.width = "(col* 38 + 160)"+ "px";


}


function deleteone() {
    var hId=$("#hId").text();
    var json={
        hId:hId,
    }
    $.ajax({
        dataType:'json',
        type: "post",
        url: "/hall/delete",    //向后端请求数据的url
        data:{hId:hId},
        success: function (data) {

            viewmodel.text = "数据请求成功，已渲染";
        }
    });

}
function edit(){
    var hId=$("#hId").text();
    var addmodel=document.getElementById("addModal");
    $.ajax({
        type : 'GET',
        dataType : 'json',
        url: "/hall/hallDetail",
        data:{hId:hId},
        success: function (data) {
            var hallDetail=data.data;
            alert(hallDetail)
            alert(hallDetail.hname)
            var hName=document.getElementById('addhname');
            var cate=document.getElementById('addcate');
            var row=document.getElementById('row');
            var col=document.getElementById('col');
            var seatModal= document.getElementById('seatModal');
            var seats =document.getElementById('seats');
            hName.value=hallDetail["hname"];
            cate.value=hallDetail["screenCate"];
            row.value=hallDetail["rows"];
            col.value=hallDetail["cols"];
            addmodel.style.display="block";


        }
    });
}
window.onload=function(){
    document.getElementById("addModalClose").onclick=function () {
        var addModal=document.getElementById("addModal");
        var seatModal=document.getElementById("seatModal");
        if(addModal.style.display=="block"){
            if(seatModal.style.display=="none"){
                addmodel.style.display="none";
            }
        }

    };
    document.getElementById("seatModalclose").onclick=function () {
        var seatModal=document.getElementById("seatModal");
        seatModal.style.display="none";
        seatArray=new Array();
        $('#seats ul').remove();

    };
}