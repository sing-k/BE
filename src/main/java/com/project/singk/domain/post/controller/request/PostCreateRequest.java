package com.project.singk.domain.post.controller.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class PostCreateRequest {
    private String title;
    private String content;
}
