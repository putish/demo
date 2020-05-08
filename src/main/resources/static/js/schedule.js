var scId,mId;
function toaddSchedule() {
    var fCount=$("#fCount").val();
    var sCount=$("#sCount").val();
    var tCount=$("#tCount").val();
    var screenCate=$("#screenCate").val();
    var price=$("#price").val();


    var json={
        fCount:fCount,
        sCount:sCount,
        tCount:tCount,
        price:price,
        screenCate:screenCate,
        mId:mId
    };
    $.ajax({
        dataType:'json',
        type: "post",
        url: "/schedule/add",//向后端请求数据的url
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
    document.getElementById("addModal").style.display="none";
}
function deleteSchedule(e) {
    var test = window.location.search;
    mId=test.substring(5,test.length);
    scId=e.name;
    var json={
        scId:scId,
    };
    $.ajax({
        dataType:'json',
        type: "post",
        url: "/schedule/delete",//向后端请求数据的url
        data: {scId:scId},
        success: function (data) {
            window.location.reload();

        },
        error:function(xhr,state,errorThrown){
            alert("删除失败");
        }
    });
}
function toeditSchedule() {

    var fCount=$("#fCount").val();
    var sCount=$("#sCount").val();
    var tCount=$("#tCount").val();
    var screenCate=$("#screenCate").val();
    var sprice=$("#price").val();


    var json={
        fCount:fCount,
        sCount:sCount,
        tCount:tCount,
        price:sprice,
        screenCate:screenCate,
        mId:mId,
        scId:scId
    };
    $.ajax({
        dataType:'json',
        type: "POST",
        url: "/schedule/edit",//向后端请求数据的url
        traditiona: true,
        contentType:"application/json;charset=utf-8",
        data: JSON.stringify(json),
        success: function (data) {
            window.location.reload();

        },
        error:function(xhr,state,errorThrown){
            alert("编辑失败");
        }
    });
    document.getElementById("addModal").style.display="none";
    scId=null;

}
function addSchedule(){
    var test = window.location.search;
    mId=test.substring(5,test.length);
    var addModel=document.getElementById("addModal");
    addModel.style.display="block";
}
function editSchedule(e){
    var test = window.location.search;
    mId=test.substring(5,test.length);
    scId=e.name;
    document.getElementById("addModal").style.display = "block";
    $("#addModel").modal('show');
    $.ajax({
        type : 'GET',
        dataType : 'json',
        url: "/schedule/detail",
        data:{scId:scId},
        success: function (data) {
            var scheduleDetail=data.data;
            var fCount = document.getElementById("fCount");
            var sCount = document.getElementById('sCount');
            var tCount = document.getElementById('tCount');
            var screenCate = document.getElementById('screenCate');
            var price = document.getElementById('price');
            fCount.value = scheduleDetail["fcount"];
            sCount.value = scheduleDetail["scount"];
            tCount.value = scheduleDetail["tcount"];
            screenCate.value = scheduleDetail["screenCate"];
            price.value=scheduleDetail["price"];

            document.getElementById("editMovie").style.display="block";
            document.getElementById("createMovie").style.display="none";
        }
    });
}
window.onload=function () {

    document.getElementById("addModalClose").onclick = function () {
        var addModal = document.getElementById("addModal");
        addModal.style.display = "none";
    };

};