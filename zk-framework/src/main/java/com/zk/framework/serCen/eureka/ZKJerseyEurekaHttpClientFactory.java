/** 
* Copyright (c) 2004-2020 ZK-Vinson Technologies, Inc.
* address: 
* All rights reserved. 
* 
* This software is the confidential and proprietary information of 
* ZK-Vinson Technologies, Inc. ("Confidential Information"). You shall not 
* disclose such Confidential Information and shall use it only in 
* accordance with the terms of the license agreement you entered into 
* with ZK-Vinson. 
*
* @Title: ZKJerseyEurekaHttpClientFactory.java 
* @author Vinson 
* @Package com.zk.framework.serCen.eureka 
* @Description: TODO(simple description this file what to do.) 
* @date May 3, 2020 8:30:46 AM 
* @version V1.0 
*/
package com.zk.framework.serCen.eureka;

import static com.netflix.discovery.util.DiscoveryBuildInfo.buildVersion;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SchemeSocketFactory;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.CoreProtocolPNames;

import com.netflix.appinfo.AbstractEurekaIdentity;
import com.netflix.appinfo.EurekaAccept;
import com.netflix.appinfo.EurekaClientIdentity;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClientConfig;
import com.netflix.discovery.EurekaIdentityHeaderFilter;
import com.netflix.discovery.provider.DiscoveryJerseyProvider;
import com.netflix.discovery.shared.resolver.EurekaEndpoint;
import com.netflix.discovery.shared.transport.EurekaClientFactoryBuilder;
import com.netflix.discovery.shared.transport.EurekaHttpClient;
import com.netflix.discovery.shared.transport.jersey.EurekaJerseyClient;
import com.netflix.discovery.shared.transport.jersey.EurekaJerseyClientImpl.EurekaJerseyClientBuilder;
import com.netflix.discovery.shared.transport.jersey.JerseyEurekaHttpClientFactory;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.filter.ClientFilter;
import com.sun.jersey.api.client.filter.GZIPContentEncodingFilter;
import com.sun.jersey.client.apache4.ApacheHttpClient4;
import com.sun.jersey.client.apache4.config.ApacheHttpClient4Config;
import com.sun.jersey.client.apache4.config.DefaultApacheHttpClient4Config;
import com.zk.framework.serCen.ZKSerCenEncrypt;

/** 
* @ClassName: ZKJerseyEurekaHttpClientFactory 
* @Description: TODO(simple description this class what to do.) 
* @author Vinson 
* @version 1.0 
*/
@SuppressWarnings("deprecation")
public class ZKJerseyEurekaHttpClientFactory extends JerseyEurekaHttpClientFactory {

    private final ApacheHttpClient4 apacheClient;

    private final Map<String, String> additionalHeaders;

    private ZKSerCenEncrypt zkSerCenEncrypt;

    @Deprecated
    public ZKJerseyEurekaHttpClientFactory(ZKSerCenEncrypt zkSerCenEncrypt, EurekaJerseyClient jerseyClient,
            Map<String, String> additionalHeaders) {
        super(jerseyClient, additionalHeaders);
        this.apacheClient = jerseyClient.getClient();
        this.additionalHeaders = additionalHeaders;
        this.zkSerCenEncrypt = zkSerCenEncrypt;
    }

    public ZKJerseyEurekaHttpClientFactory(ZKSerCenEncrypt zkSerCenEncrypt, ApacheHttpClient4 apacheClient,
            long connectionIdleTimeout, Map<String, String> additionalHeaders) {
        super(apacheClient, connectionIdleTimeout, additionalHeaders);
        this.apacheClient = apacheClient;
        this.additionalHeaders = additionalHeaders;
        this.zkSerCenEncrypt = zkSerCenEncrypt;
    }

    @Override
    public EurekaHttpClient newClient(EurekaEndpoint endpoint) {
        return new ZKJerseyApplicationClient(zkSerCenEncrypt, apacheClient, endpoint.getServiceUrl(),
                additionalHeaders);
    }

