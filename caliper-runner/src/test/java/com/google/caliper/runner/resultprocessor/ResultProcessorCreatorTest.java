/*
 * Copyright (C) 2015 Google Inc.
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

package com.google.caliper.runner.resultprocessor;

import static com.google.caliper.runner.resultprocessor.ResultProcessorCreator.NO_VALID_CONSTRUCTOR;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.google.caliper.api.ResultProcessor;
import com.google.caliper.core.UserCodeException;
import com.google.caliper.model.Trial;
import com.google.caliper.runner.config.ResultProcessorConfig;
import java.io.IOException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/** Unit test to ensure that {@link ResultProcessorCreator} works properly. */
@RunWith(JUnit4.class)
public class ResultProcessorCreatorTest {

  private static ResultProcessor createProcessor(Class<? extends ResultProcessor> processorClass) {
    return ResultProcessorCreator.createResultProcessor(processorClass, config(processorClass));
  }

  private static ResultProcessorConfig config(Class<? extends ResultProcessor> processorClass) {
    return new ResultProcessorConfig.Builder()
        .className(processorClass.getName())
        .build();
  }

  @Test
  public void testNotPublicConstructor() {
    try {
      createProcessor(NoPublicConstructorResultProcessor.class);
      fail("Did not fail on non-public constructor");
    } catch (UserCodeException e) {
      assertEquals(
          String.format(NO_VALID_CONSTRUCTOR, NoPublicConstructorResultProcessor.class),
          e.getMessage());
    }
  }

  public static class NoPublicConstructorResultProcessor implements ResultProcessor {

    NoPublicConstructorResultProcessor() {}

    @Override
    public void processTrial(Trial trial) {}

    @Override
    public void close() throws IOException {}
  }

  @Test
  public void testPublicButNotDefaultConstructor() {
    try {
      createProcessor(PublicButNotDefaultDefaultConstructorResultProcessor.class);
      fail("Did not fail on public but not default constructor");
    } catch (UserCodeException e) {
      assertEquals(
          String.format(
              NO_VALID_CONSTRUCTOR, PublicButNotDefaultDefaultConstructorResultProcessor.class),
          e.getMessage());
    }
  }

  public static class PublicButNotDefaultDefaultConstructorResultProcessor
      implements ResultProcessor {

    public PublicButNotDefaultDefaultConstructorResultProcessor(
        @SuppressWarnings("UnusedParameters") int i) {}

    @Override
    public void processTrial(Trial trial) {}

    @Override
    public void close() throws IOException {}
  }

  @Test
  public void testPublicConstructor() {
    ResultProcessor processor = createProcessor(PublicDefaultConstructorResultProcessor.class);
    assertTrue(processor instanceof PublicDefaultConstructorResultProcessor);
  }

  public static class PublicDefaultConstructorResultProcessor implements ResultProcessor {

    @Override
    public void processTrial(Trial trial) {}

    @Override
    public void close() throws IOException {}
  }

  @Test
  public void testNotPublicConfigConstructor() {
    try {
      createProcessor(NoPublicConfigConstructorResultProcessor.class);
      fail("Did not fail on non-public constructor");
    } catch (UserCodeException e) {
      assertEquals(
          String.format(NO_VALID_CONSTRUCTOR, NoPublicConfigConstructorResultProcessor.class),
          e.getMessage());
    }
  }

  public static class NoPublicConfigConstructorResultProcessor implements ResultProcessor {

    NoPublicConfigConstructorResultProcessor(ResultProcessorConfig config) {}

    @Override
    public void processTrial(Trial trial) {}

    @Override
    public void close() throws IOException {}
  }

  @Test
  public void testPublicConfigConstructor() {
    ResultProcessor processor = createProcessor(PublicConfigConstructorResultProcessor.class);
    assertTrue(processor instanceof PublicConfigConstructorResultProcessor);
  }

  public static class PublicConfigConstructorResultProcessor implements ResultProcessor {

    public PublicConfigConstructorResultProcessor(ResultProcessorConfig config) {}

    @Override
    public void processTrial(Trial trial) {}

    @Override
    public void close() throws IOException {}
  }
}
