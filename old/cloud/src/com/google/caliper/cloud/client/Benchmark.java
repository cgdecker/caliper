/**
 * Copyright (C) 2009 Google Inc.
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


package com.google.caliper.cloud.client;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public final class Benchmark
    implements Serializable /* for GWT Serialization */ {

  private /*final*/ String owner;
  private /*final*/ String name;
  private /*final*/ List<RunMeta> runs;
  private /*final*/ List<String> rVariables;
  private /*final*/ String cVariable;
  private /*final*/ Map<String, Map<String, Boolean>> variableValuesShown;

  public Benchmark(String owner, String name, List<RunMeta> runs, List<String> rVariables,
      String cVariable, Map<String, Map<String, Boolean>> variableValuesShown) {
    this.owner = owner;
    this.name = name;
    this.runs = runs;
    this.rVariables = rVariables;
    this.cVariable = cVariable;
    this.variableValuesShown = variableValuesShown;
  }

  public String getOwner() {
    return owner;
  }

  public String getName() {
    return name;
  }

  public List<RunMeta> getRuns() {
    return runs;
  }

  public List<String> getRVariables() {
    return rVariables;
  }

  public String getCVariable() {
    return cVariable;
  }

  public Map<String, Map<String, Boolean>> getVariableValuesShown() {
    return variableValuesShown;
  }

  private Benchmark() {} // for GWT Serialization
}
