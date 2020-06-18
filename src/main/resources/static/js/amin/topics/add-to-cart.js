var stompClient = null;

/* обновляет страницу без ее перезагрузки */
window.onload = connect();

function connect() {
    /* ВНИМАНИЕ! Нужно обязательно добавлять префикс /amin
    из параметра server.servlet.context-path=/amin в application.properties */
    var socket = new SockJS('/amin/add-to-cart');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/add-to-cart', function(response){
            showResponse(JSON.parse(response.body).prodId,
                JSON.parse(response.body).quantity,
                JSON.parse(response.body).cartItemsQuantity);
        });
    });
}

function addToCartByProdId(prodId) {
    stompClient.send("/app/add-to-cart", {}, JSON.stringify({ 'param': prodId }));
}

function showResponse(prodId, quantity, cartItemsQuantity) {
    console.log(prodId, quantity, cartItemsQuantity);
    // меняем значение атрибута text в элементе a(кнопке Add To Cart) в catalog.html
    // получается текст ссылки-кнопки = Add To Cart({quantity})
    document.getElementById(prodId).innerText="Add To Cart(" + quantity + ")";
    document.getElementById("cart").innerText="Cart(" + cartItemsQuantity + ")";
}