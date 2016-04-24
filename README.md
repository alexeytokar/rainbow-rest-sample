# rainbow-rest-sample

# build and run
````
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

another eample is extending our flat model with "element" attribute:
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