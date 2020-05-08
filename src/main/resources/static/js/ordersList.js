function unsubscribe(e) {
    var oId=e.name;
    $.ajax({
        dataType:'json',
        type: "post",
        url: "/orders/unsubscribe",    //向后端请求数据的url
        data:{oId:oId},
        success: function (data) {
            window.location.reload();

        },error:function(xhr,state,errorThrown){
            alert("退订失败");
        }
    });

};
function payOrders(e) {
    var oId=e.name;
    $.ajax({
        dataType:'json',
        type: "post",
        url: "/orders/pay",    //向后端请求数据的url
        data:{oId:oId},
        success: function (data) {
            window.location.reload();
        },
    error:function(xhr,state,errorThrown){
        alert("支付失败");
    }
    });

}
function deleteDetail(e) {
    var odId=e.name;
    $.ajax({
        dataType:'json',
        type: "post",
        url: "/orderDetail/delete",    //向后端请求数据的url
        data:{odId:odId},
        success: function (data) {
            window.location.reload();
        },
        error:function(xhr,state,errorThrown){
            alert("删除失败");
        }
    });

}

