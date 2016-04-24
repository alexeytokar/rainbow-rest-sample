package ua.net.tokar.json.filters;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController {
    @RequestMapping("/")
    public DummyModel index() {
        return new DummyModel();
    }

    @RequestMapping("/otherresource" )
    public DummyModel otherresource() {
        return new DummyModel();
    }
}
