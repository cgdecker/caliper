/*
 * Copyright (C) 2013 Google Inc.
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
package com.google.caliper.worker;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multiset;
import java.util.List;

/**
 * Data about a particular allocation performed by a benchmark. This tracks a human readable
 * description of the allocation (e.g. 'int[23]', 'java.lang.Integer', or 'java.util.ArrayList'),
 * the total size of the allocation in bytes and the location, which is a stringified stack trace of
 * the allocation.
 */
final class Allocation {

  /** Returns the sum of the {@link #size sizes} of the allocations. */
  static long getTotalSize(Multiset<Allocation> allocations) {
    long totalSize = 0;
    for (Multiset.Entry<Allocation> allocation : allocations.entrySet()) {
      totalSize += allocation.getElement().size * allocation.getCount();
    }
    return totalSize;
  }

  private final String description;
  private final long size;
  private final ImmutableList<String> location;

  Allocation(String description, long size, List<StackTraceElement> location) {
    this.description = description.intern();
    this.size = size;
    // While particular lists of STEs can have a lot of variety within a benchmark there don't tend
    // to be many individually unique STEs.  This can save a lot of memory.
    // Within a benchmark the code paths should be fairly uniform so it should be safe to just store
    // these forever.
    ImmutableList.Builder<String> locationBuilder = ImmutableList.builder();
    for (StackTraceElement ste : location) {
      locationBuilder.add(ste.toString().intern());
    }
    this.location = locationBuilder.build();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Allocation) {
      Allocation other = (Allocation) obj;
      return other.description.equals(description)
          && other.size == size
          && other.location.equals(location);
    }
    return false;
  }

  public String getDescription() {
    return description;
  }

  public long getSize() {
    return size;
  }

  public ImmutableList<String> getLocation() {
    return location;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(description, size, location);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(description).append(" (").append(size).append(" bytes)\n\tat ");
    Joiner.on("\n\tat ").appendTo(builder, location);
    return builder.toString();
  }
}
