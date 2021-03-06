package me.smecsia.smartfox.testing;

import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;

/**
 * @author Ilya Sadykov
 *         Date: 21.09.12
 *         Time: 17:54
 */
public abstract class AbstractServerEventHandlerTest extends AbstractHandlerTest {

    protected ServerEvent eventOccurred(SFSEventType type) {
        return new ServerEvent(type);
    }

    protected void verifyWhen(BaseServerEventHandler handler, ServerEvent event, ResponseVerifier... verifiers) {
        try {
            for (ResponseVerifier verifier : verifiers) {
                if (verifier instanceof ServerEventResponseVerifier) {
                    ((ServerEventResponseVerifier) verifier).setEvent(event);
                }
            }
            handler.handleServerEvent(event.get());
            checkVerifiers(handler, verifiers);
        } catch (Exception e) {
            checkExceptionThrown(e, verifiers);
            return;
        }
        checkExceptionThrown(null, verifiers);
    }

    /**
     * Verify output against input
     */
    protected void verifyWhen(ServerEvent event, ResponseVerifier... verifiers) {
        verifyWhen((BaseServerEventHandler) createHandler(), event, verifiers);
    }
}
