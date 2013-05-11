package com.ouchadam.podcast.parser;

public interface Parser<From, To> {
    To parse(From what);
}
