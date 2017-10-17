package org.wso2.carbon.das.jobmanager.core;

import org.wso2.msf4j.MicroservicesRunner;

/**
 * Application entry point.
 *
 * @since 1.0.0-SNAPSHOT
 */
public class Application {
    public static void main(String[] args) {
        new MicroservicesRunner()
                .deploy(new ResourceManagerApi())
                .start();
    }
}
