package com.project.singk.domain.album.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GenreType {
    BALLAD("발라드"),
    POP("팝"),
    HIPHOP("힙합"),
    RNB("알앤비"),
    CLASSIC("클래식");

    private final String name;
}
