/**
 * Copyright 2015 Netflix, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package io.rsocket.demo;

import com.google.common.collect.Lists;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.JdkLoggerFactory;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LoggingUtil {
  private static List<java.util.logging.Logger> activeLoggers = Lists.newArrayList();

  public static void configureLogging(boolean debug) {
    InternalLoggerFactory.setDefaultFactory(JdkLoggerFactory.INSTANCE);

    LogManager.getLogManager().reset();

    Logger activeLogger = getLogger("");
    ConsoleHandler handler = new ConsoleHandler();
    handler.setLevel(Level.ALL);
    handler.setFormatter(new OneLineLogFormat());
    activeLogger.addHandler(handler);

    if (debug) {
      getLogger("").setLevel(Level.INFO);
      getLogger("io.netty").setLevel(Level.INFO);
      getLogger("io.reactivex").setLevel(Level.FINE);
      getLogger("io.rsocket").setLevel(Level.FINEST);
      getLogger("reactor.ipc.netty").setLevel(Level.FINEST);
    } else {
      getLogger("").setLevel(Level.SEVERE);
      getLogger("io.netty").setLevel(Level.SEVERE);
      getLogger("io.reactivex").setLevel(Level.SEVERE);
      getLogger("io.rsocket").setLevel(Level.SEVERE);
    }
  }

  public static java.util.logging.Logger getLogger(String name) {
    java.util.logging.Logger logger = java.util.logging.Logger.getLogger(name);
    activeLoggers.add(logger);
    return logger;
  }
}
