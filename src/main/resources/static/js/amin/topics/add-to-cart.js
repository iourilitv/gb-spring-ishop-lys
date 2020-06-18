var stompClient = null;

/* обновляет страницу без ее перезагрузки */
window.onload = connect();

// function connect() {
//     /* ВНИМАНИЕ! Нужно обязательно добавлять префикс /amin
//     из параметра server.servlet.context-path=/amin в application.properties */
//     var socket = new SockJS('/amin/add-to-cart');
//     stompClient = Stomp.over(socket);
//     stompClient.connect({}, function(frame) {
//         console.log('Connected: ' + frame);
//         stompClient.subscribe('/topic/add-to-cart', function(response){
//             showResponse(JSON.parse(response.body).content);
//         });
//     });
// }
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

function addToCart() {
    // получаем значение атрибута value в элементе a(кнопке Add To Cart) в catalog.html
    var prodId = document.getElementById("addToCartBtn").getAttribute("value");
    stompClient.send("/app/add-to-cart", {}, JSON.stringify({ 'param': prodId }));
}

function addToCartByProdId(prodId) {
    stompClient.send("/app/add-to-cart", {}, JSON.stringify({ 'param': prodId }));
}

// function showResponse(content) {
//     console.log(content);
//     // меняем значение атрибута text в элементе a(кнопке Add To Cart) в catalog.html
//     // получается текст ссылки-кнопки = Add To Cart({quantity})
//     document.getElementById("addToCartBtn").innerText="Add To Cart(" + content + ")";
// }
function showResponse(prodId, quantity, cartItemsQuantity) {
    console.log(prodId, quantity, cartItemsQuantity);
    // меняем значение атрибута text в элементе a(кнопке Add To Cart) в catalog.html
    // получается текст ссылки-кнопки = Add To Cart({quantity})
    document.getElementById(prodId).innerText="Add To Cart(" + quantity + ")";
    document.getElementById("cart").innerText="Cart(" + cartItemsQuantity + ")";
}