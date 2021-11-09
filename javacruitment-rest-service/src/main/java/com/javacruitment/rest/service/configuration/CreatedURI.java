package com.javacruitment.rest.service.configuration;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public final class CreatedURI {

    public static URI uri(String path) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path(path)
                .build()
                .toUri();
    }

}
