package ua.net.tokar.json.filters;

public class DummyModel {
    public String name = "asd";
    public Integer id =  42;
    public InnerClass ic = new InnerClass();
    public Link element = new Link();
}

class InnerClass {
    public String foo = "test1";
    public String baz = "test2";
    public String id = "14";
}

class Link {
    public String href = "/otherresource";
    public String rel = "subelement";
}