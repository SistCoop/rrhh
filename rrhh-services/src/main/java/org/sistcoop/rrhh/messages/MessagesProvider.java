package org.sistcoop.rrhh.messages;

import org.sistcoop.rrhh.provider.Provider;

/**
 * @author <a href="mailto:leonardo.zanivan@gmail.com">Leonardo Zanivan</a>
 */
public interface MessagesProvider extends Provider {

    String getMessage(String messageKey, Object... parameters);

}
