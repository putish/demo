function unsubscribe(e) {
    var oId=e.name;
    var oStatus=$("#otatus").val;
    if (oStatus!="支付完成"){
        alert("只有支付完成状态可退订")
    }
    else {
        $.ajax({
            dataType: 'json',
            type: "post",
            url: "/orders/unsubscribe",    //向后端请求数据的url
            data: {oId: oId},
            success: function (data) {
                window.location.reload();

            }, error: function (xhr, state, errorThrown) {
                alert("退订失败");
            }
        });
    }

};
function payOrders(e) {
    var oId=e.name;
    var oStatus=$("#otatus").val;
    if (oStatus=="退订"||oStatus=="超时"||oStatus=="支付完成"){
        alert("只有预订状态可支付")
    }
    else{
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

