var Foo = Java.type("org.tadamski.examples.js.Foo");
var ConstraintViolationException = Java.type("javax.validation.ConstraintViolationException");
var NoResultException = Java.type("javax.persistence.NoResultException");

$undertow
    .onGet("/hello",
    {headers: {"content-type": "text/plain"}},
    [ function ($exchange) {
        return "Hello World!";
    }])
    .wrapper(['jndi:java:comp/UserTransaction', function($exchange, $next, ut) {
        try {
            ut.begin();
            $next();
            ut.commit();
        } catch (e) {
            ut.rollback();
            throw e;
        }
    }])
    .wrapper([function ($exchange, $next) {
        try {
            $next();
        } catch (e) {
            if (e instanceof ConstraintViolationException) {
                var results = {};
                var constraintViolations = Java.from(e.constraintViolations);
                for (i in  constraintViolations) {
                    var cv = constraintViolations[i];
                    results[cv.propertyPath] = cv.message;
                }
                $exchange.send(400, JSON.stringify(results));
            } else {
                $exchange.send(400, JSON.stringify({"error": e.message}));
            }
            throw e;
        }
    }])
    .onGet("/rest/foos", {headers: {"content-type": "application/json"}}, ['cdi:fooRepository', function ($exchange, fooRepository) {
        return JSON.stringify(fooRepository.findAllOrderedByName());
    }])
    .onGet("/rest/foos/{id}", {headers: {"content-type": "application/json"}},  ['cdi:fooRepository', function ($exchange, fooRepository) {
        var member = fooRepository.findById($exchange.param('id'));
        if (member == null) {
            $exchange.status(404);
        } else {
            $exchange.responseHeaders("content-type", "application/json");
            return JSON.stringify(member);
        }
    }])
    .onPost("/rest/foos", ['$entity:json', 'cdi:fooRepository', 'cdi:validator',  function ($exchange, json, fooRepository, validator) {
        var foo = $undertow.toJava(Foo, json);
        var violations = validator.validate(foo);
        if (!violations.empty) {
            throw new ConstraintViolationException(violations);
        }
        fooRepository.save(foo)
    }])