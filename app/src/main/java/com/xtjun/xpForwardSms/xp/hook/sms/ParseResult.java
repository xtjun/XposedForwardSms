package com.xtjun.xpForwardSms.xp.hook.sms;

class ParseResult {

    private boolean blockSms;

    ParseResult() {

    }

    boolean isBlockSms() {
        return blockSms;
    }

    void setBlockSms(boolean blockSms) {
        this.blockSms = blockSms;
    }
}
