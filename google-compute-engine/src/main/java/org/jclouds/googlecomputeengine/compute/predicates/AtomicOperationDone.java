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
package org.jclouds.googlecomputeengine.compute.predicates;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Inject;

import org.jclouds.googlecomputeengine.compute.functions.ResourceFunctions;
import org.jclouds.googlecomputeengine.domain.Operation;

import com.google.common.base.Predicate;

public final class AtomicOperationDone implements Predicate<AtomicReference<Operation>> {

   private final ResourceFunctions resources;

   @Inject AtomicOperationDone(ResourceFunctions resources) {
      this.resources = resources;
   }

   @Override public boolean apply(AtomicReference<Operation> input) {
      checkNotNull(input.get(), "operation");
      Operation current = resources.operation(input.get().selfLink());
      input.set(current);
      checkState(current.errors().isEmpty(), "Task ended in error %s", current); // ISE will break the loop.
      switch (current.status()) {
         case DONE:
            return true;
         case PENDING:
         case RUNNING:
         default:
            return false;
      }
   }
}