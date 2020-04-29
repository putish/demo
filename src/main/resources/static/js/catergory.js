function deleteone() {
    var cId=$("#cId").text();

    $.ajax({
        dataType:'json',
        type: "post",
        url: "/catergory/delete",    //向后端请求数据的url
        data:{cId:cId},
        success: function (data) {

        }
    });

}
function edit(){
    var cId=$("#cId").text();
    var addmodel=document.getElementById("addModal");
    $.ajax({
        type : 'GET',
        dataType : 'json',
        url: "/catergory/detail",
        data:{mId:mId},
        success: function (data) {
            var catergoryDetail=data.data;
            var cName=document.getElementById('cname');
            cName.value=catergoryDetail["cname"];
            addmodel.style.display="block";
            document.getElementById("editButton").style.display="block";
            document.getElementById("addButton").style.display="none";

        }
    });
}
function add() {


    var cname=$('#cname').val();

    $.ajax({
        dataType:'json',
        type: "post",
        url: "/catergory/add",//向后端请求数据的url
        traditiona: true,
        contentType:"application/json;charset=utf-8",
        data: JSON.stringify(cname),
        success: function (data) {
        }
    });

};
function editCatergory() {
    var cname=$('#cname').val();

    $.ajax({
        dataType:'json',
        type: "post",
        url: "/catergory/edit",//向后端请求数据的url
        traditiona: true,
        contentType:"application/json;charset=utf-8",
        data: JSON.stringify(cname),
        success: function (data) {
            document.getElementById("editButton").style.display="none";
            document.getElementById("addButton").style.display="block";

        }
    });
}
