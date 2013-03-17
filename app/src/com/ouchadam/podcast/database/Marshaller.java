package com.ouchadam.podcast.database;

public interface Marshaller<From, To> {
    To marshall(From what);
}