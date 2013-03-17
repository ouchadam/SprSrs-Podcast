package com.ouchadam.podcast.database;

public interface Executable<R, P> {
    R execute(P what) throws ExecutionFailure;

    class ExecutionFailure extends Exception {
        public ExecutionFailure(Exception wrapper) {
            super(wrapper);
        }
    }
}
