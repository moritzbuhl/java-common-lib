/*
 *  SoSy-Lab Common is a library of useful utilities.
 *  This file is part of SoSy-Lab Common.
 *
 *  Copyright (C) 2007-2015  Dirk Beyer
 *  All rights reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.sosy_lab.common.collect;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.Iterators;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import javax.annotation.Nullable;

/**
 * Implementation of {@link NavigableSet} to be used as the key set of a {@link NavigableMap}.
 *
 * <p>This implementation forwards all methods to the underlying map.
 */
@SuppressFBWarnings(
    value = "NP_PARAMETER_MUST_BE_NONNULL_BUT_MARKED_AS_NULLABLE",
    justification = "nullability depends on underlying map")
final class SortedMapKeySet<K> extends AbstractSet<K> implements NavigableSet<K>, Serializable {

  private static final long serialVersionUID = 8196534519512074110L;

  private final OurSortedMap<K, ?> map;

  SortedMapKeySet(OurSortedMap<K, ?> pMap) {
    map = checkNotNull(pMap);
  }

  @Override
  public Iterator<K> iterator() {
    return Iterators.transform(map.entryIterator(), Map.Entry::getKey);
  }

  @Override
  public boolean equals(Object pO) {
    return Collections3.sortedSetEquals(this, pO);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public boolean contains(@Nullable Object pO) {
    return map.containsKey(pO);
  }

  @Override
  public boolean containsAll(Collection<?> pC) {
    return Collections3.sortedSetContainsAll(this, pC, null);
  }

  @Override
  public int size() {
    return map.size();
  }

  @Override
  public boolean isEmpty() {
    return map.isEmpty();
  }

  @Override
  public Comparator<? super K> comparator() {
    return map.comparator();
  }

  @Override
  public void clear() {
    map.clear();
  }

  @Override
  public boolean remove(@Nullable Object pO) {
    if (map.containsKey(pO)) {
      map.remove(pO);
      return true;
    }
    return false;
  }

  @Override
  public K first() {
    return map.firstKey();
  }

  @Override
  public K last() {
    return map.lastKey();
  }

  @Override
  public NavigableSet<K> subSet(@Nullable K pFromElement, @Nullable K pToElement) {
    return new SortedMapKeySet<>(map.subMap(pFromElement, pToElement));
  }

  @Override
  public NavigableSet<K> headSet(@Nullable K pToElement) {
    return new SortedMapKeySet<>(map.headMap(pToElement));
  }

  @Override
  public NavigableSet<K> tailSet(@Nullable K pFromElement) {
    return new SortedMapKeySet<>(map.tailMap(pFromElement));
  }

  @Override
  public K lower(@Nullable K pE) {
    return map.lowerKey(pE);
  }

  @Override
  public K floor(@Nullable K pE) {
    return map.floorKey(pE);
  }

  @Override
  public K ceiling(@Nullable K pE) {
    return map.ceilingKey(pE);
  }

  @Override
  public K higher(@Nullable K pE) {
    return map.higherKey(pE);
  }

  @Override
  public K pollFirst() {
    return map.pollFirstEntry().getKey();
  }

  @Override
  public K pollLast() {
    return map.pollLastEntry().getKey();
  }

  @Override
  public NavigableSet<K> descendingSet() {
    return map.descendingKeySet();
  }

  @Override
  public Iterator<K> descendingIterator() {
    return map.descendingKeySet().iterator();
  }

  @Override
  public NavigableSet<K> subSet(
      @Nullable K pFromElement,
      boolean pFromInclusive,
      @Nullable K pToElement,
      boolean pToInclusive) {
    return new SortedMapKeySet<>(
        map.subMap(pFromElement, pFromInclusive, pToElement, pToInclusive));
  }

  @Override
  public NavigableSet<K> headSet(@Nullable K pToElement, boolean pInclusive) {
    return new SortedMapKeySet<>(map.headMap(pToElement, pInclusive));
  }

  @Override
  public NavigableSet<K> tailSet(@Nullable K pFromElement, boolean pInclusive) {
    return new SortedMapKeySet<>(map.tailMap(pFromElement, pInclusive));
  }
}
