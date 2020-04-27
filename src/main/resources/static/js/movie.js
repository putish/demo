function deletemovie() {
    var mId=$("#mId").text();
    $.ajax({
        dataType:'json',
        type: "post",
        url: "/movie/delete",    //向后端请求数据的url
        data:{mId:mId},
        success: function (data) {

            viewmodel.text = "数据请求成功，已渲染";
        }
    });

}
function addmovie() {

    var poster=$("#file").val();
    var mName=$("#mName").val();
    var catergorys=$("#catergory").val();
    var duration=$("#duration").val();
    var actor=$("#actor").val();
    var description=$("#description").val();
    var price=$("#price").val();
    var showTime=$("#showTime").val();
    var endTime=$("#endTime").val();
    var director=$("#director").val();
    var catergory;
    alert(poster);
    for(var i=0;i<catergorys.length;i++){
        if(i==0){
            catergory=catergorys[i];
        }
        else {
            catergory=catergory+","+catergorys[i];
        }
    }

    var json={
        poster:poster,
        mName:mName,
        catergory:catergory,
        duration:duration,
        actor:actor,
        description:description,
        price:price,
        director:director,
        showTime:showTime,
        endTime:endTime,
        tId:1
    };
    $.ajax({
        dataType:'json',
        type: "post",
        url: "/movie/add",//向后端请求数据的url
        traditiona: true,
        contentType:"application/json;charset=utf-8",
        data: JSON.stringify(json),
        success: function (data) {
        }
    });
    document.getElementById("addModal").style.display="none";
}
function editMovie(){
    var mId=$("#mId").text();
    var addmodel=document.getElementById("addModal");
    $.ajax({
        type : 'GET',
        dataType : 'json',
        url: "/movie/detail",
        data:{mId:mId},
        success: function (data) {
            var movieDetail=data.data;
            alert(movieDetail);
            alert(movieDetail.hname);
            var previewDom = document.getElementById("preview");

            var mName=document.getElementById('mName');
            var cate=document.getElementById('cate');
            var duration=document.getElementById('duration');
            var actor=document.getElementById('actor');
            var description= document.getElementById('description');
            var price =document.getElementById('price');
            var showTime =document.getElementById('showTime');
            var endTime =document.getElementById('endTime');
            mName.value=movieDetail["mName"];
            description=movieDetail["description"];
            price=movieDetail["price"];
            showTime.value=movieDetail["showTime"];
            endTime.value=movieDetail["endTime"];
            description=movieDetail["description"];
            price=movieDetail["price"];
            addmodel.style.display="block";


        }
    });
}
window.onload=function ()
{
    var fileDom = document.getElementById("file");
    var previewDom = document.getElementById("preview");
    fileDom.addEventListener("change", function () {
        var file = fileDom.files[0];
        // check if input contains a valid image file
        if (!file || file.type.indexOf("image/") < 0) {
            fileDom.value = "";
            previewDom.src = "";
            return;
        }

        // use FileReader to load image and show preview of the image
        var fileReader = new FileReader();
        fileReader.onload = function (e) {
            previewDom.src = e.target.result;
        };
        fileReader.readAsDataURL(file);
    });

    var formDom = document.getElementById("form");

    function check() {
        var file = fileDom.files[0];
        // check if input contains a valid image file
        if (!file || file.type.indexOf("image/") < 0) {
            return false;
        }
        return true;
    }

};
function addSchedule() {
    var fCount=$("#fCount").val();
    var sCount=$("#sCount").val();
    var tCount=$("#tCount").val();
    var screenCate=$("#screenCate").val();
    var sprice=$("#sprice").val();
    var mId=$("#mId").text();

    var json={
        fCount:fCount,
        sCount:sCount,
        tCount:tCount,
        price:sprice,
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
        }
    });
    document.getElementById("addSchedualModal").style.display="none";
}
function deleteSchedule() {
    var scId=$("#scId").text();
    $.ajax({
        dataType:'json',
        type: "post",
        url: "/schedule/delete",//向后端请求数据的url
        traditiona: true,
        contentType:"application/json;charset=utf-8",
        data:{scId:scId},
        success: function (data) {
        }
    });
}
