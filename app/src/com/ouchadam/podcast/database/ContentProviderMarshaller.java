package com.ouchadam.podcast.database;

import android.content.ContentProviderOperation;

import java.util.List;

public interface ContentProviderMarshaller<F> extends Marshaller<F, List<ContentProviderOperation>> {
}
