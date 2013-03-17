package com.ouchadam.podcast.database;

import android.content.*;
import android.os.RemoteException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContentProviderOperationExecutable implements
        Executable<List<ContentProviderResult>, List<ContentProviderOperation>> {

    private final ContentResolver resolver;

    public ContentProviderOperationExecutable(ContentResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public List<ContentProviderResult> execute(List<ContentProviderOperation> what) throws ExecutionFailure {
        ContentProviderClient client = null;
        try {
            client = getContentProviderClient();
            return Arrays.asList(client.applyBatch(new ArrayList<ContentProviderOperation>(what)));
        } catch (OperationApplicationException e) {
            throw new Executable.ExecutionFailure(e);
        } catch (RemoteException e) {
            throw new Executable.ExecutionFailure(e);
        } finally {
            if (client != null) {
                client.release();
            }
        }
    }

    private ContentProviderClient getContentProviderClient() {
        return resolver.acquireContentProviderClient(SprSrsProvider.AUTHORITY);
    }

}