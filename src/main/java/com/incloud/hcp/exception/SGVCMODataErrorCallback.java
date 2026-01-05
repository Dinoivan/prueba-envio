package com.incloud.hcp.exception;

import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.exception.ODataApplicationException;
import org.apache.olingo.odata2.api.processor.ODataErrorCallback;
import org.apache.olingo.odata2.api.processor.ODataErrorContext;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by George on 03/07/2017.
 */
public class SGVCMODataErrorCallback implements ODataErrorCallback {
    private final Logger LOG = LoggerFactory.getLogger(SGVCMODataErrorCallback.class.getSimpleName());
    @Override
    public ODataResponse handleError(ODataErrorContext oDataErrorContext) throws ODataApplicationException {
        LOG.error(oDataErrorContext.getException().getClass().getName() +
                ":" + oDataErrorContext.getMessage());
        return EntityProvider.writeErrorDocument(oDataErrorContext);
    }
}
