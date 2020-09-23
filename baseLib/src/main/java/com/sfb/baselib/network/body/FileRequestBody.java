package com.sfb.baselib.network.body;

import com.sfb.baselib.utils.LogUtils;

import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.NonNull;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 * 上传文件的body
 */
public class FileRequestBody extends RequestBody {

    /**
     * okio.Segment.SIZE
     */
    private static final int SEGMENT_SIZE = 8192;

    private final InputStream inputStream;
    private final UploadProgressListener listener;
    private long mBytesRead;
    private long mBytesTotal;

    public FileRequestBody(InputStream inputStream, long totalBytes, long lastCompletedBytes, @NonNull UploadProgressListener uploadProgressListener) {
        this.inputStream = inputStream;
        this.listener = uploadProgressListener;
        this.mBytesRead = lastCompletedBytes;
        this.mBytesTotal = totalBytes;
    }

    @Override
    public long contentLength() {
        try {
            return mBytesTotal - mBytesRead;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public MediaType contentType() {
        return MediaType.parse("application/octet-stream");
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        Source source = null;
        try {
            source = Okio.source(inputStream);
            long read = 0;
            while ((read = source.read(sink.buffer(), SEGMENT_SIZE)) != -1 && !listener.isPause()) {
                mBytesRead = mBytesRead + read;
                sink.flush();
                this.listener.transferred(mBytesRead);
            }
        } catch (Exception e) {
            LogUtils.i("upload", "error:" + e.getMessage());
        } finally {
            Util.closeQuietly(source);
            Util.closeQuietly(inputStream);
        }
    }

    public interface UploadProgressListener {
        /**
         * 已传输大小回调
         *
         * @param uploadSize 已读大小
         */
        void transferred(long uploadSize);

        /**
         * 是否暂停
         *
         * @return 上传是否暂停
         */
        boolean isPause();
    }
}
