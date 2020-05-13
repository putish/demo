var sId;
var mId=document.getElementById("mId");
var hId=document.getElementById("hId");
var screenCate=document.getElementById("screenCate");
var price=document.getElementById("price");
var startTime=document.getElementById("startTime");
function deleteScreen(e) {
    sId=e.name;

    $.ajax({
        dataType:'json',
        type: "post",
        url: "/screen/delete",    //向后端请求数据的url
        data:{cId:cId},
        success: function (data) {
            window.location.reload();

        }
    });

}
function editScreen(e){

    sId=e.name;
    var addmodel=document.getElementById("addModal");
    $.ajax({
        type : 'GET',
        dataType : 'json',
        url: "/screen/detail",
        data:{cId:cId},
        success: function (data) {
            var detail=data.data;
            mId.value=detail["mId"];
            hId.value=detail["hId"];
            price.value=detail["price"];
            screenCate.value=detail["screenCate"];
            startTime.value=detail["startTime"];
            addmodel.style.display="block";
            if (detail["tickets"]!=0){
                mId.setAttribute("disabled",false);
            }
            document.getElementById("editScreen").style.display="block";
            document.getElementById("createScreen").style.display="none";

        }
    });
}
function toaddScreen() {


    var mId=$('#mId').val();
    var hId=$('#hId').val();
    var screenCate=$('#screenCate').val();
    var price=$('#price').val();
    var startTime=$('#startTime').val();

    var json={
        mId:mId,
        hId:hId,
        screenCate:screenCate,
        price:price,
        startTime:startTime
    };
    $.ajax({
        dataType:'json',
        type: "post",
        traditiona: true,
        contentType:"application/json;charset=utf-8",
        url: "/screen/add",//向后端请求数据的url
        data: JSON.stringify(json),
        success: function (data) {
            window.location.reload();

        }
    });
    sId=null;
};
function toeditScreen() {
    var mId=$('#mId').val();
    var hId=$('#hId').val();
    var screenCate=$('#screenCate').val();
    var price=$('#price').val();
    var startTime=$('#startTime').val();

    var json={
        sId:sId,
        mId:mId,
        hId:hId,
        screenCate:screenCate,
        price:price,
        startTime:startTime
    };
    alert(cname);
    $.ajax({
        dataType:'json',
        type: "post",
        url: "/screen/edit",//向后端请求数据的url
        traditiona: true,
        contentType:"application/json;charset=utf-8",
        data: JSON.stringify(json),
        success: function (data) {
            document.getElementById("editScreen").style.display="none";
            document.getElementById("createScreen").style.display="block";
            window.location.reload();

        },
        error:function(xhr,state,errorThrown){
            alert("编辑失败");
        }

    });
    document.getElementById("editScreen").style.display="none";
    document.getElementById("createScreen").style.display="block";
    window.location.reload();
    sId=null;

}
function addScreen() {
    var addModel=document.getElementById("addModal");
    addModel.style.display="block";
}
window.onload=function (){
    document.getElementById("addModalClose").onclick = function () {
        var addModal = document.getElementById("addModal");
        addModal.style.display = "none";
    };
}