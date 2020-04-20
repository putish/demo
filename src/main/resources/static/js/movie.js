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

}
