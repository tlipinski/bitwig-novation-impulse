package net.tlipinski.bitwig.controller;

import com.bitwig.extension.callback.NoArgsCallback;
import com.bitwig.extension.controller.api.ControllerHost;

class SendTemplateCallback implements NoArgsCallback {

    public SendTemplateCallback(ControllerHost host, SysexSend sysexSend) {
        this.host = host;
        this.sysexSend = sysexSend;
    }

    @Override
    public void call() {
        sysexSend.sendTemplate(new Template().toSysexString());
        host.showPopupNotification("Template sent. Confirm changes on your Impulse with Save button.");
    }

    private final ControllerHost host;
    private final SysexSend sysexSend;
}
