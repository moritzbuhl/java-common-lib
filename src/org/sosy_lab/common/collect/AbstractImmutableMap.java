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

import com.google.common.base.Joiner;
import com.google.errorprone.annotations.Immutable;
import java.util.Collection;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

@Immutable(containerOf = {"K", "V"})
abstract class AbstractImmutableMap<K, V> implements Map<K, V> {

  @Deprecated
  @Override
  public final void clear() {
    throw new UnsupportedOperationException();
  }

  @Deprecated
  @Override
  public final V put(K key, V value) {
    throw new UnsupportedOperationException();
  }

  @Deprecated
  @Override
  public final V putIfAbsent(K pKey, V pValue) {
    throw new UnsupportedOperationException();
  }

  @Deprecated
  @Override
  public final void putAll(Map<? extends K, ? extends V> pM) {
    throw new UnsupportedOperationException();
  }

  @Deprecated
  @Override
  public final V remove(Object pKey) {
    throw new UnsupportedOperationException();
  }

  @Deprecated
  @Override
  public final boolean remove(Object pKey, Object pValue) {
    throw new UnsupportedOperationException();
  }

  @Deprecated
  @Override
  public final V replace(K pKey, V pValue) {
    throw new UnsupportedOperationException();
  }

  @Deprecated
  @Override
  public final boolean replace(K pKey, V pOldValue, V pNewValue) {
    throw new UnsupportedOperationException();
  }

  @Deprecated
  @Override
  public final V compute(K pKey, BiFunction<? super K, ? super V, ? extends V> pRemappingFunction) {
    throw new UnsupportedOperationException();
  }

  @Deprecated
  @Override
  public final V computeIfAbsent(K pKey, Function<? super K, ? extends V> pMappingFunction) {
    throw new UnsupportedOperationException();
  }

  @Deprecated
  @Override
  public final V computeIfPresent(
      K pKey, BiFunction<? super K, ? super V, ? extends V> pRemappingFunction) {
    throw new UnsupportedOperationException();
  }

  @Deprecated
  @Override
  public final V merge(
      K pKey, V pValue, BiFunction<? super V, ? super V, ? extends V> pRemappingFunction) {
    throw new UnsupportedOperationException();
  }

  @Deprecated
  @Override
  public final void replaceAll(BiFunction<? super K, ? super V, ? extends V> pFunction) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean containsValue(Object pValue) {
    return values().contains(pValue);
  }

  @Override
  public Collection<V> values() {
    return new MapValues<>(this);
  }

  @Override
  public String toString() {
    if (isEmpty()) {
      return "{}";
    }
    StringBuilder sb = new StringBuilder();
    sb.append('{');
    Joiner.on(", ").withKeyValueSeparator("=").useForNull("null").appendTo(sb, this);
    sb.append('}');
    return sb.toString();
  }
}
