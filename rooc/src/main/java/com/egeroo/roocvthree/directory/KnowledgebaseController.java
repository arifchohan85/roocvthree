package com.egeroo.roocvthree.directory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/kb")
public class KnowledgebaseController {

    @Autowired
    private DirectoryService service;

    /*start v3*/
    @RequestMapping(method= RequestMethod.GET,value="/tree")
    public List<DirectoryIntentv3> getDirectoryintent(@RequestHeader HttpHeaders headers) {
        List<DirectoryIntentv3> result = service.getDirectoryintentv3(headers.get("tenantID").get(0));
        return result;
    }
    /*end v3*/
}
