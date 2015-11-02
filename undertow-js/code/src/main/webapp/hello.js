$undertow
    .onGet("/hello",
    {headers: {"content-type": "text/plain"}},
    ['cdi:bbean', function ($exchange, bean) {
        return "Hello World!";
    }]);