    public static ZKJerseyEurekaHttpClientFactory create(ZKSerCenEncrypt zkSerCenEncrypt,
            EurekaClientConfig clientConfig,
            Collection<ClientFilter> additionalFilters, InstanceInfo myInstanceInfo,
            AbstractEurekaIdentity clientIdentity, Optional<SSLContext> sslContext,
            Optional<HostnameVerifier> hostnameVerifier) {
        boolean useExperimental = "true"
                .equals(clientConfig.getExperimental("JerseyEurekaHttpClientFactory.useNewBuilder"));

        ZKJerseyEurekaHttpClientFactoryBuilder clientBuilder = (useExperimental ? myExperimentalBuilder(zkSerCenEncrypt)
                : myNewBuilder(zkSerCenEncrypt)).withAdditionalFilters(additionalFilters)
                        .withMyInstanceInfo(myInstanceInfo)
                        .withUserAgent("Java-EurekaClient").withClientConfig(clientConfig)
                        .withClientIdentity(clientIdentity);

        sslContext.ifPresent(clientBuilder::withSSLContext);
        hostnameVerifier.ifPresent(clientBuilder::withHostnameVerifier);

        if ("true".equals(System.getProperty("com.netflix.eureka.shouldSSLConnectionsUseSystemSocketFactory"))) {
            clientBuilder.withClientName("DiscoveryClient-HTTPClient-System").withSystemSSLConfiguration();
        }
        else if (clientConfig.getProxyHost() != null && clientConfig.getProxyPort() != null) {
            clientBuilder.withClientName("Proxy-DiscoveryClient-HTTPClient").withProxy(clientConfig.getProxyHost(),
                    Integer.parseInt(clientConfig.getProxyPort()), clientConfig.getProxyUserName(),
                    clientConfig.getProxyPassword());
        }
        else {
            clientBuilder.withClientName("DiscoveryClient-HTTPClient");
        }

        return clientBuilder.build();
    }

    public static ZKJerseyEurekaHttpClientFactoryBuilder myNewBuilder(ZKSerCenEncrypt zkSerCenEncrypt) {
        return new ZKJerseyEurekaHttpClientFactoryBuilder(zkSerCenEncrypt).withExperimental(false);
    }

    public static ZKJerseyEurekaHttpClientFactoryBuilder myExperimentalBuilder(ZKSerCenEncrypt zkSerCenEncrypt) {
        return new ZKJerseyEurekaHttpClientFactoryBuilder(zkSerCenEncrypt).withExperimental(true);
    }

