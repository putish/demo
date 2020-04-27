function deleteone() {
    var cId=$("#cId").text();
    var json={
        cId:cId,
    }
    $.ajax({
        dataType:'json',
        type: "post",
        url: "/catergory/delete",    //向后端请求数据的url
        data:{mId:mId},
        success: function (data) {

        }
    });

}
function edit(){
    var mId=$("#mId").text();
    var addmodel=document.getElementById("addModal");
    $.ajax({
        type : 'GET',
        dataType : 'json',
        url: "/catergory/detail",
        data:{mId:mId},
        success: function (data) {
            var catergoryDetail=data.data;
            var cName=document.getElementById('cname');
            cName.value=catergoryDetail["hname"];
            addmodel.style.display="block";


        }
    });
}
function add() {

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