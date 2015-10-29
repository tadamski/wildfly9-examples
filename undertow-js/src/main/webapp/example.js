$undertow
    .onGet("/rest/hello",
    {headers: {"content-type": "application/json"}},
    [ function ($exchange) {
        return {message: 'Hello World!!!'};
    }])
    .onGet("/hello",
    {headers: {"content-type": "text/plain"}},
    ['cdi:bbean', function ($exchange, bean) {
        return bean.sayHello();
    }]);