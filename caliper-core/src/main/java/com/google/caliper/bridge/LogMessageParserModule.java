/*
 * Copyright (C) 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.caliper.bridge;

import com.google.caliper.util.Parser;
import dagger.Binds;
import dagger.Module;

/** Binds {@link Parser Parser&lt;LogMessage&gt;} to {@link LogMessageParser}. */
@Module
public abstract class LogMessageParserModule {
  @Binds
  abstract Parser<LogMessage> provideLogMessageParser(LogMessageParser parser);
}
