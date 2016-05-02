# rainbow-rest-sample
This is a sample site for https://github.com/alexeytokar/rainbow-rest

# build and run
````shell
$ ./gradlew build
$ java -jar build/lib/rainbow-rest-sample-*.jar
````

# verify
initial request is:
````shell
$ curl -s "http://localhost:8080" | python -mjson.tool
{
    "element": {
        "href": "/otherresource",
        "rel": "subelement"
    },
    "ic": {
        "baz": "test2",
        "foo": "test1",
        "id": "14"
    },
    "id": 42,
    "name": "asd"
}
````

let's filter some fields out:
````shell
$ curl -s "http://localhost:8080?fields=id" | python -mjson.tool
{
    "id": 42
}

$ curl -s "http://localhost:8080?fields=id,ic,foo" | python -mjson.tool
{
    "ic": {
        "foo": "test1",
        "id": "14"
    },
    "id": 42
}
````

another example is extending our flat model with "element" attribute:
````shell
$ curl -s "http://localhost:8080?include=element" | python -mjson.tool
{
    "element": {
        "element": {
            "href": "/otherresource",
            "rel": "subelement"
        },
        "ic": {
            "baz": "test2",
            "foo": "test1",
            "id": "14"
        },
        "id": 42,
        "name": "asd"
    },
    "ic": {
        "baz": "test2",
        "foo": "test1",
        "id": "14"
    },
    "id": 42,
    "name": "asd"
}
````

and now it is time to combine previous solutions:
````shell
$ curl -s "http://localhost:8080?include=element,element.element&fields=id,element" | python -mjson.tool
{
    "element": {
        "element": {
            "element": {},
            "id": 42
        },
        "id": 42
    },
    "id": 42
}
````

#batch processing
````shell
curl -s -X POST
    --url http://localhost:8080/batch
    --header 'accept: application/json'
    --header 'content-type: application/json'
    --data '{
        "root" : "/",
        "other" : "/otherresource?include=element&fields=id,element,name"
    }' | python -mjson.tool
{
    "other": {
        "element": {
            "element": {},
            "id": 42,
            "name": "asd"
        },
        "id": 42,
        "name": "asd"
    },
    "root": {
        "element": {
            "href": "/otherresource",
            "rel": "subelement"
        },
        "ic": {
            "baz": "test2",
            "foo": "test1",
            "id": "14"
        },
        "id": 42,
        "name": "asd"
    }
}
````