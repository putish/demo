function unsubscribe() {
    var oId=$("#oId").text();
    $.ajax({
        dataType:'json',
        type: "post",
        url: "/orders/unsubscribe",    //向后端请求数据的url
        data:{oId:oId},
        success: function (data) {

        }
    });

};
function payOrders() {
    var oId=$("#oId").text();
    $.ajax({
        dataType:'json',
        type: "post",
        url: "/orders/pay",    //向后端请求数据的url
        data:{oId:oId},
        success: function (data) {

        }
    });

}
