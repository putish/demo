var cId;
function deleteCatergory(e) {
    cId=e.name;

    $.ajax({
        dataType:'json',
        type: "post",
        url: "/catergory/delete",    //向后端请求数据的url
        data:{cId:cId},
        success: function (data) {
            window.location.reload();

        }
    });

}
function editCatergory(e){
    cId=e.name;
    var addmodel=document.getElementById("addModal");
    $.ajax({
        type : 'GET',
        dataType : 'json',
        url: "/catergory/detail",
        data:{cId:cId},
        success: function (data) {
            var catergoryDetail=data.data;
            var cName=document.getElementById('cName');
            cName.value=catergoryDetail["cname"];
            addmodel.style.display="block";
            document.getElementById("editMovie").style.display="block";
            document.getElementById("createMovie").style.display="none";

        }
    });
}
function toaddCatergory() {


    var cname=$('#cName').val();
alert(cname)
    var json={
        cName:cname
    };
    $.ajax({
        dataType:'json',
        type: "post",
        traditiona: true,
        contentType:"application/json;charset=utf-8",
        url: "/catergory/add",//向后端请求数据的url
        data: JSON.stringify(json),
        success: function (data) {
            window.location.reload();

    }
    });
    cId=null;
};
function toeditCatergory() {
    var cname=$('#cName').val();
   var json={
       cName:cname,
       cId:cId
   };
    alert(cname);
    $.ajax({
        dataType:'json',
        type: "post",
        url: "/catergory/edit",//向后端请求数据的url
        traditiona: true,
        contentType:"application/json;charset=utf-8",
        data: JSON.stringify(json),
        success: function (data) {
            document.getElementById("editMovie").style.display="none";
            document.getElementById("createMovie").style.display="block";
            window.location.reload();

        },
        error:function(xhr,state,errorThrown){
            alert("编辑失败");
        }

    });
    document.getElementById("editMovie").style.display="none";
    document.getElementById("createMovie").style.display="block";
    window.location.reload();
    cId=null;

}
function addCatergory() {
    var addModel=document.getElementById("addModal");
    addModel.style.display="block";
}
window.onload=function (){
document.getElementById("addModalClose").onclick = function () {
    var addModal = document.getElementById("addModal");
    addModal.style.display = "none";
};}