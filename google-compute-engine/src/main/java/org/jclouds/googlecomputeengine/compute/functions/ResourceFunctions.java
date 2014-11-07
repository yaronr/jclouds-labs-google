/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jclouds.googlecomputeengine.compute.functions;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.jclouds.Fallbacks.NullOnNotFoundOr404;
import static org.jclouds.googlecomputeengine.GoogleComputeEngineConstants.COMPUTE_READONLY_SCOPE;

import java.net.URI;

import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;

import org.jclouds.googlecomputeengine.domain.Instance;
import org.jclouds.googlecomputeengine.domain.Operation;
import org.jclouds.javax.annotation.Nullable;
import org.jclouds.oauth.v2.config.OAuthScopes;
import org.jclouds.oauth.v2.filters.OAuthAuthenticationFilter;
import org.jclouds.rest.annotations.EndpointParam;
import org.jclouds.rest.annotations.Fallback;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.SkipEncoding;

@SkipEncoding({'/', '='})
@RequestFilters(OAuthAuthenticationFilter.class)
@Consumes(APPLICATION_JSON)
public interface ResourceFunctions {

   /** Returns an instance by self-link or null if not found. */
   @Named("Instances:get")
   @GET
   @OAuthScopes(COMPUTE_READONLY_SCOPE)
   @Fallback(NullOnNotFoundOr404.class) @Nullable Instance instance(@EndpointParam URI selfLink);

   /** Returns an operation by self-link or null if not found. */
   @Named("Operations:get")
   @GET
   @OAuthScopes(COMPUTE_READONLY_SCOPE)
   @Fallback(NullOnNotFoundOr404.class) @Nullable Operation operation(@EndpointParam URI selfLink);
}