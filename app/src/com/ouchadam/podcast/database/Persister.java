package com.ouchadam.podcast.database;

import android.content.ContentProviderOperation;

import java.util.List;

public class Persister<T> {

    private final ContentProviderOperationExecutable executor;
    private final Marshaller<T, List<ContentProviderOperation>> marshaller;

    public Persister(ContentProviderOperationExecutable executor, Marshaller<T, List<ContentProviderOperation>> marshaller) {
        this.executor = executor;
        this.marshaller = marshaller;
    }

    public void persist(T what) {
        try {
            executor.execute(marshaller.marshall(what));
        } catch (Executable.ExecutionFailure executionFailure) {
            executionFailure.printStackTrace();
        }
    }
}