    public static class ZKJerseyEurekaHttpClientFactoryBuilder extends
            EurekaClientFactoryBuilder<ZKJerseyEurekaHttpClientFactory, ZKJerseyEurekaHttpClientFactoryBuilder> {

        private Collection<ClientFilter> additionalFilters = Collections.emptyList();

        private boolean experimental = false;

        private ZKSerCenEncrypt zkSerCenEncrypt;

        public ZKJerseyEurekaHttpClientFactoryBuilder(ZKSerCenEncrypt zkSerCenEncrypt) {
            this.zkSerCenEncrypt = zkSerCenEncrypt;
        }

        public ZKJerseyEurekaHttpClientFactoryBuilder withAdditionalFilters(
                Collection<ClientFilter> additionalFilters) {
            this.additionalFilters = additionalFilters;
            return this;
        }

        public ZKJerseyEurekaHttpClientFactoryBuilder withExperimental(boolean experimental) {
            this.experimental = experimental;
            return this;
        }

        @Override
        public ZKJerseyEurekaHttpClientFactory build() {
            Map<String, String> additionalHeaders = new HashMap<>();
            if (allowRedirect) {
                additionalHeaders.put(HTTP_X_DISCOVERY_ALLOW_REDIRECT, "true");
            }
            if (EurekaAccept.compact == eurekaAccept) {
                additionalHeaders.put(EurekaAccept.HTTP_X_EUREKA_ACCEPT, eurekaAccept.name());
            }

            if (experimental) {
                return buildExperimental(additionalHeaders);
            }
            return buildLegacy(additionalHeaders, systemSSL);
        }

        private ZKJerseyEurekaHttpClientFactory buildLegacy(Map<String, String> additionalHeaders, boolean systemSSL) {

            EurekaJerseyClientBuilder clientBuilder = new EurekaJerseyClientBuilder().withClientName(clientName)
                    .withUserAgent("Java-EurekaClient").withConnectionTimeout(connectionTimeout)
                    .withReadTimeout(readTimeout).withMaxConnectionsPerHost(maxConnectionsPerHost)
                    .withMaxTotalConnections(maxTotalConnections).withConnectionIdleTimeout((int) connectionIdleTimeout)
                    .withEncoderWrapper(encoderWrapper).withDecoderWrapper(decoderWrapper)
                    .withProxy(proxyHost, String.valueOf(proxyPort), proxyUserName, proxyPassword);

            if (systemSSL) {
                clientBuilder.withSystemSSLConfiguration();
            }
            else if (sslContext != null) {
                clientBuilder.withCustomSSL(sslContext);
            }

            if (hostnameVerifier != null) {
                clientBuilder.withHostnameVerifier(hostnameVerifier);
            }

            EurekaJerseyClient jerseyClient = clientBuilder.build();
            ApacheHttpClient4 discoveryApacheClient = jerseyClient.getClient();
            addFilters(discoveryApacheClient);

            return new ZKJerseyEurekaHttpClientFactory(zkSerCenEncrypt, jerseyClient, additionalHeaders);
        }

        private ZKJerseyEurekaHttpClientFactory buildExperimental(Map<String, String> additionalHeaders) {
            ThreadSafeClientConnManager cm = createConnectionManager();
            ClientConfig clientConfig = new DefaultApacheHttpClient4Config();

            if (proxyHost != null) {
                addProxyConfiguration(clientConfig);
            }

            DiscoveryJerseyProvider discoveryJerseyProvider = new DiscoveryJerseyProvider(encoderWrapper,
                    decoderWrapper);
            clientConfig.getSingletons().add(discoveryJerseyProvider);

            // Common properties to all clients
            cm.setDefaultMaxPerRoute(maxConnectionsPerHost);
            cm.setMaxTotal(maxTotalConnections);
            clientConfig.getProperties().put(ApacheHttpClient4Config.PROPERTY_CONNECTION_MANAGER, cm);

            String fullUserAgentName = (userAgent == null ? clientName : userAgent) + "/v" + buildVersion();
            clientConfig.getProperties().put(CoreProtocolPNames.USER_AGENT, fullUserAgentName);

            // To pin a client to specific server in case redirect happens, we
            // handle redirects directly
            // (see DiscoveryClient.makeRemoteCall methods).
            clientConfig.getProperties().put(ClientConfig.PROPERTY_FOLLOW_REDIRECTS, Boolean.FALSE);
            clientConfig.getProperties().put(ClientPNames.HANDLE_REDIRECTS, Boolean.FALSE);

            ApacheHttpClient4 apacheClient = ApacheHttpClient4.create(clientConfig);
            addFilters(apacheClient);

            return new ZKJerseyEurekaHttpClientFactory(zkSerCenEncrypt, apacheClient, connectionIdleTimeout,
                    additionalHeaders);
        }

        /**
         * Since Jersey 1.19 depends on legacy apache http-client API, we have
         * to as well.
         */
        private ThreadSafeClientConnManager createConnectionManager() {
            try {
                ThreadSafeClientConnManager connectionManager;
                if (sslContext != null) {
                    SchemeSocketFactory socketFactory = new SSLSocketFactory(sslContext,
                            new AllowAllHostnameVerifier());
                    SchemeRegistry sslSchemeRegistry = new SchemeRegistry();
                    sslSchemeRegistry.register(new Scheme("https", 443, socketFactory));
                    connectionManager = new ThreadSafeClientConnManager(sslSchemeRegistry);
                }
                else {
                    connectionManager = new ThreadSafeClientConnManager();
                }
                return connectionManager;
            }
            catch(Exception e) {
                throw new IllegalStateException("Cannot initialize Apache connection manager", e);
            }
        }

        private void addProxyConfiguration(ClientConfig clientConfig) {
            if (proxyUserName != null && proxyPassword != null) {
                clientConfig.getProperties().put(ApacheHttpClient4Config.PROPERTY_PROXY_USERNAME, proxyUserName);
                clientConfig.getProperties().put(ApacheHttpClient4Config.PROPERTY_PROXY_PASSWORD, proxyPassword);
            }
            else {
                // Due to bug in apache client, user name/password must always
                // be set.
                // Otherwise proxy configuration is ignored.
                clientConfig.getProperties().put(ApacheHttpClient4Config.PROPERTY_PROXY_USERNAME, "guest");
                clientConfig.getProperties().put(ApacheHttpClient4Config.PROPERTY_PROXY_PASSWORD, "guest");
            }
            clientConfig.getProperties().put(DefaultApacheHttpClient4Config.PROPERTY_PROXY_URI,
                    "http://" + proxyHost + ':' + proxyPort);
        }

        private void addFilters(ApacheHttpClient4 discoveryApacheClient) {
            // Add gzip content encoding support
            discoveryApacheClient.addFilter(new GZIPContentEncodingFilter(false));

            // always enable client identity headers
            String ip = myInstanceInfo == null ? null : myInstanceInfo.getIPAddr();
            AbstractEurekaIdentity identity = clientIdentity == null ? new EurekaClientIdentity(ip) : clientIdentity;
            discoveryApacheClient.addFilter(new EurekaIdentityHeaderFilter(identity));

            if (additionalFilters != null) {
                for (ClientFilter filter : additionalFilters) {
                    if (filter != null) {
                        discoveryApacheClient.addFilter(filter);
                    }
                }
            }
        }
    }

}
