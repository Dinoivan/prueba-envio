package com.incloud.hcp.servlet;

//import com.sap.ecm.api.AbstractCmisProxyServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrador on 25/09/2017.
 */
//@Component
public class CMISProxyServlet {//extends AbstractCmisProxyServlet {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String repositoryName;
    private String repositoryKey;

    public CMISProxyServlet(String repositoryName, String repositoryKey) {
        super();
        this.repositoryName = repositoryName;
        this.repositoryKey = repositoryKey;
    }

    /*@Override
    protected String getRepositoryUniqueName() {
        logger.debug("Return repositoryUniqueName " + repositoryName);
        return repositoryName;
    }

    @Override
    protected String getRepositoryKey() {
        logger.debug("Return repositoryKey " + repositoryKey);
        return repositoryKey;
    }

    @Override
    protected boolean supportAtomPubBinding() {
        return false;
    }

    @Override
    protected boolean supportBrowserBinding() {
        return true;
    }*/
}
