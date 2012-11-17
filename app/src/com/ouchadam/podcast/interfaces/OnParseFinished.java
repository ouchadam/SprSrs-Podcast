package com.ouchadam.podcast.interfaces;

import uk.co.ouch.adam.util.Message;

import java.util.List;

public interface OnParseFinished {
    void onParseFinished(List<Message> messages);
}
