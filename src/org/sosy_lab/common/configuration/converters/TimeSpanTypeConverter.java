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
package org.sosy_lab.common.configuration.converters;

import com.google.common.base.CharMatcher;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.reflect.TypeToken;
import java.lang.annotation.Annotation;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;
import org.sosy_lab.common.configuration.InvalidConfigurationException;
import org.sosy_lab.common.configuration.TimeSpanOption;
import org.sosy_lab.common.log.LogManager;
import org.sosy_lab.common.time.TimeSpan;

/** Type converter for options annotated with {@link TimeSpanOption}. */
public class TimeSpanTypeConverter implements TypeConverter {

  private static final BiMap<String, TimeUnit> TIME_UNITS =
      ImmutableBiMap.of(
          "ns", TimeUnit.NANOSECONDS,
          "ms", TimeUnit.MILLISECONDS,
          "s", TimeUnit.SECONDS,
          "min", TimeUnit.MINUTES,
          "h", TimeUnit.HOURS);

  @Override
  public Object convert(
      String optionName,
      String valueStr,
      TypeToken<?> pType,
      Annotation pOption,
      Path pSource,
      LogManager logger)
      throws InvalidConfigurationException {
    final Class<?> type = pType.getRawType();

    if (!(pOption instanceof TimeSpanOption)) {
      throw new UnsupportedOperationException(
          "Time span options need to be annotated with @TimeSpanOption");
    }
    TimeSpanOption option = (TimeSpanOption) pOption;

    // find unit in input string
    int i = valueStr.length() - 1;
    CharMatcher letterMatcher = CharMatcher.javaLetter();
    while (i >= 0 && letterMatcher.matches(valueStr.charAt(i))) {
      i--;
    }
    if (i < 0) {
      throw new InvalidConfigurationException("Option " + optionName + " contains no number");
    }

    // convert unit string to TimeUnit
    TimeUnit userUnit = TIME_UNITS.get(valueStr.substring(i + 1));
    if (userUnit == null) {
      userUnit = option.defaultUserUnit();
    }

    // Parse string without unit
    long rawValue = Long.parseLong(valueStr.substring(0, i + 1).trim());

    // convert value from user unit to code unit
    TimeUnit codeUnit = option.codeUnit();
    long value = codeUnit.convert(rawValue, userUnit);

    if (option.min() > value || value > option.max()) {
      String codeUnitStr = TIME_UNITS.inverse().get(codeUnit);
      throw new InvalidConfigurationException(
          String.format(
              "Invalid value in configuration file: \"%s = %s (not in range [%d %s, %d %s])",
              optionName,
              value,
              option.min(),
              codeUnitStr,
              option.max(),
              codeUnitStr));
    }

    Object result;

    if (type.equals(Integer.class)) {
      if (value > Integer.MAX_VALUE) {
        throw new InvalidConfigurationException(
            "Value for option " + optionName + " is larger than " + Integer.MAX_VALUE);
      }
      result = (int) value;
    } else if (type.equals(Long.class)) {
      result = value;
    } else {
      assert type.equals(TimeSpan.class);
      result = TimeSpan.of(rawValue, userUnit);
    }
    return result;
  }

  @Override
  public <T> T convertDefaultValue(
      String pOptionName, T pValue, TypeToken<T> pType, Annotation pSecondaryOption) {

    return pValue;
  }
}
