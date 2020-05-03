avalon.ready(function () {
    viewmodel = avalon.define({
        $id: "listmodal",
        datalist: {},
        text: "请求数据",
    });

    avalon.scan();
});
var seatArray = new Array();
var seatlist = new Array();
var row, col;
var count = 0;
var hId;
function createHallSeat() {

    var x=0,index=0;
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
    var hName=$('#addhname').val();
    var cate=$('#addcate').val();
    document.getElementById("seatModal").style.display = "none";
    document.getElementById("addModal").style.display = "none";

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
        url: "/hall/add",//向后端请求数据的url
        traditiona: true,
        contentType:"application/json;charset=utf-8",
        data: JSON.stringify(json),
        success: function (data) {
            window.location.reload();
        },
        error:function(xhr,state,errorThrown){
            alert("新增失败");
        }
    });

    var seatVos=new Array();

}
function editHallSeat() {
    row = $('#row').val();
    col = $('#col').val();
    var delSeatVos=[];
    var addSeatVos=[];
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
    var hName=$('#addhname').val();
    var cate=$('#addcate').val();
    document.getElementById("seatModal").style.display = "none";
    document.getElementById("addModal").style.display = "none";

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
                 },
        error:function(xhr,state,errorThrown){
            alert("编辑失败");
        }
    });
    window.location.reload();
    hId=null;

}


function createSeats() {
    row = $('#row').val();
    col = $('#col').val();
    count = row * col;
    var hName = document.getElementById('addhname');
    var cate = document.getElementById('addcate');
    var seatModal = document.getElementById('seatModal');
    var seats = document.getElementById('seats');

    if (!isNaN(row) && !isNaN(col)) {
        if (row > 12 || col > 12) {
            seatModal.style.display = "none";
            alert("行数和列数不可以超过10");
            return;
        } else {
            seatModal.style.display = "block";
        }

        /*]]>*/
        for (var i = 0; i < row; i++) {//一维数组长度为30
            seatArray[i] = new Array();
            for (var j = 0; j < col; j++) {//第二维长度为20
                seatArray[i][j] = 1;
            }
        }

        for (var i = 0; i < row; i++) {//一维数组长度为30
            var string = "<ul name=" + "\"chair\" >";
            for (var j = 0; j < col; j++) {//第二维长度为20
                var index=i * col + j;
                // alert(index);
                string = string + '<li name="' + index + '"  onclick="chooseSeat('+index+')"><img src=' + '/img/seat_selected.png ' + '/></li>';//生成li标签座位
            }
            string = string + '</ul>';
            $('#seats').append(string);
        }

    } else {
        alert("行数和列数必需是数字");
    }
    seats.style.width = "(col* 38 + 160)" + "px";


}

function chooseSeat(index) {
    {
        // var index = $(e).attr("name");
        // alert(index);
        var indexCol = index % col;//根据下标得到列数
        var indexRow = (index - indexCol) / col;//根据下标得到行数
        if (seatArray[indexRow][indexCol] == 1) {
            document.getElementsByTagName("img")[index].setAttribute("src","/img/seat_sale.png");

            seatArray[indexRow][indexCol] = 0;//更新自己选定的数组位置状态
            count = count - 1;
        } else if (seatArray[indexRow][indexCol] == 0) {
            document.getElementsByTagName("img")[index].setAttribute("src","/img/seat_selected.png");
            seatArray[indexRow][indexCol] = 1;//更新自己选定的数组位置状态
            count = count + 1;

        } else if (seatArray[indexRow][indexCol] == 2) {
            document.getElementsByTagName("img")[index].setAttribute("src","/img/seat_sale.png");
            seatArray[indexRow][indexCol] = 3;//更新自己选定的数组位置状态
            count=count-1;
        }else if (seatArray[indexRow][indexCol] == 3) {
            document.getElementsByTagName("img")[index].setAttribute("src","/img/seat_book.png");
            seatArray[indexRow][indexCol] = 2;//更新自己选定的数组位置状态
            count=count+1;
        }
    }

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
                var index=i * col + j;
                if (seatArray[i][j]==1){
                    string = string + '<li name="' + index + '"  onclick="chooseSeat('+index+')"><img src='+'/img/seat_selected.png '+'/></li>';
                }else if (seatArray[i][j]==2){
                    string = string + '<li name="' + index + '"  onclick="chooseSeat('+index+')"><img src='+'/img/seat_book.png '+'/></li>';
                }
            }
            string = string + '</ul>';
            $('#seats').append(string);
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
function deleteHall(e) {
    var hId = e.name;
    var json = {
        hId: hId,
    };
    $.ajax({
        dataType: 'json',
        type: "post",
        url: "/hall/delete",    //向后端请求数据的url
        data: {hId: hId},
        success: function (data) {

            window.location.reload();
            },
        error:function(xhr,state,errorThrown){
            alert("删除失败");
        }
    });

}

function editHall(e){
    hId=e.name;
    var addModel=document.getElementById("addModal");
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
            addModel.style.display="block";
            var seatVos=eval(JSON.stringify(hallDetail["seatVos"]));
            for (var index = 0; index  <  seatVos.length; index++) {//座位表数组
                var seat=new Array(seatVos[index].sdId,seatVos[index].xaxis,seatVos[index].yaxis);
                seatlist[index]=seat;
            }
            var editSeatButton=document.getElementById("editSeat");
            editSeatButton.style.display="block";
            var createSeatButton=document.getElementById("createSeat");
            createSeatButton.style.display="none";


        },
        error:function(xhr,state,errorThrown){
            alert(xhr);
            alert(state);
            alert(errorThrown);
        }
    });
}
function addHall(){
    var addModel=document.getElementById("addModal");
    addModel.style.display="block";
}
window.onload = function () {
    document.getElementById("addModalClose").onclick = function () {
        var addModal = document.getElementById("addModal");
        var seatModal = document.getElementById("seatModal");
        addModal.style.display = "none";

        if (addModal.style.display == "block") {
            if (seatModal.style.display == "none") {
                addModel.style.display = "none";
            }
        }

    };
    document.getElementById("seatModalclose").onclick = function () {
        var seatModal = document.getElementById("seatModal");
        seatModal.style.display = "none";
        seatArray = new Array();
        $('#seats ul').remove();

    };
}