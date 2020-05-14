var mId;
function deletemovie(e) {
   mId=e.name;
    $.ajax({
        dataType:'json',
        type: "post",
        url: "/movie/delete",    //向后端请求数据的url
        data:{mId:mId},
        success: function (data) {
            window.location.reload();
        },
        error:function(xhr,state,errorThrown){
            alert("删除失败");
        }
    });
    mId=null;
}
function toeditMovie() {

    var poster=$("#file").val();
    var mName=$("#mName").val();
    var catergorys=$("#catergory").val();
    var duration=$("#duration").val();
    var actor=$("#actor").val();
    var description=$("#description").val();
    var showTime=$("#showTime").val();
    var endTime=$("#endTime").val();
    var director=$("#director").val();
    var catergory;
    for(var i=0;i<catergorys.length;i++){
        if(i==0){
            catergory=catergorys[i];
        }
        else {
            catergory=catergory+","+catergorys[i];
        }
    }

    var json={
        mId:mId,
        poster:poster,
        mName:mName,
        catergory:catergory,
        duration:duration,
        actor:actor,
        description:description,
        director:director,
        showTime:showTime,
        endTime:endTime
    };
    $.ajax({
        dataType:'json',
        type: "POST",
        url: "/movie/edit",//向后端请求数据的url
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
    $('#addModal').modal('hide');
    mId=null;
}
function toaddMovie() {
    var poster=$("#file").val();
    var mName=$("#mName").val();
    var catergorys=$("#catergory").val();
    var duration=$("#duration").val();
    var actor=$("#actor").val();
    var description=$("#description").val();
    var showTime=$("#showTime").val();
    var endTime=$("#endTime").val();
    var director=$("#director").val();
    var catergory;
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
        director:director,
        showTime:showTime,
        endTime:endTime
    };
    $.ajax({
        dataType:'json',
        type: "post",
        url: "/movie/add",//向后端请求数据的url
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
function editMovie(e){
    var addModel=document.getElementById("addModal");
    addModel.style.display="block";
    mId=e.name;
    addMovie();

    $.ajax({
        type : 'GET',
        dataType : 'json',
        url: "/movie/detail",
        data:{mId:mId},
        success: function (data) {
            var movieDetail=data.data;
            if(movieDetail["showState"]!=4) {//影片下映后不可编辑
                var previewDom = document.getElementById("preview");
                var mName = document.getElementById('mName');
                var duration = document.getElementById('duration');
                var actor = document.getElementById('actor');
                var director = document.getElementById('director');
                var description = document.getElementById('description');
                var showTime = document.getElementById('showTime');
                var endTime = document.getElementById('endTime');
                mName.value = movieDetail["mname"];
                description.value = movieDetail["description"];
                showTime.value = movieDetail["showTime"];
                endTime.value = movieDetail["endTime"];
                duration.value=movieDetail["duration"];
                actor.value=movieDetail["actor"];
                previewDom.src = movieDetail["poster"];
                director.value= movieDetail["director"];
                document.getElementById("editMovie").style.display="block";
                document.getElementById("createMovie").style.display="none";
                var catergory;
                for(var i=0;i<catergorys.length;i++){
                    if(i==0){
                        catergory=catergorys[i];
                    }
                    else {
                        catergory=catergory+","+catergorys[i];
                    }
                }
                if (movieDetail["showState"] == 3) {
                    showTime.attr("readonly", "true");//上映后上映时间不可编辑
                }

            }else{
                alert("影片下映后不可编辑");
            }
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
    document.getElementById("addModalClose").onclick = function () {
        var addModal = document.getElementById("addModal");
        addModal.style.display = "none";
    };

    $(function () {
        var picker1 = $('#showTime').datetimepicker({
            format: 'YYYY-MM-DD',
            locale: moment.locale('zh-cn'),
            //minDate: '2016-7-1'
        });
        var picker2 = $('#endTime').datetimepicker({
            format: 'YYYY-MM-DD',
            locale: moment.locale('zh-cn')
        });
        //动态设置最小值
        picker1.on('dp.change', function (e) {
            picker2.data('DateTimePicker').minDate(e.date);
        });
        //动态设置最大值
        picker2.on('dp.change', function (e) {
            picker1.data('DateTimePicker').maxDate(e.date);
        });
    });

};

function addMovie(){
    var addModel=document.getElementById("addModal");
    addModel.style.display="block";
}


