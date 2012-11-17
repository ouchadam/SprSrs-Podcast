package com.ouchadam.podcast.parser.interfaces;

import com.ouchadam.podcast.util.Message;

import java.util.List;

public interface OnParseFinished {
    void onParseFinished(List<Message> messages);
}
