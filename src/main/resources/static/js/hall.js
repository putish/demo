
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
var seatlist = new Array();
var row,col;
var count=0;
function createHallSeat() {

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
        seatVos:seatVos
    }
    $('#seats ul').remove();
    $.ajax({
        dataType:'json',
        type: "post",
        url: "/hall/edit",//向后端请求数据的url
        traditiona: true,
        contentType:"application/json;charset=utf-8",
        data: JSON.stringify(json),
        success: function (data) {
            viewmodel.text = "数据请求成功，已渲染";
        }
    });
    var seatVos=new Array();

}
function editHallSeat() {

    // alert(count);
    var delSeatVos=[];
    var addSeatVos=[];
    var hId=$("#hId").text();
    for(var i=0;i<row;i++){
        for (var j=0;j<col;j++){
            if (seatArray[i][j] == 1){
                var seat={};
                seat["xAxis"]=i;
                seat["yAxis"]=j;
                addSeatVos.push(seat);

            }if (seatArray[i][j] == 3){
                for (var x=0;x<seatlist.length;x++) {
                    if(seatlist[x][1]==i&&seatlist[x][2]==j){
                        delSeatVos.push(seatlist[x][0]);
                    }
                }

            }
        }
    }
    // alert(seatVos[0]);
    var hName=$('#addhname').val();
    var cate=$('#addcate').val();
    document.getElementById("seatModal").style.display = "none";
    seatArray=new Array();
    var json={
        hId:hId,
        rows:row,
        cols:col,
        hName:hName,
        screenCate:cate,
        seatCount:count,
        delSeatVos:delSeatVos,
        addSeatVos:addSeatVos
    }
    $('#seats ul').remove();
    $.ajax({
        dataType:'json',
        type: "post",
        url: "/hall/edit",//向后端请求数据的url
        traditiona: true,
        contentType:"application/json;charset=utf-8",
        data: JSON.stringify(json),
        success: function (data) {
            viewmodel.text = "数据请求成功，已渲染";
        }
    });

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

    var seatModal= document.getElementById('seatModal');
    var seats =document.getElementById('seats');

    if (!isNaN(row) && !isNaN(col)) {
        if (row > 10 || col > 10) {
            seatModal.style.display = "none";
            alert("行数和列数不可以超过10");
            return;
        } else {
            seatModal.style.display="block";
        }

        for (var i = 0; i  <  row; i++) {//一维数组长度为30
            seatArray[i] = new Array();
            for (var j = 0; j  < col; j++) {//第二维长度为20
                seatArray[i][j] = 1;
            }
        }for(var index=0;index<seatlist.length;index++){
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

        for (var v = 0; v  < row; v++) {
            var string = "<ul name="+"\"chair\" >";
            for (var i = 0; i  < col; i++) {
                string = string + '<li><img src='+'/img/seat_selected.png '+'/></li>';//生成li标签座位
            }
            string = string + '</ul>';
            $('#seats').append(string);
        }
        var list = document.querySelectorAll('ul[name="chair"] li');
        for (var i = 0; i  <  list.length; i++) {
            list[i]._index = i;
            list[i].onclick = function () {
                var indexCol = this._index % col;//根据下标得到列数
                var indexRow = (this._index - indexCol) / col;//根据下标得到行数
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
function editSeats()  {
    row = $('#row').val();
    col =  $('#col').val();
    count=row*col;

    var seatModal= document.getElementById('seatModal');
    var seats =document.getElementById('seats');

    if (!isNaN(row) && !isNaN(col)) {
        if (row > 10 || col > 10) {
            seatModal.style.display = "none";
            alert("行数和列数不可以超过10");
            return;
        } else {
            seatModal.style.display="block";
        }

        for (var i = 0; i  <  row; i++) {//一维数组长度为30
            seatArray[i] = new Array();
            for (var j = 0; j  < col; j++) {//第二维长度为20
                seatArray[i][j] = 1;
            }
        }
        for(var index=0;index<seatlist.length;index++){
            if(seatlist[index][1]<row && seatlist[index][2]<col){
                seatArray[seatlist[index][1]][seatlist[index][2]]=2;//编辑座位使用情况
            }
        }

        for (var i = 0; i  <  row; i++) {
            var string = "<ul name="+"\"chair\" >";
            for (var j = 0; j  < col; j++) {
                if (seatArray[i][j]==1){
                    string = string + '<li><img src='+'/img/seat_selected.png '+'/></li>';
                }else if (seatArray[i][j]==2){
                    string = string + '<li><img src='+'/img/seat_book.png '+'/></li>';
                }
            }
            string = string + '</ul>';
            $('#seats').append(string);
        }
        var list = document.querySelectorAll('ul[name="chair"] li');
        for (var i = 0; i  <  list.length; i++) {
            list[i]._index = i;
            list[i].onclick = function () {
                var indexCol = this._index % col;//根据下标得到列数
                var indexRow = (this._index - indexCol) / col;//根据下标得到行数
                if (seatArray[indexRow][indexCol] == 1) {
                    $(this).replaceWith('<li><img src="/img/seat_sale.png"/></li>');//更换图片
                    seatArray[indexRow][indexCol] = 0;//更新自己选定的数组位置状态
                    count=count-1;
                } else if (seatArray[indexRow][indexCol] == 0) {
                    $(this).replaceWith('<li><img src="img/seat_selected.png"/></li>');//更换图片
                    seatArray[indexRow][indexCol] = 1;//更新自己选定的数组位置状态
                    count=count+1;
                } else if (seatArray[indexRow][indexCol] == 2) {
                    $(this).replaceWith('<li><img src="/img/seat_sale.png"/></li>');//更换图片
                    seatArray[indexRow][indexCol] = 3;//更新自己选定的数组位置状态
                    count=count-1;
                }else if (seatArray[indexRow][indexCol] == 3) {
                    $(this).replaceWith('<li><img src="/img/seat_book.png"/></li>');//更换图片
                    seatArray[indexRow][indexCol] = 2;//更新自己选定的数组位置状态
                    count=count+1;
                }
            }
        }
        seats.style.width = "(col* 38 + 160)"+ "px";
        var editHallSeatButton=document.getElementById("editHallSeat");
        editHallSeatButton.style.display="block";
        var createHallSeatButton=document.getElementById("createHallSeat");
        createHallSeatButton.style.display="none";
    } else {
        alert("行数和列数必需是数字");
    }



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

        }
    });

}
function editHall(){
    var hId=$("#hId").text();
    var addmodel=document.getElementById("addModal");
    $.ajax({
        type : 'GET',
        dataType : 'json',
        url: "/hall/hallDetail",
        data:{hId:hId},
        success: function (data) {
            var hallDetail=data.data;
            var hName=document.getElementById('addhname');
            var cate=document.getElementById('addcate');
            var row=document.getElementById('row');
            var col=document.getElementById('col');
            hName.value=hallDetail["hname"];
            cate.value=hallDetail["screenCate"];
            row.value=hallDetail["rows"];
            col.value=hallDetail["cols"];
            addmodel.style.display="block";
            var seatVos=eval(JSON.stringify(hallDetail["seatVos"]));
            for (var index = 0; index  <  seatVos.length; index++) {//座位表数组
                var seat=new Array(seatVos[index].sdId,seatVos[index].xaxis,seatVos[index].yaxis);
                seatlist[index]=seat;
            }
            var editSeatButton=document.getElementById("editSeat");
            editSeatButton.style.display="block";
            var createSeatButton=document.getElementById("createSeat");
            createSeatButton.style.display="none";


        }
    });
}

window.onload=function(){
    document.getElementById("addModalClose").onclick=function () {
        var addModal=document.getElementById("addModal");
        var seatModal=document.getElementById("seatModal");
        if(addModal.style.display=="block"){
            alert("sda");
            addmodel.style.display="none";
            // addmodel.style.display="none";

            // if(seatModal.style.display=="none"){
            //
            //     addmodel.style.display="none";
            // }
        }

    };
    document.getElementById("seatModalclose").onclick=function () {
        var seatModal=document.getElementById("seatModal");
        seatModal.style.display="none";
        seatArray=new Array();
        $('#seats ul').remove();

    };
};
